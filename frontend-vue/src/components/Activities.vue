<template>
  <div id="Activities"> <!--Div grande que abarca toda la pagina -->

    <!--Div del Titulo y el selector de sprints -->
    <h5 id= "escoge">Escoge el sprint</h5><br>
    <select name="menu" id="menu" @change="TraerMetasDelSprint($event)" v-model="key"> <!--Selector de sprints -->
          <!--El siguiente for recorre todos los sprints en sprints[], los trae como opciones del selector y muestra su nombre-->
          <option v-for="spr in sprints" :key="spr.sprintID"  selected="selected" >Sprint {{sprints.indexOf(spr) +1 }}</option>
        </select>

    <!--Div de las metas del sprint para ordenar -->
    <div id="Metas">
      <!--El siguiente for recorre todas las metas en metas[] y los trae como divs e imprime su nombre-->
        <button v-for="meta   in metas" :key="meta.id" class="metas" @click="BringActivities(meta.metaID)"> 
          <h2>{{meta.nombre}}</h2>
        </button>
        <img id ="Flecha" src="../assets/flecha.png">
        <h1 id="tituloActividades">Actividades </h1>   
    </div>

    <div id="Actividades">
        <div v-for="actividad in actividades" :key="actividad.id" class="actividades">
            <input type ="checkbox">
            <h2>{{actividad.nombre}}</h2>
        </div>
    </div>

    <!--Popup para crear actividad-->
    
      <div class="ventanaOpciones crearActividad">
        <div class="contenidoActividad">
          <h2>Nueva actividad</h2>
          <p>Nombre: </p>
          <input v-model="NombreActividad" type="text" id="nombreActividad"><br>
          <p>descripción: </p>
          <input v-model="DescripcionActividad" type="text" id="descripcionActividad"><br>
        </div>
        <button @click="cerrarPopupAct()" class="cerrarVentana">x</button>
        <button @click="CrearActividad()" class="textoOpcion">Crear</button>
      </div>
    

    <!--Popup para eliminar actividad-->
    
      <div class="ventanaOpciones eliminarActividad">
        <div class="contenidoFixed">
          <h2>Eliminar actividades</h2>
              <div v-for="actividad in actividades" :key="actividad.id" class="actividadesEliminar">
                  <!--Revisar si value está correcto -->
                  <input type="checkbox" name="done" value="actividad.nombre" v-model="CheckboxDelAct">
                  <p>{{actividad.nombre}}</p>
              </div>  
        </div>
        <button @click="cerrarPopupDel()" class="cerrarVentana">x</button>
        <button @click="EliminarActividad()" class="textoOpcion">Eliminar</button>
      </div>
    
    <button @click="abrirPopupAct()" id="CreateActivityButton">+</button>
    <button @click="abrirPopupDel()" id="DeleteActivityButton"><img src="https://img.icons8.com/glyph-neue/50/4a90e2/trash.png"/></button>


  </div>
</template>

<script>
import axios from "axios";

const pathCreate = "/sprints/create";
const pathGet = "/sprints/all";

