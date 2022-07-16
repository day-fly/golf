<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-purple-9 text-white" height-hint="98">
      <q-toolbar>
        <q-toolbar-title>
          주문관리
        </q-toolbar-title>
        <q-btn flat>서버상태 : {{ serverState }}</q-btn>
      </q-toolbar>

      <q-tabs
          v-model="tab"
          class="bg-black text-white shadow-2 text-h6"
          indicator-color="yellow"
          align="left"
      >
        <q-tab name="current" label="주문현황"></q-tab>
        <q-tab name="prev" label="주문내역"></q-tab>
      </q-tabs>
    </q-header>

    <q-page-container>
      <q-tab-panels v-model="tab" animated>
        <q-tab-panel name="current">
          <div class="q-pa-md row items-start q-gutter-md">
            <template v-if="orderList.length > 0">
            <q-card v-for="order in orderList" class="my-card" :key="order.id">
              <q-card-section class="bg-yellow-4 text-black">
                <div class="text-h6 text-center text-bold">
                  주문번호 : {{order.orderSeq}}
                </div>
                <div class="text-h6 text-center text-bold">
                  {{ order.orderDong }}-{{ order.orderHo }}&nbsp;{{ order.orderName }}
                </div>
                <div class="text-subtitle2 text-center">
                  {{ order.orderDate.replace("T", " ") }}
                </div>
              </q-card-section>

              <q-separator></q-separator>

              <q-card-actions vertical>
                <q-btn class="text-subtitle1" v-for="menu of order.cafeOrderProductList" flat :key="menu.id">
                  {{ menu.orderMenuName }}
                  <q-space/>
                  <b>{{ menu.orderMenuCount }}</b>
                </q-btn>
              </q-card-actions>

              <div class="text-center div-vertical-center">
                <q-btn class="glossy text-h6" color="purple-9" label="완료" style="width: 120px; height: 30px;" @click="complete(order)"></q-btn>&nbsp;&nbsp;
                &nbsp;&nbsp;
                <q-btn class="glossy text-h6" color="grey" label="취소" style="width: 80px;" @click="cancel(order)"></q-btn>
              </div>
              <div>&nbsp;</div>
            </q-card>
            </template>
            <template v-else>
              현재 주문이 없습니다.
            </template>
          </div>
        </q-tab-panel>

        <q-tab-panel name="prev">
          <div class="q-pa-md">
            <q-table
                class="my-sticky-header-table"
                title="주문내역"
                :rows="prevOrderList"
                :columns="columns"
                bordered
                :pagination=pagination
            >
              <template v-slot:body="props">
                <q-tr :props="props">
                  <q-td key="orderName" :props="props" class="text-bold">
                    {{ props.row.orderDong + '-' + props.row.orderHo + ' ' + props.row.orderName }}
                  </q-td>
                  <q-td key="orderDate" :props="props">
                    {{ props.row.orderDate.replace("T", " ") }}
                  </q-td>
                  <q-td key="orders" :props="props" style="max-width: 500px;">
                    {{ props.row.productListText }}
                  </q-td>
                  <q-td key="status" :props="props">
                    {{ state[props.row.orderState] }}
                  </q-td>
                  <q-td key="reset" :props="props">
                    <q-btn class="text-subtitle2 text-bold" color="purple" name="restore" label='복원' @click="restore(props.row)"/>                  </q-td>
                </q-tr>
              </template>
            </q-table>
          </div>
        </q-tab-panel>
      </q-tab-panels>
    </q-page-container>

  </q-layout>
</template>

<script>

import {ref} from 'vue'
import axios from "axios";
import {useQuasar} from "quasar";

export default {
  name: 'processOrder',
  data() {
    return {
      serverState: 'OFF',
      interval: undefined,
      $q: useQuasar(),
      pagination: {
        rowsPerPage: 10 // current rows per page being displayed
      },
      columns: [
        {name: 'orderName', align: 'left', label: '이름', field: 'orderName'},
        {name: 'orderDate', align: 'left', label: '시간', field: 'orderDate'},
        {name: 'orders', align: 'left', label: '주문', field: 'orders'},
        {name: 'status', align: 'left', label: '상태', field: 'status'},
        {name: 'reset', align: 'center', label: '복원', field: 'reset'},
      ],
      state: {
        COMPLETE: '완료',
        CANCEL: '취소',
        WAIT: '대기'
      },
      orderList: [],
      prevOrderList: []
    }
  },
  created() {
    console.log('module.hot', module.hot)
    const hotEmitter = require('webpack/hot/emitter');
    hotEmitter.on('webpackHotUpdate', () => {
      console.log('zzz')
      this.getOrderList()
      this.login()
    });

    this.getOrderList()
    this.login()
  },
  methods: {
    complete(order) {
      axios.defaults.headers.post['Content-Type'] ='application/x-www-form-urlencoded';
      axios.patch(
          `http://${this.$static.SERVER_IP}/java/order/complete/`+order.id
      ).then(async () => {
        this.$q.notify({
          message: '완료되었습니다.',
          color: 'black'
        })
        await this.getOrderList()
      }).catch(() => {
        self.serverState = 'OFF'
        alert('서버에 접속할 수 없습니다.')
      })
    },
    cancel(order) {
      axios.patch(
          `http://${this.$static.SERVER_IP}/java/order/cancel/`+order.id
      ).then(async () => {
        this.$q.notify({
          message: '취소되었습니다.',
          color: 'black'
        })
        await this.getOrderList()
      }).catch(() => {
        self.serverState = 'OFF'
        alert('서버에 접속할 수 없습니다.')
      })
    },
    restore(order) {
      axios.patch(
          `http://${this.$static.SERVER_IP}/java/order/restore/`+order.id
      ).then(async () => {
        this.$q.notify({
          message: '복원되었습니다.',
          color: 'black'
        })
        await this.getOrderList()
      }).catch(() => {

      })
    },
    async getOrderList() {
      await axios
          .get(`http://${this.$static.SERVER_IP}/java/order/list`)
          .then(response => {
            self.serverState = 'ON'
            console.log(response)
            this.orderList = response.data
                .map((obj, index)=>{
                  obj.orderSeq = index+1
                  return obj
                })
                .filter((obj) => obj.orderState === 'WAIT')
            console.log('this.orderList', this.orderList)
            this.prevOrderList = response.data.filter((obj) => obj.orderState !== 'WAIT')
            console.log('this.prevOrderList', this.prevOrderList)
          }).catch(() => {
            self.serverState = 'OFF'
            //this.$router.push('/error')
      })
    },
    login(){
      const eventSource = new EventSource(`http://${this.$static.SERVER_IP}/java/connect`)
      const self = this
      eventSource.addEventListener("sse", function (event) {
        self.serverState = 'ON'
        console.log(event.data);
        if(event.data === 'NEW_ORDER'){
          const music = new Audio('ding.wav')
          music.play()
          self.getOrderList()
        }
      })
      eventSource.addEventListener("error", () => {
        self.serverState = 'OFF'
      });
      eventSource.addEventListener("open", () => {
        self.serverState = 'ON'
      });
    }
  },
  setup() {
    return {
      tab: ref('current'),
    }
  }
}
</script>

<style lang="scss" scoped>
.my-card {
  width: 100%;
  max-width: 250px;
  cursor: pointer;
}
</style>