<template>
  <div class="game_over">
    <hr>
    <img alt="img of work item" src="static/colleague.png" width="210" height="210" />
    <h1>GAME OVER</h1>
    <br>
    <br>
    <hr>
    <h4>{{ player.name }}, you have to be ventilated in a hospital 🚑 🏥</h4>
    <h2>Achieved Points <b>{{ rankingPoints }}</b></h2>
    <h2>{{ workItem }} <img alt="img of work item" src="static/work_item.png" width="30" height="30" /></h2>
  </div>
</template>

<script>
export default {
  name: "GameOver",
  props: [
    "player", "rankingPoints", "workItem"
  ]
};
</script>

<style scoped>
.game_over {
  justify-content: center;
  width: 1100px;
}

</style>
