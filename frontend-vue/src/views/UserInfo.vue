<template>
  <div class="d-flex flex-column vh-100 col-12 m-0 p-0 overflow-auto Home">
    <nav class="navbar navbar-expand-lg navbar-dark navbar-color ">
      <a class="navbar-brand pl-5" href="#"
        ><img :src="logo_URL" alt="Logo de Pagina Scrumy" @click="goToHome()"
      /></a>
      <button
        class="navbar-toggler my-0 p-0"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="ml-auto navbar-nav mr-4">
          <li class=" nav-item dropdown mr-4 btn btn-navbar">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              id="navbarDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              {{ user.nombre }}
              <img src="../assets/icono_usuario.png" style="color:white;" />
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" @click="goToSettings()">Configuracion</a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" @click="cerrarSesion()" href=""
                >Cerrar sesión</a
              >
              <div class="dropdown-divider"></div>
              <p class="text-center"><b>Temas</b></p>
              <a
                class="dropdown-item bg-dark text-light"
                @click="EstilosPredeterminados(1)"
                >Tema Oscuro</a
              >
              <a
                class="dropdown-item bg-primary text-light"
                @click="EstilosPredeterminados(2)"
                >Tema Azul</a
              >
              <a
                class="dropdown-item bg-success text-light"
                @click="EstilosPredeterminados(3)"
                >Tema Verde</a
              >
              <a
                class="dropdown-item bg-danger text-light"
                @click="EstilosPredeterminados(4)"
                >Tema Rojo</a
              >
              <a
                class="dropdown-item bg-info text-light"
                @click="EstilosPredeterminados(5)"
                >Tema Claro</a
              >
              <div class="dropdown-divider"></div>
              <p class="text-center"><b>Fondos</b></p>
              <p>Color Superior</p>
              <div class="d-flex  ">
                <button
                  class="btn b1"
                  @click="FondosPredeterminados(1, '--color2')"
                >
                  A
                </button>
                <button
                  class="btn b2"
                  @click="FondosPredeterminados(2, '--color2')"
                >
                  B
                </button>
                <button
                  class="btn b3"
                  @click="FondosPredeterminados(3, '--color2')"
                >
                  C
                </button>
                <button
                  class="btn b4"
                  @click="FondosPredeterminados(4, '--color2')"
                >
                  D
                </button>
              </div>
              <div>
                <button
                  class="btn b5"
                  @click="FondosPredeterminados(5, '--color2')"
                >
                  E
                </button>
                <button
                  class="btn b6"
                  @click="FondosPredeterminados(6, '--color2')"
                >
                  F
                </button>
                <button
                  class="btn b7"
                  @click="FondosPredeterminados(7, '--color2')"
                >
                  G
                </button>
                <button
                  class="btn b8"
                  @click="FondosPredeterminados(8, '--color2')"
                >
                  H
                </button>
              </div>
              <p>Color Inferior</p>
              <div class="d-flex  ">
                <button
                  class="btn b1"
                  @click="FondosPredeterminados(1, '--color1')"
                >
                  A
                </button>
                <button
                  class="btn b2"
                  @click="FondosPredeterminados(2, '--color1')"
                >
                  B
                </button>
                <button
                  class="btn b3"
                  @click="FondosPredeterminados(3, '--color1')"
                >
                  C
                </button>
                <button
                  class="btn b4"
                  @click="FondosPredeterminados(4, '--color1')"
                >
                  D
                </button>
              </div>
              <div>
                <button
                  class="btn b5"
                  @click="FondosPredeterminados(5, '--color1')"
                >
                  E
                </button>
                <button
                  class="btn b6"
                  @click="FondosPredeterminados(6, '--color1')"
                >
                  F
                </button>
                <button
                  class="btn b7"
                  @click="FondosPredeterminados(7, '--color1')"
                >
                  G
                </button>
                <button
                  class="btn b8"
                  @click="FondosPredeterminados(8, '--color1')"
                >
                  H
                </button>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </nav>
    <div class="d-flex flex-row h-100 m-0 p-0 ">
      <div class="col-4"></div>
      <div class="d-flex flex-column col-12 col-sm-6 col-md-4 justify-content-center align-items-center">
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
          <label class="custom-label col-md-3 display" for="password"
            > Contrase&ntilde;a actual</label
          >
          <input
            id="password"
            class="form-control col-12 col-sm-10 col-md-7 offset-sm-1"
            type="password"
            placeholder="Contraseña"
            v-model="cpassword"
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
      <button @click="goToHome()" class="btn btn-primary my-5">
        Regresar
      </button>
        
      </div>
      <div class="col-4"></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import logo from "../assets/logo_S_48px.png";

