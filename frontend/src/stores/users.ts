import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/api'
import type { User, TopUpResponse } from '@/types'

export const useUsersStore = defineStore('users', () => {
  const users = ref<User[]>([])
  const loading = ref(false)

  const fetchUsers = async () => {
    loading.value = true
    try {
      const response = await api.get<User[]>('/users')
      users.value = response.data
    } finally {
      loading.value = false
    }
  }

  const createUser = async (username: string) => {
    try {
      const response = await api.post<User>('/users', { username })
      users.value.push(response.data)
      return { success: true, data: response.data }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || 'User creation error'
      return { success: false, error: errorMessage }
    }
  }

  const updateUser = async (id: number, username: string) => {
    try {
      const response = await api.put<User>(`/users/${id}`, { username })
      const index = users.value.findIndex(u => u.id === id)
      if (index !== -1) users.value[index] = response.data
      return { success: true }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || 'Update error'
      return { success: false, error: errorMessage }
    }
  }

  const deleteUser = async (id: number) => {
    try {
      await api.delete(`/users/${id}`)
      users.value = users.value.filter(u => u.id !== id)
      return { success: true }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || 'Deletion error'
      return { success: false, error: errorMessage }
    }
  }

  const createEmeraldAccount = async (id: number) => {
    try {
      const response = await api.post(`/users/${id}/emerald-account`)
      await fetchUsers()
      return { success: true, data: response.data }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || 'Account creation error'
      return { success: false, error: errorMessage }
    }
  }

  const topUpBalance = async (id: number, amount: number) => {
    try {
      const response = await api.post<TopUpResponse>(`/users/${id}/top-up`, { amount })
      await fetchUsers()
      return { success: true, data: response.data }
    } catch (err: any) {
      const errorMessage = err.response?.data?.error || err.response?.data?.message || 'Top-up error'
      return { success: false, error: errorMessage }
    }
  }

  return { users, loading, fetchUsers, createUser, updateUser, deleteUser, createEmeraldAccount, topUpBalance }
})