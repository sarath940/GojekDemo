package com.example.gojekdemo.main;


import com.example.gojekdemo.main.adapter.ReposRecyclerAdapter;
import com.example.gojekdemo.network.main.MainApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static ReposRecyclerAdapter provideAdapter() {
        return new ReposRecyclerAdapter();
    }

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }
}
