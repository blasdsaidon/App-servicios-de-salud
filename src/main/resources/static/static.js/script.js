// Obtener los elementos del formulario
var editForm = document.getElementById('editForm');
var nombreInput = document.getElementById('edit-nombre');
var apellidoInput = document.getElementById('edit-apellido');
var generoInput = document.getElementById('edit-genero');
var dniInput = document.getElementById('edit-dni');
var imagenInput = document.getElementById('edit-imagen');

// Actualizar los datos del perfil con los valores del formulario
editForm.addEventListener('submit', function(event) {
  event.preventDefault(); // Evitar el envío del formulario

  // Obtener los valores de los campos
  var nombre = nombreInput.value;
  var apellido = apellidoInput.value;
  var genero = generoInput.value;
  var dni = dniInput.value;

  // Actualizar los elementos del perfil con los nuevos valores
  document.getElementById('nombre').textContent = nombre;
  document.getElementById('apellido').textContent = apellido;
  document.getElementById('genero').textContent = genero;
  document.getElementById('dni').textContent = dni;

  // Mostrar un mensaje de éxito
  alert('¡Cambios guardados correctamente!');
});
