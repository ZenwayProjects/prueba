package com.zenway.prueba.service;

import com.zenway.prueba.dto.DtoAsignacion;
import com.zenway.prueba.model.Rol;
import com.zenway.prueba.model.Usuario;
import com.zenway.prueba.repository.RolRepository;
import com.zenway.prueba.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AsignacionService {
    private UsuarioRepository usuarioRepo;
    private RolRepository rolRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AsignacionService(UsuarioRepository usuarioRepo, RolRepository rolRepo) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
    }

    public void asignarRol(DtoAsignacion dtoAsignacion) {
        // Crear un nuevo usuario con los datos del DTO
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(dtoAsignacion.getNombre());
        nuevoUsuario.setApellidos(dtoAsignacion.getApellido());
        nuevoUsuario.setLogin(dtoAsignacion.getLogin());
        nuevoUsuario.setPassword(passwordEncoder.encode(dtoAsignacion.getPassword()));
        nuevoUsuario.setNumeroDocumento(dtoAsignacion.getNumeroDocumento());

        // Obtener el rol "USUARIO_LOCAL" por su nombre
        Rol rolUsuarioLocal = rolRepo.findByRolNombre("USUARIO_LOCAL")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Asignar el rol "USUARIO_LOCAL" al nuevo usuario
        nuevoUsuario.setRoles(Collections.singletonList(rolUsuarioLocal));

        // Guardar el nuevo usuario en la base de datos
        usuarioRepo.save(nuevoUsuario);
    }

}
