package com.example.masterand

import kotlinx.coroutines.flow.Flow

class OfflineProfileRepository(private val profileDao: ProfileDao) :
    ProfileRepository {
    override fun getAllProfileStream(): Flow<List<Profile>> =
        profileDao.getAllProfiles()

    override suspend fun insertProfile(profile: Profile) {
        profileDao.insert(profile)
    }
    /*
   Pozostałe metody
   */
}
