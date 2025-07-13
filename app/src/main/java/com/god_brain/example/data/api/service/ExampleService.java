package com.god_brain.example.data.api.service;

import androidx.annotation.NonNull;

import com.god_brain.example.data.api.ExampleApi;
import com.god_brain.example.data.model.Commodity;
import com.god_brain.example.data.api.post.RequestDataList;

import retrofit2.Call;
import retrofit2.Callback;

public class ExampleService extends AbsGodBrainService<ExampleApi> {

    @Override
    public String URL() {
        return super.URL() + "test/";
    }

    private static final ExampleService ourInstance = new ExampleService();

    public static ExampleService getInstance() {
        return ourInstance;
    }

    public void getCommodityInfo(@NonNull Callback<RequestDataList<Commodity>> callback) {
        Call<RequestDataList<Commodity>> call = getService().getCommodityInfo();
        call.enqueue(callback);
    }
}
