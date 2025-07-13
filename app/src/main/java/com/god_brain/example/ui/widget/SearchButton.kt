package com.god_brain.example.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.div

// 搜尋按鈕
@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable() (RowScope.() -> Unit)
) {

    ResizeButton(
        onClick = onClick,
        modifier = modifier,
        shape = { density, size ->
            RoundedCornerShape(with(density) { (minOf(size.width, size.height) / 2).toDp() })
        },
        enabled = enabled,
        border = BorderStroke(1.dp, Color.Gray), // 灰色細框
        contentPadding = PaddingValues(
            horizontal = 8.dp,
        ), // 內部間距
        colors = transparentButtonColors()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 8.dp) // 左側的間距
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.size(24.dp), // 調整 icon 大小
                tint = Color.Gray // Icon 顏色
            )
            content() // 自定義內容
        }
    }
}