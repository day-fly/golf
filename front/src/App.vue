<template>
  <q-layout view="hHh lpR fFf" class="bg-image">
    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<style lang="scss" scoped>
.bg-image {
  background-image: url("./images/background.jpg");
  //background-repeat: no-repeat;
  background-size: contain;
}
</style>