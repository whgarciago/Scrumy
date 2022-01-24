<template>
  <div class="col-12 m-0 p-2 h-100 component">
    <div
      class=" d-flex flex-row col-12 m-0 mb-1 pb-2 align-items-center  titleMetas"
    >
      <h2>Sprints</h2>

      <button class=" ml-auto add" @click="openCreateSprintPopup()">
        <img src="../assets/addbutton.png" />
      </button>
    </div>

    <div class="d-flex flex-row col-12 h-75 m-0 p-0 justify-content-center">
      <!-- Lista de sprints lateral izquierdo-->
      <div
        class=" row col-8  overflow-auto align-items-center justify-content-center"
      >
        <div
          v-for="(sprint, index) in sprints"
          :sprint="sprint"
          :key="sprint.id"
        >
          <div
            class="card bg-light mb-3 h-"
            @click="openSprintInfoPopup()"
            style="max-width: 18rem;"
          >
            <div class="card-header">Sprint {{ index + 1 }}</div>
            <div class="card-body">
              <h5 class="card-title">Metas del Sprint</h5>
              <p class="card-text">
                Some quick example text to build on the card title and make up
                the bulk of the card's content.
              </p>
            </div>
            <div class="card-footer">
              Del {{ sprint.fechaInicio }} al {{ sprint.fechaFinalizacion }}
            </div>
          </div>
        </div>
      </div>

      <!-- Lista de metas lateral derecho-->
      <div class="col-4 scroll ml-3  ">
        {{this.seleccion}}
        <div v-for="goal in goals" :goal="goal" :key="goal.id">
          <div class="container goals-list" @click="selectGoal(goal)">
            {{ goal.nombre }}

            <select v-model="seleccion" class="float-right bl-1" name="select"  >
              <option
                v-for="(sprintLocal, index2) in sprints"
                :sprintLocal="sprintLocal"
                :key="sprintLocal.id"
                :value="sprintLocal.sprintID">{{sprintLocal.sprintID}}
              > {{index2+1}} </option>
            </select>
          </div>
        </div>
        <button @click="updateGoalSprint()"> gagaga</button>
      </div>
    </div>

    <div class="col-12 h-15 d-inline-block text-right"></div>

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
          min="2022-01-25"
        /><br />
        <label for="sprintDeadline">Fecha de culminación</label>
        <input
          type="date"
          id="sprintDeadline"
          v-model="sprintCreateForm.deadLine"
          min="2022-01-25"
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

    <div class="sprint-info-popup">
      <form
        class="form"
        id="formularioProyecto"
        @submit.prevent="createSprint()"
      >
        <button
          class="cancelarCrearMeta"
          type="button"
          @click="closeSprintInfoPopup()"
        >
          Cancelar
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
const pathGetSprints = "/sprints/all";
const pathCreateSprint = "/sprints/create";
const pathGetGoals = "/metas/all";
const pathUpdateGoalSprint = "/metas/update/sprint";

export default {
  name: "sprints",

  data() {
    return {
      proyectId: this.$store.state.activeProject,
      sprints: [],
      sprintsLength: 0,
      selectedGoalId:0,
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
  },

  methods: {

    selectGoal(goal){
      this.selectedGoalId=goal.metaID
      console.log('meta '+ this.selectedGoalId);
      console.log('sprint '+this.seleccion);
    },
    createSprint() {
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
          console.log(this.sprints);
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

    updateGoalSprint() {
      console.log('Penis');
      axios
        .put(
          this.$store.state.backURL + pathUpdateGoalSprint,
          {
            //idSprint: this.seleccion,
            idSprint: 312,
          },
          {
            params: {
              id: this.selectedGoalId
            },
          }
        )
        .then((response) => {})
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
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
  top: -10%;
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
</style>
