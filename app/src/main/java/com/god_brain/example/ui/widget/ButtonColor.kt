package com.god_brain.example.ui.widget

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal fun transparentButtonColors() = ButtonDefaults.buttonColors(
    contentColor = Color.White,
    containerColor = Color.Transparent,
)