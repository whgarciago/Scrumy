<template>
  <div class="d-flex flex-column vh-100 col-12 m-0 p-0 overflow-hidden Home">
    <nav class="navbar navbar-expand-lg navbar-dark navbar-color ">
      <a class="navbar-brand pl-5" href="#"
        ><img
          :src="logo_URL"
          alt="Logo de Pagina Scrumy"
          
          @click="goToHome()"
      /></a>
      <button
        class="navbar-toggler my-0 p-0"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mx-auto pl-5">
          <li
            class="nav-item active px-3 py-2 btn-navbar rounded"
            v-for="(nombre, indice) in nombres"
            :nombre="nombre"
            :indice="nombre.index"
            :key="nombre.id"
            @click="abrirComponente(indice)"
          >
            <a class="nav-link" href="#"
              ><h4>{{ nombre }}</h4><span class="sr-only">(current)</span></a
            >
          </li>
        </ul>
        <ul class="navbar-nav mr-4">
          <li class="nav-item dropdown mr-4 btn btn-navbar">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              {{ usuarioNombre }}
              <img src="../assets/icono_usuario.png" style="color:white;"/>
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" @click="goToSettings()"
                >Configuracion</a
              >
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" @click="cerrarSesion()" href="">Cerrar sesión</a>
              <div class="dropdown-divider"></div>
              <p class="text-center"><b>Temas</b></p>
              <a class="dropdown-item bg-dark text-light" @click="EstilosPredeterminados(1)" >Tema Oscuro</a>
              <a class="dropdown-item bg-primary text-light" @click="EstilosPredeterminados(2)">Tema Azul</a>
              <a class="dropdown-item bg-success text-light" @click="EstilosPredeterminados(3)">Tema Verde</a>
              <a class="dropdown-item bg-danger text-light" @click="EstilosPredeterminados(4)" >Tema Rojo</a>
              <a class="dropdown-item bg-info text-light" @click="EstilosPredeterminados(5)">Tema Claro</a>
              <div class="dropdown-divider"></div>
              <p class="text-center"><b>Fondos</b></p>
              <p>Color Superior</p>
              <div class="d-flex  ">
                <button class="btn b1" @click="FondosPredeterminados(1,'--color2')">A</button>
                <button class="btn b2" @click="FondosPredeterminados(2,'--color2')">B</button>
                <button class="btn b3" @click="FondosPredeterminados(3,'--color2')">C</button>
                <button class="btn b4" @click="FondosPredeterminados(4,'--color2')">D</button>
              </div><div>
                <button class="btn b5" @click="FondosPredeterminados(5,'--color2')">E</button>
                <button class="btn b6" @click="FondosPredeterminados(6,'--color2')">F</button>
                <button class="btn b7" @click="FondosPredeterminados(7,'--color2')">G</button>
                <button class="btn b8" @click="FondosPredeterminados(8,'--color2')">H</button>
              </div>
              <p>Color Inferior</p>
              <div class="d-flex  ">
                <button class="btn b1" @click="FondosPredeterminados(1,'--color1')">A</button>
                <button class="btn b2" @click="FondosPredeterminados(2,'--color1')">B</button>
                <button class="btn b3" @click="FondosPredeterminados(3,'--color1')">C</button>
                <button class="btn b4" @click="FondosPredeterminados(4,'--color1')">D</button>
              </div><div>
                <button class="btn b5" @click="FondosPredeterminados(5,'--color1')">E</button>
                <button class="btn b6" @click="FondosPredeterminados(6,'--color1')">F</button>
                <button class="btn b7" @click="FondosPredeterminados(7,'--color1')">G</button>
                <button class="btn b8" @click="FondosPredeterminados(8,'--color1')">H</button>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </nav>

    <div class="d-flex flex-row h-100  ">
      <div class="col-4 col-lg-2  m-0 p-0 pt-3 barraProyectos">
        <div class="col-12 mx-auto p-0 text-center ">
          
          <h3 class="text-light">Mis proyectos</h3>

          <button
            class="btn col-10 btn-crear-proyecto mt-3 mb-1"
            id="añadirproyecto"
            @click="abrirPopup()"
            data-bs-toggle="tooltip" data-bs-placement="right" title="Aqui puedes crear un nuevo proyecto"
          >
            <!--falta abrirPopup()-->
            Crear Proyecto
          </button>
          <hr class="hr-proyectos col-9" />

          <button
            class="btn btn-side-navbar my-1 col-10"
            v-for="proy in proyectos"
            :key="proy.id"
            @click="seleccionarProyecto(proy)"
            data-bs-toggle="tooltip" data-bs-placement="top" title="Haz click aquí para seleccionar este proyecto"
          >
            {{ proy.nombre }}
          </button>
          <hr class="hr-proyectos col-9" />
          <div class="text-light bg-dark p-2">
            <h5>Y Recuerda:</h5> 
            <p><i>{{Frases[Frase]}}</i></p>
          </div>
        </div>
      </div>
      <!--contenido-->

      <div
        class="d-flex flex-column h-100 col-8 col-lg-7 m-auto pt-3 content-color overflow-auto"
        v-if="proyectoActual.nombre != ''"
      >
        <div class="p-2">
          <div class="card">
            <div class="card-header d-flex">
              <h2 class="d-inline mx-auto">{{ proyectoActual.nombre }}</h2> 
              <button class="btn btn-cambio fs-6 mr-1 h-50 btn-sm" @click="abrirConfigProyecto()" data-bs-toggle="tooltip" data-bs-placement="left" title="Aqui puedes editar tu proyecto">
                <img class="img-fluid" src="../assets/config.png" /></button>
            </div>
            
            <div class="card-body text-center" data-bs-toggle="tooltip" data-bs-placement="top" title="Esta es la información de tu proyecto">
              <p class="card-text">
                <b class="text-dark">Fecha de finalización:</b>  {{ proyectoActual.fechaFin }} <b class="text-dark">Descripción:</b>  {{ proyectoActual.descripcion }}
              </p>
              <div class="card-text">
                <!--b class="text-dark"></b-->Recuerda que tu motivación es:
                <h4>{{ proyectoActual.motivacion }}</h4>
              </div>
            </div>
          </div>
        </div>
        <!--componente -->
        <div class="col-12 m-0 p-4 mt-4  componente-central">
          <router-view class=""></router-view>
        </div>
      </div>
    </div>
    <!--Div para desplegar los componentes de cada boton-->
    <!--Popup de crear nuevo proyecto-->
    <div class="popup">
      <form class="form" id="formularioProyecto" onsubmit="return false">
        <h2 id="tituloPopup">Crear Proyecto</h2>
        <input
          type="text"
          id="nombreProyecto"
          placeholder="Nombre"
          v-model="proyecto.nombre"
        />
        <input
          type="text"
          id="motivacionProyecto"
          placeholder="Motivación"
          v-model="proyecto.motivacion"
        />
        <input
          type="text"
          class="descripcion"
          id="descripcionproyecto"
          placeholder="Descripción"
          v-model="proyecto.descripcion"
        /><br />
        <label class="fecha-culminación text-light">Fecha de culminación</label>
        <input
          type="datetime-local"
          class="text-muted"
          v-model="proyecto.fechaFin"
          value="2022-01-29T19:30"
          min="2022-01-29T19:30"
          id="projectMinDate"
        /><br />
        <button class="CrearProyecto" @click="CrearProyecto(usuarioID)">
          Crear
        </button>
        <button class="cancelarCrearProyecto" @click="cerrarPopup()">
          Cancelar
        </button>
      </form>
    </div>

    <!--Ventana configuracion de proyecto -->
    <div class="ConfigProyecto">
      <form class="form" id="confproyecto" onsubmit="return false">
        <h2 class="tituloConfigProyecto">Configuracion de proyecto</h2>
        <input
          type="text"
          id="nombreProyecto"
          placeholder="Nombre"
          v-model="proyectoActual.nombre"
        />
        <input
          type="text"
          id="motivacionProyecto"
          placeholder="Motivación"
          v-model="proyectoActual.motivacion"
        />
        <input
          type="text"
          class="descripcion"
          id="descripcionproyecto"
          placeholder="Descripción"
          v-model="proyectoActual.descripcion"
        /><br />
        <label for="fecha-culminación text-light">Fecha de culminación</label>
        <input
          type="datetime-local"
          id="fechaculminacionProyecto"
          v-model="proyectoActual.fechaFin"
        /><br />
        <button class="CrearProyecto" @click="ModificarProyecto()">
          Guardar
        </button>
        <button class="cancelarCrearProyecto" @click="cerrarModificarProyecto()">
          Cancelar
        </button>
        <button class="EliminarProyecto" @click="EliminarProyecto()">
          <img src="https://img.icons8.com/material/32/000000/delete--v1.png" />
        </button>
      </form>
    </div>

    <!--Boton configuracion de proyecto
    <button id="botonConfigProyecto" @click="abrirConfigProyecto()">
    -->
  </div>
