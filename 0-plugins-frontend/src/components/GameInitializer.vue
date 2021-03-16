<template>
  <div class="grid-container">
    <div class="item1">
      <label v-if="!started" for="player"><b>Enter your name</b></label>
      <input
        type="text"
        id="player"
        class="form-control"
        v-model="player_name"
        :disabled="started"
        v-if="!started"
        placeholder="Your name here..."
      >
      <b v-else>Hi {{ this.player_name }}</b>
      <hr>
      <h3>Top Ranking</h3>
      <table>
        <tr v-for="(ranking, index) in top_ranking" :key="index">
          <th>{{ (index + 1) }}. <b>{{ ranking.name }}</b></th>
          <th style="color:green;text-align:right;font-weight:bold">{{ ranking.total }}</th>
          <th>[{{ new Date(ranking.date) | formatDate }}]</th>
        </tr>
      </table>
      <hr v-if="started">
      <div v-if="started">
        <b>{{ ranking_points }}</b> (with <img alt="img of vaccination" src="static/work_item.png" width="10" height="10" />: +50)<br>
        {{ player.workItem }} <img alt="img of vaccination" src="static/work_item.png" width="10" height="10" /> (work items)<br>
        {{ player.lifePoints }} <img alt="img of vaccination" src="static/vaccination.png" width="10" height="10" /> (life points)<br>
      </div>
    </div>
    <div class="item2 form-group">
      <button
        id="start"
        class="btn btn-default"
        :disabled="started"
        @click.prevent="start"
      >start
      </button>
      <button
        id="stop"
        class="btn btn-default"
        :disabled="!started"
        @click.prevent="stop"
      >stop
      </button>
    </div>
    <div class="item4">
      <h3 v-if="started">Office: {{ this.boardName }}</h3>
      <canvas v-if="started" id="c"></canvas>
    </div>

  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {api} from "../services/api";
import Vue from "vue";
import moment from "moment";

export default {
  name: "websocketdemo",
  data() {
    return {
      boardName: "default",
      player_name: "Default Spielername",
      top_ranking: [],
      player: null,
      reset_player: null,
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
      multiplier: 10
    };
  },
  methods: {
    stop() {
      this.stompClient.send("/frontend/stop", this.player_name, {});
      this.game_over = true;
      this.disconnect();
    },
    async start() {
      if (!this.connected) {
        await this.connect();
      }
      if (this.stompClient != null && this.connected) {
        this.started = true;
        this.rankingForBoard();
        this.stompClient.subscribe("/backend/stop", tick => {
          this.game_over = JSON.parse(tick.body);
          this.stop();
        });
        this.stompClient.subscribe("/backend/ranking", tick => {
          this.ranking_points = JSON.parse(tick.body);
          this.rankingForBoard();
        });
        this.stompClient.send("/frontend/start", this.player_name, {});
        this.stompClient.subscribe("/backend/board", tick => {
          let response = JSON.parse(tick.body);
          this.colleagues = response.colleagues;
          this.vaccination = response.vaccination;
          this.boardName = response.name;
          this.workItem = response.workItem;
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
        this.player = {
          name: this.player_name,
          position: {
            x: 0,
            y: 0
          },
          lifePoints: 3,
          workItem: 0
        };
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
        if (this.player != null) {
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
      this.player = null;
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
      var c = document.getElementById("c");
      c.width = 800;
      c.height = 500;
      this.vueCanvas = c.getContext("2d");
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
        this.vueCanvas.arc(this.colleagues[i].position.x * this.multiplier + this.multiplier / 2,
          this.colleagues[i].position.y * this.multiplier + this.multiplier / 2, this.multiplier * 3, 0, 2 * Math.PI);
        this.vueCanvas.fillStyle = "yellow";
        this.vueCanvas.fill();
        const img = new Image();
        img.src = "static/colleague.png";
        this.vueCanvas.drawImage(img, this.colleagues[i].position.x * this.multiplier, this.colleagues[i].position.y * this.multiplier, this.multiplier,
          this.multiplier);
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
      if (this.player != null) {
        this.vueCanvas.beginPath();
        const img = new Image();
        img.src = "static/pacman.png";
        this.vueCanvas.drawImage(img, this.player.position.x * this.multiplier, this.player.position.y * this.multiplier, this.multiplier * 2,
          this.multiplier * 2);

        this.vueCanvas.stroke();
      }
    },
    rankingForBoard() {
      api("/ranking?boardName=" + this.boardName, {
        method: "GET"
      }).then(value => this.top_ranking = value);
    }
  },
  mounted() {
    this.connect();
    this.rankingForBoard();
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
    'menu main main main main main'
    'menu footer footer footer footer footer';
  grid-gap: 10px;
  padding: 10px;
}

.grid-container > div {
  padding: 20px 0;
}

.item1 {
  grid-area: menu;
  font-size: 12px;
  text-align: justify;
}

.item2 {
  grid-area: header;
}

.item4 {
  grid-area: main;
}

.item5 {
  grid-area: footer;
}
</style>
