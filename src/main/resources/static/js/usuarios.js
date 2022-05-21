// Call the dataTables jQuery plugin

$(document).ready(function() {//Esto nos dice que todo lo que este aquí adentro se ejecutará cuando entre a a la página
  cargarUsuarios();
  $('#usuarios').DataTable(); //Esta es una libreria que al pasarle una tabla nos da el formato para presentarla
    actualizarEmailDeUsuario();
});


function actualizarEmailDeUsuario(){
     document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}
async function cargarUsuarios(){ //Para poder hacer uso del await necesitamos que la funcion sea asincronica
    //El siguiente código genera un JSON
    const request = await fetch('api/usuarios', { //El await me indica que se quedara esparando
      method: 'GET',
      headers: getHeaders()
    });
    const usuarios = await request.json();

    //Agregamos todos los usuarios del JSON
    let listadoHtml = '';
    for(let usuario of usuarios){
        let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle"><i class="fas fa-trash"></i></a>';

        let telefonoTexto = usuario.telefono == null ? '-': usuario.telefono;
        let usuarioHtml  = '<tr><td>'+ usuario.id +'</td><td>'+ usuario.nombre +' '+  usuario.apellido   +'</td><td>'+ usuario.email
            +'</td><td>'+ telefonoTexto
            +'</td>\n<td>'+botonEliminar +'</td></tr>';
        listadoHtml += usuarioHtml;
    }
    console.log(usuarios);
    document.querySelector('#usuarios tbody').outerHTML = listadoHtml;
}
function getHeaders(){
    return{
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization' : localStorage.token
    }
}

async function eliminarUsuario(id){
    if(!confirm(' ¿Esta seguro de que quieres eliminar este usuario')){
        return;
    }

    const request = await fetch('api/usuarios/'+ id, {
        method: 'DELETE',
        headers: getHeaders()
    });

    location.reload();//Esto funciona para recargar la pagina
}