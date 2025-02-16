package eu.kanade.presentation.more.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import eu.kanade.presentation.components.Scaffold
import eu.kanade.presentation.components.ScrollbarLazyColumn
import eu.kanade.presentation.components.SearchToolbar
import eu.kanade.presentation.util.horizontalPadding
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.ui.setting.SettingsController
import eu.kanade.tachiyomi.ui.setting.search.SettingsSearchHelper
import eu.kanade.tachiyomi.ui.setting.search.SettingsSearchPresenter
import kotlin.reflect.full.createInstance

@Composable
fun SettingsSearchScreen(
    navigateUp: () -> Unit,
    presenter: SettingsSearchPresenter,
    onClickResult: (SettingsController) -> Unit,
) {
    val results by presenter.state.collectAsState()
    var query by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { scrollBehavior ->
            SearchToolbar(
                searchQuery = query,
                onChangeSearchQuery = {
                    query = it
                    presenter.searchSettings(it)
                },
                placeholderText = stringResource(R.string.action_search_settings),
                onClickCloseSearch = navigateUp,
                onClickResetSearch = { query = "" },
                scrollBehavior = scrollBehavior,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    },
                ),
            )
        },
    ) { contentPadding ->
        ScrollbarLazyColumn(
            contentPadding = contentPadding,
        ) {
            items(
                items = results,
                key = { it.key.toString() },
            ) { result ->
                SearchResult(result, onClickResult)
            }
        }
    }
}

@Composable
private fun SearchResult(
    result: SettingsSearchHelper.SettingsSearchResult,
    onClickResult: (SettingsController) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Must pass a new Controller instance to avoid this error
                // https://github.com/bluelinelabs/Conductor/issues/446
                val controller = result.searchController::class.createInstance()
                controller.preferenceKey = result.key
                onClickResult(controller)
            }
            .padding(horizontal = horizontalPadding, vertical = 8.dp),
    ) {
        Text(
            text = result.title,
        )

        Text(
            text = result.summary,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.outline,
            ),
        )

        Text(
            text = result.breadcrumb,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
