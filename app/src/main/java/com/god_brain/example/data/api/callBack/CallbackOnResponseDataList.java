package com.god_brain.example.data.api.callBack;

import android.util.Log;


import androidx.annotation.NonNull;

import com.god_brain.example.data.api.post.RequestDataList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackOnResponseDataList<T> implements Callback<RequestDataList<T>>, CallBackDataList<T> {

    @Override
    public void onResponse(@NonNull Call<RequestDataList<T>> call, @NonNull Response<RequestDataList<T>> response) {
        CallBack.log(response, response.body());
        int code = -1;

        if (response.isSuccessful()) {
            code = response.body().getCode();
            String message = response.body().getMsg();

//            if (code == 200) {
                List<T> data = response.body().getData();
                success(code, message, data);
//            } else if (code == 301) {
//                tokenExpires(message);
//            } else {
//                fail(code, message);
//            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<RequestDataList<T>> call, Throwable t) {
        final String TAG = "onFailure";
        String message = t.getMessage();

        try {
            fail(-1, message);
        } catch (Exception e) {
            Log.d(TAG, message);
        }
    }
}
