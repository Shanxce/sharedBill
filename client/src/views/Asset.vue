<template>
    <ion-page>
              <link href="assets/css/asset.css" rel="stylesheet"  />
      <ion-header>
        <ion-toolbar class="asset"></ion-toolbar>
        <ion-toolbar class="asset">
          <ion-title class="asset">资 产</ion-title>
          <ion-row>
            <ion-col></ion-col>
            <ion-col align="right">
              <ion-button class="asset" @click="openPopover">
                <ion-label class="Asset" color="light">+</ion-label>
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
        <ion-list>
          <ion-item button class="asset" v-for="asset in assets" :key="asset.id" detail="false" @click="gotoDetail(asset)">
            <ion-label class="light circle">
              <ion-row>
                <ion-col class="asset">
                  <h2 class="left">{{ asset.name }}</h2>
                </ion-col>
                <ion-col class="asset">
                  <h2 class="right">{{ asset.amount }}</h2>
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
  IonButton,
  IonList,
  IonContent,
  popoverController,
  IonItem,
} from "@ionic/vue";

import AddAsset from "./AddAsset.vue";
import { wallet, book, personCircle } from "ionicons/icons";
import { Plugins } from "@capacitor/core";

export default defineComponent({
  inject:["reload"],
  name: "asset",
  data() {
    return {
      assets: [],
    };
  },
  methods: {
    async openPopover(ev: Event) {
      const popover = await popoverController.create({
        component: AddAsset,
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
    gotoDetail(asset) {
      this.$router.push({
        name: "AssetDetail",
        params: {
          id: asset.id,
        }
      })
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

    const ret = await Http.request({
      method: "GET",
      url: "http://124.71.224.228:8080/api/assets/getAssets",
    });
    
    const data = await ret.data.data;
    this.assets = data["assets"];
  },
});
</script>



