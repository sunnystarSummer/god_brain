package com.god_brain.example.data.api.service;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AbsRemoteService<Service> {


    public abstract String URL();

    public AbsRemoteService() {

    }

    protected RequestBody bodyJSON(Object object) {
        final String TAG = "bodyJSON";
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        Expose expose = f.getAnnotation(Expose.class);
                        // 如果有 @Expose，並且 serialize = false，就排除
                        return expose != null && !expose.serialize();// 其他欄位都正常序列化
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false; // 不跳過任何 class
                    }
                })
                .setPrettyPrinting().create();

        String json = gson.toJson(object);
        return RequestBody.create(json, MediaType.parse("application/json"));
    }

    protected RequestBody bodyFORM(String text) {
        return RequestBody.create(text, MultipartBody.FORM);
    }

    public Service getService() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(300, TimeUnit.SECONDS);
        HttpUrl newBaseUrl = HttpUrl.parse(URL());
        // 重建新的HttpUrl，修改需要修改的url部分
        HttpUrl newFullUrl = newBaseUrl
                .newBuilder()// 更換網絡協議
                .scheme(newBaseUrl.scheme())// 更換主機名
                .host(newBaseUrl.host())// 更換端口
                .port(newBaseUrl.port())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(newFullUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
        Class<Service> tClass = (Class<Service>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return retrofit.create(tClass);
    }

    public Service getService(String accessToken) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(300, TimeUnit.SECONDS);
        if (accessToken != null) {
            okHttpClient.addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Token", accessToken)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            }).build();
        }
        HttpUrl newBaseUrl = HttpUrl.parse(URL());
        // 重建新的HttpUrl，修改需要修改的url部分
        HttpUrl newFullUrl = newBaseUrl
                .newBuilder()// 更換網絡協議
                .scheme(newBaseUrl.scheme())// 更換主機名
                .host(newBaseUrl.host())// 更換端口
                .port(newBaseUrl.port())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(newFullUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();

        Class<Service> tClass = (Class<Service>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return retrofit.create(tClass);
    }

}