</template>

<script>
import axios from "axios";
import logo from "../assets/logo_S_48px.png";

export default {
  name: "Principal",
  data() {
    return {
      logo_URL: logo,
      usuarioID: localStorage.usuarioID, //trae el usuario del backend
      usuarioNombre: localStorage.usuarioNombre, //trae el nombre del usuario
      proyectoID: localStorage.proyectoID, //trae el ID del proyecto
      //nombres es un arreglo con los nombres de los 4 botones
      nombres: ["Metas", "Sprints", "Avance", "Actividades", "Retroalimentacion"],
      //ref es el arreglo que relaciona los botones con sus componentes
      ref: ["smallgoals", "sprints", "Plan", "Activities", "retroalimentacion"],
      //mesidenav es falso cuando el mouse no esta encima de los botones
      Frase: 0,
      //proyecto es el objeto base para manejar, se llena en el Popup de crear nuevo proyecto
      proyecto: {
        id: 0,
        usuarioID: 0,
        nombre: "",
        motivacion: "",
        descripcion: "",
        fechaFin: "",
      },
      //proyecto actual es el proyecto en el cual se ubica actualmente
      proyectoActual: {
        id: 0,
        usuarioID: "",
        nombre: "",
        motivacion: "",
        descripcion: "",
        fechaFin: "",
      },
      //proyectos traidos desde el backend
      proyectos: [],
      Frases:[
        "Un sueño no se hace realidad por arte de magia, necesita sudor, determinación y trabajo duro - Colin Powel",
        "A veces la adversidad es lo que necesitas encarar para ser exitoso - Zig Ziglar",
        "Para tener éxito tu deseo de alcanzarlo debe ser mayor a tu miedo al fracaso - Bill Cosby",
        "Tu actitud, no tu aptitud, determinará tu altitud - Zig Ziglar",
        "Siempre parece imposible... hasta que se hace - Nelson Mandela",
        "No cuentes los días, haz que los días cuenten - Muhammad Ali",
        "El mejor momento del día es ahora - Pierre Bonnard",
        "Con autodisciplina casi todo es posible - Theodore Roosevelt",
        "La mejor manera para empezar es callándote y empezar a hacer - Walt Disney",
        "Tienes que esperar cosas de ti mismo antes de poder hacerlas - Michael Jordan",
        "El secreto para salir adelante es comenzar - Mark Twain",
        "No pares cuando estés cansado. Para cuando hayas terminado - Marilyn Monroe",
        "El hombre que mueve montañas comienza cargando pequeñas piedras - Confucio",
        "El éxito es la suma de pequeños esfuerzos, que se repiten día tras día - Robert Collier",
        "La mejor forma de predecir el futuro es creándolo - Abraham Lincoln"
      ]
    };
  },

  methods: {
    cerrarSesion() {
      localStorage.clear()
      this.$router.push({ name: "loginform" });
    },
    Aleatorio(min, max) {
      return Math.floor(Math.random() * (max - min)) + min;
    },

    //abre el Popup de configuración de proyecto
    abrirConfigProyecto() {
      document.querySelector(".ConfigProyecto").classList.add("active");
    },
    //abre el componente central y carga su respectivo contenido según el boton presionado
    abrirComponente(index) {
      this.$router.push({ name: this.ref[index] }).catch((err) => {});
      this.Frase=this.Aleatorio(0,this.Frases.length)
    },
    //Abre el Popup para añadir un nuevo proyecto
    abrirPopup: function() {
      let dateInput = document.getElementById("projectMinDate");
      dateInput.min = new Date().toISOString().split(".")[0];
      const popup = document.querySelector(".popup").classList.add("active");
      document.getElementById("nombreProyecto").value = "";
      document.getElementById("motivacionProyecto").value = "";
      document.getElementById("descripcionproyecto").value = "";
      document.getElementById("fechaculminacionProyecto").value = "";
    },
    //Cierra el Popup para añadir un nuevo proyecto
    cerrarPopup: function() {
      document.querySelector(".popup").classList.remove("active");
    },
    //Cierra el Popup de configuración de proyecto
    cerrarModificarProyecto() {
      document.querySelector(".ConfigProyecto").classList.remove("active");
    },
    //Se ejecuta cuando se presiona el boton "crear" de añadir nuevo proyecto
    //toma los objetos del objeto "proyecto" y los guarda en el Backend
    async CrearProyecto(usuarioID) {
      const { nombre, motivacion, descripcion, fechaFin } = this.proyecto;

      try {
        const response = await axios.post(
          "http://localhost:8081/api/proyectos/create",
          {
            idUsuarios: usuarioID,
            nombre: nombre,
            motivacion: motivacion,
            fechaFin: fechaFin,
            descripcion: descripcion,
          }
        );
        console.log("se ejecuto funcion crear proyecto");
      } catch (error) {
        console.log(error);
        if (error.response) {
          // Respuesta con codigo fuera de rango
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          // No respuesta
          console.log(error.request);
        } else {
          // Error en el envío de petición
          console.log("Error", error.message);
        }
        console.log(error.config);
      }
      //Ejecuta obtenerProyectosDeBackend (linea 266)
      this.obtenerProyectosDeBackend();
      //Cierra el Popup de añadir nuevo proyecto (linea 145)
      this.cerrarPopup();
    },

    //Funcion para modificar los atributos de un proyecto
    async ModificarProyecto() {
      console.log(typeof this.proyectoActual.id);
      //Se guardan los parametros del formulario en el objeto proyecto actual
      const { nombre, motivacion, descripcion, fechaFin } = this.proyectoActual;
      try {
        const response = await axios.put(
          "http://localhost:8081/api/proyectos/update?id=" +
            this.proyectoActual.id,
          {
            idUsuarios: this.usuarioID,
            nombre: nombre,
            motivacion: motivacion,
            fechaFin: fechaFin,
            descripcion: descripcion,
          }
        );
        console.log(this.usuarioID);
        console.log(nombre);
        console.log("se ejecuto funcion modificar proyecto");
      } catch (error) {
        console.log(error);
        if (error.response) {
          // Respuesta con codigo fuera de rango
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          // No respuesta
          console.log(error.request);
        } else {
          // Error en el envío de petición
          console.log("Error", error.message);
        }
        console.log(error.config);
      }
      //Ejecuta obtenerProyectosDeBackend (linea 266)
      this.obtenerProyectosDeBackend();
      //Cierra el Popup de ModificarProyecto (linea 149)
      this.cerrarModificarProyecto();
    },
        //Funcion para modificar los atributos de un proyecto
    async EliminarProyecto() {
      console.log(typeof this.proyectoActual.id);
      //Se guardan los parametros del formulario en el objeto proyecto actual
      const { nombre, motivacion, descripcion, fechaFin } = this.proyectoActual;
      try {
        const response = await axios.delete(
          "http://localhost:8081/api/proyectos/remove?id=" +
            this.proyectoActual.id,
          {
            idUsuarios: this.usuarioID,
          }
        );
        console.log("se ejecuto funcion eliminar proyecto");
      } catch (error) {
        console.log(error);
        if (error.response) {
          // Respuesta con codigo fuera de rango
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          // No respuesta
          console.log(error.request);
        } else {
          // Error en el envío de petición
          console.log("Error", error.message);
        }
        console.log(error.config);
      }
      //Ejecuta obtenerProyectosDeBackend (linea 266)
      this.obtenerProyectosDeBackend();
      //Cierra el Popup de ModificarProyecto (linea 149)
      this.cerrarModificarProyecto();
      this.proyectoActual.id = 0;
      this.proyectoActual.usuarioID = "";
      this.proyectoActual.nombre = "";
      this.proyectoActual.motivacion = "";
      this.proyectoActual.descripcion = "";
      this.proyectoActual.fechaFin = "";
    },
    
    //Cuando se selecciona un proyecto en la barra de arriba, seleccionarProyecto trae
    //dicho proyecto desde el Backend y actualiza los datos
    seleccionarProyecto(proyecto) {
      this.proyectoActual.id = proyecto.proyectoID;
      this.proyectoActual.usuarioID = proyecto.usuarioID;
      this.proyectoActual.nombre = proyecto.nombre;
      this.proyectoActual.motivacion = proyecto.motivacion;
      this.proyectoActual.descripcion = proyecto.descripcion;
      let date = new Date(proyecto.fechaFin);
      this.proyectoActual.fechaFin = date.toLocaleString('es-CO');

      //Actualiza id de proyecto en store con el seleccionado
      this.$store.state.activeProject = this.proyectoActual.id;
      localStorage.setItem('proyectoId', this.proyectoActual.id);
      //actualiza la motivación
      this.$router.push({ name: "home" }).catch((err) => {});
      //console.log("carac ejecutada");
    },
    //Obtiene los proyectos del Backend para mostrarlos en los botones
    async obtenerProyectosDeBackend() {
      try {
        const response = await axios.get(
          "http://localhost:8081/api/proyectos/all",
          {
            params: {
              id: this.usuarioID,
            },
          }
        );
        this.proyectos = response["data"];
      } catch (error) {
        console.log(error);
        if (error.response) {
          // respuesta con codigo fuera de rango
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          // no respuesta
          console.log(error.request);
        } else {
          // Error en el envío de petición
          console.log("Error", error.message);
        }
        console.log(error.config);
      }
    },

    goToSettings() {
      this.$router.push({ name: "settings" }).catch((err) => {});
    },
    goToHome() {
      this.$router.push({ name: "home" }).catch((err) => {});
    },
    EstilosPredeterminados(opcion){
      var BarraSuperior=document.getElementsByClassName("navbar-color");
      var BarraProy=document.getElementsByClassName("barraProyectos");
      var EncabezProyecto=document.getElementsByClassName("card-header");
      var BotonCambiar=document.getElementsByClassName("btn-cambio");
      var Contenido=document.getElementsByClassName("content-color");
      switch(opcion){
        case 1://Tema Oscuro
          BarraSuperior[0].style.backgroundColor= "#000";
          BarraProy[0].style.backgroundColor= "#222222b3";
          EncabezProyecto[0].style.backgroundColor= "#000";
          BotonCambiar[0].style.backgroundColor= "#222222b3";
          Contenido[0].style.backgroundColor= "#222222b3";
          break;
        case 2://Tema Azul
          BarraSuperior[0].style.backgroundColor= '#1d3461';
          BarraProy[0].style.backgroundColor= "#6390c7b3";
          EncabezProyecto[0].style.backgroundColor= '#1d3461';
          BotonCambiar[0].style.backgroundColor= "#6390c7b3";
          Contenido[0].style.backgroundColor= "#6390c7b3";
          break;
        case 3://Tema verde
          BarraSuperior[0].style.backgroundColor= "#37261C ";
          BarraProy[0].style.backgroundColor= " #2C7C22b3";
          EncabezProyecto[0].style.backgroundColor= "#37261C ";
          BotonCambiar[0].style.backgroundColor= " #2C7C22b3";
          Contenido[0].style.backgroundColor= " #2C7C22b3";
          break;
        case 4://Tema Rojo
          BarraSuperior[0].style.backgroundColor= "#B83955";
          BarraProy[0].style.backgroundColor= "#153450b7";
          EncabezProyecto[0].style.backgroundColor= "#B83955";
          BotonCambiar[0].style.backgroundColor= "#153450b7";
          Contenido[0].style.backgroundColor= "#153450b7";
          break;
        case 5://Tema Claro
          BarraSuperior[0].style.backgroundColor= "#01657D";
          BarraProy[0].style.backgroundColor= "#BE463Db3";
          EncabezProyecto[0].style.backgroundColor= "#01657D";
          BotonCambiar[0].style.backgroundColor= "#BE463Db3";
          Contenido[0].style.backgroundColor= "#BE463Db3";
          break;
        default://Default
          BarraSuperior[0].style.backgroundColor= "#1d3461";
          BarraProy[0].style.backgroundColor= "#6390c7b3";
          EncabezProyecto[0].style.backgroundColor= "#1d3461";
          BotonCambiar[0].style.backgroundColor= "#6390c7b3";
          Contenido[0].style.backgroundColor= "#6390c7b3";
          break;
      }

    },
    FondosPredeterminados(opcion,color){
      var fondo = document.getElementsByClassName("Home");
      switch (opcion) {
        case 1://Azul
          fondo[0].style.setProperty(color,"#99e2ff");
          break;
        case 2://Verde
          fondo[0].style.setProperty(color,"#9df8a9");
          break;
        case 3://Rojo
          fondo[0].style.setProperty(color,"#e6a7a0");
          break;
        case 4://Amarillo
          fondo[0].style.setProperty(color,"#ffffc1");
          break;
        case 5://Morado
          fondo[0].style.setProperty(color,"#e0a2f3");
          break;
        case 6://Naranja
          fondo[0].style.setProperty(color,"#e9c8a0");
          break;
        case 7://Acuamarina
          fondo[0].style.setProperty(color,"#87e6d1");
          break;
        case 8://Blanco
          fondo[0].style.setProperty(color,"#fff");
          break;
        default:
          fondo[0].style.setProperty(color,"#fff");
          break;
      }

    }
  },
  //Carga los proyectos del backend apenas inicia la pagina
  mounted() {
    this.obtenerProyectosDeBackend();
  },
};
</script>

