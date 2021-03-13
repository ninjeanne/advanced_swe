<template>
  <div>
    <div
      id="main-content"
      class="container"
    >
      <div class="row">
        <div class="col-md-6">
          <div class="form-group">
            <label for="player">What is your name?</label>
            <input
              type="text"
              id="player"
              class="form-control"
              v-model="player_name"
              :disabled="started"
              placeholder="Your name here..."
            >
          </div>
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
            @click.prevent="disconnect"
          >stop
          </button>
        </div>
      </div>
    </div>
    <div>
      <canvas id="c"></canvas>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "websocketdemo",
  data() {
    return {
      player_name: "Default Spielername",
      vueCanvas: null,
      connected: false,
      started: false,
      plan: null,
      colleagues: null,
      obstacles: null,
      vaccination: null,
      multiplier: 10
    };
  },
  methods: {
    async start() {
      if (!this.connected) {
        await this.connect();
      }
      if (this.stompClient != null && this.connected) {
        this.started = true;
        this.stompClient.send("/frontend/initialize", this.player_name, {});
        this.stompClient.subscribe("/backend/start", tick => {
          let response = JSON.parse(tick.body);
          this.colleagues = response.colleagues;
          this.vaccination = response.vaccination;
          if (this.plan == null) {
            this.plan = response.plan;
          }
          if (this.obstacles == null) {
            this.obstacles = response.obstacles;
          }
          this.drawMap();
          this.drawObstacles();
          this.drawVaccination();
          this.drawColleagues();
        });
      }
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
      c.height = 1000;
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
        const img = new Image();
        img.src = "static/colleague.png";
        this.vueCanvas.drawImage(img, this.colleagues[i].position.x * this.multiplier, this.colleagues[i].position.y * this.multiplier, this.multiplier,
          this.multiplier);
        this.vueCanvas.stroke();
      }
    },
    drawObstacles() {
      for (var i = 0; i < this.obstacles.length; i++) {
        this.vueCanvas.beginPath();
        this.vueCanvas.rect(this.obstacles[i].x * this.multiplier, this.obstacles[i].y * this.multiplier, this.multiplier, this.multiplier);
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
        this.vueCanvas.drawImage(img, this.vaccination.x * this.multiplier, this.vaccination.y * this.multiplier, this.multiplier * 3, this.multiplier * 3);
        this.vueCanvas.stroke();
      }
    }
  },
  mounted() {
    this.connect();
  }
};
</script>

<style scoped>
</style>
