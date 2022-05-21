package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
* Esta clase es especial para el inicio de sesión
 */
@RestController
public class AuthController {

    @Autowired
    //Esto crea un objeto de la implemetación, en el resto del proyecto no tenemos que crear varios Autowired, por que sería un objeto compartido
    private UsuarioDao usuarioDao;

    @Autowired //Le ponemos autowired porque la clase tiene @Component
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)//Con los corchete lo vuelves una variable
    public String login(@RequestBody Usuario usuario){//Con esta anotación transforma un JSON a un usuario automaticamente

        Usuario usuarioLogeado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);
        if(usuarioLogeado != null){
            //Creamos el JWT
            String tokenJWT = jwtUtil.create(String.valueOf(usuarioLogeado.getId()), usuarioLogeado.getEmail());

            return tokenJWT;
        }
        return "FAIL";
    }
}
