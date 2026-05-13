<template>
  <div class="tag-input" ref="target">
    <div class="selected-tags mb-2">
      <span 
        v-for="tag in modelValue" 
        :key="tag.id" 
        class="badge bg-primary me-1 mb-1 p-2"
        style="font-size: 0.9rem;"
      >
        {{ tag.name }}
        <button 
          type="button" 
          class="btn-close btn-close-white ms-2" 
          style="font-size: 0.6rem;"
          @click="removeTag(tag.id)"
        ></button>
      </span>
    </div>
    
    <div class="autocomplete position-relative">
      <input 
        type="text"
        class="form-control"
        v-model="searchQuery"
        @input="onSearch"
        @keydown.enter.prevent="selectFirstOption"
        @keydown.down.prevent="nextOption"
        @keydown.up.prevent="prevOption"
        placeholder="Enter keyword name..."
      />
      
      <div v-if="showDropdown && filteredOptions.length > 0" class="dropdown-menu show position-absolute w-100 mt-1" style="max-height: 200px; overflow-y: auto;">
        <button
          v-for="(option, index) in filteredOptions"
          :key="option.id"
          type="button"
          class="dropdown-item"
          :class="{ active: selectedIndex === index }"
          @click="selectOption(option)"
          @mouseenter="selectedIndex = index"
        >
          {{ option.name }}
        </button>
      </div>
    </div>
    
    <small class="text-muted" v-if="modelValue.length === 0">Select keywords from the list or type to search</small>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { onClickOutside } from '@vueuse/core'
import type { Keyword } from '@/types'

const props = defineProps<{
  modelValue: Keyword[]
  allKeywords: Keyword[]
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: Keyword[]): void
}>()

const target = ref(null)
const searchQuery = ref('')
const selectedIndex = ref(-1)
const showDropdown = ref(false)

onClickOutside(target, () => {
  showDropdown.value = false
})

const filteredOptions = computed(() => {
  if (!searchQuery.value.trim()) {
    return props.allKeywords.filter(k => !props.modelValue.some(selected => selected.id === k.id))
  }
  
  const query = searchQuery.value.toLowerCase()
  return props.allKeywords.filter(k => 
    k.name.toLowerCase().includes(query) && 
    !props.modelValue.some(selected => selected.id === k.id)
  )
})

const onSearch = () => {
  showDropdown.value = true
  selectedIndex.value = -1
}

const selectOption = (option: Keyword) => {
  emit('update:modelValue', [...props.modelValue, option])
  searchQuery.value = ''
  showDropdown.value = false
  selectedIndex.value = -1
}

const selectFirstOption = () => {
  if (filteredOptions.value.length > 0) {
    selectOption(filteredOptions.value[0])
  }
}

const nextOption = () => {
  if (selectedIndex.value < filteredOptions.value.length - 1) {
    selectedIndex.value++
  }
}

const prevOption = () => {
  if (selectedIndex.value > 0) {
    selectedIndex.value--
  }
}

const removeTag = (id: number) => {
  emit('update:modelValue', props.modelValue.filter(tag => tag.id !== id))
}
</script>

<style scoped>
.tag-input {
  position: relative;
}

.dropdown-menu {
  z-index: 1000;
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.dropdown-item {
  cursor: pointer;
  padding: 8px 12px;
}

.dropdown-item:hover,
.dropdown-item.active {
  background-color: #0d6efd;
  color: white;
}

.badge {
  display: inline-flex;
  align-items: center;
  gap: 5px;
}

.btn-close {
  width: 0.5rem;
  height: 0.5rem;
  opacity: 0.8;
}

.btn-close:hover {
  opacity: 1;
}
</style>