<template>
  <ion-page>
    <link href="assets/css/billDetail.css" rel="stylesheet" />
    <ion-header>
      <ion-toolbar class="bill"></ion-toolbar>
      <ion-toolbar class="bill">
        <ion-title class="bill">账本详情</ion-title>
        <ion-row>
          <ion-col align="left" @click="back">
            <ion-button class="asset">
              <ion-label class="back" color="light">back</ion-label>
            </ion-button>
          </ion-col>
          <ion-col align="right">
            <ion-button class="bill" @click="openModal">
              <ion-label class="addBillDetail" color="light">+</ion-label>
            </ion-button>
          </ion-col>
        </ion-row>

        <ion-label align="center" v-if="share == 'true'"
          >邀请码：{{ joinCode }}</ion-label
        >
      </ion-toolbar>
    </ion-header>

    <ion-content
      class="bill"
      :scroll-events="true"
      @ionScrollStart="logScrollStart()"
      @ionScroll="logScrolling($event)"
      @ionScrollEnd="logScrollEnd()"
    >
      <ion-list>
        <ion-item
          button
          class="bill"
          v-for="billdetail in billdetails"
          :key="billdetail.billdetailId"
          detail="false"
          @click="gotoDetail(billdetail)"
        >
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="bill">
                <h2 class="left">{{ billdetail.billDescribe }}</h2>
              </ion-col>
              <ion-col class="bill">
                <h2 class="right">{{ billdetail.count }}元</h2>
              </ion-col>
            </ion-row>
          </ion-label>
        </ion-item>
      </ion-list>
      <ion-row align="center" v-if="share == 'true'">
        <ion-button class="bill" @click="calculate">
          <ion-label class="calculate" color="light">清算</ion-label>
        </ion-button>
      </ion-row>
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import {
  IonLabel,
  IonPage,
  IonList,
  IonButton,
  IonContent,
  IonItem,
  modalController,
} from "@ionic/vue";

import { Plugins } from "@capacitor/core";
import AddBillContent from "./AddBillContent.vue";

export default defineComponent({
  inject:['reload'],
  name: "bill",
  data() {
    return {
      billdetails: [],
      joinCode: "",
      share: false,
      billId: "",
    };
  },
  methods: {
    async back() {
      this.$router.replace("/tabs/bill");
    },
    async openModal() {
      const modal = await modalController.create({
        component: AddBillContent,
        cssClass: "modal",
        componentProps: { billId: this.billId, share: this.share },
      });

      modal.onDidDismiss().then((data) => {
        if (data.data.msg == "changed")
        {
          this.reload();
        }
      });
      return await modal.present();
    },
    async calculate() {
      const { Http } = Plugins;
      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/bill/calculate",
        data: {
          id: this.billId,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
      const data = await ret.data;
      console.log(data);
      if (data.code == 200) {
        alert("清算成功");
        this.reload();
      } else {
        alert("清算失败");
      }
    },
    gotoDetail(billdetail) {
      this.$router.push({
        name: "BillContent",
        params: {
          billDetailId: billdetail.billdetailId,
          flag:"bill",
          share:this.share,
          id:this.billId,
        },
      });
    },
  },
  components: {
    IonLabel,
    IonPage,
    IonButton,
    IonList,
    IonContent,
    IonItem,
  },
  created: async function () {
    this.billId = this.$route.params.billId;
    this.share = this.$route.params.share;

    console.log("billId=" + JSON.stringify(this.billId));
    console.log("share=" + JSON.stringify(this.share));
    console.log("share=" + JSON.stringify(true));

    const { Http } = Plugins;
    const ret = await Http.request({
      method: "GET",
      url: `http://124.71.224.228:8080/api/bill/getRecords?id=${this.billId}`,
    });
    const data = await ret.data.data;
    //console.log(data["records"]);
    this.billdetails = data["records"];
    console.log(JSON.stringify(data["records"]));
    const ret2 = await Http.request({
      method: "GET",
      url: `http://124.71.224.228:8080/api/bill/getInviteCode?billId=${this.billId}`,
    });
    const data2 = await ret2.data.data;
    this.joinCode = data2["inviteCode"];
  },
});
</script>
