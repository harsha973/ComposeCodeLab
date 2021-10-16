package com.example.compose.rally

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.rally.data.UserData
import com.example.compose.rally.ui.accounts.AccountsBody
import com.example.compose.rally.ui.accounts.SingleAccountBody
import com.example.compose.rally.ui.bills.BillsBody
import com.example.compose.rally.ui.overview.OverviewBody


@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val accountScreenName = RallyScreen.Accounts.name
    NavHost(
        navController,
        RallyScreen.Overview.name,
        modifier
    ) {
        composable(RallyScreen.Overview.name) {
            OverviewBody(
                onClickSeeAllAccounts = { navController.navigate(RallyScreen.Accounts.name) },
                onClickSeeAllBills = { navController.navigate(RallyScreen.Bills.name) },
                onAccountClick = { navigateToSingleAccount(navController, it) }
            )
        }
        composable(accountScreenName) {
            AccountsBody(UserData.accounts) { navigateToSingleAccount(navController, it) }
        }
        val accountNameArgument = "name"
        composable(
            "$accountScreenName/{$accountNameArgument}",
            arguments = listOf(
                navArgument(accountNameArgument) { type = NavType.StringType }
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern = "rally://$accountScreenName/{$accountNameArgument}"
            })
        ) {
            val accountName = it.arguments?.getString(accountNameArgument)
            val account = UserData.getAccount(accountName = accountName)
            SingleAccountBody(account)
        }
        composable(RallyScreen.Bills.name) {
            BillsBody(UserData.bills)
        }
    }
}

private fun navigateToSingleAccount(
    navController: NavHostController,
    accountName: String
) {
    navController.navigate(RallyScreen.Accounts.name + "/${accountName}")
}