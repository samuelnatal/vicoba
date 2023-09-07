package com.example.vicoba.data.repositories

import com.example.vicoba.data.models.items.KIDsearchItem
import com.example.vicoba.data.models.items.SearchItem
import com.example.vicoba.data.models.lists.GeneralSearchResult
import com.example.vicoba.data.models.lists.UserSearchedToAddInKikoba
import com.example.vicoba.data.network.VicobaApiService

/** Repository for providing data being searched by vicoba app user */
interface SearchRepository {
    suspend fun generalSearch( searchItem: SearchItem):List<GeneralSearchResult>
    suspend fun searchUserToAddInKikoba(kIDsearchItem: KIDsearchItem ):List<UserSearchedToAddInKikoba>
}

class DefaultSearchRepository(
    private val vicobaApiService: VicobaApiService
): SearchRepository {
    override suspend fun generalSearch(searchItem: SearchItem): List<GeneralSearchResult> {
        return vicobaApiService.searchGeneral(searchItem)
    }

    override suspend fun searchUserToAddInKikoba(kIDsearchItem: KIDsearchItem): List<UserSearchedToAddInKikoba> {
        return vicobaApiService.searchUserToAddInKikoba(kIDsearchItem)
    }
}