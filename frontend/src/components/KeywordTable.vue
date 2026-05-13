<template>
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5 class="mb-0">Keywords</h5>
      <button class="btn btn-primary btn-sm" @click="$emit('show-form')">+ Add Keyword</button>
    </div>
    <div class="card-body">
      <div v-if="store.loading" class="text-center">Loading...</div>
      <div v-else class="table-responsive">
        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="keyword in store.keywords" :key="keyword.id">
              <td>{{ keyword.id }}</td>
              <td>{{ keyword.name }}</td>
              <td>
                <button class="btn btn-sm btn-warning me-1" @click="$emit('edit', keyword)">Edit</button>
                <button class="btn btn-sm btn-danger" @click="$emit('delete', keyword.id)">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useKeywordsStore } from '@/stores/keywords'
import type { Keyword } from '@/types'

const store = useKeywordsStore()

defineEmits<{
  (e: 'show-form'): void
  (e: 'edit', keyword: Keyword): void
  (e: 'delete', id: number): void
}>()
</script>