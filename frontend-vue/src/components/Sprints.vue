<template>
  <div class="col-12 m-0 p-2 h-100 component">
    <div
      class=" d-flex flex-row col-12 m-0 mb-1 pb-2 align-items-center  titleMetas"
    >
      <h2>Sprints</h2>

      <button
        v-if="sprints.length < 1"
        class=" ml-auto add"
        @click="openCreateSprintPopup()"
      >
        <img src="../assets/addbutton.png" />
      </button>
    </div>

    <div class="d-flex flex-row col-12 h-75 m-0 p-0 justify-content-center">
      <!-- Lista de sprints lateral izquierdo-->
      <div
        class=" column col-8 overflow-auto align-items-center justify-content-center"
      >
        <button
          class="btn btn-primary mb-2 mx-1"
          v-for="(sprint, index) in sprints"
          :sprint="sprint"
          :key="sprint.id"
          @click="selectSprint(sprint, index + 1)"
        >
          Sprint {{ index + 1 }}
        </button>
        <div class="d-flex align-items-center justify-content-center">
          <div
            v-if="actualSprint.index > 0"
            class="card bg-light mb-3 "
            style="max-width: 18rem;"
          >
            <div class="card-header">Sprint {{ actualSprint.index }}</div>
            <div class="card-body">
              <h5 class="card-title">Metas del Sprint</h5>
              <button
                class=" btn btn-sm col-12 color mb-1"
                @click="removeGoalFromSprint(goal)"
                v-for="(goal, index) in currentGoals"
                :goal="goal"
                :key="goal.MetaID"
              >
                {{ index + 1 }}. {{ goal.nombre }}
              </button>
            </div>
            <div
              class="card-footer m-auto"
              v-for="(sprint, index) in sprints"
              :sprint="sprint"
              :key="sprint.id"
            >
              Inicio {{ sprints[index].fechaInicio }} Fin {{ sprints[index].fechaFinalizacion }}
            </div>
          </div>
        </div>
      </div>

      <!-- Lista de metas lateral derecho-->
      <div class="col-4 scroll ml-3 ">
        <div class="container goals-title goals-list text-center m-1">
          Metas Sin Sprint
        </div>
        <br />
        <div v-for="goal in goals" :goal="goal" :key="goal.id">
          <button
            v-if="goal.idSprint == 0  && activeSprintId>0"
            class="btn goals-list col-12 m-1"
            @click="updateGoalSprint(goal)"
          >
            {{ goal.nombre }}
          </button>
        </div>
      </div>
    </div>

    <div class="col-12 h-15 d-inline-block text-right">
      <button @click="getActiveSprint()">Pruebas</button>
    </div>

    <div class="create-sprint-popup">
      <form
        class="form"
        id="formularioProyecto"
        @submit.prevent="createSprint()"
      >
        <h3>Nuevo Sprint</h3>

        <label for="sprintStartDate">Fecha de Inicio</label>
        <input
          type="date"
          id="sprintStartDate"
          v-model="sprintCreateForm.startDate"
          min="2022-01-24"
          max="2030-01-01"
        /><br />
        <label for="sprintDeadline">Fecha de culminación</label>
        <input
          type="date"
          id="sprintDeadline"
          v-model="sprintCreateForm.deadLine"
          min="2022-01-24"
          max="2030-01-01"
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
    <div class="col-12 h-15 d-inline-block text-right"></div>
  </div>
</template>

<script>
import axios from "axios";

const pathGetSprints = "/sprints/all";
const pathCreateSprint = "/sprints/create";
const pathGetGoals = "/metas/all";
const pathUpdateGoalSprint = "/metas/update/sprint";
const pathGetGoalsBySprintId = "/metas/findBySprint";