<style scoped>
#Principal {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.popup {
  background: #446792;
  position: absolute;
  top: 10%;
  left: 50%;
  opacity: 0;
  display: none;
  transform: translate(-50%, 50%) scale(1.25);
  width: 500px;
  padding: 20px 30px;
  background: #fff;
  box-shadow: 2px 2px 5px 5px rgba(0, 0, 0, 0.15);
  border-radius: 10px;
  border-width: 2px;
  border-color: white;
  transition: top 0ms ease-in-out 200ms, opacity 200ms ease-in-out 200 ms,
    transform 20ms ease-in-out 0 ms;
}
.popup.active {
  display: block;
  background: #424242;
  
  top: 1%;
  left: 50%;
  opacity: 1;
  transform: translate(-50%, 50%) scale(1);
  transition: top 0ms ease-in-out 0ms, opacity 200ms ease-in-out 200 ms,
    transform 20ms ease-in-out 0 ms;
}

.ConfigProyecto {
  background: #446792;
  position: absolute;
  top: 10%;
  left: 50%;
  opacity: 0;
  display: none;
  transform: translate(-50%, 50%) scale(1.25);
  width: 500px;
  padding: 20px 30px;
  background: #fff;
  box-shadow: 2px 2px 5px 5px rgba(0, 0, 0, 0.15);
  border-radius: 10px;
  transition: top 0ms ease-in-out 200ms, opacity 200ms ease-in-out 200 ms,
    transform 20ms ease-in-out 0 ms;
}

