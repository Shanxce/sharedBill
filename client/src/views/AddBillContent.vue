<template>
  <ion-content class="bill">
    <ion-list-header>
      <ion-Label>新建账单内容</ion-Label>
    </ion-list-header>

    <ion-item class="bill">
      <ion-input placeholder="请输入账单内容" v-model="describe"></ion-input>
    </ion-item>

    <ion-radio-group v-model="assetId" value="0">
      <ion-list-header>
        <ion-label>资产类型</ion-label>
      </ion-list-header>

      <ion-item class="bill" v-for="asset in assets" :key="asset.id">
        <ion-label>{{ asset.name }}</ion-label>
        <ion-radio slot="start" v-bind:value="asset.id"></ion-radio>
      </ion-item>
    </ion-radio-group>

    <ion-list-header>
      <ion-Label>账单金额</ion-Label>
    </ion-list-header>

    <ion-item class="bill">
      <ion-input placeholder="请输入账单金额" v-model="money"></ion-input>
    </ion-item>

    <ion-list-header>
      <ion-Label>账单参与人员</ion-Label>
    </ion-list-header>

    <ion-item class="bill">
      <ion-label>选择人员</ion-label>
      <ion-select
        multiple="true"
        ok-text="确定"
        cancel-text="取消"
        v-model="userId"
      >
        <ion-select-option
          v-for="user in users"
          :key="user.uid"
          v-bind:value="user.uid"
          
        >
          {{ user.name }}
        </ion-select-option>
      </ion-select>
    </ion-item>

    <ion-row>
      <ion-col align="center">
        <ion-button class="control" @click="addbill()">
          <ion-label class="addbill" color="light">确定</ion-label>
        </ion-button>
      </ion-col>
      <ion-col align="center">
        <ion-button class="control" @click="close()">
          <ion-label class="addbill" color="light">取消</ion-label>
        </ion-button>
      </ion-col>
    </ion-row>
  </ion-content>
</template>

<script>
import {
  IonContent,
  IonRadio,
  IonRadioGroup,
  IonButton,
  IonItem,
  IonInput,
  IonLabel,
  IonSelect,
  IonSelectOption,
  modalController,
} from "@ionic/vue";
import { defineComponent } from "vue";
import { Plugins } from "@capacitor/core";
import "@capacitor-community/http";

export default defineComponent({
  name: "AddBill",
  props: {
    billId: { type: Number },
    share: { type: Boolean },
  },
  data() {
    return {
      describe: "",
      assets: [],
      assetId: 0,
      money: "",
      users: [],
      userId: "",
    };
  },
  components: {
    IonContent,
    IonRadio,
    IonRadioGroup,
    IonButton,
    IonInput,
    IonItem,
    IonLabel,
    IonSelect,
    IonSelectOption,
  },

  methods: {
    async addbill() {
      const { Http } = Plugins;
      console.log(JSON.stringify(this.userId));
      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/bill/createRecord",
        data: {
          billId: this.billId,
          assetsId: this.assetId,
          count: this.money,
          describe: this.describe,
          photoAddr: "",
          joiner: this.userId.join(" "),
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
      const data = await ret.data;
      console.log(data);
      if (data.code == 200) {
        alert("添加成功");
        await modalController.dismiss({billId: this.billId, share: this.share, msg: "changed"});
      } else {
        alert("添加失败");
        await modalController.dismiss({billId: this.billId, share: this.share, msg: "nochange"});
      }
    },
    async close() {
      await modalController.dismiss({billId: this.billId, share: this.share, msg: "nochange"});
    },
  },
  created: async function () {
    const { Http } = Plugins;

    const ret1 = await Http.request({
      method: "GET",
      url: "http://124.71.224.228:8080/api/assets/getAssets",
    });
    const data1 = await ret1.data.data;
    this.assets = data1["assets"];

    const ret2 = await Http.request({
      method: "GET",
      url: `http://124.71.224.228:8080/api/bill/getUsers?id=${this.billId}`,
    });
    const data2 = await ret2.data.data;
    this.users = data2;
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
</style>