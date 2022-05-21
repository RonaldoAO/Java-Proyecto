package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    //Inyeccion de dependencia
    @Autowired //Esto crea un objeto de la implemetación, en el resto del proyecto no tenemos que crear varios Autowired, por que sería un objeto compartido
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.GET)//Con los corchete lo vuelves una variable
    public Usuario getUsuario(@PathVariable Long id){//La varible de arriba la tomamos con esta Notación
        Usuario usuario = new Usuario(id, "Ronaldo", "Acevedo", "ronaldoa.ojeda@gmail.com", "123444");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)//Con los corchete lo vuelves una variable
    public List<Usuario> getUsuarios(@RequestHeader(value ="Authorization") String token){//La varible de arriba la tomamos con esta Notación
        if(!validarToken(token))return null;

        return usuarioDao.getUsuarios();
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)//Con los corchete lo vuelves una variable
    public void registrarUsuarios(@RequestBody Usuario usuario) {//Con esta anotación transforma un JSON a un usuario automaticamente

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());//El primer numero es el numero de iteraciones, entre mas tenga mas seguro será, y por lo mismo tardará mas
        //El tercer numero, indica el numero de hilos que ocupará, esto por si tenemos mas de una iteracion, podamos hacer mas rapido su procesamiento
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "usuario322")
    public Usuario editar(){
        Usuario usuario = new Usuario(1L, "Ronaldo", "Acevedo", "ronaldoa.ojeda@gmail.com", "123444");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value ="Authorization") String token, @PathVariable Long id){
        if(!validarToken(token))return;
        usuarioDao.eliminar(id);
    }


    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);//Aquí estamos sacando el id del token,porque cuando lo creamos lo creamos con el id y con el email
        return usuarioId != null;
    }
}
