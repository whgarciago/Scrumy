import Vue from 'vue';
import Router from 'vue-router';
import SignUp from "./views/SignUp";
import LoginForm from '@/components/LoginForm';
import SignUpForm from '@/components/SignUpForm';
import Login from "./views/Login";
import Home from "./views/Home";
import AddRole from "./components/AddRole";
import Courses from '@/views/Courses';
import Roles from "@/components/Roles";
import AddCourse from "@/components/AddCourse";

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/login",
      alias: "/iniciar-sesion",
      name: "login",
      component: Login,
      children:[
        {
          path: "/loginform",
          name: "loginform",
          component: LoginForm
        },
        {
          path: "/signup",
          name: "signupform",
          component: SignUpForm
        },
      ]
    },
    {
      path: "/principal",
      name: "home",
      component: Home,
    }
  ]
})
