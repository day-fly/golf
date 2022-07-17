<template>
  <q-layout view="hHh lpR fFf" style="background-color:rgba(0, 0, 0, 0.6);">
    <q-header class="bg-black text-white text-h4 text-bold row items-center justify-center" style="min-height:100px">
      &nbsp;&nbsp;{{ userDong ? userDong.replace(/.$/,"*") : ''}}동 {{ userHo ? userHo.replace(/.$/,"*") : '' }}호 {{ userName ? userName.replace(/.$/,"*") : '' }}님 환영합니다.
      [{{20-delayTime}} 초]
    </q-header>

    <q-page class="flex flex-center">
      <div class="text-h4 text-white text-center text-bold">
        이미 대기예약이 되어있는 상태입니다.
        <br>
        <br>
        대기예약을 취소하시겠습니까?
        <br>
        <br>
        <q-btn style="width:180px" class="text-h4 text-bold" label="Yes" color="orange-8" @click="book"></q-btn>
        &nbsp;
        &nbsp;
        <q-btn style="width:150px" class="text-h4 text-bold" label="No" color="green-8" @click="cancel"></q-btn>
      </div>
    </q-page>
  </q-layout>


</template>

<script>
let interval
import {ref} from 'vue'
import axios from "axios";

export default {
  name: 'CancelWaiting',
  props: {
    userDong: String,
    userHo: String,
    userName: String
  },
  data() {
    return {
      delayTime: 0,
    }
  },
  created() {
    clearInterval(interval)

    if(!this.userName){
      this.$router.push('/')
    }

    interval = setInterval(() => {
      this.delayTime += 1
      if(this.delayTime > 19){
        this.cancel()
      }
    }, 1000)


  },
  methods: {
    async book() {
      this.$q.loading.show()
      axios.post(
          `http://${this.$static.SERVER_IP}/java/waitUser/cancel`,
          {
            "userName": this.userName,
            "userDong": this.userDong,
            "userHo": this.userHo
          }
      ).then(async () => {
        clearInterval(interval)
        this.$q.loading.hide()
        await this.$router.push({name: 'bye', params: {text: '대기예약이 취소되었습니다.'}})
      }).catch(() => {
        this.$q.loading.hide()
        this.$router.push('/error')
      })
    },
    cancel(){
      axios.post(
          `http://${this.$static.SERVER_IP}/java/order/cancel`
      ).then(async () => {
        clearInterval(interval)
        this.$q.loading.hide()
        await this.$router.push('/')
      }).catch(() => {
        this.$q.loading.hide()
        this.$router.push('/error')
      })
    },
  },
  setup() {
    return {
      tab: ref('coffee'),
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
