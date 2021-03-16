<template>
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
