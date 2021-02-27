<template>
  <div class="home">
    <p>Welcome in the RSocket JavaScript tester</p>
    <p>RSocket is connected: {{ isConnected }}</p>

    <div class="responses">
      <hr />
      <RequestStream :socket="socket" />
      <hr />
      <RequestResponse :socket="socket" />
    </div>
  </div>
</template>

<script>
import {JsonSerializers, RSocketClient} from "rsocket-core";
import RSocketWebSocketClient from "rsocket-websocket-client";
// @ is an alias to /src
import RequestStream from "../src/components/RequestStream";
import RequestResponse from "../src/components/RequestResponse";

export default {
  name: "home",
  components: {
    RequestResponse,
    RequestStream
  },
  data() {
    return {
      socket: null
    };
  },
  methods: {
    connect() {
      console.log("connecting with RSocket...");
      const transport = new RSocketWebSocketClient({
        url: "ws://localhost:7000"
      });
      const client = new RSocketClient({
        // send/receive JSON objects instead of strings/buffers
        serializers: JsonSerializers,
        setup: {
          // ms btw sending keepalive to server
          keepAlive: 60000, // ms timeout if no keepalive response
          lifetime: 180000, // format of `data`
          dataMimeType: "application/json", // format of `metadata`
          metadataMimeType: "application/json"
        },
        transport
      });
      console.log("initialized");
      client.connect().subscribe({
        onComplete: socket => {
          this.socket = socket;
        },
        onError: error => {
          console.log("got connection error");
          console.error(error);
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
