<template>
  <div class="col-12 m-0 p-2 h-100 component">
    <div
      class=" d-flex flex-row col-12 m-0 mb-1 pb-2 align-items-center border-bottom border-dark"
    >
      <h2
        data-bs-toggle="tooltip"
        data-bs-placement="right"
        title="Aquí puedes ver tus sprints y añadirles metas"
      >
        Sprints
      </h2>

      <button
        v-if="SprintActive == false"
        class=" ml-auto add"
        @click="openCreateSprintPopup()"
      >
        <img src="../assets/addbutton.png" />
      </button>
    </div>

    <div class="d-flex flex-row col-12 h-75 m-0 p-0">
      <div class="col-8 overflow-auto">
        <!-- Lista de sprints lateral izquierdo-->
        <div class="d-flex flex-row col-12 mb-2 p-0 overflow-auto">
          <!--Div de las metas del sprint para ordenar -->
          <div id="Metas">
            <!--El siguiente for recorre todas las metas en metas[] y los trae como divs e imprime su nombre-->
            <button
              v-for="(sprint, index) in sprints"
              :sprint="sprint"
              :key="sprint.id"
              @click="selectSprint(sprint, index + 1)"
              class=" metas btn btn-dark btn-sm"
              data-bs-toggle="tooltip"
              data-bs-placement="right"
              title="Selecciona este sprint para ver sus metas"
            >
              <h5>Sprint {{ index + 1 }}</h5>
            </button>
          </div>
        </div>
        <div
          v-if="actualSprint.index > 0"
          class="d-flex flex-column align-items-center justify-content-center"
        >
          <div
            class="col-12 mt-auto bg-dark border border-light text-light rounded "
          >
            <h5>Fecha de finalización: {{ actualSprint.fechaFinalizacion }}</h5>
          </div>
          <div
            class="card actividades bg-light mb-3 d-inline-block m-3"
            style="min-width:50%; max-width:70%"
          >
            <div class="card-header text-white bg-dark">
              Sprint {{ actualSprint.index }}
              <button
                v-if="actualSprint.estado == false"
                class=" float-right btn bg-success text-white btn-sm"
                data-bs-toggle="tooltip"
                data-bs-placement="right"
                title="El sprint está completado"
              >
                TERMINADO
              </button>
              <button
                v-if="actualSprint.estado == true"
                class="float-right btn bg-danger text-white btn-sm"
                @click="cambiarEstadoSprint()"
                data-bs-toggle="tooltip"
                data-bs-placement="right"
                title="Haz click aqui para marcar tu Sprint como 'COMPLETADO'"
              >
                COMPLETAR SPRINT
              </button>
            </div>
            <div class="card-body">
              <p class="card-text"><b>Metas del Sprint</b></p>
              <p v-if="currentGoals.length < 1">
                Este sprint no tiene metas añadidas
              </p>
              <button
                class=" btn btn-sm col-12 color mb-1"
                :disabled="!actualSprint.estado"
                @click="removeGoalFromSprint(goal)"
                v-for="(goal, index) in currentGoals"
                :goal="goal"
                :key="goal.MetaID"
              >
                {{ index + 1 }}. {{ goal.nombre }}
              </button>
            </div>
          </div>
        </div>
        
      </div>
      <!-- Lista de metas lateral derecho-->
      <div
        class="d-flex flex-column col-4 scroll p-0 pr-2 border-left border-dark"
        v-if="actualSprint.estado == true"
      >
        <div
          class="container bg-dark text-light text-center rounded border border-light m-1"
        >
          <h3 class="text-light">Metas</h3>
        </div>

        <br />
        <div v-for="goal in goals" :goal="goal" :key="goal.id">
          <button
            v-if="goal.idSprint == 0"
            class="btn btn-light border border-dark  col-12 m-1"
            @click="updateGoalSprint(goal)"
          >
            {{ goal.nombre }}
          </button>
        </div>
      </div>
      <div
        v-else
        class="d-flex flex column p-0 h-100 col-4 pr-2 border-left border-dark text-center align-items-center justify-content-center"
      >
        <h3 class="bg-dark text-light p-2 rounded m-auto border border-light">
          Este sprint no está activo
        </h3>
      </div>
      <div
        v-if="sprints.length < 1"
        class="d-flex flex column p-0 h-100 col-4 pr-2 border-left border-dark text-center align-items-center justify-content-center"
      >
        <h5
          class="bg-dark text-light p-2 rounded m-auto border border-light col-6"
        >
          Crea un sprint y añadele metas
        </h5>
      </div>
    </div>

    <div class="create-sprint-popup">
      <form
        class="form"
        id="formularioProyecto"
        @submit.prevent="createSprint()"
      >
        <h3>Nuevo Sprint</h3>
        <p>
          Cuando creas un sprint su fecha de inicio es la hora actual
        </p>
        <label for="sprintDeadline">Fecha de culminación</label>
        <input
          type="datetime-local"
          id="sprintDeadline"
          v-model="sprintCreateForm.deadLine"
          min="2022-01-24"
          max="2030-01-01"
          required
        /><br />
        <button class="crearMeta" type="submit">
          Crear
        </button>
        <button
          class="cancelarCrearMeta"
          type="button"
          @click="closeCreateSprintPopup()"
        >
          Cancelar
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const pathGetProject = "/proyectos/find";
const pathGetSprints = "/sprints/all";
const pathCreateSprint = "/sprints/create";
const pathGetGoals = "/metas/all";
const pathUpdateGoalSprint = "/metas/update/sprint";
const pathGetGoalsBySprintId = "/metas/findBySprint";
const pathChangeSprintState = "/sprints/updateState";
const pathUpdateSprint = "/sprints/update";

