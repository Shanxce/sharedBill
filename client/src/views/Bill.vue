<template>
  <ion-page>
    <link href="assets/css/bill.css" rel="stylesheet" />
    <ion-header>
      <ion-toolbar class="bill"></ion-toolbar>
      <ion-toolbar class="bill">
        <ion-title class="bill">账 本</ion-title>
        <ion-row>
          <ion-col></ion-col>
          <ion-col align="right">
            <ion-button class="bill" @click="openPopover">
              <ion-label class="addBill" color="light">+</ion-label>
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
        <ion-item
          button
          class="bill"
          v-for="bill in bills"
          :key="bill.id"
          detail="false"
          @click="gotoDetail(bill)"
        >
          <ion-label class="orange circle" v-if="bill.isshare == true">
            <ion-row>
              <ion-col class="bill">
                <h2 class="left">{{ bill.name }}</h2>
              </ion-col>
              <ion-col class="bill" v-if="bill.isshare == false">
                <ion-icon
                  class="bill"
                  src="assets/icon/lock-closed-outline.svg"
                ></ion-icon>
              </ion-col>
              <ion-col v-if="bill.isshare == true">
                <ion-icon
                  class="bill"
                  src="assets/icon/people-outline.svg"
                ></ion-icon>
              </ion-col>
            </ion-row>
          </ion-label>
          <ion-label class="light_yellow circle"  v-if="bill.isshare == false">
            <ion-row>
              <ion-col class="bill">
                <h2 class="left">{{ bill.name }}</h2>
              </ion-col>
              <ion-col class="bill" v-if="bill.isshare == false">
                <ion-icon
                  class="bill"
                  src="assets/icon/lock-closed-outline.svg"
                ></ion-icon>
              </ion-col>
              <ion-col v-if="bill.isshare == true">
                <ion-icon
                  class="bill"
                  src="assets/icon/people-outline.svg"
                ></ion-icon>
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
  IonIcon,
  IonLabel,
  IonPage,
  IonButton,
  IonList,
  IonContent,
  popoverController,
} from "@ionic/vue";
import { wallet, book, personCircle } from "ionicons/icons";
import AddBill from "./AddBill.vue";
import { Plugins } from "@capacitor/core";

export default defineComponent({
  inject:["reload"],
  name: "bill",
  data() {
    return {
      bills:[],
      /*
      bills: [
        { name: "test1", isshare: false },
        { name: "test2", isshare: true },
        { name: "test3", isshare: false },
        { name: "test4", isshare: false },
        { name: "test1", isshare: false },
        { name: "test2", isshare: true },
        { name: "test3", isshare: false },
        { name: "test4", isshare: false },
        { name: "test1", isshare: false },
        { name: "test2", isshare: true },
        { name: "test3", isshare: false },
        { name: "test4", isshare: false },
      ],
      */
    };
  },
  components: {
    IonIcon,
    IonLabel,
    IonPage,
    IonButton,
    IonList,
    IonContent,
  },

  methods: {
    async openPopover(ev: Event) {
      const popover = await popoverController.create({
        component: AddBill,
        cssClass: "popover",
        event: ev,
        translucent: true,
      });
      popover.onDidDismiss().then((data) => {
        if(data.data == "changed")
        {
          location.reload();
        }
      });
      await popover.present();
    },
    gotoDetail(bill) {
      this.$router.push({
        name: "BillDetail",
        params: {
          billId: bill.id,
          share: bill.isshare,
        },
      });
    },
  },
  created: async function () {
    const { Http } = Plugins;

    const ret = await Http.request({
      method: "GET",
      url: "http://124.71.224.228:8080/api/user/getBills",
    });
    const data = await ret.data.data;
    this.bills = data["bills"];
    console.log(JSON.stringify(this.bills));
  },

  setup() {
    return {
      wallet,
      book,
      personCircle,
    };
  },
});
</script>


