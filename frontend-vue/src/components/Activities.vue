<template>
      <div id="Activities col-12 m-0 p-2 h-100 ">
        <div class="col-12 d-inline-block m-2 titleMetas">
          <h2 data-bs-toggle="tooltip" data-bs-placement="right" title="Aqui puedes añadir actividades a tus metas">Actividades</h2>
        </div>
       <h5 id= "escoge">Escoge el sprint</h5><br>
      <select name="menu" id="menu" @change="TraerMetasDelSprint($event)" v-model="key" data-bs-toggle="tooltip" data-bs-placement="right" title="Selecciona un Sprint"> <!--Selector de sprints -->
          <!--El siguiente for recorre todos los sprints en sprints[], los trae como opciones del selector y muestra su nombre-->
          <option v-for="spr in sprints" :key="spr.sprintID"  selected="selected" >Sprint {{sprints.indexOf(spr) +1 }}</option>
        </select>

      <div class="d-flex flex-row col-12 m-0 p-0 overflow-auto">       
         <!--Div de las metas del sprint para ordenar -->
        <div id="Metas">
      <!--El siguiente for recorre todas las metas en metas[] y los trae como divs e imprime su nombre-->
        <button v-for="meta   in metas" :key="meta.id" class=" metas btn btn-dark btn-sm"  @click="BringActivities(meta.metaID)" data-bs-toggle="tooltip" data-bs-placement="right" title="Selecciona esta meta para agregarle una actividad"> 
          <h5>{{meta.nombre}}</h5>
        </button>
        <!--<img id ="Flecha" src="../assets/flecha.png">-->
        
    </div>
  </div>  
  <div class="col-12 h-75 m-0 p-4 justify-content-center d-grid" >
      <!--div v-for="actividad in actividades" :key="actividad.id" class="actividades g-col-4"-->
        <div class="card actividades bg-light mb-3 d-inline-block m-3" v-for="actividad in actividades" :key="actividad.id">
          <div class="card-header text-white bg-dark">
            {{actividad.nombre}}
            <button v-show="actividad.estado" class="btn bg-success text-white btn-sm" @click="cambiarEstado(actividad.actividadID)" data-bs-toggle="tooltip" data-bs-placement="right" title="Haz click aqui para marcar tu actividad como 'POR HACER'">
              COMPLETADA</button>
            <button v-show="!actividad.estado" class="btn bg-danger text-white btn-sm" @click="cambiarEstado(actividad.actividadID)" data-bs-toggle="tooltip" data-bs-placement="right" title="Haz click aqui para marcar tu actividad como 'COMPLETADA'"> POR HACER</button>
          </div>
          <div class="card-body" @click="abrirPopupCamb(actividad.actividadID)" data-bs-toggle="tooltip" data-bs-placement="right" title="Haz click aqui para editar tu actividad">
            <p class="card-text"><b>DESCRIPCIÓN:</b> {{actividad.descripcion}}</p>
            <p class="card-text" v-if="actividad.dificultad"><b>DIFICULTAD: </b>{{actividad.dificultad}}</p>
            <!--button class="btn btn-outline-danger" @click="EliminarActividad(actividad.actividadID)">ELIMINAR</button-->
            <!--button class="btn btn-outline-success" @click="abrirPopupCamb(actividad.actividadID)">EDITAR</button-->
          </div>
        </div>
    <!--/div-->

  </div>
      <!--Popup para crear actividad-->
      <div class="ventanaOpciones crearActividad bg-dark">
        <div class="contenidoActividad w-100 h-100">
          <h2 class="text-light">Nueva actividad</h2>
          <p class="text-light m-0">Nombre: </p>
          <input v-model="NombreActividad" type="text" class="rounded bg-secondary"><br>
          <p class="text-light m-0">Descripción: </p>
          <input v-model="DescripcionActividad" type="text" class="rounded bg-secondary w-100"><br>
        </div>
        <button @click="cerrarPopupAct()" class="cerrarVentana btn bg-dark text-light">x</button>
        <button @click="CrearActividad()" class="textoOpcion btn btn-outline-success text-dark btn-lg">CREAR</button>
      </div>
    
     <!--Popup para editar actividad-->
      <div class="ventanaOpcion2 cambiarActividad bg-dark">
        <div class="contenidoActividad w-100 h-100">
          <h2 class="text-light">Editar Actividad</h2>
          <p class="text-light">Nombre: </p>
          <input v-model="NombreActividad" type="text" class="rounded bg-secondary"><br>
          <p class="text-light">Descripción: </p>
          <input v-model="DescripcionActividad" type="text" class="rounded bg-secondary w-100"><br>
          <p class="text-light">Dificultad: </p>
          <textarea class="w-100 goalDescription bg-secondary" cols="30" rows="3" v-model="DificultadActividad"></textarea>
          <!--input v-model= type="text" class="bg-secondary w-100"--><br>
        </div>
        <button @click="cerrarPopupCam()" class="cerrarVentana btn bg-dark text-light">x</button>
        <button @click="cambiarActividad()" class="textoOpcion2 btn text-dark btn-lg btn-outline-success">GUARDAR</button>
        <button @click="EliminarActividad(IDActividad)" class="textoOpcion btn text-dark btn-lg btn-outline-danger">ELIMINAR</button>
      </div>
    
    <button v-if="this.idMetA!=0" @click="abrirPopupAct()" class="CreateActivityButton btn bg-dark text-white btn-lg"><b>NUEVA ACTIVIDAD</b></button>   
  </div>
  
