<template>
  <div class="row d-flex justify-content-center">
    <div class="col-10">
      <div id="login">
        <form
          class="border border-primary rounded form-inline"
          @submit.prevent="login"
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
              class="col-sm-10 col-md-10 btn btn-primary "
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
      nombre:"",
      users: [],
    };
  },
  methods: {
    login() {
      axios
        .get(this.$store.state.backURL + path)
        .then((response) => {
          if (response.status !== 200) {
            alert("Error en la autenticación");
          } else {
            this.users = response["data"];
            for (let i = 0; i < this.users.length; i++) {
              if (this.users[i].correo == this.email) {
                console.log("hallado");
                console.log(this.users[i].nombre);
                this.$router.push({ name: "home" });
                localStorage.setItem("usuarioID", this.users[i].usuarioID);
                localStorage.setItem("usuarioNombre",this.users[i].nombre)
              }
            }
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
