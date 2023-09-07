package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.entities.ActiveKikoba
import com.example.vicoba.data.models.entities.EditedKikobaProfile
import com.example.vicoba.data.models.entities.InvitedKikoba
import com.example.vicoba.data.models.entities.KikobaRequestCredentials
import com.example.vicoba.data.models.entities.NewKikoba
import com.example.vicoba.data.models.items.InvitationCoupon
import com.example.vicoba.data.models.items.TotalKikobaMembers
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.keys.KikobaIDUserID
import com.example.vicoba.data.models.keys.KikobaLeaderID
import com.example.vicoba.data.models.keys.UserID
import com.example.vicoba.data.models.keys.UserIDAddressID
import com.example.vicoba.data.models.lists.KikobaMember
import com.example.vicoba.data.models.lists.VikobaNearUser
import com.example.vicoba.data.models.lists.VikobaUserInvolvedIn
import com.example.vicoba.data.network.VicobaApiService

interface KikobaRepository {
    suspend fun saveNewKikoba(kikoba: NewKikoba): ActiveKikoba
    suspend fun getKikobaInfo(kikobaID: KikobaID): ActiveKikoba
    suspend fun getKikobaMembers(kikobaID: KikobaID): List<KikobaMember>
    suspend fun getTotalKikobaMembers(kikobaID: KikobaID): TotalKikobaMembers
    suspend fun getInvitedKikobaInfo(kikobaID: KikobaID): InvitedKikoba
    suspend fun getVikobaUserInvolvedIn(userID: UserID): List<VikobaUserInvolvedIn>
    suspend fun getVikobaNearUser(userIDAddressID: UserIDAddressID): List<VikobaNearUser>
    suspend fun saveKikobaJoinRequest(kikobaRequestCredentials: KikobaRequestCredentials): Boolean
    suspend fun acceptKikobaInvitation(invitationCoupon: InvitationCoupon): Boolean
    suspend fun rejectKikobaInvitation(invitationCoupon: InvitationCoupon): Boolean
    suspend fun getKikobaMemberID(kikobaIDUserID: KikobaIDUserID): Int
    suspend fun selectKikobaAdmin(kikobaLeaderID: KikobaLeaderID): Boolean
    suspend fun selectKikobaSecretary(kikobaLeaderID: KikobaLeaderID): Boolean
    suspend fun selectKikobaAccountant(kikobaLeaderID: KikobaLeaderID): Boolean
    suspend fun selectKikobaChairPerson(kikobaLeaderID: KikobaLeaderID): Boolean
    suspend fun updateKikobaProfile(editedKikobaProfile: EditedKikobaProfile): Boolean
}

class DefaultKikobaRepository(private val vicobaApiService: VicobaApiService) : KikobaRepository {
    override suspend fun saveNewKikoba(kikoba: NewKikoba): ActiveKikoba {
        return vicobaApiService.saveNewKikoba(kikoba)
    }

    override suspend fun getVikobaUserInvolvedIn(userID: UserID): List<VikobaUserInvolvedIn> {
        return vicobaApiService.getVikobaUserInvolvedIn(userID)
    }

    override suspend fun getVikobaNearUser(userIDAddressID: UserIDAddressID): List<VikobaNearUser> {
        return vicobaApiService.getVikobaNearUser(userIDAddressID)
    }

    override suspend fun getKikobaInfo(kikobaID: KikobaID): ActiveKikoba {
        return vicobaApiService.getKikobaInfo(kikobaID)
    }

    override suspend fun getInvitedKikobaInfo(kikobaID: KikobaID): InvitedKikoba {
        return vicobaApiService.getInvitedKikobaInfo(kikobaID)
    }

    override suspend fun saveKikobaJoinRequest(kikobaRequestCredentials: KikobaRequestCredentials): Boolean {
        return vicobaApiService.saveKikobaJoinRequest(kikobaRequestCredentials)
    }

    override suspend fun getKikobaMembers(kikobaID: KikobaID): List<KikobaMember> {
        return vicobaApiService.getKikobaMembers(kikobaID)
    }

    override suspend fun getTotalKikobaMembers(kikobaID: KikobaID): TotalKikobaMembers {
        return vicobaApiService.getTotalKikobaMembers(kikobaID)
    }

    override suspend fun acceptKikobaInvitation(invitationCoupon: InvitationCoupon): Boolean {
        return vicobaApiService.acceptKikobaInvitation(invitationCoupon)
    }

    override suspend fun rejectKikobaInvitation(invitationCoupon: InvitationCoupon): Boolean {
        return vicobaApiService.rejectKikobaInvitation(invitationCoupon)
    }


    override suspend fun updateKikobaProfile(editedKikobaProfile: EditedKikobaProfile): Boolean {
        return vicobaApiService.updateKikobaProfile(editedKikobaProfile)
    }

    override suspend fun selectKikobaAdmin(kikobaLeaderID: KikobaLeaderID): Boolean {
        return vicobaApiService.selectKikobaAdmin(kikobaLeaderID)
    }
    override suspend fun selectKikobaSecretary(kikobaLeaderID: KikobaLeaderID): Boolean {
        return vicobaApiService.selectKikobaSecretary(kikobaLeaderID)
    }
    override suspend fun selectKikobaAccountant(kikobaLeaderID: KikobaLeaderID): Boolean {
        return vicobaApiService.selectKikobaAccountant(kikobaLeaderID)
    }
    override suspend fun selectKikobaChairPerson(kikobaLeaderID: KikobaLeaderID): Boolean {
        return vicobaApiService.selectKikobaChairPerson(kikobaLeaderID)
    }

    override suspend fun getKikobaMemberID(kikobaIDUserID: KikobaIDUserID): Int {
        return vicobaApiService.getKikobaMemberID(kikobaIDUserID)
    }
}