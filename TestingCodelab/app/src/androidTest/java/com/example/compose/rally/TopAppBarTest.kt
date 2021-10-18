package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.toUpperCase
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test
import java.util.*

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = RallyScreen.values().asList(),
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsDisplayed()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelIsDisplayed() {
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = RallyScreen.values().asList(),
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase(Locale.getDefault())) and
                        hasParent(hasContentDescription(RallyScreen.Accounts.name)),
                useUnmergedTree = true
            )
            .assertExists()
    }

    @Test
    fun rallyTopAppBarTest_currentTabDisplayedByState() {
        composeTestRule.setContent {
            RallyApp()
        }

        composeTestRule
            .onNodeWithText(
                RallyScreen.Overview.name.uppercase(Locale.getDefault()),
                useUnmergedTree = true
            )
            .onParent()
            .assertIsSelected()

        // Click accounts tab
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .performClick()

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        // Assert ACCOUNTS tab is selected
        composeTestRule
            .onNodeWithText(
                RallyScreen.Accounts.name.uppercase(Locale.getDefault()),
                useUnmergedTree = true
            )
            .onParent()
            .assertIsSelected()

        // BILLS
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Bills.name)
            .performClick()

        // Assert BILLS tab is selected
        composeTestRule
            .onNodeWithText(
                RallyScreen.Bills.name.uppercase(Locale.getDefault()),
                useUnmergedTree = true
            )
            .onParent()
            .assertIsSelected()

    }
}