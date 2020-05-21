package com.example.gojekdemo.network.main;

import com.example.gojekdemo.model.RepoClassModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface MainApi {

    // /posts?userId=1/
    @GET("repositories")
    Flowable<List<RepoClassModel>> getRepositories();

}