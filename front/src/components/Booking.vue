<template>
    <q-layout view="hHh lpR fFf">
      <q-header class="bg-black text-white text-h4 text-bold row items-center justify-center" style="min-height:100px">
        &nbsp;&nbsp;{{ userDong ? userDong.replace(/.$/,"*") : ''}}동 {{ userHo ? userHo.replace(/.$/,"*") : '' }}호 {{ userName ? userName.replace(/.$/,"*") : '' }}님 환영합니다.
        [{{20-delayTime}} 초]
      </q-header>

      <q-page-container>
        <div class="row">
          <div class="fixed-center text-h5">
            <template v-for="(n,index) in boxInfos" :key="n.id">
              <template v-if="index % 2 == 1">
                &nbsp;
                &nbsp;
                &nbsp;
                &nbsp;
              </template>
              <q-btn style="width:200px;"
                     class="text-h4 text-bold text-green-7"
                     :disabled = "n.id == 'x' || n.useYn == 'Y' ? true : false"
                     :color="n.id == 'x'  || n.useYn == 'Y' ? 'grey' : 'white'"
                     :label="'타석'+n.name"
                     @click="booking(n.id, n.name)"></q-btn>
              <template v-if="index % 2 == 1">
                <br/><br/>
              </template>
            </template>
          </div>
        </div>
      </q-page-container>

      <q-footer elevated class="bg-black text-white text-h4 text-bold row items-center justify-center" style="min-height:100px">
        원하시는 타석을 선택해주세요.
      </q-footer>

    </q-layout>


  <q-dialog v-model="showAlert">
    <q-card>
      <q-card-section class="bg-green-9 text-white">
        <div class="text-h6 text-bold">{{selectedBoxName}}번 타석을 예약진행 하시겠습니까?</div>
      </q-card-section>

      <q-card-actions align="center">
        <q-btn style="width:150px" class="text-h6 text-bold" label="확인" color="black" @click="completeBooking" v-close-popup></q-btn>
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
      showAlert: false,
      orders: [],
      orderSeq: 0,
      totalPrice: 0,
      selectedBoxId: '',
      selectedBoxName: '',
      boxInfos: [
        {id: 'golf_9', name: '9'},
        {id: 'golf_14', name: '14'},
        {id: 'golf_8', name: '8'},
        {id: 'golf_13', name: '13'},
        {id: 'golf_7', name: '7'},
        {id: 'golf_12', name: '12'},
        {id: 'golf_6', name: '6'},
        {id: 'golf_11', name: '11'},
        {id: 'golf_5', name: '5'},
        {id: 'golf_10', name: '10'},
        {id: 'golf_4', name: '4'},
        {id: 'x', name: 'x'},
        {id: 'golf_3', name: '3'},
        {id: 'x', name: 'x'},
        {id: 'golf_2', name: '2'},
        {id: 'x', name: 'x'},
        {id: 'golf_1', name: '1'},
        {id: 'x', name: 'x'},
      ],
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
        //this.cancel()
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
    this.getBoxList()
  },
  methods: {
    async getBoxList() {
      this.$q.loading.show()
      await axios
          .get(`http://${this.$static.SERVER_IP}/java/box/list`)
          .then(response => {
            this.$q.loading.hide()
            const boxInfos = response.data
            boxInfos.forEach((obj) => {
              this.boxInfos.find(box => box.id == obj.id).useYn = obj.useYn
            })
          }).catch(() => {
            this.$q.loading.hide()
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
    booking(id, name) {

      //테스트 이후 주석해제
      if(!this.userName){
        this.$router.push('/')
        return
      }

      this.delayTime = 0
      this.selectedBoxId = id
      this.selectedBoxName = name
      this.showAlert = true

    },
    completeBooking(){
      axios.post(
          `http://${this.$static.SERVER_IP}/java/box/start`,
          {
            "id": this.selectedBoxId,
            "userName": this.userName,
            "userDong": this.userDong,
            "userHo": this.userHo
          }
      ).then(async () => {
        clearInterval(interval)
        this.$q.loading.hide()
        await this.$router.push({name: 'bye', params: {text: '예약이 완료되었습니다. 감사합니다.'}})
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
