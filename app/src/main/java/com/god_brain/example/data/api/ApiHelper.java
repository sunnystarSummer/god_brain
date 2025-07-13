package com.god_brain.example.data.api;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.god_brain.example.data.api.callBack.CallbackOnResponseDataList;
import com.god_brain.example.data.api.callBack.CallbackOnResponseDataObject;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ApiHelper {

    public static final String TAG = "ApiHelper";


    public ApiHelper() {

    }

    //=============================================================================
    //應用程式沒有檢查Device是否開啟鎖定機制。如果程式需要有鎖定機制的保護(避免Device被冒用)，則應用程式
    //應該對此功能進行驗證。

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void setLoading(Boolean value) {
        isLoading.postValue(value);
    }

    //=============================================================================

    private final MutableLiveData<Boolean> isTokenValid = new MutableLiveData<>(null);

    public LiveData<Boolean> isTokenValid() {
        return isTokenValid;
    }

    //=============================================================================

    public <T, R> void fetch(
            T request,
            BiConsumer<T, CallbackOnResponseDataObject<R>> api,
            CallbackOnResponseDataObject<R> callback
    ) {

        try {
            api.accept(request, new CallbackOnResponseDataObject<R>() {
                @Override
                public void success(Integer code, String msg, R result) {


                    if (callback != null) {
                        callback.success(code, msg, result);
                    }
                }

                @Override
                public void fail(Integer code, String msg) {


                    if (callback != null) {
                        callback.fail(code, msg);
                    }
                }

                @Override
                public void tokenExpires(String msg) {

                    if (callback != null) {
                        callback.tokenExpires(msg);
                    }
                    isTokenValid.postValue(false);
                }

            });
        } catch (Exception e) {

            Log.d(TAG, "Exception: " + e.getMessage());
        }

    }

    public <R> void fetch(
            Consumer<CallbackOnResponseDataObject<R>> api,
            CallbackOnResponseDataObject<R> callback
    ) {


        try {
            api.accept(new CallbackOnResponseDataObject<R>() {
                @Override
                public void success(Integer code, String msg, R result) {

                    if (callback != null) {
                        callback.success(code, msg, result);
                    }
                }

                @Override
                public void fail(Integer code, String msg) {

                    if (callback != null) {
                        callback.fail(code, msg);
                    }
                }

                @Override
                public void tokenExpires(String msg) {

                    if (callback != null) {
                        callback.tokenExpires(msg);
                    }
                    isTokenValid.postValue(false);
                }
            });
        } catch (Exception e) {

            Log.d(TAG, "Exception: " + e.getMessage());
        }

    }

    public <R> void fetchList(
            Consumer<CallbackOnResponseDataList<R>> api,
            CallbackOnResponseDataList<R> callback
    ) {


        try {
            api.accept(new CallbackOnResponseDataList<R>() {
                @Override
                public void success(Integer code, String msg, List<R> result) {

                    if (callback != null) {
                        callback.success(code, msg, result);
                    }
                }

                @Override
                public void fail(Integer code, String msg) {

                    if (callback != null) {
                        callback.fail(code, msg);
                    }
                }

                @Override
                public void tokenExpires(String msg) {

                    if (callback != null) {
                        callback.tokenExpires(msg);
                    }
                    isTokenValid.postValue(false);
                }
            });
        } catch (Exception e) {

            Log.d(TAG, "Exception: " + e.getMessage());
        }

    }

    // 你可以依照上面範例再加上 List<R> 的版本的 fetchWithToken
    @FunctionalInterface
    public interface TriConsumer<A, B, C> {
        void accept(A a, B b, C c);
    }

    @FunctionalInterface
    public interface QuadConsumer<A, B, C, D> {
        void accept(A a, B b, C c, D d);
    }
}

