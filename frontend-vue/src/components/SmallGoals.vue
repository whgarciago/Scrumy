<template>
  <div class="col-12 m-0 p-2 h-100 component">
    <div
      class=" d-flex flex-row col-12 m-0 mb-1 pb-2 align-items-center  titleMetas"
    >
      <h2>Sprints</h2>

      <button
        class=" ml-auto add"
        @click="abrirPopup()"
      >
        <img src="../assets/addbutton.png" />
      </button>
    </div>
    <div class="row col-12 h-75 justify-content-center pl-5 overflow-auto">
      <div
        v-for="goal in goals"
        :goal="goal"
        :indice="goal.index"
        :key="goal.id"
      >
        <div class="card bg-light mb-3" @click="abrirGoalPopup(goal)" style="width: 18rem;">
          <div class="card-header">{{goal.nombre}}</div>
          <div class="card-body">
            <p class="card-text">{{goal.descripcion}}
            </p>
          </div>
        </div>

      
      </div>
    </div>
    <div class="col-12 h-15 d-inline-block text-right">
      
    </div>

    <div class="create-goal-popup">
      <form class="form" id="formularioProyecto" onsubmit="return false">
        <h3>Nueva Meta</h3>
        <label for="goalName">Nombre</label><br />
        <input
          type="text"
          name="goalName"
          id="goalName"
          placeholder="Nombre"
          v-model="formGoal.name"
          required
        />
        <label for="proyectDescription">Descripción</label><br />
        <textarea
          name="textDescription"
          id="goalDescription"
          class="w-100 goalDescription"
          placeholder="Descripción"
          cols="30"
          rows="3"
          v-model="formGoal.description"
          required
        ></textarea>

        <br />

        <button class="crearMeta" type="submit" @click="crearMeta()">
          Crear
        </button>
        <button class="cancelarCrearMeta" type="button" @click="cerrarPopup()">
          Cancelar
        </button>
      </form>
    </div>

    <div class="goal-popup">
      <h1>Nombre: {{ activeGoal.nombre }}</h1>
      <h4>Descripción: {{ activeGoal.descripcion }}</h4>
      <h5 v-if="activeGoal.dificultad != null">
        Dificultad: {{ activeGoal.dificultad }}
      </h5>
      <button class="difficulties-button" @click="openDifficultyPopup()">
        Dificultad
      </button>
      <button class="cancelarCrearMeta" @click="cerrarGoalPopup()">
        Cerrar
      </button>
      <button class="edit-goal-button" @click="openEditGoalPopup()">
        <img src="https://img.icons8.com/material/32/000000/edit--v1.png" />
      </button>
    </div>

    <div class="goal-difficulty-popup">
      <h1>Añadir Dificultad</h1>
      <textarea
        name="textDescription"
        id="goalDescription"
        class="  w-100 goalDescription"
        :placeholder="[[activeGoal.dificultad]]"
        cols="30"
        rows="3"
        v-model="activeGoal.dificultad"
        required
      ></textarea>
      <button class="cancelarCrearMeta" @click="saveDifficultyPopup()">
        Guardar
      </button>
      <button class="difficulties-button" @click="closeDifficultyPopup()">
        Cancelar
      </button>
      <button class="delete-goal-button" @click="deleteDifficulty()">
        <img src="https://img.icons8.com/material/32/000000/delete--v1.png" />
      </button>
    </div>

    <div class="update-goal-popup">
      <form class="form" id="editGoalForm" onsubmit="return false">
        <h3>Modificar Meta</h3>
        <label for="goalName">Nombre</label><br />
        <input
          type="text"
          name="editGoalName"
          id="editGoalName"
          placeholder="Nombre"
          v-model="editGoal.name"
          required
        />
        <label for="proyectDescription">Descripción</label><br />
        <textarea
          name="editGoalDescription"
          id="editGoalDescription"
          class="w-100 goalDescription"
          placeholder="Descripción"
          cols="30"
          rows="3"
          v-model="editGoal.description"
          required
        ></textarea>

        <br />

        <button class="crearMeta" type="submit" @click="editGoalInBackend()">
          Guardar
        </button>
        <button
          class="cancelarCrearMeta"
          type="button"
          @click="closeEditGoalPopup()"
        >
          Cancelar
        </button>
        <button class="delete-goal-button" @click="deleteGoal()">
          <img src="https://img.icons8.com/material/32/000000/delete--v1.png" />
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const pathCreate = "/metas/create";
const pathGet = "/metas/all";
const pathUpdateDifficulty = "/metas/updateDificulties";
const pathDeleteGoal = "/metas/remove";
const pathUpdateGoal = "/metas/update";

