<template>
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5 class="mb-0">Users</h5>
      <button class="btn btn-primary btn-sm" @click="$emit('show-form')">+ Add User</button>
    </div>
    <div class="card-body">
      <div v-if="store.loading" class="text-center">Loading...</div>
      <div v-else class="table-responsive">
        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Emerald Account</th>
              <th>Balance</th>
              <th>Campaign Count</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in store.users" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.username }}</td>
              <td>
                <span :class="'badge ' + (user.hasEmeraldAccount ? 'bg-success' : 'bg-warning')">
                  {{ user.hasEmeraldAccount ? 'Active' : 'None' }}
                </span>
              </td>
              <td>{{ user.emeraldBalance || 0 }} PLN</td>
              <td>{{ user.campaignCount || 0 }}</td>
              <td>
                <button class="btn btn-sm btn-warning me-1" @click="$emit('edit', user)">Edit</button>
                <button 
                  v-if="user.hasEmeraldAccount" 
                  class="btn btn-sm btn-success me-1" 
                  @click="$emit('topup', user)"
                >
                  Top Up
                </button>
                <button 
                  v-if="!user.hasEmeraldAccount" 
                  class="btn btn-sm btn-info me-1" 
                  @click="$emit('create-account', user.id)"
                >
                  Create Account
                </button>
                <button class="btn btn-sm btn-danger" @click="$emit('delete', user.id)">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUsersStore } from '@/stores/users'
import type { User } from '@/types'

const store = useUsersStore()

defineEmits<{
  (e: 'show-form'): void
  (e: 'edit', user: User): void
  (e: 'delete', id: number): void
  (e: 'topup', user: User): void
  (e: 'create-account', id: number): void
}>()
</script>