<template>
  <div class="card">
    <div class="card-header d-flex justify-content-between align-items-center">
      <h5 class="mb-0">Advertising Campaigns</h5>
      <button class="btn btn-primary btn-sm" @click="$emit('show-form')">+ Add Campaign</button>
    </div>
    <div class="card-body">
      <div v-if="store.loading" class="text-center">Loading...</div>
      <div v-else-if="store.error" class="alert alert-danger">{{ store.error }}</div>
      <div v-else class="table-responsive">
        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Keywords</th>
              <th>Bid</th>
              <th>Budget</th>
              <th>Status</th>
              <th>Town</th>
              <th>Radius</th>
              <th>User</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="campaign in store.campaigns" :key="campaign.id">
              <td>{{ campaign.id }}</td>
              <td>{{ campaign.campaignName }}</td>
              <td>{{ campaign.keywords?.join(', ') || '-' }}</td>
              <td>{{ campaign.bidAmount }} PLN</td>
              <td>{{ campaign.campaignFund }} PLN</td>
              <td>
                <span :class="'badge ' + (campaign.status === 'ON' ? 'bg-success' : 'bg-secondary')">
                  {{ campaign.status }}
                </span>
              </td>
              <td>{{ campaign.town }}</td>
              <td>{{ campaign.radius }} km</td>
              <td>{{ campaign.username }}</td>
              <td>
                <button class="btn btn-sm btn-warning me-1" @click="$emit('edit', campaign)">Edit</button>
                <button class="btn btn-sm btn-danger" @click="$emit('delete', campaign.id)">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useCampaignsStore } from '@/stores/campaigns'
import type { Campaign } from '@/types'

const store = useCampaignsStore()

defineEmits<{
  (e: 'show-form'): void
  (e: 'edit', campaign: Campaign): void
  (e: 'delete', id: number): void
}>()
</script>