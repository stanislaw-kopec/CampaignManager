<template>
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5 class="mb-0">Towns</h5>
      <button class="btn btn-primary btn-sm" @click="$emit('show-form')">+ Add Town</button>
    </div>
    <div class="card-body">
      <div v-if="store.loading" class="text-center">Loading...</div>
      <div v-else class="table-responsive">
        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <th>ID</th>
              <th>Town Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="town in store.towns" :key="town.id">
              <td>{{ town.id }}</td>
              <td>{{ town.name }}</td>
              <td>
                <button class="btn btn-sm btn-warning me-1" @click="$emit('edit', town)">Edit</button>
                <button class="btn btn-sm btn-danger" @click="$emit('delete', town.id)">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useTownsStore } from '@/stores/towns'
import type { Town } from '@/types'

const store = useTownsStore()

defineEmits<{
  (e: 'show-form'): void
  (e: 'edit', town: Town): void
  (e: 'delete', id: number): void
}>()
</script>