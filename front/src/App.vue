<template>
  <q-layout view="hHh lpR fFf" class="bg-image">
    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
//ctrl + 휠 금지
document.addEventListener(
    'wheel',
    function touchHandler(e) {
      if (e.ctrlKey) {
        e.preventDefault();
      }
    },
    { passive: false }
)
</script>

<style lang="scss" scoped>
.bg-image {
  background-image: url("./images/background.jpg");
  //background-repeat: no-repeat;
  background-size: cover
}

div {
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
}
</style>