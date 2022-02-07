<template>
  <div class="col-12 m-0 p-0 mb-5 pb-5 h-100 component">
    <!--encabezado del componente-->
    <div
      class=" d-flex flex-row col-12 m-0 mb-1 pb-2 align-items-center border-bottom border-dark"
    >
      <h2
        data-bs-toggle="tooltip"
        data-bs-placement="right"
        title="Aquí puedes ver tus sprints y añadirles metas"
      >
        Retroalimentación
      </h2>
    </div>
    <!-- Fin encabezado -->
    <div class=" d-flex flex-row flex-wrap col-12 p-0 m-0 mb-3">
      <button
        v-for="(sprint, index) in sprints"
        :sprint="sprint"
        :key="sprint.id"
        @click="selectSprint(sprint, index + 1)"
        class=" metas btn btn-dark btn-sm col-12 col-sm-4"
        data-bs-toggle="tooltip"
        data-bs-placement="right"
        title="Selecciona este sprint para ver sus metas"
      >
        <h5>Sprint {{ index + 1 }}</h5>
      </button>
    </div>
    <!-- contenedor de sprints -->
    <div  class="mb-5 pb-5">
      <div
      class="card text mb-5 "
      v-for="goal in currentGoals"
      :goal="goal"
      :key="goal.MetaID"
    >
      <h3 class="card-header text-white bg-dark ">
        {{ goal.nombre }}
      </h3>
      <div class="card-body text-center">
        <div
          class="card actividades bg-light mb-3 d-inline-block mx-auto "
          v-for="actividad in actividadesUnArreglo"
          :goal="goal"
          :key="actividad.MetaID"
        >
          <div v-if="goal.metaID == actividad.idMeta" >
            <div class="card-header text-white bg-dark">
              {{ actividad.nombre }}
              <button
                v-show="actividad.estado"
                class="btn bg-success text-white btn-sm"
                data-bs-toggle="tooltip"
                data-bs-placement="right"
                title="Haz click aqui para marcar tu actividad como 'POR HACER'"
              >
                COMPLETADA
              </button>
              <button
                v-show="!actividad.estado"
                class="btn bg-danger text-white btn-sm"
                data-bs-toggle="tooltip"
                data-bs-placement="right"
                title="Haz click aqui para marcar tu actividad como 'COMPLETADA'"
              >
                POR HACER
              </button>
            </div>
            <div
              class="card-body"
              data-bs-toggle="tooltip"
              data-bs-placement="right"
              title="Haz click aqui para editar tu actividad"
            >
              <p class="card-text" v-if="actividad.dificultad">
                <b>DIFICULTAD: </b>{{ actividad.dificultad }}
              </p>
            </div>
          </div>
        </div>

        <div v-if="goal.actividades.length < 1" class="text-dark text-center">
          <h5> <b>No hay actividades en esta meta</b>  </h5>
        </div>
      </div>
      <div class="card-footer  text-white bg-dark">
        <b>DIFICULTAD: </b>{{ goal.dificultad }}
      </div>
    </div></div>
  </div>
</template>

<script>
import axios from "axios";

const pathGetProject = "/proyectos/find";
const pathGetSprints = "/sprints/all";
const pathGetGoals = "/metas/all";
const pathGetGoalsBySprintId = "/metas/findBySprint";
const pathGetActivitiesByMetaId = "/actividades/all";

