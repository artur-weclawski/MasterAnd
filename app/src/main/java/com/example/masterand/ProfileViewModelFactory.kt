package com.example.masterand

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileViewModelFactory(private val repository: ProfileRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Nieznana klasa ViewModel")
    }
}