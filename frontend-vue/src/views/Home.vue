<template>
  <div id="Principal" >

    <div class="topNavigationBar" id="MyTopNavigationBar">
      <img id="logo" src="../assets/logo_scrumy.png">
      <div id="proyectos">
        <button class="botonesProyectos" v-for="proy in proyectos" :key="proy.id" @click="caracProyecto(proy)">
          {{proy.nombre}}
        </button>
        <button class="botonesProyectos" id="añadirproyecto" @click="abrirPopup()" ><!--falta abrirPopup()-->
        +
        </button>
      </div>
      <a href="#home" class="userbutton">
        <!--img src="./assets/usuario.png" width="40px"-->
        <b id="neg">{{usuarioNombre}}</b>    Configuración
      </a>
      
    </div>
    <div class="sidenav" v-on:mouseover="mesidenav=true" v-on:mouseout="mesidenav=false">
      <a v-for='(mini,indice) in minis' 
      :mini="mini" :indice="mini.index" :key="mini.id"> <!--Falta incluir el href-->
        <p v-show="!mesidenav"><img :src="mini"></p>
        <p v-show="mesidenav">{{nombres[indice]}}</p>
      </a>
    </div>
    <button id="retroicon"> 
      <img class="logos" src="../assets/retro.png" >
    </button>
    <div class="popup">
      <form class="form" id="formularioProyecto" onsubmit="return false">
          <h2 id="tituloPopup">Crear Proyecto</h2>
          <input type="text" id="nombreProyecto" placeholder="Nombre" v-model="proyecto.nombre">
          <input type="text" id="motivacionProyecto" placeholder="Motivación" v-model="proyecto.motivacion">
          <input type="text" class="descripcion" id="descripcionproyecto" placeholder="Descripción" v-model="proyecto.descripcion"><br>
          <label for="fecha-culminación">Fecha de culminación</label>
          <input type="date" id="fechaculminacionProyecto" v-model="proyecto.fecha"><br>
          <button class="CrearProyecto" @click="CrearProyecto()" >Crear</button>
          <button class="cancelarCrearProyecto" @click="cerrarPopup()">Cancelar</button>
      </form>
    </div>
    <div class="ConfigProyecto">
      <form action="form" id="confproyecto" onsubmit="return false">
        <h2></h2>


      </form>
    </div>
    <h3 id="fecha"></h3>
    <div id="contenido"></div>
    <h3 id="motivacion"></h3>
    <button id="configProyecto"><img class="logos" src="../assets/config.png" ></button>
  </div>
</template>


<script>
 import axios from 'axios';
  export default{
    name: "Principal",
    data(){
      return{
        usuarioID: localStorage.usuarioID ,
        usuarioNombre: localStorage.usuarioNombre,
        minis:[require('../assets/mp.png'),require('../assets/sp.png'),require('../assets/pl.png'),require('../assets/ac.png')],
        nombres:['Metas pequeñas','Sprints','Plan','Actividades'],
        ref:['#','#','#','#'],
        mesidenav:false,
        proyecto:{
          nombre:'',
          motivacion:'',
          descripcion:'',
          fecha:''
        },
        proyectoactual:{
          nombre:'',
          motivacion:'',
          descripcion:'',
          fecha:''
        },
        proyectos:[] 
      }
    },
    
    methods:{
      abrirPopup: function(){
        const popup=document.querySelector(".popup").classList.add("active");
        document.getElementById("nombreProyecto").value = "";
        document.getElementById("motivacionProyecto").value = "";
        document.getElementById("descripcionproyecto").value = "";
        document.getElementById("fechaculminacionProyecto").value = "";
      },
      cerrarPopup: function(){
        document.querySelector(".popup").classList.remove("active");
      },
      CrearProyecto: function(){
        const {nombre,motivacion,descripcion,fecha}= this.proyecto;
        this.proyectos.push({
          nombre,
          motivacion,
          descripcion,
          fecha
        })
        this.cerrarPopup();
      },
      caracProyecto(proyecto){
        var moti=document.getElementById("motivacion");
        moti.innerHTML="Recuerda que tu motivación es: "+ proyecto.motivacion;
        var fec=document.getElementById("fecha");
        fec.innerHTML="La fecha de culminación es "+ proyecto.fecha;
        this.proyectoactual.nombre=proyecto.nombre;
        this.proyectoactual.motivacion=proyecto.motivacion;
        this.proyectoactual.descripcion=proyecto.descripcion;
        this.proyectoactual.fecha=proyecto.fecha;
      },

    
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
        console.log(proyectos);
        console.log("try");
      } catch (error) {
        console.log(error);
        if (error.response) {
          // get response with a status code not in range 2xx
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
          console.log("catch 1");
        } else if (error.request) {
          // no response
          console.log(error.request);
          console.log("catch 2");
        } else {
          // Something wrong in setting up the request
          console.log("Error", error.message);
          console.log("catch 3");
        }
        console.log(error.config);
        console.log("catch 4");
      }
    }

    },
    mounted(){
      this.obtenerProyectosDeBackend();
      console.log("created");
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
    width: 300px;
    
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
    margin: 8rem 17rem;
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
  #configProyecto{
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
  #configProyecto:hover, #retroicon:hover{
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