export default {
  name: "sprints",

  data() {
    return {
      proyectId: this.$store.state.activeProject,
      sprints: [],
      proyecto: {},
      currentGoals: [
        {
          descripcion: "",
          dificultad: "",
          estado: "",
          idProyecto: "",
          idSprint: "",
          metaID: "",
          nombre: "",
          actividades: {},
        },
      ],
      actividades: [],
      actividadesUnArreglo: [],

      actualSprint: {
        fechaFinalizacion: "2022-02-17",
        fechaInicio: "2022-02-02",
        estado: false,
        idProyecto: 0,
        index: 0,
        sprintID: 0,
      },
      selectedGoalId: 0,
      goals: [],
    };
  },

  async created() {
    //obtiene los sprint del proyecto
    await this.getSprints();
  },
  beforeCreate() {
    //obtiene el proyecto asociado
    let proyectoid = localStorage.getItem("proyectoId");
    axios
      .get(this.$store.state.backURL + pathGetProject, {
        params: {
          id: proyectoid,
        },
      })
      .then((response) => {
        this.proyecto = response["data"];
      })
      .catch((response) => {
        alert("No es posible conectar con el backend en este momento");
      });
  },

  methods: {
    async selectSprint(sprint, index) {
      this.actividades = [];
      this.actualSprint = sprint;
      let date = new Date(this.actualSprint.fechaFinalizacion);
      this.actualSprint.fechaFinalizacion = date.toLocaleString();

      await this.getGoalsBySprintId(this.actualSprint.sprintID);

      for (let i = 0; i < this.currentGoals.length; i++) {
        await this.getActivitiesByMetaId(this.currentGoals[i].metaID);
        this.currentGoals[i].actividades = this.currentActivities;
      }
      this.actividadesUnArreglo = [];

      for (let i = 0; i < this.actividades.length; i++) {
        if (this.actividades[i].length > 0) {
          for (let j = 0; j < this.actividades[i].length; j++) {
            this.actividadesUnArreglo.push(this.actividades[i][j]);
          }
        }
      }

      console.log(this.currentGoals);
      this.actualSprint.index = index;
    },
    formatDate(date) {
      if (date < 10) {
        return "0" + date;
      } else {
        return date;
      }
    },
    getCurrentDate() {
      //regresa el tiempo actual en formato yyyy-mm-ddThh:mm:ss
      var today = new Date();
      let year = today.getFullYear();
      year = this.formatDate(year);
      let month = today.getMonth() + 1;
      month = this.formatDate(month);
      let day = today.getDate();
      day = this.formatDate(day);
      let hours = today.getHours();
      hours = this.formatDate(hours);
      let minutes = today.getMinutes();
      minutes = this.formatDate(minutes);
      let seconds = today.getSeconds();
      seconds = this.formatDate(seconds);

      var date = year + "-" + month + "-" + day;

      var time = hours + ":" + minutes + ":" + seconds;

      var dateTime = date + "T" + time;
      return dateTime;
    },

    sortSprints() {
      //ordena los sprint segun id
      for (let i = 0; i < this.sprints.length; i++) {
        for (let j = 0; j < this.sprints.length - i - 1; j++) {
          if (this.sprints[j + 1].sprintID < this.sprints[j].sprintID) {
            [this.sprints[j + 1].sprintID, this.sprints[j].sprintID] = [
              this.sprints[j].sprintID,
              this.sprints[j + 1].sprintID,
            ];
          }
        }
      }
    },

    async getSprints() {
      await axios
        .get(this.$store.state.backURL + pathGetSprints, {
          params: {
            id: this.$store.state.activeProject,
          },
        })
        .then((response) => {
          this.sprints = response["data"];
          this.sortSprints();
        })
        .catch((response) => {
          console.log(response);
          alert("No es posible conectar con el backend en este momento");
        });
    },

    async getActivitiesByMetaId(idMeta) {
      await axios
        //Se cargan las actividades de la meta actual
        .get(this.$store.state.backURL + pathGetActivitiesByMetaId, {
          params: {
            id: idMeta,
          },
        })
        .then((response) => {
          let actividades = response["data"];
          this.currentActivities = response["data"];
          this.actividades.push(actividades);
        })
        .catch((response) => {
          alert(
            "No es posible conectar con el backend en este momento 1 (SetAvance)"
          );
        });
    },

    async getGoalsBySprintId(sprintId) {
      await axios
        .get(this.$store.state.backURL + pathGetGoalsBySprintId, {
          params: {
            id: sprintId,
          },
        })
        .then((response) => {
          this.currentGoals = response["data"];
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },

    openCreateSprintPopup: function() {
      this.setPopupMinEndDate();
      this.setPopupMaxEndDate();
      document.querySelector(".create-sprint-popup").classList.add("active");
    },
    closeCreateSprintPopup: function() {
      document.querySelector(".create-sprint-popup").classList.remove("active");
    },

    openSprintInfoPopup: function() {
      document.querySelector(".sprint-info-popup").classList.add("active");
    },
    closeSprintInfoPopup: function() {
      document.querySelector(".sprint-info-popup").classList.remove("active");
    },

    setPopupMinEndDate() {
      let dateInput = document.getElementById("sprintDeadline");
      let fecha = this.getCurrentDate();
      fecha = fecha.slice(0, -3);
      dateInput.min = fecha;
    },
    setPopupMaxEndDate() {
      let dateInput = document.getElementById("sprintDeadline");
      dateInput.max = this.proyecto.fechaFin;
    },
    showCurrentGoals() {
      console.log(this.currentGoals);
      console.log(this.actividades);
    },
  },
};
</script>

<style scoped>
h2 {
  color: #fff;
}

.add {
  border: none;
  border-radius: 5px;
  color: #fff;
}

.create-sprint-popup,
.sprint-info-popup {
  z-index: 100;
  background: #fff;
  position: absolute;
  top: -10%;
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
  font-size: large;
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

.create-sprint-popup textarea,
.sprint-info-popup textarea {
  background-color: rgb(182, 182, 182);
  color: rgb(78, 78, 78);
  margin-top: 5px;
  margin-bottom: 5px;
  display: block;
  padding: 10px;
  outline: none;
  border: 1px solid #aaa;
  border-radius: 5px;
  font-size: large;
}

.create-sprint-popup.active,
.sprint-info-popup.active {
  display: block;
  background: #424242;
  top: -30%;
  left: 50%;
  opacity: 1;
  transform: translate(-50%, 50%) scale(1);
  transition: top 0ms ease-in-out 0ms, opacity 200ms ease-in-out 200 ms,
    transform 20ms ease-in-out 0 ms;
  color: white;
}

.crearMeta,
.cancelarCrearMeta {
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

.card {
  cursor: pointer;
}
.scroll {
  overflow-x: hidden; /* Hide horizontal scrollbar */
  overflow-y: scroll; /* Add vertical scrollbar */
}
.metas {
  overflow: hidden;
  color: #163350;
  font-size: 10px;
  --dim: 5;
  width: 30%;
  height: 35%;
  text-align: center;
  top: 100%;
  border-radius: 6px;
}
.metas h5 {
  color: rgb(255, 255, 255);
  font-size: 20px;
}
.actividades {
  height: 25%;
  -ms-grid-row-align: center;
}
.component {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  width: 100%;
  height: 100%;
}
</style>
