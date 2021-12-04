<template>
  <ion-content class="bill">
    <ion-list-header v-if="clean==false">
      <ion-Label>修改账单内容</ion-Label>
    </ion-list-header>

    <ion-item class="bill" v-if="clean==false">
      <ion-input placeholder="请输入账单内容" v-model="describe"></ion-input>
    </ion-item>

    <ion-radio-group v-model="assetId" value="0">
      <ion-list-header>
        <ion-label>修改资产类型</ion-label>
      </ion-list-header>

      <ion-item class="bill" v-for="asset in assets" :key="asset.id">
        <ion-label>{{ asset.name }}</ion-label>
        <ion-radio slot="start" v-bind:value="asset.id"></ion-radio>
      </ion-item>
    </ion-radio-group>

    <ion-list-header v-if="clean==false">
      <ion-Label>修改账单金额</ion-Label>
    </ion-list-header>

    <ion-item class="bill" v-if="clean==false">
      <ion-input placeholder="请输入账单金额" v-model="money"></ion-input>
    </ion-item>

    <ion-row>
      <ion-col align="center">
        <ion-button class="control" @click="modifyNocleanBillContent()" v-if="clean==false">
          <ion-label class="addbill" color="light">确定</ion-label>
        </ion-button>
        <ion-button class="control" @click="modifyCleanBillContent()" v-if="clean==true">
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
  popoverController,
} from "@ionic/vue";
import { defineComponent } from "vue";
import { Plugins } from "@capacitor/core";
import "@capacitor-community/http";

export default defineComponent({
  name: "ModifyBillContent",
  props: {
    recordId: {type: Number},
    clean: {type: Boolean},
  },
  data() {
    return {
      describe: "",
      assets:[],
      assetId:0,
      money: "",
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
  },

  methods: {
    async close() {
      await popoverController.dismiss({recordId:this.recordId, msg:"nochange"});
    },
    async modifyNocleanBillContent() {
      const { Http } = Plugins;
      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/bill/recordNocleanModify",
        data: {
          recordId: this.recordId,
          assetsId: this.assetId,
          count: this.money,
          describe: this.describe,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
      const data = await ret.data;
      console.log(data);
      if (data.code == 200) {
        await popoverController.dismiss({recordId:this.recordId, msg:"changed"});
        alert("修改成功");
      } else {
        await popoverController.dismiss({recordId:this.recordId, msg:"nochange"});
        alert("修改失败");
      }
    },
    async modifyCleanBillContent() {
      const { Http } = Plugins;
      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/bill/recordCleanModify",
        data: {
          recordId: this.recordId,
          assetsId: this.assetId,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
      const data = await ret.data;
      console.log(data);
      if (data.code == 200) {
        await popoverController.dismiss({recordId:this.recordId, msg:"changed"});
        alert("修改成功");
      } else {
        await popoverController.dismiss({recordId:this.recordId, msg:"nochange"});
        alert("修改失败");
      }
    },
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