<template>
  <div class="col-12 m-0 p-2 h-100 component">
    <div class="col-12 h-15 d-inline-block">
      <h2>Metas pequeñas</h2>
    </div>
    <div class="row col-12 h-75 justify-content pl-5">
      <div
        v-for="(goal, index) in goals"
        :goal="goal"
        :indice="goal.index"
        :key="goal.id"
      >
        <div class="card text-center" style="width: 18rem; height:100% ">
          <div class="card-body" @click="abrirGoalPopup(goal)">
            <h3 class="card-subtitle mb-2 ">
              {{ index + 1 }}. {{ goal.nombre }}
            </h3>
          </div>
        </div>
      </div>
    </div>
    <div class="col-12 h-15 d-inline-block text-right">
      <button class=" mt-2 add" @click="abrirPopup()">
        <img src="../assets/addbutton.png" />
      </button>
    </div>

    <div class="create-goal-popup">
      <form class="form" id="formularioProyecto" onsubmit="return false">
        <h3>Nueva Meta Pequeña</h3>
        <label for="goalName">Nombre</label><br />
        <input
          type="text"
          id="goalName"
          placeholder="Nombre"
          v-model="formGoal.name"
          required
        />
        <label for="proyectDescription">Descripción</label><br />
        <textarea
          name="textDescription"
          id="goalDescription"
          class="  w-100 goalDescription"
          placeholder="Descripción"
          cols="30"
          rows="3"
          v-model="formGoal.description"
          required
        ></textarea>

        <br />
        <button class="crearMeta" @click="crearMeta()">Crear</button>
        <button class="cancelarCrearMeta" @click="cerrarPopup()">
          Cancelar
        </button>
      </form>
    </div>

    <div class="goal-popup">
      <h1>Nombre: {{ activeGoal.nombre }}</h1>
      <h4>Descripción: {{ activeGoal.descripcion }}</h4>
      <button class="cancelarCrearMeta" @click="cerrarGoalPopup()">
        Cerrar
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const pathCreate = "/metas/create";
const pathGet = "/metas/all";

export default {
  name: "SmallGoals",

  beforeCreate() {
    axios
      .get(this.$store.state.backURL + pathGet, {
        params: {
          id: 123,
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
        name: "",
        description: "",
        proyectId: this.proyectId,
        state: 1,
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
    abrirPopup: function() {
      document.querySelector(".create-goal-popup").classList.add("active");
    },
    cerrarPopup: function() {
      document.querySelector(".create-goal-popup").classList.remove("active");
    },

    abrirGoalPopup: function(goal) {
      this.activeGoal = goal;
      document.querySelector(".goal-popup").classList.add("active");
    },
    cerrarGoalPopup: function() {
      document.querySelector(".goal-popup").classList.remove("active");
    },
    crearMeta() {
      axios
        .post(this.$store.state.backURL + pathCreate, {
          nombre: this.formGoal.name,
          descripcion: this.formGoal.description,
          estado: this.formGoal.state,
          idProyecto: 123,
          idSprint: 1,
        })
        .then((response) => {
          console.log("meta creada");
          this.cargarMetas();
          this.formGoal.name = "";
          this.formGoal.description = "";
          this.formGoal.state = "";
          this.cerrarPopup();
          console.log(this.$store.state.activeProject + "``````");
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },

    cargarMetas() {
      axios
        .get(this.$store.state.backURL + pathGet, {
          params: {
            id: 123,
          },
        })
        .then((response) => {
          this.goals = response["data"];
          console.log("Metas conseguidas");
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

.create-goal-popup {
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
.create-goal-popup label {
  color: rgb(21, 73, 198, 0.6);
}
.create-goal-popup textarea {
  background-color: #7ba9d1;
  color: #fff;
  font-size: large;
  overflow: hidden;
  resize: none;
}
.create-goal-popup.active {
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

.crearMeta,
.cancelarCrearMeta {
  margin-left: 10px;
  width: 40%;
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
</style>