</template>

<script>
import axios from "axios";

const pathCreate = "/sprints/create";
const pathGet = "/sprints/all";

export default {
  name: 'Activities',

  beforeCreate() {
    axios
      .get(this.$store.state.backURL + pathGet, {
        params: {
          id: this.$store.state.activeProject,
        },
      })
      .then((response) => {
        this.sprints = response["data"];
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento");
      });
 

  },

  data () {
    return {
      proyectId: this.$store.state.activeProject,
      key: 0,
      sprint:{ //Sprint actual
        id:1,
      },
      sprints:[//Arreglo con los sprints del proyecto
        //valores de prueba
      ], 
      metas:[], //Arreglo con las metas del sprint seleccionado
      actividades:[],
      NombreActividad:"",
      DescripcionActividad:"",
      DificultadActividad:"",
      EstadoActividad:"",
      IDActividad:0,
      CheckboxDelAct:[],
      isHiddenCreate: false,
      isHiddenDelete: false,
      idMetA: 0 ,

    }
  },
  methods:{
    abrirPopupAct: function() {
      //document.querySelector(".eliminarActividad").style.display ="none";
      document.querySelector(".crearActividad").style.display ="flex";
    },
    cerrarPopupAct: function() {
      document.querySelector(".crearActividad").style.display ="none";
    },
    abrirPopupCamb: function(id) {
      document.querySelector(".crearActividad").style.display ="none";
      document.querySelector(".cambiarActividad").style.display ="flex";
      for (let i = 0; i< this.actividades.length; i++) {
        if (this.actividades[i].actividadID==id) {
          this.DescripcionActividad=this.actividades[i].descripcion;
          this.NombreActividad=this.actividades[i].nombre;
          this.DificultadActividad=this.actividades[i].dificultad;
          this.IDActividad=id;
          this.EstadoActividad=this.actividades[i].estado;
        }
       
      }
    },
    cerrarPopupCam: function() {
      document.querySelector(".cambiarActividad").style.display ="none";
      this.DescripcionActividad="";
      this.NombreActividad="";
      this.DificultadActividad="";
      this.IDActividad=0;
      this.EstadoActividad;
    },
    BringActivities(idMeta){
      //let actividades=[]; 
      let pathActividades = "/actividades/all";
      axios
      //Se cargan las actividades de la meta actual
      .get(this.$store.state.backURL + pathActividades, {
        params: {
          id: idMeta,
        },
      })
      .then((response) => {
        this.actividades = response["data"];
        this.idMetA = idMeta;
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento 1 (SetAvance)");
      });
      
    },
    TraerSprintsBackend(){//Trae los Sprints del Backend y los almacena en sprints[]
       axios
        .get(this.$store.state.backURL + pathGet, {
          params: {
            id: this.$store.state.activeProject,
          },
        })
        .then((response) => {
          this.sprints = response["data"];
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento 2");
        });

    },
    TraerMetasDelSprint(event){ //Trae las metas del Sprint que se le pasa como atributo y las almacena en metas[]
      
      for (let i = 0; i < this.sprints.length; i++) {//Función de ejemplo, recorre todos los sprints en sprints[]
        //console.log( 'Sprint ' + (i+1));
        if(event.target.value == 'Sprint ' + (i+1)  ){//Si la opción elegida coincide con el nombre de un sprint
          this.sprint.id = this.sprints[i].sprintID;  //Asigna el id de ese sprint a "sprint actual"
        }
      }

      //Traemos las metas del sprint
      let pathMetas = "/metas/findBySprint";
      axios
      .get(this.$store.state.backURL + pathMetas, {
        params: {
          id: this.sprint.id,
        },
      })
      .then((response) => {
        this.metas = response["data"];
        //console.log(this.metas.length);
 

      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento 3");
      });
      //this.SetAvance();//Actualiza la barra con el avance del sprint seleccionado
    },
    cambiarEstado(idActividad){
      console.log(idActividad);
      let pathChangeState = "/actividades/updateState?id=";
      axios
      .put(this.$store.state.backURL + pathChangeState + idActividad,{} )
      .then((response) => {
        console.log("Pudo acceder a las actividades");
        this.BringActivities(this.idMetA);
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento 5");
      });
    },
    cambiarActividad(){
      let pathChangeState = "/actividades/update";
      axios
      .put(this.$store.state.backURL + pathChangeState, {
            nombre: this.NombreActividad,
            descripcion: this.DescripcionActividad,
            dificultad: this.DificultadActividad,
            idMeta: this.idMetA,
            estado: this.EstadoActividad,
          },
          {
            params: {
              id: this.IDActividad,
            },
          } )
      .then((response) => {
        console.log("Pudo hacer el update");
        this.BringActivities(this.idMetA);
        this.cerrarPopupCam();
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento 5");
      });
    },
  CrearActividad(){
      let pathCreateActivity = "/actividades/create";
      axios
      .post(this.$store.state.backURL + pathCreateActivity, {
          
          id: this.sprint.id,
          idMeta: this.idMetA,
          nombre: this.NombreActividad,
          descripcion: this.DescripcionActividad,
          dificultad:" ",

      })
      .then((response) => {
        console.log("Pudo acceder a las actividades");
        this.cerrarPopupAct();
        this.BringActivities(this.idMetA);
        this.NombreActividad = "";
        this.DescripcionActividad = "";
 
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento 4");
      });
      //this.SetAvance();//Actualiza la barra con el avance del sprint seleccionado
 
    },
    EliminarActividad(idActividad){
      let pathRemoveActivity = "/actividades/remove?id=";
      axios
      .delete(this.$store.state.backURL + pathRemoveActivity + idActividad, {})
      .then((response) => {
        console.log("Pudo acceder a las actividades");
        this.cerrarPopupCam();
        this.BringActivities(this.idMetA);
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento 5");
      });
    },

  },
  
}
</script>

