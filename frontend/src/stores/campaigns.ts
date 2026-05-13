import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/api'
import type { Campaign, CampaignRequest } from '@/types'

export const useCampaignsStore = defineStore('campaigns', () => {
  const campaigns = ref<Campaign[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)

  const fetchCampaigns = async () => {
    loading.value = true
    try {
      const response = await api.get<Campaign[]>('/campaigns')
      campaigns.value = response.data
      error.value = null
    } catch (err) {
      error.value = 'Failed to fetch campaigns'
      console.error(err)
    } finally {
      loading.value = false
    }
  }

  const createCampaign = async (campaignData: CampaignRequest) => {
    try {
      const response = await api.post<Campaign>('/campaigns', campaignData)
      campaigns.value.push(response.data)
      return { success: true, data: response.data }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || err.message
      return { success: false, error: errorMessage }
    }
  }

  const updateCampaign = async (id: number, campaignData: CampaignRequest) => {
    try {
      const response = await api.put<Campaign>(`/campaigns/${id}`, campaignData)
      const index = campaigns.value.findIndex(c => c.id === id)
      if (index !== -1) campaigns.value[index] = response.data
      return { success: true, data: response.data }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || err.message
      return { success: false, error: errorMessage }
    }
  }

  const deleteCampaign = async (id: number) => {
    try {
      await api.delete(`/campaigns/${id}`)
      campaigns.value = campaigns.value.filter(c => c.id !== id)
      return { success: true }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || err.message
      return { success: false, error: errorMessage }
    }
  }

  return { campaigns, loading, error, fetchCampaigns, createCampaign, updateCampaign, deleteCampaign }
})