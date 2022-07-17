<template>
  <q-page class="flex flex-center">
    <div class="text-h4 text-white text-bold">
      {{ text }}
    </div>
  </q-page>
</template>

<style>
</style>

<script>
export default {
  name: 'HelloWorld',
  props: {
    text: String
  },
  data() {
    return {}
  },
  mounted() {
    //test
    setTimeout(() => this.$router.push('/'), 5000)
  },
  methods() {

  }
}
</script>
