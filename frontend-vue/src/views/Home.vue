<template>
  <div id="Principal" >


    <!-- Logo, botones de proyectos y usuario -->
    <div class="topNavigationBar" id="MyTopNavigationBar">
      <img id="logo" src="../assets/logo_scrumy.png">

      <!-- botones de proyectos -->
      <div id="proyectos">

        <!-- botones de proyectos existentes -->
        <button class="botonesProyectos" v-for="proy in proyectos" :key="proy.id" @click="caracProyecto(proy)">
          {{proy.nombre}}
        </button>
        <!-- boton para agregar proyectos -->
        <button class="botonesProyectos" id="añadirproyecto" @click="abrirPopup()" ><!--falta abrirPopup()-->
        +
        </button>
      </div>
      <!--Nombre de usuario y configuración-->
      <a href="#home" class="userbutton">
        <b id="neg">{{usuarioNombre}}</b>    Configuración
      </a>
      
    </div>
    
    <!--Barra de 4 botones de la izquierda-->
    <div class="sidenav" v-on:mouseover="mesidenav=true" v-on:mouseout="mesidenav=false">
      <a v-for='(mini,indice) in minis' 
      :mini="mini" :indice="mini.index" :key="mini.id" @click="abrirComponente(indice)">
        <!--para cada elemento en minis se le asocia una función de abrirComponente-->
        <p v-show="!mesidenav"><img :src="mini"></p><!--Si el mouse no esta encima, se muestra el logo-->
        <p v-show="mesidenav">{{nombres[indice]}}</p><!--Si esta encima, se muestra el texto-->
      </a>
    </div>
    <!--Icono de Retro-->
    <button id="retroicon"> 
      <img class="logos" src="../assets/retro.png" >
    </button>
    <!--Div para desplegar los componentes de cada boton-->
    <div class="componente-central">
      <router-view></router-view>    
    </div>
    <!--Popup de crear nuevo proyecto-->
    <div class="popup">
      <form class="form" id="formularioProyecto" onsubmit="return false">
          <h2 id="tituloPopup">Crear Proyecto</h2>
          <input type="text" id="nombreProyecto" placeholder="Nombre" v-model="proyecto.nombre">
          <input type="text" id="motivacionProyecto" placeholder="Motivación" v-model="proyecto.motivacion">
          <input type="text" class="descripcion" id="descripcionproyecto" placeholder="Descripción" v-model="proyecto.descripcion"><br>
          <label for="fecha-culminación">Fecha de culminación</label>
          <input type="date" id="fechaculminacionProyecto" v-model="proyecto.fechaFin"><br>
          <button class="CrearProyecto" @click="CrearProyecto(usuarioID)" >Crear</button>
          <button class="cancelarCrearProyecto" @click="cerrarPopup()">Cancelar</button>
      </form>
    </div>

    <!--Ventana configuracion de proyecto -->
    <div class="ConfigProyecto">
      <form class="form" id="confproyecto" onsubmit="return false">
        <h2 class="tituloConfigProyecto">Configuracion de proyecto</h2>
        <input type="text" id="nombreProyecto" placeholder="Nombre" v-model="proyectoActual.nombre">
        <input type="text" id="motivacionProyecto" placeholder="Motivación" v-model="proyectoActual.motivacion">
        <input type="text" class="descripcion" id="descripcionproyecto" placeholder="Descripción" v-model="proyectoActual.descripcion"><br>
        <label for="fecha-culminación">Fecha de culminación</label>
        <input type="date" id="fechaculminacionProyecto" v-model="proyectoActual.fechaFin"><br>
        <button class="CrearProyecto" @click="ModificarProyecto()" >Guardar cambios</button>
        <button class="cancelarCrearProyecto" @click="cerrarModificarProyecto()">Cancelar</button>
        </form>
    </div>
    <!--Fecha de culminación, el contenido se llena con un script-->
    <h3 id="fecha"></h3>
    <!--Descripción del proyecto , el contenido se llena con un script-->
    <div id="contenido"></div>
    <!--Motivación del proyecto, el contenido se llena con un script-->
    <h3 id="motivacion"></h3>
    <!--Boton configuracion de proyecto -->
    <button id="botonConfigProyecto" @click ="abrirConfigProyecto()"> <img class="logos" src="../assets/config.png" ></button>

  </div>
</template>


