package com.god_brain.example.data.api.post;

import com.google.gson.annotations.SerializedName;

public abstract class AbsRequestData<T> {

    @SerializedName("CODE")
    protected int _CODE = -1;
    @SerializedName("MSG")
    protected String _MSG;
    @SerializedName("DATA")
    protected T _DATA;

    @SerializedName("code")
    protected int _code = -1;
    @SerializedName("msg")
    protected String _msg;
    @SerializedName("data")
    protected T _data;

    public int getCode() {
        if (_CODE != -1) {
            return _CODE;
        } else if (_code != -1) {
            return _code;
        }

        return -1;
    }

    public String getMsg() {
        if (_MSG != null) {
            return _MSG;
        } else if (_msg != null) {
            return _msg;
        }

        return null;
    }

    public T getData() {
        if (_DATA != null) {
            return _DATA;
        } else if (_data != null) {
            return _data;
        }

        return null;
    }
}
