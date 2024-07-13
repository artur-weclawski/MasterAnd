package com.example.masterand

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository):
    ViewModel() {
    /*
   ...
   */
    lateinit var profile: Profile
    fun insertProfile(profile: Profile){
        viewModelScope.launch {
            profileRepository.insertProfile(profile = profile)
        }
    }
    /*
    ...
    */
}

//val viewModel: ProfileViewModel =
//    ViewModelProvider(this,ProfileViewModelFactory(profileRepository))
//        .get(ProfileViewModel::class.java)
