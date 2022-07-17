<template>
  <q-layout view="hHh lpR fFf" style="background-color:rgba(0, 0, 0, 0.6);">
    <q-header class="bg-black text-white text-h4 text-bold row items-center justify-center" style="min-height:100px">
      &nbsp;&nbsp;{{ userDong ? userDong.replace(/.$/,"*") : ''}}동 {{ userHo ? userHo.replace(/.$/,"*") : '' }}호 {{ userName ? userName.replace(/.$/,"*") : '' }}님 환영합니다.
      [{{20-delayTime}} 초]
    </q-header>

    <q-page class="flex flex-center">
      <div class="text-h4 text-white text-center text-bold">
        <q-btn style="width:170px" class="text-h4 text-bold" label="운동종료" color="green-8" @click="end"></q-btn>
        &nbsp;
        <template v-if="showExtendBtn">
          <q-btn style="width:170px" class="text-h4 text-bold" label="타석연장" color="green-8" @click="extendTime"></q-btn>
          &nbsp;
        </template>
        <q-btn style="width:170px" class="text-h4 text-bold" label="타석이동" color="green-8" @click="moveBox"></q-btn>
          &nbsp;
        <q-btn style="width:170px" class="text-h4 text-bold" label="취소" color="orange-8" @click="cancel"></q-btn>
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
    userName: String,
    usingUserBoxId: String,
  },
  data() {
    return {
      delayTime: 0,
      showExtendBtn: false
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

    this.isExtend()

  },
  methods: {
    async isExtend(){
      this.$q.loading.show()
      axios.post(
          `http://${this.$static.SERVER_IP}/java/box/isExtend`,
          {
            "userName": this.userName,
            "userDong": this.userDong,
            "userHo": this.userHo
          }
      ).then(async (response) => {
        clearInterval(interval)
        this.$q.loading.hide()
        this.showExtendBtn = response.data
      }).catch(() => {
        this.$q.loading.hide()
        this.$router.push('/error')
      })

    },
    async end() {
      this.$q.loading.show()
      axios.post(
          `http://${this.$static.SERVER_IP}/java/box/end`,
          {
            "id": this.usingUserBoxId
          }
      ).then(async () => {
        clearInterval(interval)
        this.$q.loading.hide()
        await this.$router.push({name: 'bye', params: {text: '운동이 종료되었습니다. 감사합니다.'}})
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