const path = "/usuarios/find";
const pathUpdateUser = "/usuarios/update";

export default {
  name: "settings",

  data() {
    return {
      logo_URL: logo,
      userName: "",
      email: "",
      password: "",
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
    EstilosPredeterminados(opcion) {
      //Metodo adaptado de Home, en esta vista solo existe la barra superior
      var BarraSuperior = document.getElementsByClassName("navbar-color");
      // var BarraProy=document.getElementsByClassName("barraProyectos");
      // var EncabezProyecto=document.getElementsByClassName("card-header");
      // var BotonCambiar=document.getElementsByClassName("btn-cambio");
      // var Contenido=document.getElementsByClassName("content-color");
      switch (opcion) {
        case 1: //Tema Oscuro
          BarraSuperior[0].style.backgroundColor = "#000";
          // BarraProy[0].style.backgroundColor= "#222222b3";
          // EncabezProyecto[0].style.backgroundColor= "#000";
          // BotonCambiar[0].style.backgroundColor= "#222222b3";
          // Contenido[0].style.backgroundColor= "#222222b3";
          break;
        case 2: //Tema Azul
          BarraSuperior[0].style.backgroundColor = "#1d3461";
          //BarraProy[0].style.backgroundColor= "#6390c7b3";
          // EncabezProyecto[0].style.backgroundColor= '#1d3461';
          // BotonCambiar[0].style.backgroundColor= "#6390c7b3";
          // Contenido[0].style.backgroundColor= "#6390c7b3";
          break;
        case 3: //Tema verde
          BarraSuperior[0].style.backgroundColor = "#02ac66";
          // BarraProy[0].style.backgroundColor= " #D28769b3";
          // EncabezProyecto[0].style.backgroundColor= "#02ac66";
          // BotonCambiar[0].style.backgroundColor= " #D28769b3";
          // Contenido[0].style.backgroundColor= " #D28769b3";
          break;
        case 4: //Tema Rojo
          BarraSuperior[0].style.backgroundColor = "#B83955";
          // BarraProy[0].style.backgroundColor= "#295073b7";
          // EncabezProyecto[0].style.backgroundColor= "#B83955";
          // BotonCambiar[0].style.backgroundColor= "#295073b7";
          // Contenido[0].style.backgroundColor= "#295073b7";
          break;
        case 5: //Tema Claro
          BarraSuperior[0].style.backgroundColor = "#23bac4";
          // BarraProy[0].style.backgroundColor= "#d9473db3";
          // EncabezProyecto[0].style.backgroundColor= "#23bac4";
          // BotonCambiar[0].style.backgroundColor= "#d9473db3";
          // Contenido[0].style.backgroundColor= "#d9473db3";
          break;
        default:
          //Default
          BarraSuperior[0].style.backgroundColor = "#1d3461";
          // BarraProy[0].style.backgroundColor= "#6390c7b3";
          // EncabezProyecto[0].style.backgroundColor= "#1d3461";
          // BotonCambiar[0].style.backgroundColor= "#6390c7b3";
          // Contenido[0].style.backgroundColor= "#6390c7b3";
          break;
      }
    },
    FondosPredeterminados(opcion, color) {
      var fondo = document.getElementsByClassName("Home");
      switch (opcion) {
        case 1: //Azul
          fondo[0].style.setProperty(color, "#99e2ff");
          break;
        case 2: //Verde
          fondo[0].style.setProperty(color, "#9df8a9");
          break;
        case 3: //Rojo
          fondo[0].style.setProperty(color, "#e6a7a0");
          break;
        case 4: //Amarillo
          fondo[0].style.setProperty(color, "#ffffc1");
          break;
        case 5: //Morado
          fondo[0].style.setProperty(color, "#e0a2f3");
          break;
        case 6: //Naranja
          fondo[0].style.setProperty(color, "#e9c8a0");
          break;
        case 7: //Acuamarina
          fondo[0].style.setProperty(color, "#87e6d1");
          break;
        case 8: //Blanco
          fondo[0].style.setProperty(color, "#fff");
          break;
        default:
          fondo[0].style.setProperty(color, "#fff");
          break;
      }
    },
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

          localStorage.setItem("usuarioID", this.userId);
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
        })
        .catch((error) => {
          if (error.response.status === 400) {
            alert("Credenciales incorrectas");
          } else {
            alert("¡Parece que hubo un error de comunicación con el servidor!");
          }
        });
    },
    goToHome() {
      this.$router.push({ name: "home" }).catch((err) => {});
    },
    goToSettings() {
      this.$router.push({ name: "settings" }).catch((err) => {});
    },
    
    cerrarSesion() {
      localStorage.clear();
      this.$router.push({ name: "loginform" });
    },
  },
};

