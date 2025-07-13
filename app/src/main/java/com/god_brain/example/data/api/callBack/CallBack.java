package com.god_brain.example.data.api.callBack;

import android.util.Log;

import com.god_brain.example.data.api.post.AbsRequestData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Response;

public interface CallBack<T> {

    void success(Integer code, String msg, T result);

    void fail(Integer code, String msg);

    void tokenExpires(String msg);

    static <T extends AbsRequestData> void log(Response<T> response, T body) {
        String TAG = "Callback";
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("┌───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        stringBuilder.append("\n");

        final okhttp3.Response responseRaw = response.raw();
        final Request request = responseRaw.request();
        final RequestBody requestBody = request.body();

        stringBuilder.append("│ #0 method= ").append(responseRaw.request().method());
        stringBuilder.append("\n");

        final String url = responseRaw.request().url().toString();
        stringBuilder.append("│ #1 url= ").append(url);
        stringBuilder.append("\n");

        stringBuilder.append("│ #2 Token= ").append(responseRaw.request().headers().get("Token"));
        stringBuilder.append("\n");

        if (url.contains("File/Upload")) {

        } else if (requestBody != null) {
            try {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                String bodyString = buffer.readUtf8();

                stringBuilder.append("│ #3 Request=");
                stringBuilder.append("\n");

                stringBuilder.append("│").append(bodyString.replace("\n", "\n│"));
                stringBuilder.append("\n");

            } catch (IOException e) {

                stringBuilder.append("│ #3 Failed to read request body").append(e.getMessage());
                stringBuilder.append("\n");
            }
        } else {
            Log.d(TAG, "Request body is null");
        }

        stringBuilder.append("├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
        stringBuilder.append("\n");
        stringBuilder.append("│ #4 Response:");
        stringBuilder.append("\n");
        stringBuilder.append("│    code=").append(body.getCode());
        stringBuilder.append("\n");
        stringBuilder.append("│    msg=").append(body.getMsg());
        stringBuilder.append("\n");


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String json = gson.toJson(body.getData());

        stringBuilder.append("│    data=\n│").append(json.replace("\n", "\n│"));
        stringBuilder.append("\n");
        stringBuilder.append("└───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");

        Log.d(TAG, stringBuilder.toString());
    }
}
