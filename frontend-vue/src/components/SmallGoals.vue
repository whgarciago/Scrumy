<template>
  <div class="col-12 m-0 p-2 h-100 component">
    <div
      class=" d-flex flex-row col-12 m-0 mb-1 pb-2 align-items-center  border-bottom border-dark"
    >
      <h2>Metas</h2>

      <button class=" ml-auto add" @click="abrirPopup()">
        <img src="../assets/addbutton.png" />
      </button>
    </div>
    <div class="row col-12 h-75 m-0 overflow-auto justify-content-center">
      <div
        class="card actividades bg-light mb-3 d-inline-block m-3"
        style="width: 25rem;"
        v-for="goal in goals"
        :goal="goal"
        :indice="goal.index"
        :key="goal.id"
        @click="abrirGoalPopup(goal)"
      >
        <div class="card-header text-white bg-dark">
          {{ goal.nombre }}
        </div>
        <div
          class="card-body"
          data-bs-toggle="tooltip"
          data-bs-placement="right"
          title="Haz click aqui para editar tu actividad"
        >
          <p class="card-text"><b>DESCRIPCIÓN:</b> {{ goal.descripcion }}</p>
          <p class="card-text" v-if="goal.dificultad">
            <b>DIFICULTAD: </b>{{ goal.dificultad }}
          </p>
        </div>
      </div>
    </div>

    <div class="create-goal-popup col-12 col-sm-8">
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
        <div class="col-12 ">
          <button
            class="btn crearMeta col-12 col-sm-4 m-1"
            type="submit"
            @click="crearMeta()"
          >
            Crear
          </button>
          <button
            class="btn cancelarCrearMeta col-12 col-sm-4"
            type="button"
            @click="cerrarPopup()"
          >
            Cancelar
          </button>
        </div>
      </form>
    </div>

    <div class="goal-popup col-12 col-sm-8">
      <h3>Nombre: {{ activeGoal.nombre }}</h3>
      <h4>Descripción: {{ activeGoal.descripcion }}</h4>
      <h5 v-if="activeGoal.dificultad != null">
        Dificultad: {{ activeGoal.dificultad }}
      </h5>
      <button class="btn difficulties-button col-12 col-sm-4 m-1" @click="openDifficultyPopup()">
        Dificultad
      </button>
      <button class="btn cancelarCrearMeta col-12 col-sm-4" @click="cerrarGoalPopup()">
        Cerrar
      </button>
      <button class=" btn edit-goal-button col-12 col-sm-2 float-right" @click="openEditGoalPopup()">
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
      <button class="btn cancelarCrearMeta col-12 col-sm-4 m-1" @click="saveDifficultyPopup()">
        Guardar
      </button>
      <button class="btn difficulties-button col-12 col-sm-4" @click="closeDifficultyPopup()">
        Cancelar
      </button>
      <button class="btn delete-goal-button col-12 col-sm-2 float-right" @click="deleteDifficulty()">
        <img src="https://img.icons8.com/material/32/000000/delete--v1.png" />
      </button>
    </div>

    <div class="update-goal-popup col-12 col-sm-8">
      <form class="form" id="editGoalForm" onsubmit="return false">
        <h3>Modificar Meta</h3>
        <label for="goalName">Nombre</label><br />
        <input
          type="text"
          name="editGoalName"
          id="editGoalName"
          :placeholder="[[activeGoal.nombre]]"
          v-model="editGoal.name"
          required
        />
        <label for="proyectDescription">Descripción</label><br />
        <textarea
          name="editGoalDescription"
          id="editGoalDescription"
          class="w-100 goalDescription"
          :placeholder="[[activeGoal.descripcion]]"
          cols="30"
          rows="3"
          v-model="editGoal.description"
          required
        ></textarea>

        <br />

        <button class="btn crearMeta col-12 col-sm-4 m-1" type="submit" @click="editGoalInBackend()">
          Guardar
        </button>
        <button
          class="btn cancelarCrearMeta col-12 col-sm-4"
          type="button"
          @click="closeEditGoalPopup()"
        >
          Cancelar
        </button>
        <button class="btn delete-goal-button col-12 col-sm-2 float-right" @click="deleteGoal()">
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
        state: 1,
      },
    };
  },

  methods: {
    
    abrirGoalPopup: function(goal) {
      this.activeGoal = goal;

      document.querySelector(".goal-popup").classList.add("active");
    },
    cerrarGoalPopup: function() {
      document.querySelector(".goal-popup").classList.remove("active");
    },
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
      this.cerrarGoalPopup()
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
          dificultad: " ",
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
.add {
  border: none;
  border-radius: 5px;
  color: #fff;
}

.create-goal-popup,
.goal-difficulty-popup,
.update-goal-popup {
  z-index: 100;
  background: #424242;
  position: absolute;
  top: -10%;
  left: 50%;
  opacity: 0;
  display: none;
  transform: translate(-50%, 50%) scale(1.25);
  width: 500px;
  padding: 20px 30px;
  box-shadow: 2px 2px 5px 5px rgba(0, 0, 0, 0.15);
  border-radius: 10px;
  transition: top 0ms ease-in-out 200ms, opacity 200ms ease-in-out 200 ms,
    transform 20ms ease-in-out 0 ms;
  font-size: large;
}

.create-goal-popup.active,
.goal-difficulty-popup.active,
.update-goal-popup.active {
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
textarea {
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

.crearMeta,
.cancelarCrearMeta,
.difficulties-button {
  font-size: 15px;
  background: rgb(182, 182, 182);
  color: rgb(255, 255, 255);
  font-weight: 600;
  transition: 0.5s;
}
.delete-goal-button,
.edit-goal-button {
  font-size: 15px;
  background: rgb(182, 182, 182);
  color: rgb(255, 255, 255);
  font-weight: 600;
  transition: 0.5s;
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

.goal-popup.active {
  display: block;
  background: #424242;
  top: 1%;
  left: 50%;
  opacity: 1;
  transform: translate(-50%, 50%) scale(1);
  transition: top 0ms ease-in-out 0ms, opacity 200ms ease-in-out 200 ms,
    transform 20ms ease-in-out 0 ms;
  color: white;
}
.card {
  cursor: pointer;
}
.actividades {
  height: 50%;
  -ms-grid-row-align: center;
}
</style>
