<template>
  <form @submit.prevent="submit">
    <div class="mb-3">
      <label class="form-label">Username *</label>
      <input 
        type="text" 
        class="form-control" 
        v-model="username" 
        required
        minlength="3"
        maxlength="50"
      >
      <small class="text-muted">Min. 3 characters, max 50 characters</small>
    </div>
    <div class="modal-footer px-0 pb-0">
      <button type="button" class="btn btn-secondary" @click="$emit('cancel')">Cancel</button>
      <button type="submit" class="btn btn-primary">Save</button>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { User } from '@/types'

const props = defineProps<{
  user?: User
}>()

const emit = defineEmits<{
  (e: 'save', username: string): void
  (e: 'cancel'): void
}>()

const username = ref(props.user?.username || '')

const submit = () => {
  if (username.value.trim()) {
    emit('save', username.value.trim())
  }
}
</script>