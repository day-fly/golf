<template>
  <q-layout view="hHh lpR fFf" style="background-color:rgba(0, 0, 0, 0.6);">
    <q-header class="bg-black text-white text-h4 text-bold row items-center justify-center" style="min-height:100px">
      &nbsp;&nbsp;{{ userDong ? userDong.replace(/.$/,"*") : ''}}동 {{ userHo ? userHo.replace(/.$/,"*") : '' }}호 {{ userName ? userName.replace(/.$/,"*") : '' }}님 환영합니다.
      [{{20-delayTime}} 초]
    </q-header>

    <q-page class="flex flex-center">
      <div class="text-h4 text-white text-center text-bold">
        <q-btn style="width:180px" class="text-h4 text-bold" label="운동종료" color="green-8" @click="finish"></q-btn>
        &nbsp;
        &nbsp;
        <q-btn style="width:150px" class="text-h4 text-bold" label="타석연장" color="green-8" @click="extendTime"></q-btn>
        &nbsp;
        &nbsp;
        <q-btn style="width:150px" class="text-h4 text-bold" label="타석이동" color="green-8" @click="moveBox"></q-btn>
      </div>
    </q-page>
  </q-layout>


</template>

<script>
let interval
import {ref} from 'vue'
import axios from "axios";

export default {
  name: 'UsingUserAction',
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