export default {
  name: 'Plan',

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
      CheckboxDelAct:[],
      isHiddenCreate: false,
      isHiddenDelete: false,

    }
  },
  methods:{
    abrirPopupAct: function() {
      document.querySelector(".eliminarActividad").style.display ="none";
      document.querySelector(".crearActividad").style.display ="flex";
    },
    cerrarPopupAct: function() {
      document.querySelector(".crearActividad").style.display ="none";
    },
    abrirPopupDel: function() {
      document.querySelector(".crearActividad").style.display ="none";
      document.querySelector(".eliminarActividad").style.display ="flex";
    },
    cerrarPopupDel: function() {
      document.querySelector(".eliminarActividad").style.display ="none";
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


  },
  CrearActividad(){
      let pathCreateActivity = "/actividades/create";
      axios
      .get(this.$store.state.backURL + pathCreateActivity, {
        params: {
          id: this.sprint.id,
          nombre: this.NombreActividad,
          descripcion: this.DescripcionActividad,
        },
      })
      .then((response) => {
        console.log("Pudo acceder a las actividades");
        cerrarPopupAct();
        BringActivities(this.meta.id);
        this.NombreActividad = "";
        this.DescripcionActividad = "";
 
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento 4");
      });
      //this.SetAvance();//Actualiza la barra con el avance del sprint seleccionado
 
  },
  auxEliminarActividad(idActividad){
      let pathRemoveActivity = "/actividades/remove";
      axios
      .get(this.$store.state.backURL + pathRemoveActivity, {
        params: {
          id: idActividad,
        },
      })
      .then((response) => {
        console.log("Pudo acceder a las actividades");
        
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento 5");
      });
      //this.SetAvance();//Act
  },
  EliminarActividad(){
    arregloActividades = this.CheckboxDelAct;
    for (let index = 0; index < arregloActividades.length; index++) {
        this.auxEliminarActividad(arregloActividades[index]);
        
    }      
    this.cerrarPopupDel();
    BringActivities(this.meta.id);
  },

  mounted(){ //Al iniciar, ejecuta estos comandos
    //this.TraerSprintsBackend();
    //this.TraerMetasDelSprint(1);
 
  }
  
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
    top :30%;
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
  top :13%;
  right: 6%;
  width: 12%;
  height: 5%;
  border-radius: 20px;
  font-size: 15px;
}
#escoge {
  display: inline;  
  color: white;
  position :absolute;
  top :7%;
  right: 2%;
  }
#Metas{
  height: 20%;
  width: 70%;
  text-align: center;
  padding: 40px;
  display: flex; 
	flex-wrap: wrap;
	justify-content: space-around;
  align-items: center;
}
#Flecha {
    width:60%;
    position: absolute;
    top: 60px;
}

.metas{
  border-width: 1px;
  border-color: white;
  border-style: solid;
  overflow: hidden;
  color: #163350;
  font-size: 15px;
  --dim:5;
  width: calc(var(--dim)*2%);
  height: calc(var(--dim)*2%);
  text-align: center;
  top: 50%;
  border-radius: 6px;

}
.metas div {
  --variable:0;
  background-color: rgb(50, 205, 159);
  height: 100%;
  width: calc(var(--variable)*1%);
  border-radius: 3px;
}
.metas h2{
  color: rgb(255, 255, 255);
  font-size: 25px;
}

#Actividades {
    width: 100%;
    height: 65%;
}

.actividades{
    width: 100px;
    border: ridge black;
    padding: 10px;
    margin: 40px; 
}

#DeleteActivityButton{
    position: absolute;
    bottom: 8px;
    right: 16px;
    background-color:#ffffff;
    border-radius: 20%;
    border-style: solid;
    color:#15a8e2;
    width: 65px;
    height: 65px;
    cursor: pointer;
  }

  #CreateActivityButton{
    position: absolute;
    bottom: 8px;
    right: 90px;
    font-size: 40px;
    background-color:#ffffff;
    border-radius: 20%;
    border-style: solid;
    color:#15a8e2;
    cursor: pointer;
  }
    .ventanaOpciones{
    background-color: #ffffff;
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

  .ventanaOpciones h2{
    color: black;
    font-size: 20px;
    font-weight: bold;
    text-justify: center;
  }

  .eliminarActividad{
      position: absolute;
      display: none;
      transition: top 0ms ease-in-out 200ms, opacity 200ms ease-in-out 200 ms,
      transform 20ms ease-in-out 0 ms;
  }
  .crearActividad{
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
    top: 5%;
    right: 5%;
    font-size: 25px;
    border-style: hidden;
    background-color: #FFFFFF;
    color: #15a8e2;
  }
  .textoOpcion{
    position: absolute;
    bottom: 5%;
    Right: 10%;
    border-style: solid blue;
    background-color: #FFFFFF;
    color: #15a8e2;
  }

</style>
