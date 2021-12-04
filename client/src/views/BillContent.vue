<template>
  <ion-page>
    <link href="assets/css/billContent.css" rel="stylesheet" />
    <ion-header>
      <ion-toolbar class="bill"></ion-toolbar>
      <ion-toolbar class="bill">
        <ion-title class="bill">账单详情</ion-title>
        <ion-row>
          <ion-col align="left">
            <ion-button class="billContent" @click="back">
              <ion-label class="billContent" color="light">back</ion-label>
            </ion-button>
          </ion-col>
          <ion-col></ion-col>
          <ion-col align="right">
            <ion-button class="billContent" @click="ModifyBillContent">
              <ion-label class="billContent" color="light">修改</ion-label>
            </ion-button>
          </ion-col>
        </ion-row>
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
        <ion-item class="bill">
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="asset">
                <h2 class="left">描述</h2>
              </ion-col>
              <ion-col class="asset">
                <h2 class="right">{{ describe }}</h2>
              </ion-col>
            </ion-row>
          </ion-label>
        </ion-item>
        <ion-item class="bill">
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="asset">
                <h2 class="left">金额</h2>
              </ion-col>
              <ion-col class="asset">
                <h2 class="right">{{ money }}</h2>
              </ion-col>
            </ion-row>
          </ion-label>
        </ion-item>
        <ion-item class="bill">
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="asset">
                <h2 class="left">付款资产</h2>
              </ion-col>
              <ion-col class="asset">
                <h2 class="right">{{ assetName }}</h2>
              </ion-col>
            </ion-row>
          </ion-label>
        </ion-item>
        <ion-item class="bill">
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="asset">
                <h2 class="left">记账时间</h2>
              </ion-col>
              <ion-col class="asset">
                <h2 class="right">{{ changeTime }}</h2>
              </ion-col>
            </ion-row>
          </ion-label>
        </ion-item>

        <ion-item button class="bill" detail="false" @click="ShowJoiners()">
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="asset">
                <h2 class="left">账单参与人员</h2>
              </ion-col>
            </ion-row>
          </ion-label>
        </ion-item>
      </ion-list>
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
  popoverController,
  modalController,
  IonItem,
} from "@ionic/vue";

import ModifyBillContent from "./ModifyBillContent.vue";
import BillContentJoiner from "./BillContentJoiner.vue";
import { Plugins } from "@capacitor/core";

export default defineComponent({
  inject: ["reload"],
  name: "bill",
  data() {
    return {
      describe: "",
      changeTime: "",
      money: "",
      assetName: "",
      billName: "",
      clean: "",
    };
  },
  methods: {
    async ModifyBillContent(ev: Event) {
      const popover = await popoverController.create({
        component: ModifyBillContent,
        cssClass: "popover",
        event: ev,
        translucent: true,
        componentProps: { recordId: this.billdetailId, clean: this.clean },
      });
      popover.onDidDismiss().then((data) => {
        if (data.data.msg == "changed") {
          this.billdetailId = data.data.recordId;
          console.log(data.data.msg);
          this.reload();
        }
      });
      return await popover.present();
    },
    async back() {
      console.log("flag2: " + this.flag);
      if (this.flag == "bill") {
        this.$router.replace({
          name: "BillDetail",
          params: {
            billId: this.id,
            share: this.share,
          },
        });
      } else {
        this.$router.replace({
          name: "AssetDetail",
          params: {
            id: this.id,
          },
        });
      }
      this.reload();
    },
    async ShowJoiners() {
      const modal = await modalController.create({
        component: BillContentJoiner,
        cssClass: "modal",
        componentProps: { recordId: this.billdetailId },
      });
      return await modal.present();
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
    const { Http } = Plugins;

    this.billdetailId = this.$route.params.billDetailId;
    this.flag = this.$route.params.flag;
    console.log("flag1: " + this.flag);
    this.share = this.$route.params.share;
    this.id = this.$route.params.id;
    console.log("billdetailId=" + JSON.stringify(this.billdetailId));

    const ret = await Http.request({
      method: "GET",
      url: `http://124.71.224.228:8080/api/bill/getRecordsInfo?id=${this.billdetailId}`,
    });
    const data = await ret.data.data;
    console.log(JSON.stringify(data));
    this.describe = data.describe;
    this.changeTime = data.createtime;
    this.money = data.count;
    this.assetName = data.assetsName;
    this.clean = data.clean;
  },
});
</script>
