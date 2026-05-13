<template>
  <div id="app">
    <NavigationBar />
    <div class="container-fluid mt-3">
      <router-view />
    </div>
  </div>
</template>

<script setup lang="ts">
import NavigationBar from './components/NavigationBar.vue'
</script>

<style>
#app {
  font-family: Arial, sans-serif;
}
</style>