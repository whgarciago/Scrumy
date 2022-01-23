<template>
  <div class="row col-12 vh-100 whole-page m-0 p-0 ">
    <!-- Logo, botones de proyectos y usuario -->
    <div class="topNavigationBar" id="MyTopNavigationBar">
      <img id="logo" alt="Logo Scrumy" src="../assets/logo_scrumy.png" />
      <!--Nombre de usuario y configuración-->
      <a href="#" class="userbutton">
        <b id="neg">{{ user.nombre }}</b> Configuración
      </a>
    </div>

    <div class="col-3 p-0 m-0 spacing-to-navbar"></div>
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
            >Contrase&ntilde;a</label
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

        <div class="form-group col-12">
          <label class="custom-label col-md-3 display" for="cPassword"
            >Confirmar Contrase&ntilde;a</label
          >
          <input
            id="cPassword"
            class="form-control col-12 col-sm-10 col-md-7 offset-sm-1"
            type="password"
            placeholder="Confirmar Contraseña"
            v-model="cPassword"
            required
            :class="{
              'is-invalid': cPassword !== '' && cPassword !== password,
              'is-valid': cPassword !== '' && cPassword === password,
            }"
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
      this.$router.push({ name: "home" });
    },
  },
};
</script>

<style scoped>
#Principal {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

h1,
h2,
#logousuario {
  font-weight: normal;
  color: blue;
  right: 2em;
}

body {
  background-color: cornflowerblue;
  background-size: cover;
  background-repeat: no-repeat;
  background-position: 0% 35%;
  width: 100%;
  height: 100%;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}

.topNavigationBar {
  position: fixed;
  top: 0;
  background-color: rgba(255, 255, 255, 0.103);
  overflow: hidden;
  height: 126px;
  width: 100vw;
  border-bottom: 2px solid rgb(156, 156, 156);
}

.topNavigationBar .userbutton {
  font-size: 22px;
  color: rgb(21, 73, 198, 0.6);
  padding-top: 40px;
  float: right;
  /*text-align: right;*/
}
.topNavigationBar .userbutton:hover {
  color: rgb(255, 255, 255);
  background-color: rgba(94, 94, 94, 0.164);
}
#neg {
  font-size: 42px;
  float: unset;
}
.topNavigationBar a {
  line-height: 30px;
  float: left;
  display: block;
  color: #f2f2f2;
  text-align: center;
  text-decoration: none;
  font-size: 20px;
  width: 240px;
  height: 120px;
}

#logo {
  width: 230px;
}
.logos {
  width: 50px;
}
.whole-page {
  min-width: 768px;
  padding-top: 130px !important;

  background-image: url("../assets/background_picture.jpg");
  background-size: cover;
  height: 100%;
  background-repeat: no-repeat;
  background-position: center;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}
.spacing-to-navbar {
  padding-top: 150px;
}
</style>
