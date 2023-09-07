package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.keys.KikobaIDUserID
import com.example.vicoba.data.network.VicobaApiService

/** A repository that maintain data concerning members of kikoba from the internet vicoba database */
interface KikobaMemberRepository {
    suspend fun approveKikobaJoinRequest(kikobaIDUserID: KikobaIDUserID):Boolean
    suspend fun cancelKikobaJoinRequest(kikobaIDUserID: KikobaIDUserID):Boolean
    suspend fun inviteUserToJoinKikoba(kikobaIDUserID: KikobaIDUserID):Boolean
    suspend fun removeKikobaMember(kikobaIDUserID: KikobaIDUserID):Boolean
}

class DefaultKikobaMemberRepository(
    private val vikobaApiService: VicobaApiService
): KikobaMemberRepository {
    override suspend fun approveKikobaJoinRequest(kikobaIDUserID: KikobaIDUserID): Boolean {
        return vikobaApiService.approveKikobaJoinRequest(kikobaIDUserID)
    }

    override suspend fun cancelKikobaJoinRequest(kikobaIDUserID: KikobaIDUserID): Boolean {
        return vikobaApiService.cancelKikobaJoinRequest(kikobaIDUserID)
    }

    override suspend fun inviteUserToJoinKikoba(kikobaIDUserID: KikobaIDUserID): Boolean {
        return vikobaApiService.inviteUserToJoinKikoba(kikobaIDUserID)
    }

    override suspend fun removeKikobaMember(kikobaIDUserID: KikobaIDUserID): Boolean {
        return vikobaApiService.removeKikobaMember(kikobaIDUserID)
    }
}