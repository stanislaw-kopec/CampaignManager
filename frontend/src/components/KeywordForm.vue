<template>
  <form @submit.prevent="submit">
    <div class="mb-3">
      <label class="form-label">Keyword Name *</label>
      <input type="text" class="form-control" v-model="name" required>
    </div>
    <div class="modal-footer px-0 pb-0">
      <button type="button" class="btn btn-secondary" @click="$emit('cancel')">Cancel</button>
      <button type="submit" class="btn btn-primary">Save</button>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const props = defineProps<{
  keyword?: Keyword
}>()

const emit = defineEmits<{
  (e: 'save', name: string): void
  (e: 'cancel'): void
}>()

const name = ref(props.keyword?.name || '')

const submit = () => {
  if (name.value.trim()) {
    emit('save', name.value.trim())
  }
}
</script>