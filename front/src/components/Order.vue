<template>
  <q-layout view="hHh lpR fFf">
    <q-header class="bg-brown-7 text-white text-h4 text-bold row items-center justify-center" style="min-height:100px">
      &nbsp;&nbsp;{{ userDong ? userDong.replace(/.$/,"*") : ''}}동 {{ userHo ? userHo.replace(/.$/,"*") : '' }}호 {{ userName ? userName.replace(/.$/,"*") : '' }}님 환영합니다.
      [{{20-delayTime}} 초]
    </q-header>

    <q-drawer behavior="desktop"
              :show-if-above="true" side="right" bordered :width="400">
      <!-- drawer content -->
      <div class="q-pa-md" style="max-width: 400px">
        <div align="center">
          <q-btn class="glossy text-h4 text-weight-bold" rounded color="deep-orange" label="주문"
                 style="height: 50px;min-width: 150px" @click="confirmOrder"></q-btn>&nbsp;&nbsp;
          <q-btn class="glossy text-h4 text-weight-bold" rounded color="grey" label="취소"
                 style="height: 50px;min-width: 150px" @click="cancelOrder"></q-btn>
        </div>
        <br>
        <q-toolbar class="bg-brown text-white shadow-2 glossy">

          <q-toolbar-title>
            주문목록
          </q-toolbar-title>
        </q-toolbar>

        <q-list bordered>
          <q-item v-for="order in orders" :key="order.id" class="q-my-sm" clickable v-ripple>
            <q-item-section avatar>
              <q-btn color="black" text-color="white" push label="X" style="font-size: 1.2em"
                     @click="deleteOrder(order)"></q-btn>
            </q-item-section>
            <q-item-section>
              <q-item-label style="font-size: 1.2em">{{ order.menuName }}</q-item-label>
              <q-item-label style="font-size: 1.2em" caption lines="1">주문수량 : <b class="text-accent">{{ order.qty }}</b>
              </q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-btn-group push>
                <q-btn color="white" text-color="black" push label="+" style="font-size: 1.5em"
                       @click="addQty(order)"></q-btn>
                <q-btn color="grey-4" text-color="black" push label="-" style="font-size: 1.5em"
                       @click="minusQty(order)"></q-btn>
                <!--                <q-btn color="red" text-color="white" push label="X" style="font-size: 1.2em" @click="minusQty(order)"></q-btn>-->
              </q-btn-group>

            </q-item-section>
          </q-item>
          <q-separator></q-separator>
        </q-list>
        <div v-if="orders.length > 0" class="row justify-end text-h5 text-bold">₩ {{ totalPrice }}원</div>
      </div>
    </q-drawer>

    <q-page-container>
      <q-tabs
          v-model="tab"
          indicator-color="black"
          class="bg-purple-9 text-white shadow-2"
          dense
          align="justify"
      >

        <template v-for="(menu,index) in menu1Levels.slice(0,4)" :key="menu.id">
          <q-tab class="text-bold text-h6" :name="menu.menuName" :label="menu.menuName"
                 :style="`background-color:${colorRgbList[index]}`" style="min-height: 100px;" @click="delayTime = 0"></q-tab>
        </template>
      </q-tabs>

      <q-tabs
          v-model="tab"
          indicator-color="black"
          class="bg-purple-9 text-white shadow-2"
          dense
          align="justify"
      >
        <template v-for="(menu,index) in menu1Levels.slice(4,8)" :key="menu.id">
          <q-tab class="text-bold text-h6" :name="menu.menuName" :label="menu.menuName"
                 :style="`background-color:${colorRgbList[index+4]}`" style="min-height: 100px;" @click="delayTime = 0"></q-tab>
        </template>
      </q-tabs>

      <q-tab-panels v-model="tab" animated>
        <q-tab-panel v-for="menu in menu1Levels" :name="menu.menuName" :key="menu.id">
          <q-toolbar class="bg-brown text-white">
            <q-toolbar-title clas="text-h6 text-bold">{{ menu.menuName }}</q-toolbar-title>
          </q-toolbar>
          <div class="q-pa-md row items-start q-gutter-md">
            <q-card v-for="subMenu in menu2Levels.filter((obj) => obj.parentId === menu.id)" :key="subMenu.id"
                    class="my-card" @click="selectMenu(subMenu)">
              <q-img src="../images/coffee.jpg"/>
              <q-card-section class="text-center">
                <b class="text-accent" style="font-size: 1.4em;">{{ subMenu.menuCost }}원</b>
                <div style="font-size: 1.5vw;" v-html="subMenu.menuName"></div>
              </q-card-section>
            </q-card>
          </div>
        </q-tab-panel>
      </q-tab-panels>
    </q-page-container>
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
