<template>
  <div class="container mt-4">
    <CampaignTable 
      @show-form="showAddModal" 
      @edit="showEditModal" 
      @delete="deleteCampaign" 
    />
    
    <div class="modal fade show" tabindex="-1" style="display: block;" v-if="modalVisible">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ editingCampaign ? 'Edit Campaign' : 'Add Campaign' }}</h5>
            <button type="button" class="btn-close" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <CampaignForm 
              :campaign="editingCampaign"
              @save="saveCampaign" 
              @cancel="closeModal" 
            />
          </div>
        </div>
      </div>
    </div>
    <div class="modal-backdrop fade show" v-if="modalVisible"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import CampaignTable from '@/components/CampaignTable.vue'
import CampaignForm from '@/components/CampaignForm.vue'
import { useCampaignsStore } from '@/stores/campaigns'
import type { CampaignRequest, Campaign } from '@/types'

const campaignsStore = useCampaignsStore()
const modalVisible = ref(false)
const editingCampaign = ref<Campaign | null>(null)

const showAddModal = () => {
  editingCampaign.value = null
  modalVisible.value = true
}

const showEditModal = (campaign: Campaign) => {
  editingCampaign.value = campaign
  modalVisible.value = true
}

const closeModal = () => {
  modalVisible.value = false
  editingCampaign.value = null
}

const saveCampaign = async (campaignData: CampaignRequest) => {
  let result
  if (editingCampaign.value) {
    result = await campaignsStore.updateCampaign(editingCampaign.value.id, campaignData)
  } else {
    result = await campaignsStore.createCampaign(campaignData)
  }
  
  if (result.success) {
    closeModal()
    await campaignsStore.fetchCampaigns()
  } else {
    alert(result.error)
  }
}

const deleteCampaign = async (id: number) => {
  if (confirm('Are you sure you want to delete this campaign?')) {
    const result = await campaignsStore.deleteCampaign(id)
    if (!result.success) alert(result.error)
  }
}

onMounted(() => {
  campaignsStore.fetchCampaigns()
})
</script>