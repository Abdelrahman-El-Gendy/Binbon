package com.binbon.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.binbon.app.presentation.feed.FeedScreen

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(FeedRoute)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeAt(backStack.lastIndex) },
        entryProvider = entryProvider {
            entry<FeedRoute> {
                FeedScreen()
            }
        }
    )
}
