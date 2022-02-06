<template>
  <div class="row col-12 vh-100 whole-page m-0 p-0 ">
    <!-- Logo, botones de proyectos y usuario -->
    
    <div
      class="col-6 p-0 m-0 d-flex flex-column justify-content-center align-items-center m-0 p-0 spacing-to-navbar"
    >
      <h2 class="align-self-start text-primary">Información de Usuario</h2>
      <form
        class="border border-primary rounded form-inline"
        @submit="updateUserData()"
      >
        <h3 class="col-12 text-center text-primary mt-3 mb-5 ">
          Modificar Datos
        </h3>

        <div class="form-group col-12">
          <label class="custom-label col-md-3" for="username"
            >Nombre de Usuario</label
          >
          <input
            id="username"
            class="form-control col-12 col-sm-10 col-md-7 offset-sm-1"
            type="text"
            placeholder="Nombre de Usuario"
            v-model="userName"
            required
          />
        </div>

        <div class="form-group col-12">
          <label class="custom-label col-md-3" for="names">Email</label>
          <input
            id="names"
            class="form-control col-12 col-sm-10 col-md-7 offset-sm-1"
            type="email"
            placeholder="Ingresa tu correo electrónico"
            v-model="email"
            required
          />
        </div>

        <div class="form-group col-12">
          <label class="custom-label col-md-3 display" for="password"
            >Nueva Contrase&ntilde;a</label
          >
          <input
            id="password"
            class="form-control col-12 col-sm-10 col-md-7 offset-sm-1"
            type="password"
            placeholder="Contraseña"
            v-model="password"
            required
          />
        </div>
        <div class="col-12 col-sm-5 col-md-4 mb-3">
          <button
            class="col-sm-10 col-md-10 offset-sm-1 offset-md-2 btn btn-primary"
            type="submit"
          >
            Registrar
          </button>
        </div>
      </form>
      <button @click="pushToHome()" class="btn btn-primary my-5">
        Regresar
      </button>
    </div>
    <div class="col-3 p-0 m-0 spacing-to-navbar"></div>
  </div>
</template>

<script>
import axios from "axios";
const path = "/usuarios/find";
const pathUpdateUser = "/usuarios/update";

export default {
  name: "settings",

  data() {
    return {
      userName: "",
      email: "",
      password: "",
      userId: 238,
      cPassword: "",
      userId: localStorage.getItem("usuarioID"),
      user: {
        contraseña: "",
        correo: "",
        idProyectos: 0,
        nombre: "",
        usuarioID: 0,
      },
    };
  },
  created() {
    this.getUserData();
  },
  methods: {
    updateUserData() {
      axios
        .put(
          this.$store.state.backURL + pathUpdateUser,
          {
            nombre: this.userName,
            correo: this.user.correo,
            contraseña: this.password,
            idProyectos: this.user.idProyectos,
          },
          {
            params: {
              id: this.userId,
            },
          }
        )
        .then((response) => {
          this.getUserData();

          localStorage.setItem("usuarioID",  this.userId);
          localStorage.setItem("usuarioNombre", this.userName);
          alert("Información actualizada");
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
        });
    },

    getUserData() {
      let id = this.userId.toString();
      axios
        .get(this.$store.state.backURL + path + "?id=" + id, {
          params: {
            id: this.userId,
          },
        })
        .then((response) => {
          this.user = response["data"];
          console.log(this.user);
        })
        .catch((error) => {
          if (error.response.status === 400) {
            alert("Credenciales incorrectas");
          } else {
            alert("¡Parece que hubo un error de comunicación con el servidor!");
          }
        });
    },
    pushToHome() {
      this.$router.push({ name: "home" }).catch((err=>{}));
    },
  },
};
</script>

<style scoped>
#Principal {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}


</style>
