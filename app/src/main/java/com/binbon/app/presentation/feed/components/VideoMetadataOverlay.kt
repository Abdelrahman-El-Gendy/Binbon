package com.binbon.app.presentation.feed.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binbon.app.domain.model.Video

import androidx.compose.material3.MaterialTheme
import com.binbon.app.ui.theme.Dimens
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow

@Composable
fun VideoMetadataOverlay(
    video: Video,
    modifier: Modifier = Modifier
) {
    val textShadow = Shadow(
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
        offset = Offset(1f, 1f),
        blurRadius = 4f
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Username
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "@${video.username}",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium.copy(
                    shadow = textShadow
                ),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(Dimens.paddingSmall))

        // Description
        Text(
            text = video.description,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
            style = MaterialTheme.typography.bodyMedium.copy(
                shadow = textShadow,
                lineHeight = 20.sp
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