</script>

<style scoped>
#Principal {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.topNavigationBar .userbutton {
  font-size: 22px;
  color: rgb(21, 73, 198, 0.6);
  padding-top: 40px;
  float: right;
}
.topNavigationBar .userbutton:hover {
  color: rgb(255, 255, 255);
  background-color: rgba(94, 94, 94, 0.164);
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

#tituloPopup {
  font-size: 30px;
  color: rgb(255, 255, 255);
  text-align: center;
}

.navbar-color {
  background-color: #1d3461;
}

.componente-central {
  min-height: 65vh;
  max-height: 65vh;
}

.btn-crear-proyecto,
.btn-side-navbar {
  border-radius: 4px;
  border-width: 2px;
  border-color: white;
  color: white;
  transition: 0.5s;
}
.btn-crear-proyecto:hover {
  background-color: #000000;
  box-shadow: 0 0 5px #ffffff, 0 0 20px #ffffff, 0 0 40px #ffffff;
}

.btn-side-navbar:hover {
  background-color: #000000;
  /*box-shadow: 0 0 5px #ffffff, 0 0 10px #ffffff, 0 0 10px #ffffff;*/
}
.btn-navbar {
  transition: 0.5s;
}
.btn-navbar:hover {
  box-shadow: 0 0 5px #ffffff, 0 0 20px #ffffff, 0 0 40px #ffffff;
}
.btn-cambio {
  background-color: #6390c7b3;
  /*background-image: linear-gradient(to left,#446792,#1d3461);*/
  color: white;
  transition: 0.5s;
}

.content-color {
  background-color: #6390c7b3;
}
.hr-proyectos {
  border-top: 2px solid #1d3461;
}
.barraProyectos {
  /*background-image: linear-gradient(to top,rgb(182, 182, 182),#454b50);*/
  background-color: #6390c7b3;
}
.card-header {
  background: #1d3461;
  color: white;
}
.b1 {
  background: #99e2ff;
}
.b2 {
  background: #9df8a9;
}
.b3 {
  background: #e6a7a0;
}
.b4 {
  background: #ffffc1;
}
.b5 {
  background: #e0a2f3;
}
.b6 {
  background: #e9c8a0;
}
.b7 {
  background: #87e6d1;
}
.b8 {
  background: #fff;
}
.Home {
  --color1: #fff;
  --color2: #fff;
  background-image: linear-gradient(to top, var(--color1), var(--color2));
}
</style>
