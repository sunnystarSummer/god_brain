package com.god_brain.example.data.api.callBack;

import android.util.Log;


import androidx.annotation.NonNull;

import com.god_brain.example.data.api.post.RequestDataObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackOnResponseDataObject<T> implements Callback<RequestDataObject<T>>, CallBackDataObject<T> {

    @Override
    public void onResponse(@NonNull Call<RequestDataObject<T>> call, @NonNull Response<RequestDataObject<T>> response) {
        CallBack.log(response, response.body());
        int code = -1;

        if (response.isSuccessful()) {
            code = response.body().getCode();
            String message = response.body().getMsg();

//            if (code == 200) {
                T data = response.body().getData();
                success(code, message, data);
//            } else if (code == 301) {
//                tokenExpires(message);
//            } else {
//                fail(code, message);
//            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<RequestDataObject<T>> call, Throwable t) {
        final String TAG = "onFailure";
        String message = t.getMessage();

        try {
            fail(-1, message);
        } catch (Exception e) {
            Log.d(TAG, message);
        }
    }
}
