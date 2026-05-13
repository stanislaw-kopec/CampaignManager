<template>
  <div class="container mt-4">
    <UserTable 
      @show-form="showAddModal" 
      @edit="showEditModal" 
      @delete="deleteUser"
      @topup="showTopUp"
      @create-account="createAccount"
    />
    
    <div class="modal fade show" tabindex="-1" style="display: block;" v-if="userModalVisible">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ editingUser ? 'Edit User' : 'Add User' }}</h5>
            <button type="button" class="btn-close" @click="closeUserModal"></button>
          </div>
          <div class="modal-body">
            <UserForm 
              :user="editingUser" 
              @save="saveUser" 
              @cancel="closeUserModal" 
            />
          </div>
        </div>
      </div>
    </div>
    <div class="modal-backdrop fade show" v-if="userModalVisible"></div>

    <TopUpModal 
      :show="topUpModalVisible" 
      :user="selectedUser"
      @close="topUpModalVisible = false"
      @confirm="confirmTopUp"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import UserTable from '@/components/UserTable.vue'
import UserForm from '@/components/UserForm.vue'
import TopUpModal from '@/components/TopUpModal.vue'
import { useUsersStore } from '@/stores/users'

const usersStore = useUsersStore()
const userModalVisible = ref(false)
const editingUser = ref<any>(null)
const topUpModalVisible = ref(false)
const selectedUser = ref<any>(null)

const showAddModal = () => {
  editingUser.value = null
  userModalVisible.value = true
}

const showEditModal = (user: any) => {
  editingUser.value = user
  userModalVisible.value = true
}

const closeUserModal = () => {
  userModalVisible.value = false
  editingUser.value = null
}

const saveUser = async (username: string) => {
  let result
  if (editingUser.value) {
    result = await usersStore.updateUser(editingUser.value.id, username)
  } else {
    result = await usersStore.createUser(username)
  }
  
  if (result.success) {
    closeUserModal()
    await usersStore.fetchUsers()
  } else {
    alert(result.error)
  }
}

const deleteUser = async (id: number) => {
  if (confirm('Are you sure you want to delete this user?')) {
    const result = await usersStore.deleteUser(id)
    if (!result.success) alert(result.error)
  }
}

const showTopUp = (user: any) => {
  selectedUser.value = user
  topUpModalVisible.value = true
}

const confirmTopUp = async (amount: number) => {
  if (amount > 0) {
    const result = await usersStore.topUpBalance(selectedUser.value.id, amount)
    if (result.success) {
      topUpModalVisible.value = false
      await usersStore.fetchUsers()
    } else {
      alert(result.error)
    }
  }
}

const createAccount = async (userId: number) => {
  if (confirm('Are you sure you want to create an Emerald account for this user?')) {
    const result = await usersStore.createEmeraldAccount(userId)
    if (result.success) {
      alert('Account has been successfully created!')
      await usersStore.fetchUsers()
    } else {
      alert(result.error)
    }
  }
}

onMounted(() => {
  usersStore.fetchUsers()
})
</script>