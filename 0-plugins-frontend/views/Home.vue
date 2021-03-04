<template>
  <div class="home">
    <game-initializer></game-initializer>
  </div>
</template>

<script>
// @ is an alias to /src
import GameInitializer from "@/components/GameInitializer.vue";

export default {
  name: "home",
  components: {
    GameInitializer
  }
};
</script>