export default {
  name: "sprints",

  data() {
    return {
      proyectId: this.$store.state.activeProject,
      sprints: [],
      SprintActive: true,
      actualDate: "2022-01-24",
      proyecto: {},
      activeSprintId: 0,
      goalsBySprint: [],
      currentGoals: [],
      actualSprint: {
        fechaFinalizacion: "2022-02-17",
        fechaInicio: "2022-02-02",
        estado: false,
        idProyecto: 0,
        index: 0,
        sprintID: 0,
      },
      sprintsLength: 0,
      selectedGoalId: 0,
      goals: [],
      seleccion: 0,
      sprintCreateForm: {
        startDate: "",
        deadLine: "",
      },
    };
  },

  async created() {
    //Obtiene las metas del proyecto
    await this.loadGoals();
    //obtiene los sprint del proyecto
    await this.getSprints();
    //verifica si los sprint siguen activos
    this.getActiveSprint();
  },
  mounted() {
    this.getActiveSprint();
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
    selectGoal(goal) {
      this.selectedGoalId = goal.metaID;
    },
    selectSprint(sprint, index) {
      this.actualSprint = sprint;
      this.actualSprint.index = index;
      let date = new Date(this.actualSprint.fechaFinalizacion);
      this.actualSprint.fechaFinalizacion = date.toLocaleString();

      this.getGoalsBySprintId(this.actualSprint.sprintID);
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

    async createSprint() {
      let date = this.getCurrentDate();
      await axios
        .post(this.$store.state.backURL + pathCreateSprint, {
          idProyecto: this.proyectId,
          fechaInicio: date,
          fechaFinalizacion: this.sprintCreateForm.deadLine,
        })
        .then((response) => {
          this.sprintCreateForm.startDate = "";
          this.sprintCreateForm.deadLine = "";
          this.getSprints();
          this.getGoalsBySprintId(this.actualSprint.sprintID);
          this.closeCreateSprintPopup();
          this.getActiveSprint();
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
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
          this.getActiveSprint();
        })
        .catch((response) => {
          console.log(response);
          alert("No es posible conectar con el backend en este momento");
        });
    },

    async loadGoals() {
      await axios
        .get(this.$store.state.backURL + pathGetGoals, {
          params: {
            id: this.$store.state.activeProject,
          },
        })
        .then((response) => {
          this.goals = response["data"];
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },

    updateGoalSprint(goal) {
      this.selectGoal(goal);
      axios
        .put(
          this.$store.state.backURL + pathUpdateGoalSprint,
          {},
          {
            params: {
              id: this.selectedGoalId,
              sprintID: this.activeSprintId,
            },
          }
        )
        .then((response) => {
          this.loadGoals();
          this.getGoalsBySprintId(this.actualSprint.sprintID);
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },

    getGoalsBySprintId(sprintId) {
      axios
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

    removeGoalFromSprint(goal) {
      axios
        .put(
          this.$store.state.backURL + pathUpdateGoalSprint,
          {},
          {
            params: {
              id: goal.metaID,
              sprintID: 0,
            },
          }
        )
        .then((response) => {
          this.loadGoals();
          this.getGoalsBySprintId(this.actualSprint.sprintID);
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },
    getActiveSprint() {
      //el sprint activo siempre es el ultimo ya que solo puede haber uno a la vez

      if (this.sprints.length > 0) {
        if (this.sprints[this.sprints.length - 1].estado == true) {
          this.activeSprintId = this.sprints[this.sprints.length - 1].sprintID;
          this.SprintActive = true;
        }
      } else {
        this.SprintActive = false;
      }
    },

    async updateSprintEndDate() {
      let date = this.getCurrentDate();
      let sprint = this.sprints[this.sprints.length - 1];
      await axios
        .put(
          this.$store.state.backURL + pathUpdateSprint,
          {
            fechaFinalizacion: date,
            fechaInicio: sprint.fechaInicio,
            estado: sprint.estado,
            idProyecto: sprint.idProyecto,
            index: sprint.index,
          },
          { params: { id: this.activeSprintId } }
        )
        .then((response) => {
          this.getSprints();
          console.log(this.actualSprint.estado);
          this.actualSprint.estado = false;
          console.log(this.actualSprint.estado);
          this.SprintActive = true;
          console.log("Se cambió fecha del sprint");
        })
        .catch((response) => {
          console.log(response);
          alert("No es posible conectar con el backend en este momento 5");
        });
    },

    async cambiarEstadoSprint() {
      await this.updateSprintEndDate();
      await axios
        .put(
          this.$store.state.backURL + pathChangeSprintState,
          {},
          { params: { id: this.activeSprintId } }
        )
        .then((response) => {
          console.log(response);
          this.loadGoals();
          this.getSprints();
          this.actualSprint.estado = false;
          this.SprintActive = false;
        })
        .catch((response) => {
          console.log(response);
          alert("No es posible conectar con el backend en este momento 5");
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
.goals-list {
  background-color: #fff;
  color: black;
}
.scroll {
  overflow-x: hidden; /* Hide horizontal scrollbar */
  overflow-y: scroll; /* Add vertical scrollbar */
}
.color {
  border: 1px solid black;
  border-radius: 5px;
  color: black;
}
#Metas {
  height: 15%;
  width: 70%;
  text-align: center;
  padding: 1px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
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
