package com.god_brain.example.ui.view_01_store_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.god_brain.example.data.model.Commodity
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDetailScaffoldComposeUI(
    commodity: Commodity,
    back: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("商品資訊")
                },
                navigationIcon = {
                    //ToDo: 新增BackICON
                    IconButton(
                        onClick = back
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        commodity.run {
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                //
                AsyncImage(
                    model = commodity.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text("商品編號: ${commodity.martId}")
                Text(commodity.martShortName)

                val priceText = remember(commodity.finalPrice) {
                    NumberFormat.getCurrencyInstance(Locale.TAIWAN).apply {
                        maximumFractionDigits = 0
                    }.format(commodity.finalPrice)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    //FIXME: 至左
                    Text(priceText)

                    //FIXME: 至右
                    Row {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            modifier = Modifier.size(24.dp), // 調整 icon 大小
                            tint = Color.Gray // Icon 顏色
                        )
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "ShoppingCart",
                            modifier = Modifier.size(24.dp), // 調整 icon 大小
                            tint = Color.Gray // Icon 顏色
                        )
                    }
                }
            }
        }

    }
}