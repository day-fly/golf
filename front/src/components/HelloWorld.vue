<template>
  <q-page class="flex flex-center">
    <div class="text-h4 text-purple text-bold">
      <q-btn class="glossy text-h2 text-weight-bold" rounded color="deep-orange" label="주문하기"
             style="height: 150px;min-width: 250px" @click="order"></q-btn>&nbsp;&nbsp;
    </div>
  </q-page>
</template>
<style>
</style>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {}
  },
  mounted() {

  },
  methods: {
    order() {
      this.$router.push('cert')
    }
  }
}
</script>
