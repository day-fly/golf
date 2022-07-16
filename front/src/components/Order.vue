<template>
    <q-layout view="hHh lpR fFf">
      <q-header class="bg-black text-white text-h4 text-bold row items-center justify-center" style="min-height:100px">
        &nbsp;&nbsp;{{ userDong ? userDong.replace(/.$/,"*") : ''}}동 {{ userHo ? userHo.replace(/.$/,"*") : '' }}호 {{ userName ? userName.replace(/.$/,"*") : '' }}님 환영합니다.
        [{{20-delayTime}} 초]
      </q-header>

      <q-page-container>
        <div class="row">
          <div class="fixed-center text-h5">
            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석9" @click="confirmOrder"></q-btn>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석14" @click="confirmOrder"></q-btn>

            <br/><br/>

            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석8" @click="confirmOrder"></q-btn>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석13" @click="confirmOrder"></q-btn>

            <br/><br/>

            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석7" @click="confirmOrder"></q-btn>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석12" @click="confirmOrder"></q-btn>

            <br/><br/>

            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석6" @click="confirmOrder"></q-btn>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석11" @click="confirmOrder"></q-btn>

            <br/><br/>

            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석5" @click="confirmOrder"></q-btn>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석10" @click="confirmOrder"></q-btn>

            <br/><br/>

            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석4" @click="confirmOrder"></q-btn>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
            <q-btn style="width:200px;" class="text-h4 text-bold" color="grey" label="x"></q-btn>

            <br/><br/>

            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석3" @click="confirmOrder"></q-btn>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
            <q-btn style="width:200px;" class="text-h4 text-bold" color="grey" label="x"></q-btn>

            <br/><br/>

            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석2" @click="confirmOrder"></q-btn>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
            <q-btn style="width:200px;" class="text-h4 text-bold" color="grey" label="x"></q-btn>

            <br/><br/>

            <q-btn style="width:200px;" class="text-h4 text-bold text-green" color="white" label="타석1" @click="confirmOrder"></q-btn>
            &nbsp;
            &nbsp;
            &nbsp;
            &nbsp;
            <q-btn style="width:200px;" class="text-h4 text-bold" color="grey" label="x"></q-btn>

          </div>
        </div>
      </q-page-container>

      <q-footer elevated class="bg-black text-white text-h4 text-bold row items-center justify-center" style="min-height:100px">
        원하시는 타석을 선택해주세요.
      </q-footer>

    </q-layout>

  <q-dialog v-model="showConfirmOrders">
    <q-card>
      <q-card-section class="bg-brown text-white">
        <div class="text-h6 text-bold">주문을 확인해주세요</div>
      </q-card-section>

      <q-separator></q-separator>

      <q-list bordered separator>
        <q-item v-for="n in orders" :key="n.id">
          <q-item-section class="text-h6">{{ n.menuName }}</q-item-section>
          <q-item-section class="text-h6" side>{{ n.qty }}</q-item-section>
        </q-item>
      </q-list>

      <q-separator></q-separator>

      <q-card-actions align="right">
        <q-btn style="width:150px" class="text-h6 text-bold" label="완료" color="deep-orange"
               @click="completeOrders"></q-btn>
        <q-btn style="width:150px" class="text-h6 text-bold" label="취소" color="grey" v-close-popup></q-btn>
      </q-card-actions>
    </q-card>
  </q-dialog>

  <q-dialog v-model="showAlert">
    <q-card>
      <q-card-section class="bg-purple-9 text-white">
        <div class="text-h6 text-bold">주문할 메뉴를 선택하세요.</div>
      </q-card-section>

      <q-card-actions align="center">
        <q-btn style="width:150px" class="text-h6 text-bold" label="확인" color="deep-orange" v-close-popup></q-btn>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
let interval
import {ref} from 'vue'
import axios from "axios";
import {useQuasar} from 'quasar'

