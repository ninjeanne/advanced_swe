import Vue from "vue";
import Router from "vue-router";
import Game from "../views/Game";
import HighscoreView from "../views/HighscoreView";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "HighscoreView",
      component: HighscoreView
    },
    {
      path: "/game",
      name: "Game",
      component: Game
    }
  ]
});
