<template>
  <div class="container-fluid">
    <div class="row d-flex justify-content-center">
      <div class="col-8 ">
        <div id="signUp" class="mt-5">
          <form
            class="border border-primary rounded form-inline"
            @submit.prevent="signUpVerification()"
          >
            <h2 class="col-12 text-center text-primary mt-3 mb-5">Registro</h2>

            <div class="form-group col-12">
              <label class="custom-label col-md-3" for="username"
                >Nombre de Usuario</label
              >
              <input
                id="username"
                class="form-control col-12 col-sm-10 col-md-7 offset-sm-1"
                type="text"
                placeholder="Nombre de Usuario"
                v-model="username"
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
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const path = "/usuarios/create";

export default {
  name: "SignUpForm",
  data() {
    return {
      username: "",
      email: "",
      password: "",
      cPassword: "",
    };
  },
  methods: {
    

    async signUpVerification() {
      if (this.cPassword !='' && this.password.localeCompare(this.cPassword)==0) {
        console.log("inicio");
        await this.saveNewUserInBackend();
        console.log("fin");
      } else {
        alert("Las contraseñas no coinciden");
      }
    },
    async saveNewUserInBackend() {
       axios
        .post(this.$store.state.backURL + path, {
          contraseña: this.password.trim(),
          correo: this.email.trim(),
          nombre: this.username.trim(),
          idProyectos: null,
        })
        .then((response) => {
          this.$router.push({ name: "loginform" }).catch((err) => {});
        })
        .catch((response) => {
          alert("No es posible conectar con el backend en este momento");
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
