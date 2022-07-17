<template>
  <q-page class="flex flex-center" style="background-color:rgba(0, 0, 0, 0.6);">
    <div class="text-h3 text-white text-bold">
      안녕하세요. 생채인증을 해주세요.
      <q-linear-progress indeterminate color="warning"></q-linear-progress>
    </div>
  </q-page>
</template>
<style>
</style>

<script>
let interval
let count = 0
import axios from "axios";

export default {
  name: 'HelloWorld',
  data() {
    return {
      userName: '',
      userDong: '',
      userHo: ''
    }
  },
  mounted() {
    count = 0
    axios.post(
        `http://${this.$static.SERVER_IP}/java/order/cancel`
    ).then(() => {
      clearInterval(interval)
      interval = setInterval(() => this.checkUser(), 2000)
      this.$q.loading.hide()
    }).catch(() => {
      this.$q.loading.hide()
      this.$router.push('/error')
    })
  },
  methods: {
    checkUser() {
      count = count + 2
      if(count > 15){
        count = 0
        clearInterval(interval)
        this.$router.push('/')
      }
      axios
          .get(`http://${this.$static.SERVER_IP}/java/user/`)
          .then(response => {
            const data = response.data
            if (data.userDongHo !== null) {
              clearInterval(interval)

              this.userDong = data.userDongHo.split('-')[0]
              this.userHo = data.userDongHo.split('-')[1]
              this.userName = data.userName

              this.checkUserStatus()
            }
          })
          .catch(() => {
            this.$router.push('error')
          })
    },
    async checkUserStatus() {
      axios
          .post(`http://${this.$static.SERVER_IP}/java/box/status`,
              {
                "userName": this.userName,
                "userDong": this.userDong,
                "userHo": this.userHo,
              }
          )
          .then(response => {
            const data = response.data
            console.log(data)

            // {
            //   "fullBox": false,
            //     "firstBooking": true,
            //     "usingUser": false,
            //     "watingUser": false
            // }

            //최초예약 + 타석이 비어있는 경우
            if(data.firstBooking && !data.fullBox){
              this.forwardPage('booking')
            //최초예약 + 타석이 꽉 찬 경우
            }else if(data.firstBooking && data.fullBox){
              this.forwardPage('alertFullBox')
            }
          })
          .catch(() => {
            this.$router.push('error')
          })
    },
    forwardPage(pageName){
      this.$router.push({
        name: pageName, params: {
          userDong: this.userDong,
          userHo: this.userHo,
          userName: this.userName
        }
      })
    }
  }
}
</script>
