<template>
  <q-page class="flex flex-center">
    <div class="text-h4 text-purple text-bold">
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
    return {}
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

              this.$router.push({
                name: 'order', params: {
                  userDong: data.userDongHo.split('-')[0],
                  userHo: data.userDongHo.split('-')[1],
                  userName: data.userName
                }
              })
            }
          })
          .catch(() => {
            this.$router.push('error')
          })
    }
  }
}
</script>
