//makes the create project popup  visible

function createProjectPopup(){
  document.querySelector("#addproject").addEventListener("click",function(){
    document.querySelector(".popup").classList.add("active")
  });
}

//makes the create project popup not visible
document.querySelector(".popup .close-btn").addEventListener("click",function(){
  document.querySelector(".popup").classList.remove("active")
})

//makes the create project popup not visible pressing the "cancelar" button
document.querySelector(".popup .form div .cancelCreateProjectButton").addEventListener("click",function(){
  document.querySelector(".popup").classList.remove("active")
})
/* Toggle between adding and removing the "responsive" class to topnav when the user clicks on the icon */
function navbarResponse() {
    var x = document.getElementById("myTopnav");
    if (x.className === "topnav") {
      x.className += " responsive";
    } else {
      x.className = "topnav";
    }
  }
 function showProjectTab(){
   console.log("Proyecto seleccionado")
   //TODO: retrieve project info
 }

 function loadApp(){
   //TODO: execute all initial loads of the page, for now the projectstab
   loadProjects();
 }

 function loadProjects(){
   //TODO: retrieve the quantity and name of all created projects
   //search trough all the projects and list them in the projects tab
 }
 
 
  let formularioProyecto;
 function crearProyecto(){
   formularioProyecto = document.forms['formularioProyecto'];
   let nombreProyecto = formularioProyecto['nombreProyecto'];
   let motivacionProyecto = formularioProyecto['motivacionProyecto'];
   let descripcionproyecto = formularioProyecto['descripcionproyecto'];
   let fechaculminacionProyecto = formularioProyecto['fechaculminacionProyecto'];
   
   console.log(nombreProyecto.value);
   console.log(motivacionProyecto.value);
   console.log(descripcionproyecto.value);
   console.log(fechaculminacionProyecto.value);
 }
 

 function verificarCamposformularioProyecto(nombreProyecto,motivacionProyecto,descripcionproyecto,fechaculminacionProyecto){
   if (nombreProyecto =='' || motivacionProyecto =='' || descripcionproyecto =='' || fechaculminacionProyecto =='' ){
     return 'Campos Vacios'
   }
   //else if{
    
   //}
   
 }


 