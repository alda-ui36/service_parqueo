package com.example.sistema_parqueo.config;

import com.example.sistema_parqueo.domain.entity.Rol;
import com.example.sistema_parqueo.domain.entity.Usuario;
import com.example.sistema_parqueo.domain.port.output.IRolRepository;
import com.example.sistema_parqueo.domain.port.output.IUsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Configuration
public class InitAdminConfig {

    @Bean
    public CommandLineRunner initAdmin(IUsuarioRepository usuarioRepository, IRolRepository rolRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(usuarioRepository.count() == 0){
                List<Rol> roles = Stream.of("ADMIN","USER")
                        .map(nombre -> rolRepository.findByNombre(nombre)
                                .orElseGet(() -> rolRepository.save(Rol.builder().nombre(nombre).build())))
                        .toList();
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setEmail("shujaadev36@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Set.of(roles.get(0)));
                admin.setEstado(true);
                usuarioRepository.save(admin);
            }
        };
    }
}
