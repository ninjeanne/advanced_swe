<template>
  <div>
    <logo />
    <div class="grid-container">
      <div class="menu">
        <hr>
        <router-link to="/" size="lg">BACK</router-link>
        <hr>
        <player-board v-bind:player="player" v-on:update:player_name="player.name = $event" v-bind:started="started" />
        <start-stop-controller v-bind:started="started" v-on:stop="stop" v-on:start="start" />
        <hr>
        <game-status v-bind:started="started" v-bind:rankingPoints="ranking_points" v-bind:player="player"
          v-bind:board-name="this.boardName" />
        <hr>
      </div>
      <div class="main" ref="mainGame">
        <br v-if="started">
        <canvas v-if="started" id="c"></canvas>
        <game-over v-if="game_over && !started" v-bind:player="player" v-bind:ranking-points="last_ranking_points" v-bind:work-item="last_work_items" />
      </div>
      <div v-if="started" class="sorry">
        Dear Player, sorry for my bad frontend skills! :D
        <br>
        <br>
        Use the arrow keys to move the figure (to focus the board you need to click inside the canvas sometimes).<br>
        The goal is to collect as many points as possible.<br>
        Work items will increase the score faster. Vaccinations are the lives of a player.<br>
        A colleague can infect you and take your lives! Watch out.<br>
      </div>
      <div class="ranking">
        <hr>
        <highscore-component v-bind:headline="true" v-bind:highscore="highscore" />
        <b-button v-on:click="getHighscore">Refresh</b-button>
        <hr>
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {api} from "../services/api";
import Vue from "vue";
import moment from "moment";
import HighscoreComponent from "../components/HighscoreComponent";
import PlayerBoard from "../components/PlayerBoard";
import StartStopController from "../components/StartStopController";
import GameOver from "../components/GameOver";
import Logo from "../components/Logo";
import GameStatus from "../components/GameStatus";

