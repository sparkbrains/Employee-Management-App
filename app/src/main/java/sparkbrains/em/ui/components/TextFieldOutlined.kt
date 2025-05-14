package sparkbrains.em.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sparkbrains.em.R

@Preview(showBackground = true)
@Composable
fun TextFieldOutlined(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String = "",
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    leadingIcon: ImageVector? = null,
    supportingTextError: String? = null,
    supportingTextSuccess: String? = null,
    supportingTextDefault: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val textColor = when {
        supportingTextError != null -> MaterialTheme.colorScheme.error
        supportingTextSuccess != null -> Color(0xFF4CAF50) // Material green
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val supportingText = supportingTextError ?: supportingTextSuccess ?: supportingTextDefault
    val isError = supportingTextError != null

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = "$label Icon",
                    tint = if (isError) textColor else MaterialTheme.colorScheme.primary
                )
            }
        },
        isError = isError,
        supportingText = supportingText?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor
                )
            }
        },
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (isPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                        ),
                        contentDescription = if (isPasswordVisible) stringResource(R.string.hide_password) else stringResource(
                            R.string.show_password
                        )
                    )
                }
            }
        } else null,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp
        )
    )
}
