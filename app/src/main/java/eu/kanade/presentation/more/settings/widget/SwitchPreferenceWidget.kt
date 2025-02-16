package eu.kanade.presentation.more.settings.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SwitchPreferenceWidget(
    title: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    checked: Boolean = false,
    onCheckedChanged: (Boolean) -> Unit,
) {
    BasePreferenceWidget(
        title = title,
        subtitle = subtitle,
        icon = icon,
        onClick = { onCheckedChanged(!checked) },
    ) {
        Switch(
            checked = checked,
            onCheckedChange = null,
            modifier = Modifier.padding(start = TrailingWidgetBuffer),
        )
    }
}

@Preview
@Composable
fun SwitchPreferenceWidgetPreview() {
    MaterialTheme {
        Surface {
            Column {
                SwitchPreferenceWidget(
                    title = "Text preference with icon",
                    subtitle = "Text preference summary",
                    icon = Icons.Default.Preview,
                    checked = true,
                    onCheckedChanged = {},
                )
                SwitchPreferenceWidget(
                    title = "Text preference",
                    subtitle = "Text preference summary",
                    checked = false,
                    onCheckedChanged = {},
                )
                SwitchPreferenceWidget(
                    title = "Text preference no summary",
                    checked = false,
                    onCheckedChanged = {},
                )
                SwitchPreferenceWidget(
                    title = "Another text preference no summary",
                    checked = false,
                    onCheckedChanged = {},
                )
            }
        }
    }
}
