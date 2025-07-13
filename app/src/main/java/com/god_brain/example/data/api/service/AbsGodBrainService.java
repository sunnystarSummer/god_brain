package com.god_brain.example.data.api.service;

public class AbsGodBrainService<I> extends AbsRemoteService<I> {

    @Override
    public String URL() {
        return getURL();
    }

    public static String getURL() {

        //https://static-resrc.s3.amazonaws.com/app/test/marttest.json

        return "https://static-resrc.s3.amazonaws.com/app/";
    }

}
