package com.god_brain.example.ui.view_00_basic

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.god_brain.example.data.api.callBack.CallbackOnResponseDataList
import com.god_brain.example.data.api.callBack.CallbackOnResponseDataObject
import com.god_brain.example.data.repository.MainRepository
import java.lang.Boolean
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer
import java.util.function.Consumer
import kotlin.Exception
import kotlin.Int
import kotlin.NullPointerException
import kotlin.String


open class BasicPresenter<V : ViewBinding?>(
    context: Context,
    binding: V?,
    protected val repository: MainRepository = MainRepository.getInstance() // Assuming MainRepository is a singleton
) {

    protected fun <R> processApiTokenExpires(
        msg: String?,
        future: CompletableFuture<R?>
    ) {
//        hideFullScreenDialog();
//        val errorDialog: Dialog = MaterialAlertDialogBuilder(context)
//            .setMessage(msg)
//            .setCancelable(false)
//            .setPositiveButton(
//                "確認",
//                DialogInterface.OnClickListener { d: DialogInterface?, which: Int ->
//                    d!!.dismiss()
//                })
//            .create()

        future.completeExceptionally(Exception(msg ?: "Token Expires"))
    }

    private fun <R> processApiSuccess(
        code: Int?, msg: String?, result: R?,
        future: CompletableFuture<R?>,
        successCallback: Consumer<R?>
    ) {
        if (result != null) {
            future.complete(result)
            successCallback.accept(result)
        } else {
            future.completeExceptionally(NullPointerException("Result is null"))
        }
    }

    private fun <R> processApiSuccess(
        code: Int?, msg: String?, result: MutableList<R?>?,
        future: CompletableFuture<MutableList<R?>?>,
        successCallback: Consumer<MutableList<R?>?>
    ) {
        if (result != null) {
            val list: MutableList<R?> = ArrayList<R?>()
            for (item in result) {
                if (item != null) list.add(item)
            }
            future.complete(list)
            successCallback.accept(list)
        } else {
            future.completeExceptionally(NullPointerException("Result is null"))
        }
    }

    private fun <R> processApiFail(
        code: Int?, msg: String?,
        future: CompletableFuture<R?>,
        failCallback: BiConsumer<Int?, String?>
    ) {
        future.completeExceptionally(Exception(msg ?: "Unknown error"))
        failCallback.accept(code, msg)
    }

    fun <R> processSync(
        api: Consumer<CallbackOnResponseDataObject<R?>?>,
        successCallback: Consumer<R?>,
        failCallback: BiConsumer<Int?, String?>
    ): CompletableFuture<R?> {
        val future = CompletableFuture<R?>()

        api.accept(object : CallbackOnResponseDataObject<R?>() {
            override fun success(code: Int?, msg: String?, result: R?) {
                processApiSuccess<R?>(code, msg, result, future, successCallback)
            }

            override fun fail(code: Int?, msg: String?) {
                processApiFail<R?>(code, msg, future, failCallback)
            }

            override fun tokenExpires(msg: String?) {
                processApiTokenExpires<R?>(msg, future)
            }
        })

        return future
    }

    fun <R> processSyncOfList(
        api: Consumer<CallbackOnResponseDataList<R?>?>,
        successCallback: Consumer<MutableList<R?>?>,
        failCallback: BiConsumer<Int?, String?>
    ): CompletableFuture<MutableList<R?>?> {
        val future = CompletableFuture<MutableList<R?>?>()


        api.accept(object : CallbackOnResponseDataList<R?>() {
            override fun success(code: Int?, msg: String?, result: MutableList<R?>?) {
                processApiSuccess<R?>(code, msg, result, future, successCallback)
            }

            override fun fail(code: Int?, msg: String?) {
                processApiFail<MutableList<R?>?>(code, msg, future, failCallback)
            }

            override fun tokenExpires(msg: String?) {
                processApiTokenExpires<MutableList<R?>?>(msg, future)
            }
        })

        return future
    }
}
