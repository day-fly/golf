<template>
  <q-page class="flex flex-center">
    <div class="text-h4 text-green text-bold">
      예약이 완료되었습니다. 감사합니다.
    </div>
  </q-page>
</template>

<style>
</style>

<script>
export default {
  name: 'HelloWorld',
  props: {
    orderSeq: String,
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
