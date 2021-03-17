import Vue from "vue";
import Router from "vue-router";
import Game from "../views/Game";
import TopRanking from "../views/TopRanking";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "TopRanking",
      component: TopRanking
    },
    {
      path: "/game",
      name: "Game",
      component: Game
    }
  ]
});
