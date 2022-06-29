// Call the dataTables jQuery plugin

$(document).ready(function() {//Esto nos dice que todo lo que este aquí adentro se ejecutará cuando entre a a la página
    //on ready
});


async function registrarUsuarios(){ //Para poder hacer uso del await necesitamos que la funcion sea asincronica
    let datos = {};
    datos.nombre = document.querySelector('#txtNombre').value;
    datos.apellido = document.querySelector('#txtApellido').value;
    datos.email = document.querySelector('#txtEmail').value;
    datos.password = document.querySelector('#txtPassword').value;

    let repetirPassword = document.querySelector('#txtRepetirPassword').value;

    if(datos.nombre == "" || datos.email == "" || datos.password == ""){
        alert('No puede dejar los campos con * vacíos')
        return;
    }

    if(repetirPassword != datos.password){
        alert('La contraseña es diferente')
        return;
    }

    //El siguiente código genera un JSON
    const request = await fetch('api/usuarios', { //El await me indica que se quedara esparando
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    alert("La cuenta fue creada con exito");
    window.location.href = "login.html"
}

