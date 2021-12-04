<template>
  <ion-page>
    <ion-content>
      <p>{{ this.id }}</p>
    </ion-content>
  </ion-page>
</template>
<script lang="ts">
import { defineComponent } from "vue";
import { IonPage, IonContent } from "@ionic/vue";
export default defineComponent({
  name: "FlushAssetd",
  data() {
    return {
      id: 0,
    };
  },

  components: {
    IonPage,
    IonContent,
  },
  created: async function () {
    this.id = this.$route.params.id;
    console.log("---||||||||||||||||---");
    /*this.$router.push({
      name: "AssetDetail",
      params: {
        id: this.id,
      },
    });*/
  },
});
</script>