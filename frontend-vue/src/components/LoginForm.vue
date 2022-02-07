<template>
  <div class="row d-flex justify-content-center">
    <div class="col-8">
      <div id="login">
        <form
          class="border border-primary rounded form-inline"
          @submit.prevent="loginVerification()"
        >
          <h2 class="col-12 text-center text-primary mt-3 mb-5">Inicio</h2>

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

          <div class="col-12 col-sm-5 col-md-4 mb-3 justify-content-end">
            <button
              class=" col-12 col-sm-12 col-md-12 btn btn-primary "
              type="submit"
            >
              Iniciar Sesión
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const path = "/usuarios/all";

export default {
  name: "LoginForm",
  data() {
    return {
      email: "",
      password: "",
      nombre: "",
      users: [],
    };
  },
  methods: {
    async loginVerification() {
      await this.login();
      console.log(this.users);
      for (let index = 0; index < this.users.length; index++) {
        if (this.users[index].correo == this.email) {
          console.log("Concuerdan nombres");
          if ((this.users[index].contraseña == this.password)) {
            localStorage.setItem("usuarioID", this.users[index].usuarioID);
            localStorage.setItem("usuarioNombre", this.users[index].nombre);
            this.$router.push({ name: "home" });
          }
          else{
            alert('Correo o contraseña incorrectos')
          }
        }
      }

    },
    async login() {
      await axios
        .get(this.$store.state.backURL + path)
        .then((response) => {
          if (response.status !== 200) {
            alert("Error en la autenticación");
          } else {
            this.users = response["data"];
          }
        })
        .catch((error) => {
          if (error.response.status === 400) {
            alert("Credenciales incorrectas");
          } else {
            alert("¡Parece que hubo un error de comunicación con el servidor!");
          }
        });
    },
  },
};
</script>

<style scoped>
.form-inline {
  width: auto;
}

.form-inline .form-group {
  margin-bottom: 1rem;
}

.custom-label {
  display: inline-block;
  justify-content: right !important;
  text-align: right !important;
}

@media (max-width: 767px) {
  .custom-label {
    display: none !important;
  }
}
</style>
