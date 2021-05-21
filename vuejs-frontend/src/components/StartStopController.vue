<template>
  <div class="item2">
    <b-button
      :disabled="started"
      @click.prevent="start"
      variant="dark"
      size="lg"
      id="start"
    >start
    </b-button>
    <b-button
      id="stop"
      variant="dark"
      size="lg"
      :disabled="!started"
      @click.prevent="stop"
    >stop
    </b-button>
  </div>
</template>

<script>
export default {
  name: "StartStopController",
  props: [
    'started'
  ],
  methods: {
    stop(){
      this.$emit('stop');
    },
    start(){
      this.$emit('start');
    }
  }
};
</script>

<style scoped>

</style>