<script>
 import axios from 'axios'; 
  
  export default{
    name: "Principal",
    data(){
      return{
        usuarioID: localStorage.usuarioID , //trae el usuario del backend
        usuarioNombre: localStorage.usuarioNombre, //trae el nombre del usuario
        proyectoID: localStorage.proyectoID,//trae el ID del proyecto
        //minis es un "arreglo" con los logos de los 4 botones
        minis:[require('../assets/mp.png'),require('../assets/sp.png'),require('../assets/pl.png'),require('../assets/ac.png')],
        //nombres es un arreglo con los nombres de los 4 botones
        nombres:['Metas pequeñas','Sprints','Plan','Actividades'],
        //ref es el arreglo que relaciona los botones con sus componentes
        ref:['smallgoals','#','Plan','#'],
        //mesidenav es falso cuando el mouse no esta encima de los botones
        mesidenav:false,
        //proyecto es el objeto base para manejar, se llena en el Popup de crear nuevo proyecto
        proyecto:{
          id : 0,
          usuarioID: 0,
          nombre:'',
          motivacion:'',
          descripcion:'',
          fechaFin:''
        },
        //proyecto actual es el proyecto en el cual se ubica actualmente
        proyectoActual:{
          id : 0,
          usuarioID: '',
          nombre:'',
          motivacion:'',
          descripcion:'',
          fechaFin:''
        },
        //proyectos traidos desde el backend
        proyectos: [] 
      }
    },
    
    methods:{
      //abre el Popup de configuración de proyecto
      abrirConfigProyecto(){
        document.querySelector(".ConfigProyecto").classList.add("active");
      },
      //abre el componente central y carga su respectivo contenido según el boton presionado
      abrirComponente(index){
        document.querySelector(".componente-central").classList.add("active");
        this.$router.push( {name: this.ref[index]} );
      },
      //Abre el Popup para añadir un nuevo proyecto
      abrirPopup: function(){
        const popup=document.querySelector(".popup").classList.add("active");
        document.getElementById("nombreProyecto").value = "";
        document.getElementById("motivacionProyecto").value = "";
        document.getElementById("descripcionproyecto").value = "";
        document.getElementById("fechaculminacionProyecto").value = "";
      },
      //Cierra el Popup para añadir un nuevo proyecto
      cerrarPopup: function(){
        document.querySelector(".popup").classList.remove("active");
      },
      //Cierra el Popup de configuración de proyecto
      cerrarModificarProyecto(){
        document.querySelector(".ConfigProyecto").classList.remove("active");
      },
      //Se ejecuta cuando se presiona el boton "crear" de añadir nuevo proyecto
      //toma los objetos del objeto "proyecto" y los guarda en el Backend
      async CrearProyecto(usuarioID) {
      const {nombre,motivacion,descripcion,fechaFin}= this.proyecto;
       
        try {
        const response = await axios.post("http://localhost:8081/api/proyectos/create",
          {
              idUsuarios: usuarioID,
              nombre: nombre,
              motivacion: motivacion,
              fechaFin: fechaFin,
              descripcion : descripcion
          }
        );
        console.log(id)
        console.log(usuarioID);
        console.log(nombre);
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
      //Actualiza los mensajes en motivación y fecha 
        var moti=document.getElementById("motivacion");
        moti.innerHTML="Recuerda que tu motivación es: "+ this.proyectoActual.motivacion;
        var fec=document.getElementById("fecha");
        fec.innerHTML="La fecha de culminación es "+ this.proyectoActual.fechaFin;
        //Ejecuta obtenerProyectosDeBackend (linea 266)
        this.obtenerProyectosDeBackend();
        //Cierra el Popup de añadir nuevo proyecto (linea 145)
        this.cerrarPopup();
        
      },

      //Funcion para modificar los atributos de un proyecto
      async ModificarProyecto( ) {
        console.log(typeof(this.proyectoActual.id));
        //Se guardan los parametros del formulario en el objeto proyecto actual
        const {nombre,motivacion,descripcion,fechaFin}= this.proyectoActual;
        try {
        const response = await axios.put( "http://localhost:8081/api/proyectos/update?id="+ this.proyectoActual.id,
          {
              idUsuarios: this.usuarioID,
              nombre: nombre,
              motivacion: motivacion,
              fechaFin: fechaFin,
              descripcion : descripcion
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
        //actualiza los mensajes de motivación y fecha
        var moti=document.getElementById("motivacion");
        moti.innerHTML="Recuerda que tu motivación es: "+ this.proyectoActual.motivacion;
        var fec=document.getElementById("fecha");
        fec.innerHTML="La fecha de culminación es "+ this.proyectoActual.fechaFin;
        //Ejecuta obtenerProyectosDeBackend (linea 266)
        this.obtenerProyectosDeBackend();
        //Cierra el Popup de ModificarProyecto (linea 149)
        this.cerrarModificarProyecto();
      },
      //Cuando se selecciona un proyecto en la barra de arriba, caracProyecto trae
      //dicho proyecto desde el Backend y actualiza los datos
      caracProyecto(proyecto){
        
        this.proyectoActual.id = proyecto.proyectoID;
        this.proyectoActual.usuarioID = proyecto.usuarioID;
        this.proyectoActual.nombre=proyecto.nombre;
        this.proyectoActual.motivacion=proyecto.motivacion;
        this.proyectoActual.descripcion=proyecto.descripcion;
        this.proyectoActual.fechaFin=proyecto.fechaFin;

        //Actualiza id de proyecto en store con el seleccionado
        this.$store.state.activeProject = this.proyectoActual.id;
        //actualiza la motivación
        var moti=document.getElementById("motivacion");
        moti.innerHTML="Recuerda que tu motivación es: "+ this.proyectoActual.motivacion;
        //actualiza la fecha
        var fec=document.getElementById("fecha");
        fec.innerHTML="La fecha de culminación es "+ this.proyectoActual.fechaFin;
        this.$router.push({ name: "home" });
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
    }

    },
    //Carga los proyectos del backend apenas inicia la pagina
    mounted(){
      this.obtenerProyectosDeBackend();
    }
  }

</script>

<style>
#Principal {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}


h1, h2, #logousuario {
  font-weight: normal;
  color: blue;
  right: 2em;

}

body{
    background-color: cornflowerblue;
    background-image: url("../assets/background_picture.jpg");
    background-size: cover;
    background-repeat:no-repeat;
    background-position:0% 35%;
    width: 100%;
    height: 100%;
    font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif

}

.sidenav {
    margin-top: 5px;
    width: 80px;
    position: fixed;
    z-index: 1;
    background: rgb(21, 73, 198,0.6);
    overflow-x: hidden;
    color: white;
    top: 120px;
    border-radius: 10px;
    border: 2px solid #eee;     
}
  .sidenav:hover{
    width: 250px;
    
  }
  
  .sidenav a{
    line-height: 15vh;
    height: 15vh;
    text-align: center;
    text-decoration: none;
    font-size: 25px;
    display: block;
    
    border-bottom: 2px solid;
    border-right: 2px solid;
    
  }
  
  .sidenav a p:hover {
    color: rgb(21, 73, 198,0.6);
    background-color: #eee;
    border-color: #eee;
  }

  .topNavigationBar { 
    position:fixed ;
    top: 0;
    background-color: rgba(255, 255, 255, 0.103);
    overflow: hidden;
    height: 126px;
    width: 100vw;
    border-bottom: 2px solid rgb(156, 156, 156);
  }


  .popup{
    background: rgb(123, 163, 209);
    position: absolute;
    top: 10%;
    left: 50%;
    opacity: 0;
    display: none;
    transform:translate(-50%,50%) scale(1.25);
    width: 500px;
    padding: 20px 30px;
    background: #fff;
    box-shadow: 2px 2px 5px 5px rgba(0, 0, 0, 0.15);
    border-radius: 10px;
    transition: top 0ms ease-in-out 200ms,
                opacity 200ms ease-in-out 200 ms,
                transform 20ms ease-in-out 0 ms;

  }
  .popup.active{
    display: block;
    background: rgb(123, 163, 209,0.5);
    top: 10%;
    left: 50%;
    opacity: 1;
    transform:translate(-50%,50%) scale(1);
    transition: top 0ms ease-in-out 0ms,
                opacity 200ms ease-in-out 200 ms,
                transform 20ms ease-in-out 0 ms;

  }


  .ConfigProyecto{
     background: rgb(123, 163, 209);
    position: absolute;
    top: 10%;
    left: 50%;
    opacity: 0;
    display: none;
    transform:translate(-50%,50%) scale(1.25);
    width: 500px;
    padding: 20px 30px;
    background: #fff;
    box-shadow: 2px 2px 5px 5px rgba(0, 0, 0, 0.15);
    border-radius: 10px;
    transition: top 0ms ease-in-out 200ms,
                opacity 200ms ease-in-out 200 ms,
                transform 20ms ease-in-out 0 ms;
  }

  .ConfigProyecto.active{
    display: block;
    background: rgb(123, 163, 209,0.5);
    top: 10%;
    left: 50%;
    opacity: 1;
    transform:translate(-50%,50%) scale(1);
    transition: top 0ms ease-in-out 0ms,
                opacity 200ms ease-in-out 200 ms,
                transform 20ms ease-in-out 0 ms;

  }

  .topNavigationBar .userbutton {
    font-size :22px;
    color: rgb(21, 73, 198,0.6);
    padding-top: 40px;
    float: right;
    /*text-align: right;*/
    
  }
    .topNavigationBar .userbutton:hover {
    color: rgb(255, 255, 255);
    background-color: rgba(94, 94, 94, 0.164);
  }
  #neg{
    font-size: 42px;
    float:unset;
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
  #retroicon{
    margin-top: 800px;
    margin-left: 5%;
    height: 80px;
    width: 80px;
    position:fixed;
    border-radius: 10px;
    padding: 13px;
    background-color: rgb(21, 73, 198,0.6);
    color: rgb(255, 255, 255);
    position:fixed;
    border: 2px solid #eee;
  }
  #fecha{
    margin: 8rem 16.5rem;
    height: 30px;
    width: 350px;
    position:fixed;
    font-size: 16px;
    border-radius: 10px;
    padding: 4px;
    background-color: rgb(21, 73, 198,0.6);
    color: rgb(255, 255, 255);
    position:fixed;
    border: 2px solid #eee;  
  }
  #motivacion{
    margin-top: 800px;
    margin-left: 20%;
    font-size: 22px;
    height: 4em;
    width: 40em;
    border-radius: 10px;
    padding: 10px;
    background-color: rgb(21, 73, 198,0.6);
    color: rgb(255, 255, 255);
    position:fixed;
    border: 2px solid #eee;  
    
  }
  .botonesProyectos{
    font-size: 20px;
    color: rgb(255, 255, 255);
    border-radius: 10px;
    background-color: rgb(21, 73, 198,0.6);
  }
  .botonesProyectos:hover{
    color: rgb(21, 73, 198);
    background-color:rgb(255, 255, 255); 
  }
  #botonConfigProyecto{
     margin-top: 800px;
    margin-left: 90%;
    height: 80px;
    width: 80px;
    position:fixed;
    border-radius: 10px;
    padding: 4px;
    background-color: rgb(21, 73, 198,0.6);
    color: rgb(255, 255, 255);
    position:fixed;
    border: 2px solid #eee;
  }
  #botonConfigProyecto, #retroicon:hover{
    height: 85px;
    width: 85px;
  }
  #logo{
    width: 230px;
  }
  .logos{
    width: 50px;
  }
  #proyectos{
    display: inline;
    margin-top: 70px;

  }
  input{
    background-color: rgb(123, 169, 209);
    color: white;
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
  #descripcionproyecto{
    display: block;
    line-height: 100px;
    padding: 10px 10px 100px 10px;
    max-width: 100%;
  }
  .CrearProyecto, .cancelarCrearProyecto{
    margin-left: 10px;
    width: 40%;
    padding: 12px 0px 10px 5px;
    border:none;
    outline: none;
    font-size: 15px;
    background: rgb(123, 163, 209,0.5);
    color: black;
    border-radius: 10px;
    cursor: pointer;
    font-weight: 600;
  }
  #tituloPopup{
    font-size: 30px;
    color: rgb(255, 255, 255);
    text-align: center;
  }


  .componente-central{
    background: rgb(21, 73, 198,0.6);
    display: none;
    position: absolute;
    top: 0%;
    left: 45%;
    opacity: 1;
    transform:translate(-50%,50%) scale(1.25);
    width: 50%;
    height: 50%;
    margin: 0;
    padding: 0;
    box-shadow: 2px 2px 5px 5px rgba(0, 0, 0, 0.15);
    border-radius: 10px;
  }
  .componente-central.active{
    background: rgb(21, 73, 198,0.6);
    display: block;
    position: absolute;
    top: 0%;
    left: 45%;
    opacity: 1;
    transform:translate(-50%,50%) scale(1.25);
    width: 50%;
    height: 50%;
    margin: 0;
    padding: 0;
    box-shadow: 2px 2px 5px 5px rgba(0, 0, 0, 0.15);
    border-radius: 10px;
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