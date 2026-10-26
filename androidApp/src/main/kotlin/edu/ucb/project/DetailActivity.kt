package edu.ucb.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.ucb.ds.components.buttons.PrimaryButton
import edu.ucb.ds.components.cards.DsCard
import edu.ucb.ds.components.labels.DsLabel
import edu.ucb.ds.components.surfaces.DsSurface
import edu.ucb.ds.theme.DsTheme
import edu.ucb.ds.theme.DsThemeMode

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val messageId = intent.getStringExtra(EXTRA_MESSAGE_ID)

        setContent {
            val mode = if (isSystemInDarkTheme()) DsThemeMode.DARK else DsThemeMode.LIGHT
            DsTheme(mode = mode) {
                DetailScreen(messageId = messageId, onBack = { finish() })
            }
        }
    }

    companion object {
        const val EXTRA_MESSAGE_ID = "message_id"
    }
}

@Composable
private fun DetailScreen(messageId: String?, onBack: () -> Unit) {
    DsSurface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DsLabel(text = stringResource(R.string.detail_title))
            DsCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DsLabel(text = stringResource(R.string.detail_message_id_label))
                    DsLabel(
                        text = if (messageId.isNullOrBlank()) {
                            stringResource(R.string.detail_no_id)
                        } else {
                            messageId
                        }
                    )
                }
            }
            PrimaryButton(
                text = stringResource(R.string.detail_back),
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
