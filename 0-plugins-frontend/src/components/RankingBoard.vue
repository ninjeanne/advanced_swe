<template>
  <div>
    <h3>Top Ranking</h3>
    <table>
      <tr v-for="(ranking, index) in topRanking" :key="index">
        <th>{{ (index + 1) }}. <b>{{ ranking.name }}</b></th>
        <th style="color:green;text-align:right;font-weight:bold">{{ ranking.total }}</th>
        <th>[{{ new Date(ranking.date) | formatDate }}]</th>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: "RankingBoard",
  props: [
    'topRanking'
  ]
};
</script>

<style scoped>

</style>