<style>
html,body{
  width: 100%;
  height: 100%;
}
#Activities {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  width: 100%;
  height: 100%;
}
#tituloActividades{
    height: 15%;
    text-align: left;
    padding: 15px;
    color: white;
    position :absolute;
    top:  5%;
    left: 6%;
}
#Sprints{
  height: 15%;
  text-align: left;
  padding: 15px;
  color: white;
}
#Sprints h1{display: inline;  color: white;} 
#menu {
  position :absolute;
  top :7%;
  right: 2%;
  width: 15%;
  height: 5%;
  border-radius: 20px;
  font-size: 15px;
}
#escoge {
  display: inline;  
  color: white;
  position :absolute;
  top :2%;
  right: 3%;
  }
#Metas{
  height: 15%;
  width: 70%;
  text-align: center;
  padding: 1px;
  display: flex; 
	flex-wrap: wrap;
  align-items: center;
}
#Flecha {
    width:60%;
    position: absolute;
    top: 60px;
}

.metas{
  overflow: hidden;
  color: #163350;
  font-size: 10px;
  --dim:5;
  width: 30%;
  height: 35%;
  text-align: center;
  top: 100%;
  border-radius: 6px;
}
.metas h5{
  color: rgb(255, 255, 255);
  font-size: 20px;
}

#Actividades {
    width: 100%;
    height: 55%;
    text-align: center;
    padding: 1px;
    display: flex; 
    font-size: 10px;
    flex-wrap: wrap;
    align-items: center;
}

.actividades{
    height: 25%;
    -ms-grid-row-align: center;
}

  .CreateActivityButton{
    position: absolute;
    top: 18%;
    right: 2%;
  }
  .ventanaOpciones{
    background-color: #5e5e5e;
    position: absolute;
    top: 10%;
    left: 30%;  
    width: 40%;
    height: 48%;
    border-style: solid;
    border-color:#FFFFFF;
    padding: 20px 30px;
    border-radius: 10px;
  }
  .ventanaOpcion2{
    background-color: #5e5e5e;
    position: absolute;
    top: 10%;
    left: 30%;  
    width: 40%;
    height: 70%;
    border-style: solid;
    border-color:#FFFFFF;
    padding: 20px 30px;
    border-radius: 10px;
  }

  .crearActividad{
      position: absolute;
      display: none;
      transition: top 0ms ease-in-out 200ms, opacity 200ms ease-in-out 200 ms,
      transform 20ms ease-in-out 0 ms;
  }
   .cambiarActividad{
      position: absolute;
      display: none;
      transition: top 0ms ease-in-out 200ms, opacity 200ms ease-in-out 200 ms,
      transform 20ms ease-in-out 0 ms;
  }
  .contenidoFixed{
    position: relative;
  }

  .cerrarVentana{
    position: absolute;
    display: flex;
    top: 2%;
    right: 2%;
    font-size: 25px;
    border-style: hidden;
  }
  .textoOpcion{
    position: absolute;
    bottom: 5%;
    Right: 5%;
    border-style: solid rgb(255, 0, 149);
    background-color: #FFFFFF;
    color: #15a8e2;
  }
    .textoOpcion2{
    position: absolute;
    bottom: 5%;
    Right: 50%;
    border-style: solid rgb(255, 0, 149);
    background-color: #FFFFFF;
    color: #15a8e2;
  }
  .titleMetas {
  border-bottom: 2px solid rgb(0, 0, 0);
  margin-bottom: 5px;
  }
  textarea {
  background-color: rgb(182, 182, 182);
  color: rgb(0, 0, 0);
  margin-top: 5px;
  margin-bottom: 5px;
  display: block;
  padding: 10px;
  outline: none;
  border-radius: 5px;
  font-size: large;
}
</style>
