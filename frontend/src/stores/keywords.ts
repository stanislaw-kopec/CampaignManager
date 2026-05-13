import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/api'
import type { Keyword } from '@/types'

export const useKeywordsStore = defineStore('keywords', () => {
  const keywords = ref<Keyword[]>([])
  const loading = ref(false)

  const fetchKeywords = async () => {
    loading.value = true
    try {
      const response = await api.get<Keyword[]>('/keywords')
      keywords.value = response.data
    } finally {
      loading.value = false
    }
  }

  const createKeyword = async (name: string) => {
    try {
      const response = await api.post<Keyword>('/keywords', { name })
      keywords.value.push(response.data)
      return { success: true, data: response.data }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || 'Creation error'
      return { success: false, error: errorMessage }
    }
  }

  const updateKeyword = async (id: number, name: string) => {
    try {
      const response = await api.put<Keyword>(`/keywords/${id}`, { name })
      const index = keywords.value.findIndex(k => k.id === id)
      if (index !== -1) keywords.value[index] = response.data
      return { success: true }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || 'Update error'
      return { success: false, error: errorMessage }
    }
  }

  const deleteKeyword = async (id: number) => {
    try {
      await api.delete(`/keywords/${id}`)
      keywords.value = keywords.value.filter(k => k.id !== id)
      return { success: true }
    } catch (err: any) {
      const errorData = err.response?.data
      let errorMessage = errorData?.error || errorData?.message || 'Deletion error'
      
      if (errorData?.resourceType === 'keyword' && errorData?.usageCount) {
        errorMessage = `Cannot delete the keyword because it is used by ${errorData.usageCount} campaign(s).`
      }
      
      return { success: false, error: errorMessage }
    }
  }

  return { keywords, loading, fetchKeywords, createKeyword, updateKeyword, deleteKeyword }
})