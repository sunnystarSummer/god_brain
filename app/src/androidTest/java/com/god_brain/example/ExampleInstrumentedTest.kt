package com.god_brain.example

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.god_brain.example.data.api.ApiHelper
import com.god_brain.example.data.api.ExampleApi
import com.god_brain.example.data.api.callBack.CallbackOnResponseDataList
import com.god_brain.example.data.model.Commodity
import com.god_brain.example.data.api.service.ExampleService
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.god_brain.example", appContext.packageName)
    }

    @Test
    fun getCommodityInfo() = runTest {

        val exampleService = ExampleService.getInstance()

        val response = ApiHelper().run {
            fetchList(
                exampleService::getCommodityInfo,
                object : CallbackOnResponseDataList<Commodity>() {
                    override fun success(
                        code: Int?,
                        msg: String?,
                        result: List<Commodity?>?
                    ) {
                        assertTrue(result != null)
                    }

                    override fun fail(code: Int?, msg: String?) {

                    }

                    override fun tokenExpires(msg: String?) {

                    }

                }
            )
        }

    }
}