<template>
  <div class="container mt-4">
    <KeywordTable 
      @show-form="showAddModal" 
      @edit="showEditModal" 
      @delete="deleteKeyword" 
    />
    
    <div class="modal fade show" tabindex="-1" style="display: block;" v-if="modalVisible">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ editingKeyword ? 'Edit Keyword' : 'Add Keyword' }}</h5>
            <button type="button" class="btn-close" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <KeywordForm 
              :keyword="editingKeyword" 
              @save="saveKeyword" 
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
import KeywordTable from '@/components/KeywordTable.vue'
import KeywordForm from '@/components/KeywordForm.vue'
import { useKeywordsStore } from '@/stores/keywords'

const keywordsStore = useKeywordsStore()
const modalVisible = ref(false)
const editingKeyword = ref<any>(null)

const showAddModal = () => {
  editingKeyword.value = null
  modalVisible.value = true
}

const showEditModal = (keyword: any) => {
  editingKeyword.value = keyword
  modalVisible.value = true
}

const closeModal = () => {
  modalVisible.value = false
  editingKeyword.value = null
}

const saveKeyword = async (name: string) => {
  let result
  if (editingKeyword.value) {
    result = await keywordsStore.updateKeyword(editingKeyword.value.id, name)
  } else {
    result = await keywordsStore.createKeyword(name)
  }
  
  if (result.success) {
    closeModal()
    await keywordsStore.fetchKeywords()
  } else {
    alert(result.error)
  }
}

const deleteKeyword = async (id: number) => {
  if (confirm('Are you sure you want to delete this keyword?')) {
    const result = await keywordsStore.deleteKeyword(id)
    if (!result.success) alert(result.error)
  }
}

onMounted(() => {
  keywordsStore.fetchKeywords()
})
</script>