export default {
  name: "Game",
  components: {
    HighscoreComponent,
    GameStatus,
    Logo,
    GameOver,
    StartStopController,
    PlayerBoard
  },
  data() {
    return {
      boardName: "default",
      highscore: [],
      player: {
        name: null,
        position: {
          x: 0,
          y: 0
        },
        statistics: {
          "WorkItem": 0,
          "Vaccination": 0,
          "Infection": 0
        }
      },
      ranking_points: 0,
      vueCanvas: null,
      connected: false,
      started: false,
      game_over: false,
      plan: null,
      colleagues: null,
      obstacles: null,
      vaccination: null,
      workItem: null,
      last_work_items: 0,
      last_ranking_points: 0,
      multiplier: 10
    };
  },
  methods: {
    stop() {
      this.last_ranking_points = this.ranking_points;
      this.stompClient.send("/frontend/stop", this.player.name, {});
      this.game_over = true;
      this.player.statistics = {
        "WorkItem": 0,
        "Vaccination": 0,
        "Infection": 0
      };
      this.disconnect();
      this.getHighscore();
    },
    async start() {
      this.getHighscore();
      if (this.player.statistics == null) {
        this.player.statistics = {
          "WorkItem": 0,
          "Vaccination": 0,
          "Infection": 0
        };
      }
      if (!this.connected) {
        await this.connect();
      }
      if (this.stompClient != null && this.connected) {
        this.started = true;
        this.stompClient.subscribe("/backend/stop", tick => {
          this.game_over = JSON.parse(tick.body);
          this.stop();
        });
        this.stompClient.subscribe("/backend/ranking", tick => {
          this.ranking_points = JSON.parse(tick.body);
        });
        if (this.player.name === null) {
          this.player.name = "anonymous";
        }
        this.stompClient.send("/frontend/start", this.player.name, {});
        this.stompClient.subscribe("/backend/board", tick => {
          let response = JSON.parse(tick.body);
          this.colleagues = response.colleagues;
          this.vaccination = response.gameObjects.Vaccination;
          this.workItem = response.gameObjects.WorkItem;
          this.boardName = response.nameAsEntityId;
          this.last_work_items = this.player.statistics.WorkItem;
          if (this.plan == null) {
            this.plan = response.plan;
          }
          if (this.obstacles == null) {
            this.obstacles = response.obstacles;
          }
          this.redraw();
        });
        this.stompClient.subscribe("/backend/player", tick => {
          this.player = JSON.parse(tick.body);
          this.redraw();
        });
        this.move();
      }
    },
    redraw() {
      this.drawMap();
      this.drawObstacles();
      this.drawVaccination();
      this.drawWorkItem();
      this.drawColleagues();
      this.drawPlayer();
    },
    move() {
      document.addEventListener("keydown", function(event) {
        if (this.player != null && this.started === true) {
          let validUp = ["ArrowUp", "W", "w"];
          let validDown = ["ArrowDown", "s", "S"];
          let validLeft = ["ArrowLeft", "a", "A"];
          let validRight = ["ArrowRight", "d", "D"];
          let valid = validUp.concat(validDown, validLeft, validRight);
          let position = {
            x: this.player.position.x,
            y: this.player.position.y
          };
          if (validUp.includes(event.key)) {
            position.y = this.player.position.y - 1;
          } else if (validDown.includes(event.key)) {
            position.y = this.player.position.y + 1;
          } else if (validLeft.includes(event.key)) {
            position.x = this.player.position.x - 1;
          } else if (validRight.includes(event.key)) {
            position.x = this.player.position.x + 1;
          }

          if (valid.includes(event.key)) {
            event.preventDefault(); // prevent it from doing default behavior, like downarrow moving page downward
            this.stompClient.send("/frontend/move", JSON.stringify(position), {});
          }
        }
      }.bind(this));
    },
    async connect() {
      if (this.stompClient != null || this.socket != null || this.connected) {
        await this.disconnect();
      }
      this.socket = new SockJS("http://localhost:8080/socket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.debug = () => {
      };
      await this.stompClient.connect({}, frame => {
        this.connected = true;
      }, error => {
        this.disconnect();
      });
    },
    async disconnect() {
      if (this.stompClient) {
        await this.stompClient.disconnect();
        await this.socket.close();
      }
      this.socket = null;
      this.stompClient = null;
      this.player = {
        name: this.player.name,
        position: {
          x: 0,
          y: 0
        },
        lifePoints: 0,
        workItems: 0
      };
      this.connected = false;
      this.started = false;
      this.removeCanvas();
    },
    removeCanvas() {
      if (this.vueCanvas != null) {
        this.vueCanvas.clearRect(0, 0, this.plan.width * this.multiplier, this.plan.height * this.multiplier);
      }
      this.vueCanvas = null;
    },
    addCanvas() {
      var canvas = document.getElementById("c");
      canvas.width = 800;
      canvas.height = 500;
      this.vueCanvas = canvas.getContext("2d");
    },
    drawMap() {
      if (this.vueCanvas === null) {
        this.addCanvas();
      }
      this.vueCanvas.clearRect(0, 0, this.plan.width * this.multiplier, this.plan.height * this.multiplier);
      this.vueCanvas.beginPath();
      this.vueCanvas.rect(0, 0, this.plan.width * this.multiplier, this.plan.height * this.multiplier);
      this.vueCanvas.fillStyle = "white";
      this.vueCanvas.fill();
      this.vueCanvas.stroke();
    },
    drawColleagues() {
      for (var i = 0; i < this.colleagues.length; i++) {
        this.vueCanvas.beginPath();
        this.vueCanvas.arc(this.colleagues[i].x * this.multiplier + this.multiplier / 2, this.colleagues[i].y * this.multiplier + this.multiplier / 2,
          this.multiplier * 3, 0, 2 * Math.PI);
        this.vueCanvas.fillStyle = "yellow";
        this.vueCanvas.fill();
        const img = new Image();
        img.src = "static/colleague.png";
        this.vueCanvas.drawImage(img, this.colleagues[i].x * this.multiplier, this.colleagues[i].y * this.multiplier, this.multiplier, this.multiplier);
        // this.vueCanvas.stroke();
      }
    },
    drawObstacles() {
      for (var i = 0; i < this.obstacles.length; i++) {
        this.vueCanvas.beginPath();
        this.vueCanvas.rect(this.obstacles[i].x * this.multiplier + this.multiplier / 2, this.obstacles[i].y * this.multiplier + this.multiplier / 2,
          this.multiplier, this.multiplier);
        this.vueCanvas.fillStyle = "black";
        this.vueCanvas.fill();
        this.vueCanvas.stroke();
      }
    },
    drawVaccination() {
      if (this.vaccination != null) {
        this.vueCanvas.beginPath();
        const img = new Image();
        img.src = "static/vaccination.png";
        this.vueCanvas.drawImage(img, this.vaccination.x * this.multiplier + this.multiplier / 2, this.vaccination.y * this.multiplier + this.multiplier / 2,
          this.multiplier * 2, this.multiplier * 2);
        this.vueCanvas.stroke();
      }
    },
    drawWorkItem() {
      if (this.vaccination != null) {
        this.vueCanvas.beginPath();
        const img = new Image();
        img.src = "static/work_item.png";
        this.vueCanvas.drawImage(img, this.workItem.x * this.multiplier + this.multiplier / 2, this.workItem.y * this.multiplier + this.multiplier / 2,
          this.multiplier * 2, this.multiplier * 2);
        this.vueCanvas.stroke();
      }
    },
    drawPlayer() {
      this.vueCanvas.beginPath();
      const img = new Image();
      img.src = "static/pacman.png";
      this.vueCanvas.drawImage(img, this.player.position.x * this.multiplier, this.player.position.y * this.multiplier, this.multiplier * 2,
        this.multiplier * 2);

      this.vueCanvas.stroke();
    },
    getHighscore() {
      api("/highscore", {
        method: "GET"
      }).then(value => this.highscore = value);
    }
  },
  mounted() {
    this.connect();
    this.getHighscore();
    Vue.filter("formatDate", function(date) {
      if (date) {
        return moment(date).format("DD. MMMM YYYY HH:mm");
      }
    });
  }
};
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-areas:
    'header header header header header header'
    'menu main main main main ranking'
    'menu main main main main ranking'
    'menu sorry sorry sorry sorry ranking';
  grid-gap: 10px;
  padding: 10px;
}

.grid-container > div {
  padding: 20px 0;
}

.menu {
  grid-area: menu;
  font-size: 12px;
  text-align: justify;
}

#c {
  display: inline;
}

.main {
  grid-area: main;
  width: 100%;
  height: 550px;
  text-align: center;
}

.ranking {
  grid-area: ranking;
}

.sorry {
  grid-area: sorry;
}

</style>
