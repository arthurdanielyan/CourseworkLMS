package com.coursework.featureEditBook.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.coursework.corePresentation.commonUi.IconButton
import com.coursework.corePresentation.commonUi.SpacerWidth
import com.coursework.corePresentation.commonUi.clickableWithoutIndication
import com.coursework.corePresentation.commonUi.modifyIf
import com.coursework.featureEditBook.presentation.BookPdfViewState
import com.coursework.corePresenation.R.drawable as CoreDrawables
import com.coursework.featureEditBook.R.drawable as Drawables
import com.coursework.featureEditBook.R.string as Strings

@Composable
internal fun PdfPicker(
    modifier: Modifier = Modifier,
    bookPdf: BookPdfViewState,
    onPickFileClick: () -> Unit,
    onOpenFileClick: () -> Unit,
    onRemoveClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .modifyIf(bookPdf.isUploaded.not()) {
                clickable(onClick = onPickFileClick)
            }
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.medium
            )
            .animateContentSize()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                if (bookPdf.isUploaded) {
                    CoreDrawables.ic_pdf
                } else {
                    Drawables.ic_upload
                }
            ),
            contentDescription = null,
            modifier = Modifier.requiredSize(28.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
        SpacerWidth(8.dp)
        Text(
            text = if (bookPdf.isUploaded) {
                bookPdf.displayName
            } else {
                stringResource(Strings.upload_pdf)
            },
            modifier = Modifier
                .weight(1f, false)
                .modifyIf(bookPdf.isUploaded) {
                    clickableWithoutIndication(onClick = onOpenFileClick)
                },
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        SpacerWidth(8.dp)
        if (bookPdf.isUploaded) {
            IconButton(
                imageVector = ImageVector.vectorResource(Drawables.ic_modify),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                onClick = onPickFileClick
            )

            IconButton(
                imageVector = ImageVector.vectorResource(Drawables.ic_trash),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                onClick = onRemoveClick
            )
        }
    }
}