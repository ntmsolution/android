package com.webtech.retrofitdemo;

import com.webtech.retrofitdemo.modal.Category;
import com.webtech.retrofitdemo.modal.Product;
import com.webtech.retrofitdemo.modal.SubCategory;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {

    String APIKEY = "8B89B11A17E191149D5382B338602B6E";
    String BASEPATH = "http://interierdesign.webtechinfoway.pw/feed/";
    String IMGPATH = "http://interierdesign.webtechinfoway.pw/uploads/";

    @FormUrlEncoded
    @POST("Category")
    Call<Category> getCategory(@Field("api_key") String apikey);

    @FormUrlEncoded
    @POST("Subcategory/bycatid")
    Call<SubCategory> getSubCategory(@Field("api_key") String apikey, @Field("cat_id") String cat_id);

    @FormUrlEncoded
    @POST("Interier/bysubcatid")
    Call<Product> getProduct(@Field("api_key") String apikey, @Field("subcat_id") String subcat_id);
}
