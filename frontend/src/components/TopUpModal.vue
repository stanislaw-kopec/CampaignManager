<template>
  <div class="modal fade show" tabindex="-1" style="display: block;" v-if="show">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Top Up Balance - {{ user?.username }}</h5>
          <button type="button" class="btn-close" @click="$emit('close')"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label class="form-label">Top Up Amount</label>
            <input type="number" step="0.01" min="0.01" class="form-control" v-model="amount">
          </div>
          <div class="alert alert-info">
            Current Balance: {{ user?.emeraldBalance || 0 }} 
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="$emit('close')">Cancel</button>
          <button type="button" class="btn btn-success" @click="$emit('confirm', amount)">Top Up</button>
        </div>
      </div>
    </div>
  </div>
  <div class="modal-backdrop fade show" v-if="show"></div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { User } from '@/types'

defineProps<{
  show: boolean
  user?: User
}>()

const emit = defineEmits<{
  (e: 'close'): void
  (e: 'confirm', amount: number): void
}>()

const amount = ref(0)
</script>