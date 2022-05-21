// Call the dataTables jQuery plugin

$(document).ready(function() {//Esto nos dice que todo lo que este aquí adentro se ejecutará cuando entre a a la página
    //on ready
});


async function iniciarSesion(){ //Para poder hacer uso del await necesitamos que la funcion sea asincronica
    let datos = {};
    datos.email = document.querySelector('#txtEmail').value;
    datos.password = document.querySelector('#txtPassword').value;

    //El siguiente código genera un JSON
    const request = await fetch('api/login', { //El await me indica que se quedara esparando
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    const response = await request.text();

    if(response != 'FAIL'){
        localStorage.token = response; //Aquí guardamos el JWT del lado del browser
        localStorage.email = datos.email;
        window.location.href = "usuarios.html"
    }else{
        alert("Las credenciales son incorrectas. Por favor intente de nuevo ");
    }
}
