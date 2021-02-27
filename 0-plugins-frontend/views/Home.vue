<template>
  <div class="home">
    <p>connection to backend established: {{ isConnected }}</p>
    <p>Message: {{ message }}</p>
  </div>
</template>

<script>
import {IdentitySerializer, JsonSerializer, RSocketClient} from "rsocket-core";
import RSocketWebSocketClient from "rsocket-websocket-client";
// @ is an alias to /src
export default {
  name: "home",
  components: {},
  data() {
    return {
      socket: null,
      payload: null
    };
  },
  methods: {
    connect() {
      console.log("connecting with RSocket...");
      // Create an instance of a client
      this.socket = new RSocketClient({
        serializers: {
          data: JsonSerializer,
          metadata: IdentitySerializer
        },
        setup: {
          // ms btw sending keepalive to server
          keepAlive: 60000, // ms timeout if no keepalive response
          lifetime: 180000, // format of `data`
          dataMimeType: "application/json",
          metadataMimeType: "message/x.rsocket.routing.v0"

        },
        transport: new RSocketWebSocketClient({
          url: "ws://localhost:7000"
        })
      });

      // Open the connection
      this.socket.connect().subscribe({
        onComplete: socket => {
          // socket provides the rsocket interactions fire/forget, request/response,
          // request/stream, etc as well as methods to close the socket.
          socket.requestStream({
            data: {
              "origin": "Client",
              "interaction": "Request"
            },
            metadata: String.fromCharCode("tweets.by.author".length) + "tweets.by.author"
          }).subscribe({
            onComplete: () => console.log("complete"),
            onError: error => {
              console.log(error);
            },
            onNext: payload => {
              // console.log(payload.data);
              this.payload = payload.data;
            },
            onSubscribe: subscription => {
              subscription.request(2147483642);
            }
          });
        },
        onError: error => {
          console.log(error);
        },
        onSubscribe: cancel => {
          /* call cancel() to abort */
        }
      });
    }
  },
  computed: {
    isConnected() {
      return this.socket ? true : false;
    },
    message() {
      return this.payload;
    }
  },
  mounted() {
    this.connect();
  }
};
</script>

<style scoped>
div.responses {
  width: 700px;
  margin: auto;
}
</style>
