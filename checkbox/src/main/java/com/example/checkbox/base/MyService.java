package com.example.checkbox.base;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MyService {

    @GET
    Observable<BaseResponse<List<ListData>>> get(@Url String url);
}
