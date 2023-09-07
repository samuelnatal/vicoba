package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.items.ShareCoupon
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.lists.Share
import com.example.vicoba.data.network.VicobaApiService

interface ShareRepository {
    suspend fun buyShare(shareCoupon: ShareCoupon):Boolean
    suspend fun getAllShares(kikobaID: KikobaID):List<Share>
}

class DefaultShareRepository(
    private val vicobaApiService: VicobaApiService
):ShareRepository{
    override suspend fun buyShare(shareCoupon: ShareCoupon):Boolean {
        return vicobaApiService.buyShare(shareCoupon)
    }

    override suspend fun getAllShares(kikobaID: KikobaID): List<Share> {
        return vicobaApiService.getAllShares(kikobaID)
    }
}