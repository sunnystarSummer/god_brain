package com.god_brain.example.ui.view_01_store_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.god_brain.example.ViewModelFactory
import com.god_brain.example.data.model.Commodity
import com.god_brain.example.data.repository.MainRepository
import com.god_brain.example.ui.view_00_basic.BasicViewModel
import com.god_brain.example.ui.widget.SearchButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import kotlin.collections.filterNotNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreListScaffoldComposeUI(
    repository: MainRepository = MainRepository.getInstance(),
    factory: ViewModelProvider.Factory = ViewModelFactory(repository),
    viewModel: StoreListViewModel = viewModel(
        factory = factory
    ),
) {

    var queryValue by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {

                    SearchButton(
                        onClick = {}
                    ) {

                        TextField(
                            value = queryValue,
                            onValueChange = { newValue ->

                                queryValue = newValue
                            },
                            placeholder = { Text("") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            StoreListComposeUI(
                queryValue = queryValue,
                viewModel = viewModel,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreListComposeUI(
    repository: MainRepository = MainRepository.getInstance(),
    factory: ViewModelProvider.Factory = ViewModelFactory(repository),
    viewModel: StoreListViewModel = viewModel(
        factory = factory
    ),
    queryValue: String
) {
    val commodityList by viewModel.list.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCommodityInfo()
    }

    LaunchedEffect(queryValue) {

        if (queryValue.isNotEmpty()) {
            viewModel.query(queryValue)
        } else {
            // 如果搜尋值為空，則恢復顯示全部商品
            viewModel.query("")
        }
    }


    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }
    LazyColumn(state = listState) {
        items(
            commodityList,
        ) { commodity ->
            Card(
                onClick = {

                    viewModel.setCommodityInfo(commodity)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()         // 讓 Row 充滿整行，權重才會生效
                ) {
                    // 圖片佔 1 份
                    AsyncImage(
                        model = commodity.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .weight(1f)
                    )

                    //ToDo: 比例均分
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .padding(16.dp)
                    ) {

                        Text(
                            text = commodity.martName,
                            style = MaterialTheme.typography.titleMedium,
                        )

                        // 新台幣格式化_去掉小數位
                        val priceText = remember(commodity.finalPrice) {
                            NumberFormat.getCurrencyInstance(Locale.TAIWAN).apply {
                                maximumFractionDigits = 0
                            }.format(commodity.finalPrice)
                        }

                        Text(
                            text = priceText,
                            style = MaterialTheme.typography.titleSmall,
                        )

                        //FIXME: 在Column中使用Row至右
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ){
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
}

class StoreListViewModel(
    private val repository: MainRepository
) : BasicViewModel() {

    private val _commodityInfo = MutableStateFlow<Commodity?>(null)
    val commodityInfo: StateFlow<Commodity?> = _commodityInfo.asStateFlow()

    fun setCommodityInfo(value: Commodity?) {
        _commodityInfo.value = value
    }

    private val _list = MutableStateFlow<List<Commodity>>(emptyList())
    private var _listWithAll: MutableList<Commodity>? = mutableListOf()
    val list: StateFlow<List<Commodity>> = _list.asStateFlow()

    fun getCommodityInfo() = viewModelScope.launch {

        processSyncOfList(
            api = repository::getCommodityInfo,
            successCallback = { result ->

                result?.run {

                    _list.value = result
                        .filterNotNull()
                        .toMutableList()
                    //FIXME_clone
                    _listWithAll = _list.value.map { it.copy() }.toMutableList()
                }
            },
            failCallback = { code, msg ->

            }
        )
    }

    fun query(string: String) = viewModelScope.launch {
        //FIXME: 我希望能做到進階型的搜索，更方便找到商品名稱
        // 範例：
        // iPhone 12 Pro Max 256GB【下殺97折 送保護貼兌換券】
        // iPhone 12 Pro 256GB【下殺97折 送保護貼兌換券】
        // iPhone 12  128GB 紫色【新機預約 下殺98折 贈旅充】
        // iPhone 12  128GB 紫色【新機預約 下殺97折 送保護貼兌換券】
        // iPhone 12 Pro Max 128GB【下殺98折 送保護貼兌換券】
        // iPhone 12 Pro 128GB【送保護貼兌換券】
        // iPhone 12 256GB【下殺97折 送保護貼兌換券】
        // iPhone 12 128GB【下殺97折 送保護貼兌換券】

        val keywords = string.trim()
            .split("\\s+".toRegex())
            .filter { it.isNotBlank() }
            .map { it.lowercase() }

        if (string.isNotEmpty() && _listWithAll != null) {

            _list.value = _listWithAll!!.filter { commodity ->
                val name = commodity.martName.lowercase()
                keywords.all { name.contains(it) }
            }.toMutableList()
        } else {
            _list.value = _listWithAll!!
        }
    }
}
