import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/api'
import type { Town } from '@/types'

export const useTownsStore = defineStore('towns', () => {
  const towns = ref<Town[]>([])
  const loading = ref(false)

  const fetchTowns = async () => {
    loading.value = true
    try {
      const response = await api.get<Town[]>('/towns')
      towns.value = response.data
    } finally {
      loading.value = false
    }
  }

  const createTown = async (name: string) => {
    try {
      const response = await api.post<Town>('/towns', { name })
      towns.value.push(response.data)
      return { success: true, data: response.data }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || 'Creation error'
      return { success: false, error: errorMessage }
    }
  }

  const updateTown = async (id: number, name: string) => {
    try {
      const response = await api.put<Town>(`/towns/${id}`, { name })
      const index = towns.value.findIndex(t => t.id === id)
      if (index !== -1) towns.value[index] = response.data
      return { success: true }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || 'Update error'
      return { success: false, error: errorMessage }
    }
  }

  const deleteTown = async (id: number) => {
    try {
      await api.delete(`/towns/${id}`)
      towns.value = towns.value.filter(t => t.id !== id)
      return { success: true }
    } catch (err: any) {
      const errorData = err.response?.data
      let errorMessage = errorData?.error || errorData?.message || 'Deletion error'
      
      if (errorData?.resourceType === 'town' && errorData?.usageCount) {
        errorMessage = `Cannot delete the town because it is used by ${errorData.usageCount} campaign(s).`
      }
      
      return { success: false, error: errorMessage }
    }
  }

  return { towns, loading, fetchTowns, createTown, updateTown, deleteTown }
})