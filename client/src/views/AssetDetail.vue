<template>
  <ion-page>
    <link href="assets/css/assetDetail.css" rel="stylesheet" />
    <ion-header>
      <ion-toolbar class="assettitle"></ion-toolbar>
      <ion-toolbar class="assettitle">
        <ion-title class="assetdetail">资产详细信息</ion-title>
        <ion-row>
          <ion-col align="left">
            <ion-button class="asset" @click="back">
              <ion-label class="addAsset" color="light">back</ion-label>
            </ion-button>
          </ion-col>
          <ion-col></ion-col>
          <ion-col align="right">
            <ion-button class="asset" @click="ModifyAsset">
              <ion-label class="addAsset" color="light">修改</ion-label>
            </ion-button>
          </ion-col>
        </ion-row>
      </ion-toolbar>
    </ion-header>

    <ion-content
      class="asset"
      :scroll-events="true"
      @ionScrollStart="logScrollStart()"
      @ionScroll="logScrolling($event)"
      @ionScrollEnd="logScrollEnd()"
    >
      <ion-item>
        <ion-label class="listhead">资产信息</ion-label>
      </ion-item>

      <ion-list>
        <ion-item class="asset">
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="asset">
                <h2 class="left">资产名称</h2>
              </ion-col>
              <ion-col class="asset">
                <h2 class="right">{{ name }}</h2>
              </ion-col>
            </ion-row>
          </ion-label>
        </ion-item>

        <ion-item class="asset">
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="asset">
                <h2 class="left">资产金额</h2>
              </ion-col>
              <ion-col class="asset">
                <h2 class="right">{{ number }}</h2>
              </ion-col>
            </ion-row>
          </ion-label>
        </ion-item>

        <ion-item class="asset">
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="asset">
                <h2 class="left">资产创建时间</h2>
              </ion-col>
              <ion-col class="asset">
                <h2 class="right">{{ createtime }}</h2>
              </ion-col>
            </ion-row>
          </ion-label>
        </ion-item>
      </ion-list>

      <ion-item>
        <ion-label class="listhead">资产消费信息</ion-label>
      </ion-item>

      <ion-list>
        <ion-item
          button
          class="asset"
          v-for="billdetail in billdetails"
          :key="billdetail.billdetailId"
          detail="false"
          @click="gotoDetail(billdetail)"
        >
          <ion-label class="light circle">
            <ion-row>
              <ion-col class="bill">
                <h2 class="left">{{ billdetail.describe }}</h2>
              </ion-col>
              <ion-col class="bill">
                <h2 class="right">{{ billdetail.count }}元</h2>
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
  IonItem,
} from "@ionic/vue";

import ModifyAsset from "./ModifyAsset.vue";
import { wallet, book, personCircle } from "ionicons/icons";
import { Plugins } from "@capacitor/core";

export default defineComponent({
  inject: ["reload"],
  name: "asset",
  data() {
    return {
      assets: "",
      assetId: 0,
      name: "",
      number: 0,
      createtime: "",
      billdetails: [],
    };
  },
  methods: {
    async ModifyAsset(ev: Event) {
      const popover = await popoverController.create({
        component: ModifyAsset,
        cssClass: "popover",
        event: ev,
        translucent: true,
        componentProps: { id: this.assetId },
      });

      popover.onDidDismiss().then((data) => {
        if (data.data.msg == "changed")
        {
          this.assetId = data.data.id;
          console.log(data.data.msg);
          this.reload();
        }
      });
      return await popover.present();
    },
    async back() {
      this.$router.replace("/tabs/asset");
    },
    gotoDetail(billdetail) {
      console.log(JSON.stringify(billdetail));
      this.$router.push({
        name: "BillContent",
        params: {
          billDetailId: billdetail.recordId,
          flag: "asset",
          share:true,
          id:this.assetId,
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

  setup() {
    return {
      wallet,
      book,
      personCircle,
    };
  },
  created: async function () {
    const { Http } = Plugins;

    this.assetId = this.$route.params.id;
    const ret1 = await Http.request({
      method: "GET",
      url: `http://124.71.224.228:8080/api/assets/getAssetsInfo?id=${this.assetId}`,
    });
    const dataAsset = await ret1.data.data;
    //const data = {"name": "weixin", "number": 100, "createtime": "2021-6-1"};
    this.name = dataAsset["name"];
    this.number = dataAsset["number"];
    this.createtime = dataAsset["createtime"];

    const ret2 = await Http.request({
      method: "GET",
      url: `http://124.71.224.228:8080/api/assets/getRecords?id=${this.assetId}`,
    });
    const dataBill = await ret2.data.data;
    this.billdetails = dataBill["billDetail"];
  },
});
</script>


