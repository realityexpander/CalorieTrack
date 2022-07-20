package com.realityexpander.tracker_presentation.tracker_overview_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.realityexpander.core.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable  // we make it a composable so we can access the string resources without a context
fun parseDateText(date: LocalDate): String {
    val today = LocalDate.now()

    return when(date) {
        today                               -> stringResource(id = R.string.today)
        today.minusDays(1)    -> stringResource(id = R.string.yesterday)
        today.plusDays(1)        -> stringResource(id = R.string.tomorrow)
        else -> DateTimeFormatter.ofPattern("dd LLLL").format(date)  // day FullNameMonth
    }
}