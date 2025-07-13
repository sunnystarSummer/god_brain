package com.god_brain.example.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize

@Composable
fun ResizeButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors,
    shape: @Composable (Density, IntSize) -> Shape = { _, _ ->
        ButtonDefaults.shape
    },
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit)
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    Button(
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                size = layoutCoordinates.size
            },
        colors = colors,
        shape = shape(density, size),
        border = border,
        contentPadding = contentPadding,
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}