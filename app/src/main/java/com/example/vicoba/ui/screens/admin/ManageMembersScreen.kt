package com.example.vicoba.ui.screens.admin

import OkAlertDialog
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vicoba.R
import com.example.vicoba.data.models.items.KIDsearchItem
import com.example.vicoba.data.models.keys.KikobaIDUserID
import com.example.vicoba.data.models.lists.KikobaMember
import com.example.vicoba.data.models.lists.UserRequestedKikoba
import com.example.vicoba.data.models.lists.UserSearchedToAddInKikoba
import com.example.vicoba.ui.components.RemoveMemberCard
import com.example.vicoba.ui.viewmodels.KikobaManagementViewModel
import com.example.vicoba.ui.viewmodels.KikobaViewModel
import com.example.vicoba.ui.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MembersScreen(
    kikobaManagementViewModel: KikobaManagementViewModel,
    kikobaViewModel: KikobaViewModel,
    searchViewModel: SearchViewModel,
) {
    Scaffold() {
        /** Observe the details of the user requested kikoba from kikoba management view model */
        val userRequestedKikobaInfo =
            kikobaManagementViewModel.userRequestedKikobaInfo.collectAsState().value

        /** activeKikoba variable stores information of the current active kikoba */
        val activeKikoba = kikobaViewModel.activeKikoba.collectAsState()

        val members = kikobaViewModel.members.value

        val userToSearch = searchViewModel.userToSearch.value

        val usersSearchedToAddInKikobaList = searchViewModel.usersSearchedToAddInKikobaList.value


        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(10.dp))
            SearchTextField(
                kikobaID = activeKikoba.value.kikobaID,
                searchViewModel = searchViewModel,
                userToSearch = userToSearch,
                usersSearchedToAddInKikobaList = usersSearchedToAddInKikobaList,
                kikobaManagementViewModel = kikobaManagementViewModel,
                userRequestedKikobaInfo = userRequestedKikobaInfo
            )

            UserRequestsCard(
                kikobaManagementViewModel = kikobaManagementViewModel,
                usersRequestedKikobaList = kikobaManagementViewModel.usersRequestedKikobaList.value,
                userRequestedKikobaInfo = userRequestedKikobaInfo
            )

            AllMembersCard(
                kikobaManagementViewModel = kikobaManagementViewModel,
                members = members,
                kikobaViewModel = kikobaViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    userToSearch: String,
    kikobaID: Int,
    searchViewModel: SearchViewModel,
    userRequestedKikobaInfo: UserRequestedKikoba,
    kikobaManagementViewModel: KikobaManagementViewModel,
    usersSearchedToAddInKikobaList: List<UserSearchedToAddInKikoba>
) {
    var searchStatus by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    var approvalStatus by remember { mutableStateOf(false) }
    var showUserInfo by remember { mutableStateOf(false) }


    Column(
        Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = userToSearch,
            onValueChange = { searchViewModel.updateUserToSearch(it) },
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
                approvalStatus = false
                searchViewModel.searchUserToAddInKikoba(
                    kIDsearchItem = KIDsearchItem(
                        kikobaID = kikobaID,
                        searchItem = userToSearch
                    )
                )

                searchStatus = "No user found with such name!"
                keyboardController?.hide()
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (usersSearchedToAddInKikobaList.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(usersSearchedToAddInKikobaList) { user ->

                    Card(
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.members),
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )

                            Text(
                                text = "${user.firstName} ${user.lastName}",
                                modifier = Modifier.padding(start = 8.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "View",
                                    modifier = Modifier.clickable {
                                        val kikobaIDUserID = KikobaIDUserID(
                                            userID = user.userID,
                                            kikobaID = kikobaID
                                        )

                                        kikobaManagementViewModel.getUserRequestedKikobaInfo(
                                            kikobaIDUserID
                                        )
                                        showUserInfo = true
                                    }
                                )

                                if (showUserInfo) {
                                    OkAlertDialog(
                                        icon = Icons.Default.Info,
                                        title = "User Information",
                                        body = "" +
                                                "NAME : ${userRequestedKikobaInfo.firstName} ${userRequestedKikobaInfo.lastName} \n" +
                                                "GENDER : ${
                                                    if (userRequestedKikobaInfo.gender == "f") {
                                                        "Female"
                                                    } else {
                                                        "Male"
                                                    }
                                                } \n" +
                                                "AGE : ${userRequestedKikobaInfo.age} \n" +
                                                "EMAIL : ${userRequestedKikobaInfo.email} \n" +
                                                "PHONE : ${userRequestedKikobaInfo.phone} \n" +
                                                "REGION : ${userRequestedKikobaInfo.region} \n" +
                                                "DISTRICT : ${userRequestedKikobaInfo.district} \n" +
                                                "WARD : ${userRequestedKikobaInfo.ward} \n" +
                                                "",
                                        showDialog = showUserInfo,
                                        onDismiss = { showDialogStatus ->
                                            showUserInfo = showDialogStatus
                                        }
                                    )
                                }


                                Spacer(modifier = Modifier.width(20.dp))

                                if (approvalStatus) {
                                    Text(text = "Invited", color = colorResource(id = R.color.dark_green))
                                }

                                if (!approvalStatus) {
                                    Text(
                                        text = "Invite",
                                        modifier = Modifier.clickable {
                                            val kikobaIDUserID = KikobaIDUserID(
                                                userID = user.userID,
                                                kikobaID = kikobaID
                                            )
                                            approvalStatus =
                                                kikobaManagementViewModel.inviteUserToJoinKikoba(
                                                    kikobaIDUserID
                                                )
                                        }
                                    )
                                }

                                Spacer(modifier = Modifier.width(20.dp))

                            }
                        }
                    }
                }
            }
        } else {
            Text(text = searchStatus)
        }
    }
}

