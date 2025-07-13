package com.god_brain.example.ui.view_01_store_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

import com.god_brain.example.ViewModelFactory
import com.god_brain.example.data.repository.MainRepository
import com.god_brain.example.databinding.Fragment01StoreListBinding

//ToDo: 2025-07-13_Jason
// 第一頁為賣場列表頁：取得 api 後組顯示此頁面，需要把資料儲存，然後顯示出來，
// 搜尋 martName 需要過濾資料，「加入最愛」及「加入購物車」icon 請隨意找按鈕取代, 無點擊
// 作用, 每一個賣場區塊被點擊後, 會跳至該第二頁賣場的細節頁:

class StoreListFragment : Fragment() {

    private var _binding: Fragment01StoreListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = Fragment01StoreListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 嵌入 ComposeView 的使用方式
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

                StoreListScreenComposeUI()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreListScreenComposeUI(
    repository: MainRepository = MainRepository.getInstance(),
    factory: ViewModelProvider.Factory = ViewModelFactory(repository),
    viewModel: StoreListViewModel = viewModel(
        factory = factory
    ),
) {
    val commodityInfo by viewModel.commodityInfo.collectAsState()

    Box {
        StoreListScaffoldComposeUI()
        if (commodityInfo != null) {
            StoreDetailScaffoldComposeUI(
                commodity = commodityInfo!!,
                back = {
                    viewModel.setCommodityInfo(null)
                }
            )
        }
    }
}
