package sparkbrains.em.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularLoader(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .size(56.dp)
            .padding(top = 16.dp)
    )
}