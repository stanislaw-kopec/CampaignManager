<template>
  <form @submit.prevent="submit">
    <div class="mb-3">
      <label class="form-label">Campaign Name *</label>
      <input type="text" class="form-control" v-model="form.campaignName" required>
    </div>

    <div class="mb-3">
      <label class="form-label">User *</label>
      <select 
        class="form-select" 
        v-model="form.userId" 
        required
        :disabled="isEditing"
      >
        <option value="">Select a user</option>
        <option v-for="user in usersStore.users" :key="user.id" :value="user.id">
          {{ user.username }} (ID: {{ user.id }})
        </option>
      </select>
      <small v-if="isEditing" class="text-muted">
        Cannot change the campaign owner during editing
      </small>
    </div>

    <div class="mb-3">
      <label class="form-label">Keywords *</label>
      <TagInput 
        v-model="selectedKeywords" 
        :allKeywords="keywordsStore.keywords"
      />
    </div>

    <div class="mb-3">
      <label class="form-label">Town *</label>
      <select class="form-select" v-model="form.townId" required>
        <option value="">Select a town</option>
        <option v-for="town in townsStore.towns" :key="town.id" :value="town.id">
          {{ town.name }}
        </option>
      </select>
    </div>

    <div class="mb-3">
      <label class="form-label">Bid Amount (PLN) *</label>
      <input type="number" step="0.01" min="0.01" class="form-control" v-model="form.bidAmount" required>
    </div>

    <div class="mb-3">
      <label class="form-label">Campaign Budget (PLN) *</label>
      <input type="number" step="0.01" min="0.01" class="form-control" v-model="form.campaignFund" required>
      <small v-if="isEditing" class="text-muted">
        Changing the budget will withdraw or refund the difference from the Emerald account
      </small>
    </div>

    <div class="mb-3">
      <label class="form-label">Radius (km) *</label>
      <input type="number" min="1" class="form-control" v-model="form.radius" required>
    </div>

    <div class="mb-3">
      <label class="form-label">Status</label>
      <select class="form-select" v-model="form.status">
        <option value="ON">ON - Active</option>
        <option value="OFF">OFF - Inactive</option>
      </select>
    </div>

    <div class="modal-footer px-0 pb-0">
      <button type="button" class="btn btn-secondary" @click="$emit('cancel')">Cancel</button>
      <button type="submit" class="btn btn-primary">
        {{ isEditing ? 'Update' : 'Save' }}
      </button>
    </div>
  </form>
</template>

<script setup lang="ts">
import { reactive, onMounted, ref, watch, computed } from 'vue'
import { useUsersStore } from '@/stores/users'
import { useKeywordsStore } from '@/stores/keywords'
import { useTownsStore } from '@/stores/towns'
import type { CampaignRequest, Campaign } from '@/types'
import TagInput from './TagInput.vue'

const props = defineProps<{
  campaign?: Campaign
}>()

const emit = defineEmits<{
  (e: 'save', data: CampaignRequest): void
  (e: 'cancel'): void
}>()

const usersStore = useUsersStore()
const keywordsStore = useKeywordsStore()
const townsStore = useTownsStore()

const selectedKeywords = ref<{ id: number; name: string }[]>([])

const isEditing = computed(() => !!props.campaign)

const form = reactive<CampaignRequest>({
  campaignName: '',
  userId: 0,
  keywordIds: [],
  townId: 0,
  bidAmount: 0,
  campaignFund: 0,
  status: 'ON',
  radius: 0
})

const populateFormForEditing = () => {
  if (!props.campaign) return

  form.campaignName = props.campaign.campaignName
  form.bidAmount = props.campaign.bidAmount
  form.campaignFund = props.campaign.campaignFund
  form.status = props.campaign.status
  form.radius = props.campaign.radius

  const user = usersStore.users.find(u => u.username === props.campaign?.username)
  if (user) {
    form.userId = user.id
  }

  const town = townsStore.towns.find(t => t.name === props.campaign?.town)
  if (town) {
    form.townId = town.id
  }

  if (props.campaign.keywords && props.campaign.keywords.length > 0) {
    const keywordObjects = keywordsStore.keywords.filter(k => 
      props.campaign?.keywords.includes(k.name)
    )
    selectedKeywords.value = keywordObjects
    form.keywordIds = keywordObjects.map(k => k.id)
  }
}

watch(selectedKeywords, (newKeywords) => {
  form.keywordIds = newKeywords.map(k => k.id)
}, { deep: true })

onMounted(async () => {
  await Promise.all([
    usersStore.fetchUsers(),
    keywordsStore.fetchKeywords(),
    townsStore.fetchTowns()
  ])

  if (props.campaign) {
    populateFormForEditing()
  }
})

const submit = () => {
  if (form.userId && form.townId && form.keywordIds.length) {
    emit('save', { ...form })
  } else {
    alert('Please fill in all required fields')
  }
}
</script>

<style scoped>
select:disabled {
  background-color: #e9ecef;
  cursor: not-allowed;
  opacity: 0.7;
}

.text-muted {
  font-size: 0.85rem;
  display: block;
  margin-top: 0.25rem;
}
</style>