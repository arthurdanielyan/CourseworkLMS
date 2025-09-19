package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    errorMessageEnabled: Boolean = false,
    showCleanIcon: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TextField(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            ),
            isError = isError,
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
            ),
            visualTransformation = if (keyboardType == KeyboardType.Password) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = if (showCleanIcon) {
                {
                    IconButton(
                        onClick = {
                            onValueChange("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear input"
                        )
                    }
                }
            } else null
        )
        if (errorMessageEnabled) {
            Text(
                text = errorMessage.takeIf { isError }.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}