export default {
  name: 'order',
  props: {
    userDong: String,
    userHo: String,
    userName: String
  },
  data() {
    return {
      delayTime: 0,
      $q: useQuasar(),
      showConfirmOrders: false,
      showAlert: false,
      orders: [],
      orderSeq: 0,
      menu1Levels: [],
      totalPrice: 0,
      menu2Levels: [],
      colorRgbList: [
        'rgb(215, 128, 18)',
        'rgb(166, 12, 34)',
        'rgb(63, 8, 24)',
        'rgb(250, 207, 20)',

        'rgb(93, 190, 165)',
        'rgb(242, 157, 17)',
        'rgb(191, 21, 111)',
        'rgb(132, 48, 142)'
      ]
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
        //this.cancelOrder()
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

    this.getOrderList()

    this.$q.loading.show()
    axios
        .get(`http://${this.$static.SERVER_IP}/java/menu/list`)
        .then(response => {
          const menu1Levels = response.data.filter((obj) => obj.parentId === null)
          const menu2Levels = response.data.filter((obj) => obj.parentId !== null)

          menu1Levels.sort((a, b) => (a.menuIndex > b.menuIndex ? 1 : -1))
          this.menu1Levels = menu1Levels
          this.menu2Levels = menu2Levels
          this.$q.loading.hide()
        })
  },
  methods: {
    async getOrderList() {
      await axios
          .get(`http://${this.$static.SERVER_IP}/java/order/list`)
          .then(response => {
            console.log(response.data)
            this.orderSeq = response.data.length + 1
            console.log('orderSeq : ', this.orderSeq)
          }).catch(() => {

          })
    },
    cancelOrder(){
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
    confirmOrder() {
      this.delayTime = 0
      if (this.orders.length === 0) {
        this.showAlert = true
        return
      }
      this.showConfirmOrders = true
    },
    selectMenu(menu) {
      this.delayTime = 0
      const existOrder = this.orders.find(c => c.id === menu.id);
      if (!existOrder) {
        const order = Object.assign({}, menu)
        order.qty = 1
        this.orders.push(order)
      } else {
        existOrder.qty += 1
      }
      this.calTotalPrice()
    },
    addQty(order) {
      this.delayTime = 0
      order.qty += 1
      this.calTotalPrice()
    },
    minusQty(order) {
      this.delayTime = 0
      if (order.qty > 1) {
        order.qty -= 1
        this.calTotalPrice()
      }
    },
    deleteOrder(order) {
      this.delayTime = 0
      const orders = this.orders.filter((obj) => order.id !== obj.id)
      this.orders = orders
      this.calTotalPrice()
    },
    completeOrders() {
      this.delayTime = 0
      if(!this.userName){
        this.$router.push('/')
        return
      }

      let orderProducts = []
      this.orders.forEach((obj) => {
        orderProducts.push({
          "orderMenuId": obj.id,
          "orderMenuName": obj.menuName,
          "orderMenuCost": obj.menuCost,
          "orderMenuCount": obj.qty,
        })
      })

      this.$q.loading.show()
      axios.post(
              `http://${this.$static.SERVER_IP}/java/order/`,
          {
            "orderName": this.userName,
            "orderDong": this.userDong,
            "orderHo": this.userHo,
            "orderProducts": orderProducts
          }
      ).then(async () => {
        clearInterval(interval)
        this.$q.loading.hide()
        await this.$router.push({name: 'bye', params: {orderSeq: this.orderSeq}})
      }).catch(() => {
        this.$q.loading.hide()
        this.$router.push('/error')
      })
    },
    calTotalPrice() {
      //const target = Object.assign({}, this.orders)
      const result = this.orders.reduce(function (acc, obj) {
        return acc + (obj.menuCost * obj.qty)
      }, 0)
      this.totalPrice = result
    }
  },
  setup() {
    return {
      tab: ref('coffee'),
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .q-tab__label {
  font-size: 1.8em;
}

::v-deep .q-tab__icon {
  width: 45px;
  height: 40px;
  font-size: 45px;
}

.bg-image {
  background-image: url(https://cdn.quasar.dev/img/mountains.jpg);
  background-repeat: no-repeat;
  background-size: contain;
}


.my-card {
  width: 100%;
  max-width: 140px;
  min-height: 300px;
  cursor: pointer;
}

.div-vertical-center {
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}

</style>
