package com.cursojava.curso.dao;
import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //Va acceder al repositorio de la base de datos
@Transactional //Este sirve para tratar las consultas de mysql
public class usuarioDaoImp implements UsuarioDao{
    @PersistenceContext
    EntityManager entityManager; //Nos va a servir para hacer la conexión con la base de datos

    @Override
    public List getUsuarios() {
        //Esto hace referencia a la clase Usuario, despues de aquí va para la clase Usuario
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";

        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if(lista.isEmpty())return null;
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(lista.get(0).getPassword(), usuario.getPassword())){
            return lista.get(0);
        }

        return null;
    }
}
