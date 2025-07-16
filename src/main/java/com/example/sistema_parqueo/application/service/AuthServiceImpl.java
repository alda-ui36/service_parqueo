package com.example.sistema_parqueo.application.service;

import com.example.sistema_parqueo.application.dtos.mapper.AuthMapper;
import com.example.sistema_parqueo.application.dtos.request.LoginRequest;
import com.example.sistema_parqueo.application.dtos.response.AuthResponse;
import com.example.sistema_parqueo.domain.entity.Rol;
import com.example.sistema_parqueo.domain.entity.Usuario;
import com.example.sistema_parqueo.domain.port.input.IAuthService;
import com.example.sistema_parqueo.domain.port.output.IUsuarioRepository;
import com.example.sistema_parqueo.infrestucture.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthMapper authMapper;

    @Override
    public AuthResponse login(LoginRequest request) {
        var usuario = usuarioRepository.findByUsernameOrEmail(request.getLogin(),request.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("usuario o contraseña incorrecta"));
        if(usuario.getEstado() != null && !usuario.getEstado()){
            throw new IllegalArgumentException("Usuario inactivo");
        }
        if(!passwordEncoder.matches(request.getPassword(), usuario.getPassword())){
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        return buildAuthResponse(usuario);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new IllegalArgumentException("token invalido o ya expiro");
        }
        var claims = jwtUtils.getClaims(refreshToken);
        Integer userId = claims.get("userId",Integer.class);
        var usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("usuario no encontrado"));

        return buildAuthResponse(usuario);
    }

    private AuthResponse buildAuthResponse(Usuario usuario){
        var roles = usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet());
        var accessToken = jwtUtils.generateAccessToken(usuario.getId(),usuario.getUsername(),roles);
        var refreshToken = jwtUtils.generateRefreshToken(usuario.getId(),usuario.getUsername());
        return authMapper.toAuthResponse(usuario,accessToken,refreshToken);
    }
}
