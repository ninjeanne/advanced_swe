<template>
  <div>
    <logo />
    <h2>Your Highscore</h2>
    <div class="content">
      <highscore-component v-bind:highscore="highscore" />
    </div>
    <router-link to="game" pill size="lg" variant="dark">GAME</router-link>
  </div>
</template>

<script>
import {api} from "../services/api";
import HighscoreComponent from "../components/HighscoreComponent";
import Logo from "../components/Logo";
import Vue from "vue";
import moment from "moment";

export default {
  name: "Highscore",
  components: {
    HighscoreComponent,
    Logo
  },
  data() {
    return {
      highscore: [],
      connected: false
    };
  },
  methods: {
    defaultRankingForBoard() {
      api("/highscore", {
        method: "GET"
      }).then(value => this.highscore = value);
    }
  },
  mounted() {
    this.defaultRankingForBoard();
    Vue.filter("formatDate", function(date) {
      if (date) {
        return moment(date).format("DD. MMMM YYYY HH:mm");
      }
    });
  }
};
</script>

<style scoped>
.content {
  display: flex;
  justify-content: center;
  height: 400px;
}

</style>
