import Vue from "vue";
import Router from "vue-router";
import LoginForm from "@/components/LoginForm";
import SignUpForm from "@/components/SignUpForm";
import Login from "./views/Login";
import Home from "./views/Home";
import SmallGoals from "@/components/SmallGoals";
import UserInfo from "./views/UserInfo";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      redirect: "/login",
    },
    {
      path: "/login",
      alias: "/iniciar-sesion",
      name: "login",
      component: Login,
      children: [
        {
          path: "/loginform",
          name: "loginform",
          component: LoginForm,
        },
        {
          path: "/signup",
          name: "signupform",
          component: SignUpForm,
        },
      ],
    },
    {
      path: "/principal",
      name: "home",
      component: Home,
      children: [
        {
          path: "/smallgoals",
          name: "smallgoals",
          component: SmallGoals,
        },
      ],
    },
    {
      path: "/settings",
      name: "settings",
      component: UserInfo,
    },
  ],
});