@Composable
fun AllMembersCard(
    members: List<KikobaMember>,
    kikobaManagementViewModel: KikobaManagementViewModel,
    kikobaViewModel: KikobaViewModel
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .shadow(
                elevation = 2.dp
            )
    ) {
        Text(
            "Wanakikundi",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(start = 15.dp, top = 20.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        /** A forloop that creates cards UI holding information of users requested to join a particular group */

        if (members.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(members) { member ->
                    RemoveMemberCard(
                        member = member,
                        kikobaManagementViewModel = kikobaManagementViewModel,
                        kikobaViewModel = kikobaViewModel
                    )
                }
            }
        } else {
            Text(text = "No members found at this kikoba")
        }
    }
}


@Composable
fun UserRequestsCard(
    usersRequestedKikobaList: List<UserRequestedKikoba>,
    userRequestedKikobaInfo: UserRequestedKikoba,
    kikobaManagementViewModel: KikobaManagementViewModel
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .shadow(
                elevation = 2.dp
            )
    ) {
        Text(
            "Maombi kujiunga.",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(start = 15.dp, top = 20.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (usersRequestedKikobaList.isNotEmpty()) {
            /** A forloop that creates cards UI holding information of users requested to join a particular group */
            usersRequestedKikobaList.forEach { user ->
                Card(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                ) {
                    var approvalStatus by remember { mutableStateOf(false) }
                    var rejectStatus by remember { mutableStateOf(false) }
                    var showUserInfo by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.members),
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )

                        Text(
                            text = "${user.firstName} ${user.lastName}",
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "View",
                                modifier = Modifier.clickable {
                                    val kikobaIDUserID = KikobaIDUserID(
                                        userID = user.userKey,
                                        kikobaID = user.kikobaKey
                                    )

                                    kikobaManagementViewModel.getUserRequestedKikobaInfo(
                                        kikobaIDUserID
                                    )
                                    showUserInfo = true
                                }
                            )

                            /** Show details of the user requested kikoba to an admin */
                            if (showUserInfo) {
                                OkAlertDialog(
                                    icon = Icons.Default.Info,
                                    title = "User Information",
                                    body = "" +
                                            "NAME : ${userRequestedKikobaInfo.firstName} ${userRequestedKikobaInfo.lastName} \n" +
                                            "GENDER : ${
                                                if (userRequestedKikobaInfo.gender == "f") {
                                                    "Female"
                                                } else {
                                                    "Male"
                                                }
                                            } \n" +
                                            "AGE : ${userRequestedKikobaInfo.age} \n" +
                                            "EMAIL : ${userRequestedKikobaInfo.email} \n" +
                                            "PHONE : ${userRequestedKikobaInfo.phone} \n" +
                                            "REGION : ${userRequestedKikobaInfo.region} \n" +
                                            "DISTRICT : ${userRequestedKikobaInfo.district} \n" +
                                            "WARD : ${userRequestedKikobaInfo.ward} \n" +
                                            "",
                                    showDialog = showUserInfo,
                                    onDismiss = { showDialogStatus ->
                                        showUserInfo = showDialogStatus
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            if (approvalStatus) {
                                Icon(Icons.Default.Check, contentDescription = "Approved")
                            }

                            if (!approvalStatus) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    modifier = Modifier.clickable {
                                        val kikobaIDUserID = KikobaIDUserID(
                                            userID = user.userKey,
                                            kikobaID = user.kikobaKey
                                        )
                                        approvalStatus =
                                            kikobaManagementViewModel.approveKikobaJoinRequest(
                                                kikobaIDUserID
                                            )
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            if (rejectStatus) {
                                Text(
                                    text = "Canceled",
                                    color = Color.Red
                                )
                            }

                            if (!rejectStatus) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "View",
                                    modifier = Modifier.clickable {
                                        val kikobaIDUserID = KikobaIDUserID(
                                            userID = user.userKey,
                                            kikobaID = user.kikobaKey
                                        )
                                        rejectStatus =
                                            kikobaManagementViewModel.cancelKikobaJoinRequest(
                                                kikobaIDUserID
                                            )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Text(
                text = "Hakuna maombi yoyote ya kujiunga yaliyotumwa.",
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}


@Preview
@Composable
fun MembersScreenPreview() {
    val kikobaManagementViewModel: KikobaManagementViewModel =
        viewModel(factory = KikobaManagementViewModel.kikobaManagementFactory)

    val searchViewModel: SearchViewModel =
        viewModel(factory = SearchViewModel.searchFactory)

    val kikobaViewModel: KikobaViewModel =
        viewModel(factory = KikobaViewModel.kikobaFactory)
    MembersScreen(
        kikobaManagementViewModel = kikobaManagementViewModel,
        kikobaViewModel = kikobaViewModel,
        searchViewModel = searchViewModel,
    )
}
