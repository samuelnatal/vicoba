package com.example.vicoba.ui.screens.kikoba

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vicoba.ui.viewmodels.KikobaViewModel

@Composable
fun AboutKikobaScreen(
    kikobaViewModel: KikobaViewModel
) {
    /** activeKikoba variable stores information of the current active kikoba */
    val activeKikoba = kikobaViewModel.activeKikoba.collectAsState().value
    Column() {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "UTAMBULISHO",fontWeight = FontWeight.Bold)
        Text(text = "JINA: ${activeKikoba.kikobaName}")
        Text(text = "UFAFANUZI: ${activeKikoba.kikobaDesc}")
        Text(text = "KIMEANZISHWA TAREHE: ${activeKikoba.createdAt}")

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "TARATIBU ZA HISA",fontWeight = FontWeight.Bold)
        Text(text = "THAMANI YA HISA: ${activeKikoba.sharePrice}")
        Text(text = "KIWANGO CHA JUU CHA KUNUNUA HISA: ${activeKikoba.maxShare}")
        Text(text = "MZUNGUKO WA KUCHANGIA HISA: ${activeKikoba.shareCircle}")

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "MAHALI KILIPO KIKOBA",fontWeight = FontWeight.Bold)
        Text(text = "MKOA: ${activeKikoba.kikobaRegion}")
        Text(text = "WILAYA: ${activeKikoba.kikobaDistrict}")
        Text(text = "KATA: ${activeKikoba.kikobaWard}")
    }

}