.ConfigProyecto.active {
  display: block;
  background: #424242;
  top: 10%;
  left: 50%;
  opacity: 1;
  transform: translate(-50%, 50%) scale(1);
  transition: top 0ms ease-in-out 0ms, opacity 200ms ease-in-out 200 ms,
    transform 20ms ease-in-out 0 ms;
}

.topNavigationBar .userbutton {
  font-size: 22px;
  color: rgb(21, 73, 198, 0.6);
  padding-top: 40px;
  float: right;
  /*text-align: right;*/
}
.topNavigationBar .userbutton:hover {
  color: rgb(255, 255, 255);
  background-color: rgba(94, 94, 94, 0.164);
}
#neg {
  font-size: 42px;
  float: unset;
}
.topNavigationBar a {
  line-height: 30px;
  float: left;
  display: block;
  color: #f2f2f2;
  text-align: center;
  text-decoration: none;
  font-size: 20px;
  width: 240px;
  height: 120px;
}

input {
  background-color: rgb(182, 182, 182);
  color: rgb(78, 78, 78);
  margin-top: 5px;
  display: block;
  width: 100%;
  height: 10px;
  padding: 10px;
  outline: none;
  border: 1px solid #aaa;
  border-radius: 5px;
  font-size: large;
}
#descripcionproyecto {
  display: block;
  line-height: 100px;
  padding: 10px 10px 100px 10px;
  max-width: 100%;
}
.CrearProyecto,
.cancelarCrearProyecto,
.EliminarProyecto {
  margin-left: 10px;
  width: 30%;
  padding: 12px 0px 10px 5px;
  outline: none;
  font-size: 15px;
  background: rgb(182, 182, 182);
  color: rgb(255, 255, 255);
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: 0.5s;
}
.CrearProyecto:hover,.cancelarCrearProyecto:hover,.EliminarProyecto:hover{
  color: #ffffff;
  box-shadow: 0 0 5px #ffffff, 0 0 20px #ffffff, 0 0 40px #ffffff;
}
#tituloPopup {
  font-size: 30px;
  color: rgb(255, 255, 255);
  text-align: center;
}

