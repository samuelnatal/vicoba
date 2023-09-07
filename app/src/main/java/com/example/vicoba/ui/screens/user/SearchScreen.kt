package com.example.vicoba.ui.screens.user

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vicoba.data.models.items.SearchItem
import com.example.vicoba.ui.components.ArrowForwardCard
import com.example.vicoba.ui.viewmodels.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel
) {
    /** query observes the value required for being searched for as user types it */
    val query = searchViewModel.query.value

    val generalSearchResult = searchViewModel.generalSearchResultList.value

    var searchStatus by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { searchViewModel.updateQuery(it) },
            label = { Text(text = "Tafuta kikoba, ama mwanakikundi") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Tafuta"
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions {
                searchViewModel.generalSearch(
                    searchItem = SearchItem(
                        searchItem = query
                    )
                )

                searchStatus = "No result found!"
                keyboardController?.hide()
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )

        if (generalSearchResult.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var firstName =""
                var lastName =""
                items(generalSearchResult) { item ->

                    if(firstName != item.firstName && lastName != item.lastName){
                        ArrowForwardCard(
                            icon = Icons.Default.Info,
                            title = "User: ${item.firstName} ${item.lastName}",
                            desc = "User"
                        )
                    }

                    if( item.kikobaName != "Not found"){
                        ArrowForwardCard(
                            icon = Icons.Default.Info,
                            title = "Kikoba: ${item.kikobaName}",
                            desc = "Kikoba"
                        )
                    }

                    firstName = item.firstName
                    lastName = item.lastName

                }
            }
        } else {
            Text(text = searchStatus)
        }
    }
}

@Preview
@Composable
fun Search() {

    val searchViewModel: SearchViewModel =
        viewModel(factory = SearchViewModel.searchFactory)

    SearchScreen(searchViewModel = searchViewModel)
}
