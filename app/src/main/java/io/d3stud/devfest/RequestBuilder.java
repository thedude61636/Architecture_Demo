package io.d3stud.devfest;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// i didn't create this class so i'm not commenting it :D
public class RequestBuilder {

    private int connectTimeout, readTimeout;
    private String url;

    private RequestBuilder() {
    }

    public static RequestBuilder init() {
        RequestBuilder builder = new RequestBuilder();
        // default values
        builder.connectTimeout = 2000;
        builder.readTimeout = 8000;
        builder.url = Consts.API_URL;
        return builder;
    }

    public RequestBuilder connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public RequestBuilder readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }


    public RequestBuilder url(String url) {
        this.url = url;
        return this;
    }


    public Retrofit build() {


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor);

        OkHttpClient client = clientBuilder
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
