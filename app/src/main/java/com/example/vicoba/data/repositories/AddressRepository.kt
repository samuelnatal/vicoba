package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.lists.Address
import com.example.vicoba.data.network.VicobaApiService

/** A repository that communicate with the internet vicoba database
 * to get a list of available addresses
 */
interface AddressRepository {
    suspend fun getAddresses():List<Address>
}

class DefaultAddressRepository(private val vicobaApiService: VicobaApiService): AddressRepository {
    override suspend fun getAddresses(): List<Address> {
        return vicobaApiService.getAddresses()
    }
}