export default {
  name: "SmallGoals",

  beforeCreate() {
    axios
      .get(this.$store.state.backURL + pathGet, {
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

  data() {
    return {
      proyectId: this.$store.state.activeProject,
      path: "",
      goals: [""],
      activeGoal: {
        //this is filled with the information of a goal when the user clicks the card of a goal
        actividadID: 0,
        descripcion: "",
        dificultad: "",
        estado: 0,
        idProyecto: 0,
        idSprint: 0,
        metaID: 0,
        nombre: "",
      },
      editGoal: {
        name: "",
        description: "",
        proyectId: this.proyectId,
        state: 0,
      },

      formGoal: {
        name: "",
        description: "",
        proyectId: this.proyectId,
        state: 0,
      },
    };
  },

  methods: {
    abrirPopup: function() {
      document.querySelector(".create-goal-popup").classList.add("active");
    },
    cerrarPopup: function() {
      document.querySelector(".create-goal-popup").classList.remove("active");
    },
    openDifficultyPopup: function() {
      document.querySelector(".goal-difficulty-popup").classList.add("active");
      document.querySelector(".goal-popup").classList.remove("active");
    },
    closeDifficultyPopup: function() {
      document
        .querySelector(".goal-difficulty-popup")
        .classList.remove("active");
    },

    openEditGoalPopup: function() {
      document.querySelector(".update-goal-popup").classList.add("active");
    },
    closeEditGoalPopup: function() {
      document.querySelector(".update-goal-popup").classList.remove("active");
    },
    editGoalInBackend: function() {
      axios
        .put(
          this.$store.state.backURL + pathUpdateGoal,
          {
            nombre: this.editGoal.name,
            descripcion: this.editGoal.description,
            estado: this.formGoal.state,
            idProyecto: this.$store.state.activeProject,
            idSprint: 0,
          },
          {
            params: {
              id: this.activeGoal.metaID,
            },
          }
        )
        .then((response) => {
          this.cargarMetas();
          this.closeEditGoalPopup();
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },
    deleteGoal: function() {
      axios
        .delete(this.$store.state.backURL + pathDeleteGoal, {
          params: {
            id: this.activeGoal.metaID,
          },
        })
        .then((response) => {
          this.cargarMetas();
          this.cerrarGoalPopup();
          this.closeEditGoalPopup();
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },
    deleteDifficulty: function() {
      axios
        .put(
          this.$store.state.backURL + pathUpdateDifficulty,
          {
            dificultad: null,
          },
          {
            params: {
              id: this.activeGoal.metaID,
            },
          }
        )
        .then((response) => {
          this.cargarMetas();
          this.closeDifficultyPopup();
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },
    abrirGoalPopup: function(goal) {
      this.activeGoal = goal;

      document.querySelector(".goal-popup").classList.add("active");
    },
    cerrarGoalPopup: function() {
      document.querySelector(".goal-popup").classList.remove("active");
    },

    saveDifficultyPopup: function() {
      axios
        .put(
          this.$store.state.backURL + pathUpdateDifficulty,
          {
            dificultad: this.activeGoal.dificultad,
          },
          {
            params: {
              id: this.activeGoal.metaID,
            },
          }
        )
        .then((response) => {
          this.closeDifficultyPopup();
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },
    crearMeta() {
      axios
        .post(this.$store.state.backURL + pathCreate, {
          nombre: this.formGoal.name,
          descripcion: this.formGoal.description,
          estado: this.formGoal.state,
          idProyecto: this.$store.state.activeProject,
          idSprint: 0,
        })
        .then((response) => {
          this.cargarMetas();
          this.formGoal.name = "";
          this.formGoal.description = "";
          this.formGoal.state = "";
          this.cerrarPopup();
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },
    cargarMetas() {
      axios
        .get(this.$store.state.backURL + pathGet, {
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

.create-goal-popup,
.goal-difficulty-popup,
.update-goal-popup {
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
.create-goal-popup label,
.goal-difficulty-popup,
.update-goal-popup {
  color: rgb(21, 73, 198, 0.6);
}
.create-goal-popup textarea,
.goal-difficulty-popup textarea,
.update-goal-popup textarea {
  background-color: #7ba9d1;
  color: #fff;
  font-size: large;
  overflow: hidden;
  resize: none;
  border: 1px solid #aaa;
  border-radius: 5px;
}
.create-goal-popup.active,
.goal-difficulty-popup.active,
.update-goal-popup.active {
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
.difficulties-button {
  margin-left: 2px;
  width: 30%;
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
.delete-goal-button,
.edit-goal-button {
  margin-left: 5px;
  width: 50px;
  padding: 2px;
  border: none;
  outline: none;
  font-size: 15px;
  background: rgb(123, 163, 209, 0.5);
  color: black;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  float: right;
}

.component {
  z-index: 0;
}

.goal-popup {
  z-index: 100;
  background: #fff;
  position: absolute;
  top: 10%;
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
.goal-popup label {
  color: rgb(21, 73, 198, 0.6);
}
.goal-popup textarea {
  background-color: #7ba9d1;
  color: #fff;
  font-size: large;
  border: 1px solid #aaa;
  border-radius: 5px;
}
.goal-popup.active {
  color: rgb(21, 73, 198, 0.6);
  display: block;
  background: #fff;
  top: 10%;
  left: 50%;
  opacity: 1;
  transform: translate(-50%, 50%) scale(1);
  transition: top 0ms ease-in-out 0ms, opacity 200ms ease-in-out 200 ms,
    transform 20ms ease-in-out 0 ms;
}
.card {
  cursor: pointer;
  color: rgb(21, 73, 198, 0.6);
}
.titleMetas {
  border-bottom: 2px solid rgb(156, 156, 156);
  margin-bottom: 5px;
}
</style>
