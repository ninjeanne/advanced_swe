<template>
  <div>
    <hr>
    <h1>GAME OVER</h1>
    <br>
    <hr>
    <br>
    <h3>{{ player.name }}, you should try again!</h3>
    <br>
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

</style>
