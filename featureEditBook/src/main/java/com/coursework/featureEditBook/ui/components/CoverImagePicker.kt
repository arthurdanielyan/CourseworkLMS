package com.coursework.featureEditBook.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.coursework.corePresentation.commonUi.IconButton
import com.coursework.corePresentation.commonUi.MaximizableContent
import com.coursework.corePresentation.commonUi.SpacerWidth
import com.coursework.corePresentation.commonUi.modifyIf
import com.coursework.featureEditBook.presentation.CoverImageViewState
import com.coursework.corePresentation.R.drawable as CoreDrawables
import com.coursework.featureEditBook.R.drawable as Drawables
import com.coursework.featureEditBook.R.string as Strings

@Composable
internal fun CoverImagePicker(
    modifier: Modifier = Modifier,
    coverImage: CoverImageViewState,
    onPickImageClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    var isImageMaximized by rememberSaveable(stateSaver = autoSaver()) {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .modifyIf(coverImage.isUploaded.not()) {
                clickable(onClick = onPickImageClick)
            }
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.medium,
            )
            .animateContentSize()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (coverImage.isUploaded) {
            MaximizableContent(
                isMaximized = isImageMaximized,
                onToggle = { isImageMaximized = !isImageMaximized },
                overlayContent = {
                    Box(
                        modifier = Modifier
                            .systemBarsPadding()
                            .align(Alignment.TopEnd)
                    ) {
                        IconButton(
                            modifier = Modifier
                                .padding(16.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.tertiaryContainer),
                            imageVector = ImageVector.vectorResource(CoreDrawables.ic_close),
                            contentDescription = "Minimize",
                            onClick = {
                                isImageMaximized = false
                            },
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(coverImage.url ?: coverImage.localUri?.toUri())
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .requiredWidth(36.dp)
                )
            }
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(Drawables.ic_upload),
                contentDescription = null,
                modifier = Modifier.requiredSize(28.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        SpacerWidth(8.dp)
        Text(
            text = stringResource(
                if (coverImage.isUploaded) {
                    Strings.cover_uploaded
                } else {
                    Strings.upload_cover_image
                }
            ),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
        )
        SpacerWidth(2.dp)
        if (coverImage.isUploaded) {
            IconButton(
                imageVector = ImageVector.vectorResource(Drawables.ic_modify),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                onClick = onPickImageClick
            )
            SpacerWidth(2.dp)
            IconButton(
                imageVector = ImageVector.vectorResource(Drawables.ic_trash),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                onClick = onRemoveClick
            )
        }
    }
}

