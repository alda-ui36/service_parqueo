package com.shujaa.parking_system.application.service;

import com.shujaa.parking_system.application.dtos.request.RecuperarPasswordRequest;
import com.shujaa.parking_system.application.dtos.request.ResetPasswordRequest;
import com.shujaa.parking_system.application.utils.EmailService;
import com.shujaa.parking_system.domain.port.input.IPasswordResetService;
import com.shujaa.parking_system.domain.port.output.IUsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetService implements IPasswordResetService {
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public void solicitarCodigo(RecuperarPasswordRequest request) {
        var usuario = usuarioRepository.findByUsernameOrEmail(request.getEmail(), request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email no registrado"));
        String codigo = generarCodigo();
        usuario.setCodigoVerificacion(codigo);
        usuario.setExpiracionCodigo(LocalDateTime.now().plusMinutes(15));
        usuarioRepository.save(usuario);
        emailService.sendSimpleMessage(usuario.getEmail(), "Código de recuperación de contraseña", buildEmailContent(codigo));
    }

    public void resetearPassword(ResetPasswordRequest request) {
        var usuario = usuarioRepository.findByUsernameOrEmail(request.getEmail(), request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (usuario.getCodigoVerificacion() == null || usuario.getExpiracionCodigo() == null || usuario.getExpiracionCodigo().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("El código ha expirado o no existe. Solicita uno nuevo.");
        }
        if (!usuario.getCodigoVerificacion().equals(request.getCodigo())) {
            throw new IllegalArgumentException("Código incorrecto");
        }
        usuario.setPassword(passwordEncoder.encode(request.getNuevaPassword()));
        usuario.setCodigoVerificacion(null);
        usuario.setExpiracionCodigo(null);
        usuarioRepository.save(usuario);
    }

    private String generarCodigo() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    private String buildEmailContent(String code) {
        return String.format("""
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
            <h2 style="color: #1e3a8a; text-align: center;">Código de verificación - Sistema de Parqueo</h2>
            <div style="background-color: #f1f5f9; padding: 20px; border-radius: 8px; border: 1px solid #d1d5db;">
                <p style="font-size: 16px; color: #374151;">Has solicitado un código para continuar con tu operación.</p>
                <p style="font-size: 16px; color: #374151;">Tu código de verificación es:</p>
                <h1 style="color: #1e3a8a; text-align: center; font-size: 36px; letter-spacing: 6px;">%s</h1>
                <p style="font-size: 14px; color: #6b7280; text-align: center;">Este código es válido por <strong>15 minutos</strong>.</p>
                <p style="font-size: 14px; color: #6b7280;">Si no realizaste esta solicitud, puedes ignorar este mensaje.</p>
            </div>
            <p style="font-size: 12px; color: #9ca3af; text-align: center; margin-top: 25px;">
                © %d Sistema de Parqueo. Todos los derechos reservados.
            </p>
        </div>
    """, code, Year.now().getValue());
    }
}