package com.god_brain.example.data.repository;

import com.god_brain.example.data.api.ApiHelper;
import com.god_brain.example.data.api.callBack.CallbackOnResponseDataList;
import com.god_brain.example.data.api.callBack.CallbackOnResponseDataObject;
import com.god_brain.example.data.api.post.RequestDataList;
import com.god_brain.example.data.api.service.ExampleService;
import com.god_brain.example.data.model.Commodity;
import com.god_brain.example.data.model.CommodityInfo;

public class MainRepository {


    private final ApiHelper apiHelper;

    private final ExampleService exampleService = ExampleService.getInstance();

    private static final MainRepository ourInstance = new MainRepository();

    public static MainRepository getInstance() {
        return ourInstance;
    }


    public MainRepository() {
        this.apiHelper = new ApiHelper();

    }

    //============================================================================

    public void getCommodityInfo(
            CallbackOnResponseDataList<Commodity> callback
    ) {
        apiHelper.fetchList(
                exampleService::getCommodityInfo,
                callback
        );
    }
    //============================================================================
}
