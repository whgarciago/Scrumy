<template>
  <div id="PlanDeOrganizacion"> <!--Div grande que abarca toda la pagina -->

    <!--Div del Titulo y el selector de sprints -->
    <div class="col-12 h-15 d-inline-block m-2 titleMetas">
      <h2 data-bs-toggle="tooltip" data-bs-placement="right" title="Aqui puedes ver el avance que llevas por cada sprint y por cada meta">Avance</h2>
    </div>
    <h5 id= "escoge">Escoge el sprint</h5>
    <select name="menu" id="menu" @change="TraerMetasDelSprint($event)" v-model="key" data-bs-toggle="tooltip" data-bs-placement="right" title="Elige un sprint"> <!--Selector de sprints -->s
          <!--El siguiente for recorre todos los sprints en sprints[], los trae como opciones del selector y muestra su nombre-->
          <option v-for="spr in sprints" :key="spr.sprintID"  selected="selected" >Sprint {{sprints.indexOf(spr) +1 }}</option>
          {{ActualizarBarra()}}
        </select>

    <!--Div de las metas del sprint para ordenar -->
    <div id="Plan">
      <!--El siguiente for recorre todas las metas en metas[] y los trae como divs e imprime su nombre-->
        <div v-for="meta   in metas" :key="meta.id" class="metas d-flex flex-column"> 
          <h2 class="bg-dark p-0">{{meta.nombre}}</h2>
          <h3 data-bs-toggle="tooltip" data-bs-placement="right" title="Este es el avance de esta meta" class="p-0">
            
          </h3>
        </div>
    </div>

    <!--Div de la barra de progreso del sprint -->
    <div id="BarraProgreso" data-bs-toggle="tooltip" data-bs-placement="right" title="Este es el avance de tu sprint">
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
      valid: 0,

    }
  },
  methods:{
    //Retorna el avance individual
    async AvanceMeta(idMeta){
      let pathActividades = "/metas/avanceMeta";
      await axios
      //Se cargan las actividades de la meta actual
      .get(this.$store.state.backURL + pathActividades, {
        params: {
          id: idMeta,
        },
      })
      .then((response) => {
        this.valid = response["data"];
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento (SetAvance)");
      });

    },
    //
    async SetAvance(idMeta,i){
      var suma=0; //Avance de la meta
      await this.AvanceMeta(idMeta)
      console.log(this.valid);
      this.avances.push({key: idMeta , value: this.valid});
      //console.log('Se ejecuto SEt avance');+
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
    TraerMetasDelSprint(event){ //Trae las metas del Sprint que se le pasa como atributo y las almacena en metas[]
      this.avances = [];
      this.avance = 0;
      document.getElementById("avance").style.setProperty('--largo',this.avance); //Actualiza la barra de progreso
      //Recorre los sprints y selecciona el que escogio el ususario
      for (let i = 0; i < this.sprints.length; i++) {
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

        //this.avances = new Array(this.metas.length);

        //console.log(this.metas.length);
        for (let i = 0; i < this.metas.length; i++) {
          this.SetAvance(this.metas[i].metaID,i );
        }
        console.log(this.avances);
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento");
      });
      //this.SetAvance();//Actualiza la barra con el avance del sprint seleccionado
    },
    ActualizarBarra() {
      if(this.avances.length != 0 && this.avances.length==this.metas.length){
      var sum = 0;
      var avanceMeta = document.getElementById("Plan").childNodes;

        //console.log(this.avances.length);
        for (let index = 0; index < this.metas.length; index++) {
          var cambio=0;
          for (let j = 0; j < this.avances.length; j++) {
            if(this.avances[j].key==this.metas[index].metaID){
              cambio=j;
            }
            
          }
          sum += this.avances[cambio].value;
          var primerHijo = avanceMeta[index].firstChild;
          var segundoHijo = primerHijo.nextSibling;
          if(this.avances[cambio].value==100){
            avanceMeta[index].style.backgroundColor= "#32cd9f";
            primerHijo.style.backgroundColor="#32cd9f";
          }
          if(this.avances[cambio].value!=0){
            segundoHijo.innerHTML = this.avances[cambio].value.toFixed(2) + '%';
          }
          if(this.avances[cambio].value==100){
            segundoHijo.innerHTML = "¡COMPLETADA!";
          }
          segundoHijo.style.setProperty('--variable',this.avances[cambio].value); 

        }
      //Barras individuales

      //Actualizamos barra sprint
      sum= sum/this.avances.length; //Divide la suma en la cantidad de metas, para hacer un promedio
      this.avance= sum.toFixed(2);//Modifica el avance con 2 digitos
      document.getElementById("avance").style.setProperty('--largo',this.avance); //Actualiza la barra de progreso
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
h2{
  color: white !important;

}


#Sprints{
  height: 15%;
  text-align: left;
  padding: 15px;
  color: white;
}
#Sprints h2{display: inline;  color: white;} 
#menu {
  position :absolute;
  top :11%;
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
  border-width: 1px;
  border-color: white;
  border-style: solid;
  overflow: hidden;
  color: #163350;
  font-size: 15px;
  --dim:15;
  width: calc(var(--dim)*2%);
  height: calc(var(--dim)*2%);
  text-align: center;
  top: 50%;
  border-radius: 6px;

}
.metas h3 {
  --variable:0;
  background-color: #32cd9f;
  height: 100%;
  width: calc(var(--variable)*1%);
  border-radius: 3px;
  font-size: auto;
  color: white;
}
.metas h2{
  color: rgb(255, 255, 255);
  font-size: 25px;
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
.titleMetas {
  border-bottom: 2px solid rgb(156, 156, 156);
  margin-bottom: 5px;
}

</style>
