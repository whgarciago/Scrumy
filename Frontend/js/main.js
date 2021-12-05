const app = Vue.createApp({
  data: () => ({
    formClassValueIniciarSesion: "formulario-inicio-sesion ",
    formClassValueRegistro: ".formulario-registro inactive",
    logo: "./images/logo_scrumy.png",

    valoresInicioDeSesionUsuario: {
      email: "",
      password: "",
    },

    valoresRegistroUsuario: {
      nombre: "",
      email: "",
      password: "",
      confirmPassword: "",
    },
  }),
  methods: {
    iniciarSesion() {
      this.formClassValueIniciarSesion = "formulario-inicio-sesion";
      this.formClassValueRegistro = "formulario-registro inactive";
    },
    iniciarRegistro() {
      this.formClassValueIniciarSesion = "formulario-inicio-sesion inactive";
      this.formClassValueRegistro = "formulario-registro";
    },
    manejarEnvioLoginUsuario() {
      console.log(this.valoresInicioDeSesionUsuario.email);
      console.log(this.valoresInicioDeSesionUsuario.password);
    },
    manejarEnvioRegistroUsuario() {
      console.log(this.valoresRegistroUsuario.nombre);
      console.log(this.valoresRegistroUsuario.email);
      console.log(this.valoresRegistroUsuario.password);
      console.log(this.valoresRegistroUsuario.confirmPassword);
    },
  },
});