.navbar-color {
  background-color: #1d3461;
}

.componente-central {
  min-height: 70vh;
  max-height: 70vh;
}


.btn-crear-proyecto,.btn-side-navbar {
  border-radius: 4px;
  border-width: 2px;
  border-color: white;
  color: white;
  transition: 0.5s;
}
.btn-crear-proyecto:hover {
  background-color: #000000;
  box-shadow: 0 0 5px #ffffff, 0 0 20px #ffffff, 0 0 40px #ffffff;
}

.btn-side-navbar:hover{
  background-color: #000000;
  /*box-shadow: 0 0 5px #ffffff, 0 0 10px #ffffff, 0 0 10px #ffffff;*/
}
.btn-navbar {
  transition: 0.5s;
}
.btn-navbar:hover {
  box-shadow: 0 0 5px #ffffff, 0 0 20px #ffffff, 0 0 40px #ffffff;
}
.btn-cambio{
  background-color: #6390c7b3;
  /*background-image: linear-gradient(to left,#446792,#1d3461);*/
  color: white;
  transition: 0.5s;
}

.content-color {
  background-color: #6390c7b3;
}
.hr-proyectos {
  border-top: 2px solid #1d3461;
}
.barraProyectos{
  /*background-image: linear-gradient(to top,rgb(182, 182, 182),#454b50);*/
  background-color: #6390c7b3;
}
.card-header {
  background: #1d3461;
  color: white;
}
.b1{background: #99e2ff;}
.b2{background: #9df8a9;}
.b3{background: #e6a7a0;}
.b4{background: #ffffc1;}
.b5{background: #e0a2f3;}
.b6{background: #e9c8a0;}
.b7{background: #87e6d1;}
.b8{background: #fff;}
.Home{
  --color1:#fff;
  --color2: #fff;
  /*background-color: var(--color1);*/
  background-image: linear-gradient(to top,var(--color1),var(--color2));
}
</style>
<!--COSAS QUE HACER:
    -Cuando selecciono un proyecto debería quedarse "presionado" el boton
    -Los botones no están en responsive-design
    -Falta redireccionar bien los botones de metas, sprints, plan y act
      tambien config, config de proyecto y retro (crear esas ventanas)
    -Plantear un popup similar para los botones del punto anterior
    -limitar el numero de proyectos a crear
    -Crear metodos para guardar los proyectos en base de datos
    -(quizas mover todo lo de Style a un .css)

    BUGS:
    -cuando se escribe un campo en un segundo proyecto, aparecen
    los demas elementos del proyecto creado anteriormente
-->
