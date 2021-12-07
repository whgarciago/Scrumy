//makes the create project popup  visible

document.querySelector("#addproject").addEventListener("click", function () {
  document.querySelector(".popup").classList.add("active");
});
let usuarioID = localStorage.getItem("usuarioID");
console.log(usuarioID);

//makes the create project popup not visible
document
  .querySelector(".popup .cancel-create-project-button")
  .addEventListener("click", function () {
    document.querySelector(".popup").classList.remove("active");
  });

//makes the create project popup not visible pressing the "cancelar" button
document
  .querySelector(".popup .form div .cancelCreateProjectButton")
  .addEventListener("click", function () {
    document.querySelector(".popup").classList.remove("active");
  });
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
  //TODO: execute all initial loads of the page, for now the projectstab
  loadProjects();
}

function loadProjects() {
  //TODO: retrieve the quantity and name of all created projects
  //search trough all the projects and list them in the projects tab
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
    console.log(response);
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

  await registrarProyecto(
    nombreProyecto,
    motivacionProyecto,
    descripcionproyecto,
    fechaculminacionProyecto
  );
  alert("Proyecto creado");

  // console.log(nombreProyecto.value);
  // console.log(motivacionProyecto.value);
  // console.log(descripcionproyecto.value);
  //console.log(fechaculminacionProyecto.value);
}

function verificarCamposformularioProyecto(
  nombreProyecto,
  motivacionProyecto,
  descripcionproyecto,
  fechaculminacionProyecto
) {
  if (
    nombreProyecto == "" ||
    motivacionProyecto == "" ||
    descripcionproyecto == "" ||
    fechaculminacionProyecto == ""
  ) {
    alert("Datos incompletos");
    return "Campos Vacios";
  }
  //else if{

  //}
}