export default {
  name: "sprints",

  data() {
    return {
      proyectId: this.$store.state.activeProject,
      sprints: [],
      actualDate: "2022-01-24",
      activeSprintId: 0,
      goalsBySprint: [],
      currentGoals: [],
      actualSprint: {
        index: 0,
        sprintID: 0,
        startDate: "",
        deadLine: "",
        proyectId: "",
        goals: [],
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

  created() {
    this.loadGoals();
    this.getSprints();
    this.getActiveSprint();
  },
  beforeMount() {
    this.getActiveSprint();
  },

  methods: {
    selectGoal(goal) {
      this.selectedGoalId = goal.metaID;
    },
    selectSprint(sprint, index) {
      this.actualSprint = sprint;
      this.actualSprint.index = index;
      this.getGoalsBySprintId(this.actualSprint.sprintID);
      this.getActiveSprint();
    },
    createSprint() {
      this.setMinDate();
      axios
        .post(this.$store.state.backURL + pathCreateSprint, {
          idProyecto: this.proyectId,
          fechaInicio: this.sprintCreateForm.startDate,
          fechaFinalizacion: this.sprintCreateForm.deadLine,
        })
        .then((response) => {
          this.sprintCreateForm.startDate = "";
          this.sprintCreateForm.deadLine = "";
          this.getSprints();
          this.getGoalsBySprintId(this.actualSprint.sprintID);

          this.getActiveSprint();
          this.closeCreateSprintPopup();
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },
    getSprints() {
      axios
        .get(this.$store.state.backURL + pathGetSprints, {
          params: {
            id: this.$store.state.activeProject,
          },
        })
        .then((response) => {
          this.sprints = response["data"];
          this.getActiveSprint();
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },

    loadGoals() {
      axios
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

    getCurrentDate() {
      var today = new Date();
      var dd = today.getDate();
      var mm = today.getMonth() + 1; //January is 0!
      var yyyy = today.getFullYear();
      today = yyyy + "-" + mm + "-" + dd;
      return [yyyy, mm, dd];
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
      let today = this.getCurrentDate();

      for (let index = 0; index < this.sprints.length; index++) {
        let fechainicioString = this.sprints[index].fechaInicio;
        let fechaFinString = this.sprints[index].fechaFinalizacion;

        let startYear = parseInt(fechainicioString.substring(0, 4), 10);
        let startMonth = parseInt(fechainicioString.substring(5, 7), 10);
        let startDay = parseInt(fechainicioString.substring(8, 10), 10) + 1;
        console.log(startYear + " " + today[0]);
        console.log(startMonth + " " + today[1]);
        console.log(startDay + " " + today[2]);

        let yyyy = parseInt(fechaFinString.substring(0, 4), 10);
        let mm = parseInt(fechaFinString.substring(5, 7), 10);
        let dd = parseInt(fechaFinString.substring(8, 10), 10) + 1;
        console.log(yyyy + " " + today[0]);
        console.log(mm + " " + today[1]);
        console.log(dd + " " + today[2]);

        if (startYear >= today[0] && today[0] <= yyyy) {
          if (startMonth >= today[1] && today[1] <= mm) {
            if (startDay >= today[2] && today[2] <= dd) {
              console.log(true);
              this.activeSprintId = this.sprints[index].sprintID;
              console.log(this.activeSprintId);
            } else {
              console.log("false 1");
            }
          } else {
            console.log("false 2");
          }
        }
      }
    },

    openCreateSprintPopup: function() {
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
    setMinDate() {
      let input = document.getElementById("sprintStartDate");
      this.getCurrentDate();
      input.setAttribute("min", this.actualDate);
    },
  },
};
</script>

<style scoped>
h2 {
  color: #fff;
}

h3 {
  color: #7ba9d1;
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
.create-sprint-popup,
.sprint-info-popup {
  color: rgb(21, 73, 198, 0.6);
}
.create-sprint-popup textarea,
.sprint-info-popup textarea {
  background-color: #7ba9d1;
  color: #fff;
  font-size: large;
  overflow: hidden;
  resize: none;
  border: 1px solid #aaa;
  border-radius: 5px;
}
.create-sprint-popup.active,
.sprint-info-popup.active {
  color: rgb(21, 73, 198, 0.6);
  display: block;
  background: #fff;
  top: -30%;
  left: 50%;
  opacity: 1;
  transform: translate(-50%, 50%) scale(1);
  transition: top 0ms ease-in-out 0ms, opacity 200ms ease-in-out 200 ms,
    transform 20ms ease-in-out 0 ms;
}

.crearMeta,
.cancelarCrearMeta {
  margin-left: 5px;
  width: 35%;
  padding: 12px 0px 10px 5px;
  border: none;
  outline: none;
  font-size: 15px;
  background: rgb(123, 163, 209, 0.5);
  color: black;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
}

.card {
  cursor: pointer;
  color: rgb(21, 73, 198, 0.6);
}
.titleMetas {
  border-bottom: 2px solid rgb(156, 156, 156);
  margin-bottom: 5px;
}
.goals-list {
  background-color: #fff;
  color: #1549c699;
}
.scroll {
  overflow-x: hidden; /* Hide horizontal scrollbar */
  overflow-y: scroll; /* Add vertical scrollbar */
}
select {
  border: 0;
  color: #1549c699;
  outline: 0px;
}
.color{
  border: 1px solid rgb(21, 73, 198, 0.6);
  border-radius: 5px;
  color: #1549c699;
}
</style>
