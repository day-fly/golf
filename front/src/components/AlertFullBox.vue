<template>
  <q-layout view="hHh lpR fFf" style="background-color:rgba(0, 0, 0, 0.6);">
    <q-header class="bg-black text-white text-h4 text-bold row items-center justify-center" style="min-height:100px">
      &nbsp;&nbsp;{{ userDong ? userDong.replace(/.$/,"*") : ''}}동 {{ userHo ? userHo.replace(/.$/,"*") : '' }}호 {{ userName ? userName.replace(/.$/,"*") : '' }}님 환영합니다.
      [{{20-delayTime}} 초]
    </q-header>

    <q-page class="flex flex-center">
      <div class="text-h4 text-white text-center text-bold">
        현재 이용가능한 타석이 없습니다.
        <br>
        <br>
        대기예약 진행하시겠습니까?
        <br>
        <br>
        <q-btn style="width:180px" class="text-h4 text-bold" label="대기예약" color="green-8" @click="book"></q-btn>
        &nbsp;
        &nbsp;
        <q-btn style="width:150px" class="text-h4 text-bold" label="취소" color="orange-8" @click="cancel"></q-btn>
      </div>
    </q-page>
  </q-layout>


</template>

<script>
let interval
import {ref} from 'vue'
import axios from "axios";

export default {
  name: 'AlertFullBox',
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
        this.cancelOrder()
      }
    }, 1000)

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
  },
  methods: {
    async book() {
      this.$q.loading.show()
      axios.post(
          `http://${this.$static.SERVER_IP}/java/waitUser/insert`,
          {
            "userName": this.userName,
            "userDong": this.userDong,
            "userHo": this.userHo
          }
      ).then(async () => {
        clearInterval(interval)
        this.$q.loading.hide()
        await this.$router.push({name: 'bye', params: {text: '대기예약이 완료되었습니다. 예약현황판을 수시로 확인해주세요.'}})
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
