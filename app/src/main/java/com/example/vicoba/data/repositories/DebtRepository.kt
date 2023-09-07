package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.items.DebtCoupon
import com.example.vicoba.data.models.keys.KikobaID
import com.example.vicoba.data.models.lists.Debt
import com.example.vicoba.data.network.VicobaApiService

interface DebtRepository {
    suspend fun payDebt(debtCoupon: DebtCoupon):Boolean
    suspend fun getAllDebts(kikobaID: KikobaID):List<Debt>
}

class DefaultDebtRepository(
    private val vicobaApiService: VicobaApiService
):DebtRepository{
    override suspend fun payDebt(debtCoupon: DebtCoupon): Boolean {
        return vicobaApiService.payDebt(debtCoupon)
    }

    override suspend fun getAllDebts(kikobaID: KikobaID): List<Debt> {
        return vicobaApiService.getAllDebts(kikobaID)
    }
}