document.addEventListener('DOMContentLoaded', function () {
  document.getElementById('miFormulario').addEventListener('submit', function (e) {
      e.preventDefault();

      let nombre = document.getElementById('nombre').value;
      let apellido = document.getElementById('apellido').value;
      let matricula = document.getElementById('matricula').value;

      let datosOdontologo = {
          nombre: nombre,
          apellido: apellido,
          matricula: matricula
      };

      fetch('http://localhost:8081/odontologos/registrar', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(datosOdontologo)
      })
      .then(response => response.json())
      .then(data => {
      
          mostrarMensaje(data);
      })
      .catch(error => {
      
          console.error('Error al enviar la solicitud:', error);
          mostrarMensaje({ mensaje: 'Error en la solicitud al servidor', tipo: 'error' });
      });
  });


  function mostrarMensaje(data) {
    const mensajeContainer = document.getElementById('mensaje-container');

    if (data) {
        mensajeContainer.innerHTML = '';

        const mensajeElement = document.createElement('p');

        if (data.mensaje) {
            mensajeElement.textContent = data.mensaje;
        } else {
            mensajeElement.textContent = JSON.stringify(data);
        }

        if (data.tipo) {
            mensajeElement.classList.add(data.tipo);
        }

        mensajeContainer.appendChild(mensajeElement);

        setTimeout(() => {
            mensajeContainer.innerHTML = '';
        }, 5000);
    }
}
})























