package com.example.masterand

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getAllProfileStream(): Flow<List<Profile>>
    suspend fun insertProfile(profile: Profile)
}