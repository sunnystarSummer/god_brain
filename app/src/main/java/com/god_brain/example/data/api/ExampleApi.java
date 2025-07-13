package com.god_brain.example.data.api;

import com.god_brain.example.data.api.post.RequestDataList;
import com.god_brain.example.data.api.post.RequestDataObject;
import com.god_brain.example.data.model.Commodity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExampleApi {
    @GET("marttest.json")
    Call<RequestDataList<Commodity>> getCommodityInfo();
}
