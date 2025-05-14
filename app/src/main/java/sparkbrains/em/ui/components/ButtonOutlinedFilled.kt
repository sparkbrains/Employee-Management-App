package sparkbrains.em.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sparkbrains.em.R

@Preview
@Composable
fun ButtonOutlinedFilled(
    modifier: Modifier = Modifier,
    @StringRes text: Int = R.string.app_name,
    enabled: Boolean = true,
    isFilled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {

    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        onClick = { onClick.invoke() },
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isFilled) MaterialTheme.colorScheme.primary else Color.Transparent,
            contentColor = if (isFilled) Color.Transparent else MaterialTheme.colorScheme.primary,
        )
    ) {
        if (isLoading) {
            CircularLoader()
        } else {
            Text(
                text = stringResource(text),
                style = TextStyle(
                    color = if (isFilled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )
            )
        }
    }
}