package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.entities.ActiveKikobaUser
import com.example.vicoba.data.models.entities.NewKikobaUser
import com.example.vicoba.data.models.entities.UserLoginCredentials
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.keys.KikobaIDUserID
import com.example.vicoba.data.models.lists.UserRequestedKikoba
import com.example.vicoba.data.network.VicobaApiService

/**
 * UserRepository maintains user data from the internet vicoba database server
 */
interface UserRepository {
    suspend fun loginUser(credentials: UserLoginCredentials): ActiveKikobaUser
    suspend fun registerUser(newKikobaUser: NewKikobaUser): ActiveKikobaUser
    suspend fun getUsersRequestedKikobaInfo(kikobaID: KikobaID): List<UserRequestedKikoba>
    suspend fun getUserRequestedKikobaInfo(kikobaIDUserID: KikobaIDUserID): UserRequestedKikoba
}

/**
 * DefaultUserRepository implements UserRepository and
 * providing it a retrofit services through dependency injection
 */
class DefaultUserRepository( private val vicobaApiService: VicobaApiService) : UserRepository {
    override suspend fun loginUser(credentials: UserLoginCredentials): ActiveKikobaUser {
        return vicobaApiService.loginUser(credentials)
    }

    override suspend fun registerUser(newKikobaUser: NewKikobaUser): ActiveKikobaUser {
        return vicobaApiService.registerUser(newKikobaUser)
    }

    override suspend fun getUsersRequestedKikobaInfo(kikobaID: KikobaID): List<UserRequestedKikoba> {
        return vicobaApiService.getUsersKikobaRequests(kikobaID)
    }

    override suspend fun getUserRequestedKikobaInfo(kikobaIDUserID: KikobaIDUserID): UserRequestedKikoba {
        return vicobaApiService.getUserRequestedKikobaInfo(kikobaIDUserID)
    }
}