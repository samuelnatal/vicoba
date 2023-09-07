package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.lists.Occupation
import com.example.vicoba.data.network.VicobaApiService

/** Occupation repository handles the retrieval of occupations lists
 * from the vicoba database to be consumed by an app*/
interface OccupationRepository {
    suspend fun getOccupations(): List<Occupation>
}

class DefaultOccupationRepository(private val vicobaApiService: VicobaApiService):
    OccupationRepository {
    override suspend fun getOccupations(): List<Occupation> {
        return vicobaApiService.getOccupations()
    }
}