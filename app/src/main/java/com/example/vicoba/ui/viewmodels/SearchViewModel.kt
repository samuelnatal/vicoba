package com.example.vicoba.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vicoba.VicobaApplication
import com.example.vicoba.data.models.items.KIDsearchItem
import com.example.vicoba.data.models.items.SearchItem
import com.example.vicoba.data.models.lists.GeneralSearchResult
import com.example.vicoba.data.models.lists.UserSearchedToAddInKikoba
import com.example.vicoba.data.repositories.SearchRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

/** A view model that exposes data to all search screen UI*/
class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    /** Observable states for all screens with search field*/
    private val _query = mutableStateOf("")
    var query: State<String> = _query


    private val _userToSearch = mutableStateOf("")
    var userToSearch: State<String> = _userToSearch

    /** State for managing a list of items returned in a general search screen */
    private val _generalSearchResultList = mutableStateOf(emptyList<GeneralSearchResult>())
    var generalSearchResultList: State<List<GeneralSearchResult>> = _generalSearchResultList

    /** State for managing a list of users to be added in kikoba in manage member screen by an admin */
    private val _usersSearchedToAddInKikobaList = mutableStateOf(emptyList<UserSearchedToAddInKikoba>())
    var usersSearchedToAddInKikobaList: State<List<UserSearchedToAddInKikoba>> = _usersSearchedToAddInKikobaList

    /** Update the search item typed by user in a general search screen */
    fun updateQuery(query:String){
        _query.value = query
    }

    /** Update the search field when typing a user to search */
    fun updateUserToSearch(user:String){
        _userToSearch.value = user
    }


    /** A function for searching general data  enquired by a user */
    fun generalSearch(searchItem: SearchItem) {
        viewModelScope.launch {
            try {
                _generalSearchResultList.value = searchRepository.generalSearch(searchItem)
            } catch (e: IOException) {
                Log.e("getReq", "Error: ", e)
            } catch (e: HttpException) {
                Log.e("getReq", "Error: ", e)
            } catch (e: SerializationException) {
                Log.e("getReq", "Error: ", e)
            }
        }
    }

    /** A function for searching user to add to kikoba by an admin */
    fun searchUserToAddInKikoba(kIDsearchItem: KIDsearchItem) {
        viewModelScope.launch {
            try {
                _usersSearchedToAddInKikobaList.value = searchRepository.searchUserToAddInKikoba(kIDsearchItem)
            } catch (e: IOException) {
                Log.e("getReq", "Error: ", e)
            } catch (e: HttpException) {
                Log.e("getReq", "Error: ", e)
            } catch (e: SerializationException) {
                Log.e("getReq", "Error: ", e)
            }
        }
    }

    /**
     * Because the Android framework does not allow a ViewModel
     * to be passed values in the constructor when created,
     * we implement a ViewModelProvider.Factory object,
     * which lets us get around this limitation.
     */
    companion object {
        val searchFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VicobaApplication)
                val searchRepository = application.container.searchRepository
                SearchViewModel(
                    searchRepository = searchRepository,
                )
            }
        }
    }
}