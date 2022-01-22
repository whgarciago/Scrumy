<template>
  <div id="PlanDeOrganizacion"> <!--Div grande que abarca toda la pagina -->

    <!--Div del Titulo y el selector de sprints -->
    <div id="Sprints"> 
        <h1>Plan de Organización</h1>
        <select name="menu" id="menu" @change="TraerMetasDelSprint($event)" v-model="key"> <!--Selector de sprints -->
          <!--El siguiente for recorre todos los sprints en sprints[], los trae como opciones del selector y muestra su nombre-->
          //<option v-for="spr in sprints" :key="spr.sprintID">Sprint {{sprints.indexOf(spr) +1 }}</option>

        </select>
    </div>

    <!--Div de las metas del sprint para ordenar -->
    <div id="Plan">
      <!--El siguiente for recorre todas las metas en metas[] y los trae como divs e imprime su nombre-->
        <div v-for="meta  in metas" :key="meta" class="metas">
          <h2>{{meta.nombre}}</h2>
          {{avances.indexOf(meta)}}
        </div>
    </div>
    

    <!--Div de la barra de progreso del sprint -->
    <div id="BarraProgreso">
          {{sprint.id}}
      <h3>Tu avance en el Sprint es: </h3>
      <div id="barra"><!--Div que es literalmente la barra de progreso -->
        <div id="avance"><!--Div que es el avance dentro de la barra -->
          {{avance}}% <!--Escribo el porcentaje de avance -->
        </div>
      </div>
    </div>

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
      avance:0,//Avance del sprint
      sprint:{ //Sprint actual
        id:1,
      },
      sprints:[//Arreglo con los sprints del proyecto
        //valores de prueba
      ], 
      metas:[], //Arreglo con las metas del sprint seleccionado
      actividades:[],
      avances:[],

    }
  },
  methods:{


    SetAvance(idMeta){
      var suma=0; //Avance de la meta
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
        //console.log(actividades[0]);
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento (SetAvance)");
      });
      //return this.actividades;
      //Se calcula avance
      var cantidad = 100/this.actividades.length;
      //console.log(actividades[0]);
      for (let index = 0; index < this.actividades.length; index++) {
       if(this.actividades[index].estado){
         suma += cantidad;
       }
      }
      this.avances.push(suma);
      //console.log(actividades);
      // for (let actividad in actividades){
      //     if(actividad.estado){
      //     suma += cantidad;
      // }
      // console.log(actividades[actividad]);
      // }
      //console.log(suma);
      //suma=suma/this.metas.length; //Divide la suma en la cantidad de metas, para hacer un promedio
      //this.avance=suma.toFixed(2);//Modifica el avance con 2 digitos
      //document.getElementById("avance").style.setProperty('--largo',this.avance); //Actualiza la barra de progreso
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
          alert("No es posible conectar con el backend en este momento");
        });

    },
    TraerMetasDelSprint(event){//Trae las metas del Sprint que se le pasa como atributo y las almacena en metas[]
    //console.log('Sprint ');
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
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento");
      });
      //this.SetAvance();//Actualiza la barra con el avance del sprint seleccionado
      for (let i = 0; i < this.metas.length; i++) {//Función de ejemplo, recorre todos los sprints en sprints[]
          this.SetAvance(this.metas[i].metaID);
        }
    }

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
#PlanDeOrganizacion {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  width: 100%;
  height: 100%;
}



#Sprints{
  height: 15%;
  text-align: left;
  padding: 20px;
  color: white;
}
#Sprints h1{display: inline;  color: white;} 
#menu{
  float: right;
  width: 25%;
  height: 75%;
  border-radius: 20px;
  font-size: 20px;
  
}

#Plan{
  height: 60%;
  text-align: center;
  padding: 40px;
  display: flex; 
	flex-wrap: wrap;
	justify-content: space-around;
  align-items: center;
}
.metas{
  background-color: white;
  color: #163350;
  font-size: 30px;
  --dim:15;
  width: calc(var(--dim)*1%);
  height: calc(var(--dim)*2%);
  text-align: center;
  top: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
}


#BarraProgreso{
  height: 20%;
  margin-bottom: 0%;
  padding: 20px;
  color: white;
  text-align: center;
}
#barra{
  margin-left: 10%;
  border-width: 2px;
  border-color: black;
  border-style: solid;
  width: 80%;
  background-color:rgba(255, 255, 255, 0.7);
  text-align: center;
  border-radius: 10px;
}
#avance{
  /*background-color: rgb(21, 168, 226);*/
  background-color: rgb(50, 205, 159);
  color: rgb(8, 8, 8);
  font-size: 20px;
  --largo:0;
  width: calc(var(--largo)*1%);
  border-radius: 10px;
  
}
</style>
