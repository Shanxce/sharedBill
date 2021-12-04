<template>
  <ion-content class="bill">
    <ion-list-header>
      <ion-Label>参与人员</ion-Label>
    </ion-list-header>
    <ion-list>
      <ion-item v-for="user in users" :key="user.uid">
        <ion-label class="light circle"> {{ user.name }}</ion-label>
      </ion-item>
    </ion-list>

    <ion-row align="center">
      <ion-button class="control" @click="close()">
        <ion-label class="bill" color="light">返回账单</ion-label>
      </ion-button>
    </ion-row>
  </ion-content>
</template>

<script>
import { IonContent, IonItem, IonLabel, modalController } from "@ionic/vue";
import { defineComponent } from "vue";
import { Plugins } from "@capacitor/core";
import "@capacitor-community/http";

export default defineComponent({
  name: "BillContentJoiner",
  props: {
    recordId: { type: Number },
  },
  data() {
    return {
      users: [],
    };
  },
  components: {
    IonContent,
    IonItem,
    IonLabel,
  },

  methods: {
    async close() {
      await modalController.dismiss();
    },
  },
  created: async function () {
    const { Http } = Plugins;

    const ret1 = await Http.request({
      method: "GET",
      url: `http://124.71.224.228:8080/api/bill/getJoiners?recordId=${this.recordId}`,
    });
    const data = await ret1.data.data;
    console.log(JSON.stringify(data));
    this.users = data.Users;
  },
});
</script>

<style scoped>
ion-item.bill {
  --background: white;
}

ion-button.control {
  font-size: 15px;
  --background: rgb(245, 150, 100);
}
ion-content.bill {
  --background: white;
}

ion-toolbar.blank {
  max-height: 25px;
  --background: white;
}

ion-input.input {
  font-size: 20px;
}

.light {
  background-color: rgba(238, 238, 238, 0.65);
  color: black;
}

ion-label.circle {
  padding-top: 5%;
  padding-bottom: 5%;
  border-radius: 20px 20px 20px 20px;
  vertical-align: center;
}
</style>