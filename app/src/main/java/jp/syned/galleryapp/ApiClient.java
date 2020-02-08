package jp.syned.galleryapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String base_url = "http://192.168.0.100/gallery/scripts/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiclient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
