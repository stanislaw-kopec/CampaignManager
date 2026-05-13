<template>
  <div class="container mt-4">
    <TownTable 
      @show-form="showAddModal" 
      @edit="showEditModal" 
      @delete="deleteTown" 
    />
    
    <div class="modal fade show" tabindex="-1" style="display: block;" v-if="modalVisible">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ editingTown ? 'Edit Town' : 'Add Town' }}</h5>
            <button type="button" class="btn-close" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <TownForm 
              :town="editingTown" 
              @save="saveTown" 
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
import TownTable from '@/components/TownTable.vue'
import TownForm from '@/components/TownForm.vue'
import { useTownsStore } from '@/stores/towns'

const townsStore = useTownsStore()
const modalVisible = ref(false)
const editingTown = ref<any>(null)

const showAddModal = () => {
  editingTown.value = null
  modalVisible.value = true
}

const showEditModal = (town: any) => {
  editingTown.value = town
  modalVisible.value = true
}

const closeModal = () => {
  modalVisible.value = false
  editingTown.value = null
}

const saveTown = async (name: string) => {
  let result
  if (editingTown.value) {
    result = await townsStore.updateTown(editingTown.value.id, name)
  } else {
    result = await townsStore.createTown(name)
  }
  
  if (result.success) {
    closeModal()
    await townsStore.fetchTowns()
  } else {
    alert(result.error)
  }
}

const deleteTown = async (id: number) => {
  if (confirm('Are you sure you want to delete this town?')) {
    const result = await townsStore.deleteTown(id)
    if (!result.success) alert(result.error)
  }
}

onMounted(() => {
  townsStore.fetchTowns()
})
</script>