package com.example.vicoba.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Composable component that provide a drop down list for the OutlineTextField composable*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownOutlineTextField(
    value: String,
    label: String,
    dropDownList: List<String>,
    onValueChange: (String) -> Unit,
    isError:Boolean
) {
    /** the expanded state of the Text Field */
    var mExpanded by remember { mutableStateOf(false) }

    /** Create a string value to store the selected city */


    /** Up Icon when expanded and down icon when collapsed */
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(label) },
        trailingIcon = {
            Icon(icon, "contentDescription",
                Modifier.clickable { mExpanded = !mExpanded })
        }
    )

    /** Create a drop-down menu with list of cities,
     * when clicked, set the Text Field text as the city selected
     */
    DropdownMenu(
        expanded = mExpanded,
        onDismissRequest = { mExpanded = false },
        modifier = Modifier
            .width(335.dp)
    ) {
        dropDownList.forEach { label ->
            DropdownMenuItem(
                onClick = {
                    onValueChange(label)
                    mExpanded = false
                },
                text = {
                    Text(text = label)
                }
            )
        }
    }
}
