package com.kosgei.letscook.services;

import com.kosgei.letscook.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class EdamamService {

    public static void findRecipes(String category, Callback callback)
    {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.EDAMAM_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("app_id",Constants.EDAMAM_APP_ID);
        urlBuilder.addQueryParameter("app_key" ,Constants.EDAMAM_APP_KEY);
        urlBuilder.addQueryParameter(Constants.EDAMAM_CATEGORY_QUERY_PARAMETER,category);

        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }
}
