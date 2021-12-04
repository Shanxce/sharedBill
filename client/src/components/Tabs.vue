<template>
  <ion-page>
        <ion-tabs
          @ionTabsWillChange="beforeTabChange"
          @ionTabsDidChange="afterTabChange"
        >
          <ion-tab-bar slot="bottom">
            <ion-tab-button tab="/tabs/asset" href="/tabs/asset">
              <ion-icon :icon="wallet"></ion-icon>
              <ion-label>资产</ion-label>
            </ion-tab-button>

            <ion-tab-button tab="/tabs/bill" href="/tabs/bill">
              <ion-icon :icon="book"></ion-icon>
              <ion-label>账本</ion-label>
            </ion-tab-button>

            <ion-tab-button tab="/tabs/my" href="/tabs/my">
              <ion-icon :icon="personCircle"></ion-icon>
              <ion-label>我的</ion-label>
            </ion-tab-button>
          </ion-tab-bar>
        </ion-tabs>
  </ion-page>
</template>

<script>
import { defineComponent } from "vue";
import {
  IonIcon,
  IonLabel,
  IonPage,
  IonTabBar,
  IonTabButton,
  IonTabs,
  IonToolbar,
} from "@ionic/vue";
import { wallet, book, personCircle } from "ionicons/icons";

export default defineComponent({
  components: { IonIcon, IonLabel, IonPage, IonTabBar, IonTabButton, IonTabs },
  setup() {
    const beforeTabChange = () => {
      // do something before tab change
    };
    const afterTabChange = () => {
      // do something after tab change
    };
    return {
      wallet,
      book,
      personCircle,
      beforeTabChange,
      afterTabChange,
      IonToolbar,
    };
  },
});
</script>