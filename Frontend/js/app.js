//makes the create project popup  visible


let usuarioID = localStorage.getItem("usuarioID");
console.log(usuarioID);

let proyectos = [];


/* Toggle between adding and removing the "responsive" class to topnav when the user clicks on the icon */
function navbarResponse() {
  var x = document.getElementById("myTopNavigationBar");
  if (x.className === "myTopNavigationBar") {
    x.className += " responsive";
  } else {
    x.className = "topnav";
  }
}
function showProjectTab() {
  console.log("Proyecto seleccionado");
  //TODO: retrieve project info
}

function loadApp() {
  proyectos = obtenerProyectosDeBackend();
  if (proyectos.length > 0) {
    cargarProyectos();
  }
}

async function obtenerProyectosDeBackend() {
  try {
    const response = await axios.get(
      "http://localhost:8081/api/proyectos/all",
      {
        params: {
          id: usuarioID,
        },
      }
    );
    proyectos = response["data"];
    //console.log(proyectos);
  } catch (error) {
    console.log(error);
    if (error.response) {
      // get response with a status code not in range 2xx
      console.log(error.response.data);
      console.log(error.response.status);
      console.log(error.response.headers);
    } else if (error.request) {
      // no response
      console.log(error.request);
    } else {
      // Something wrong in setting up the request
      console.log("Error", error.message);
    }
    console.log(error.config);
  }
  cargarProyectos();
}

async function registrarProyecto(
  nombreProyecto,
  motivacionProyecto,
  descripcionproyecto,
  fechaculminacionProyecto
) {
  try {
    const response = await axios.post(
      "http://localhost:8081/api/proyectos/create",
      {
        nombre: nombreProyecto.value,
        idUsuarios: usuarioID,
        motivacion: motivacionProyecto.value,
        idMeta: 2,
        descripcion: descripcionproyecto.value,
        fechaFin: fechaculminacionProyecto.value,
      }
    );
    //console.log(response);
  } catch (error) {
    console.log(error);
    if (error.response) {
      // get response with a status code not in range 2xx
      console.log(error.response.data);
      console.log(error.response.status);
      console.log(error.response.headers);
    } else if (error.request) {
      // no response
      console.log(error.request);
    } else {
      // Something wrong in setting up the request
      console.log("Error", error.message);
    }
    console.log(error.config);
  }
}

async function crearProyecto() {
  let nombreProyecto = formularioProyecto["nombreProyecto"];
  let motivacionProyecto = formularioProyecto["motivacionProyecto"];
  let descripcionproyecto = formularioProyecto["descripcionproyecto"];
  let fechaculminacionProyecto = formularioProyecto["fechaculminacionProyecto"];
  let respuesta = verificarCamposformularioProyecto(
    nombreProyecto,
    motivacionProyecto,
    descripcionproyecto,
    fechaculminacionProyecto
  );
  if (respuesta == true) {
    try {
      await registrarProyecto(
        nombreProyecto,
        motivacionProyecto,
        descripcionproyecto,
        fechaculminacionProyecto
      );
    } catch (e) {
      console.log(e.value);
    }

    alert("Proyecto creado");
    obtenerProyectosDeBackend();
    cargarProyectos();
  } else {
    alert("Campos vacíos");
  }

  // console.log(nombreProyecto.value);
  // console.log(motivacionProyecto.value);
  // console.log(descripcionproyecto.value);
  //console.log(fechaculminacionProyecto.value);
  document.querySelector(".popup").classList.remove("active");
}

function verificarCamposformularioProyecto(
  nombreProyecto,
  motivacionProyecto,
  descripcionproyecto,
  fechaculminacionProyecto
) {
  if (
    nombreProyecto.value == "" ||
    motivacionProyecto.value == "" ||
    descripcionproyecto.value == "" ||
    fechaculminacionProyecto.value == ""
  ) {
    return false;
  } else {
    return true;
  }
}
function abrirPopup(){
  document.querySelector(".popup").classList.add("active");
}
function cancelarCrearProyecto(){
  document.querySelector(".popup").classList.remove("active");
}

function cargarProyectos() {
  
  let proyectosHTML = "";
  console.log(proyectos);
  for (let proyecto of proyectos) {
    proyectosHTML += crearProyectoHTML(proyecto);
    console.log(proyecto);
  }
  proyectosHTML += `<button id="addproject" onClick="abrirPopup()">
  <i class="fa fa-plus-square addproject" aria-hidden="true"></i>
</button>`;
  document.getElementById("projectstab").innerHTML = proyectosHTML;
}
function crearProyectoHTML(proyecto) {
  let proyectoHTML = `
  <button >${proyecto.nombre}</button>
  `;
  return proyectoHTML;
}

