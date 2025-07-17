package com.example.sistema_parqueo.application.service;

import com.example.sistema_parqueo.application.dtos.request.RecuperarPasswordRequest;
import com.example.sistema_parqueo.application.dtos.request.ResetPasswordRequest;
import com.example.sistema_parqueo.application.utils.EmailService;
import com.example.sistema_parqueo.domain.port.input.IPasswordResetService;
import com.example.sistema_parqueo.domain.port.output.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements IPasswordResetService {

    private  final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public void solicitarCodigo(RecuperarPasswordRequest request) {
        var usuario = usuarioRepository.findByUsernameOrEmail(request.getEmail(),request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email no encontrado"));
        String codigo = generarCodigo();
        usuario.setCodigoVerificacion(codigo);
        usuario.setExpiracionCodigo(LocalDateTime.now().plusMinutes(15));
        usuarioRepository.save(usuario);
        emailService.sendSimpleMessage(usuario.getEmail() , "Recuperación de Contraseña",
                buildEmailContent(codigo));

    }

    @Override
    public void restablecerPassword(ResetPasswordRequest request) {
        var usuario = usuarioRepository.findByUsernameOrEmail(request.getEmail(),request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email no encontrado"));
        if(usuario.getCodigoVerificacion() == null || usuario.getExpiracionCodigo() == null || usuario.getExpiracionCodigo().isBefore(LocalDateTime.now())){
            throw  new IllegalArgumentException("codigo expirado o no encontrado");
        }
        if(!usuario.getCodigoVerificacion().equals(request.getCodigo())){
            throw new IllegalArgumentException("codigo incorrecto");
        }
        usuario.setPassword(passwordEncoder.encode(request.getNewPassword()));
        usuario.setCodigoVerificacion(null);
        usuario.setExpiracionCodigo(null);
        usuarioRepository.save(usuario);
    }

    private String generarCodigo(){
        return String.format("%06d", new Random().nextInt(1000000));
    }

    private String buildEmailContent(String code){
        return String.format("""
            <div style="max-width: 600px; margin: 0 auto; font-family: Arial, sans-serif; background-color: #f8f9fa; padding: 20px;">
                <div style="background-color: white; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); overflow: hidden;">
                    
                    <!-- Header -->
                    <div style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 30px; text-align: center;">
                        <h1 style="margin: 0; font-size: 24px; font-weight: bold;">🚗 Sistema de Parqueo</h1>
                        <p style="margin: 10px 0 0 0; opacity: 0.9;">Código de Acceso Generado</p>
                    </div>
                    
                    <!-- Content -->
                    <div style="padding: 40px 30px;">
                        <div style="text-align: center; margin-bottom: 30px;">
                            <h2 style="color: #333; margin-bottom: 15px;">Tu Código de Acceso</h2>
                            <p style="color: #666; font-size: 16px; line-height: 1.6;">
                                Utiliza el siguiente código para acceder al sistema de parqueo:
                            </p>
                        </div>
                        
                        <!-- Code Display -->
                        <div style="background-color: #f8f9fa; border: 2px dashed #667eea; border-radius: 8px; padding: 25px; text-align: center; margin: 30px 0;">
                            <div style="font-size: 32px; font-weight: bold; color: #667eea; letter-spacing: 3px; font-family: 'Courier New', monospace;">
                                %s
                            </div>
                        </div>
                        
                        <!-- Instructions -->
                        <div style="background-color: #e8f4f8; border-left: 4px solid #17a2b8; padding: 20px; margin: 25px 0; border-radius: 0 8px 8px 0;">
                            <h3 style="color: #17a2b8; margin-top: 0; font-size: 18px;">📋 Instrucciones de Uso:</h3>
                            <ul style="color: #333; line-height: 1.8; margin: 10px 0; padding-left: 20px;">
                                <li>Ingresa este código en el panel de acceso</li>
                                <li>El código es válido por tiempo limitado</li>
                                <li>No compartas este código con terceros</li>
                                <li>Conserva este email para futuras referencias</li>
                            </ul>
                        </div>
                        
                        <!-- Warning -->
                        <div style="background-color: #fff3cd; border: 1px solid #ffeaa7; border-radius: 6px; padding: 15px; margin: 20px 0;">
                            <p style="margin: 0; color: #856404; font-size: 14px; text-align: center;">
                                ⚠️ <strong>Importante:</strong> Este código es personal e intransferible
                            </p>
                        </div>
                    </div>
                    
                    <!-- Footer -->
                    <div style="background-color: #f8f9fa; padding: 20px; text-align: center; border-top: 1px solid #e9ecef;">
                        <p style="margin: 0; color: #6c757d; font-size: 14px;">
                            © %d Sistema de Parqueo - Todos los derechos reservados
                        </p>
                        <p style="margin: 10px 0 0 0; color: #6c757d; font-size: 12px;">
                            Este es un email automático, por favor no responder
                        </p>
                    </div>
                    
                </div>
            </div>
            """, code, Year.now().getValue());
    }
}
