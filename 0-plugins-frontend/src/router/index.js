import Vue from "vue";
import Router from "vue-router";
import IListenToSockets from "../components/IListenToSockets";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "IListenToSockets",
      component: IListenToSockets
    }
  ]
});
