package com.example.gojekdemo.main;

import android.util.Log;

import com.example.gojekdemo.model.RepoClassModel;
import com.example.gojekdemo.network.main.MainApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private final MainApi mainApi;

    private MediatorLiveData<RepoResource<List<RepoClassModel>>> posts;

    @Inject
    public MainViewModel(MainApi mainApi) {
        this.mainApi = mainApi;
        Log.d(TAG, "MainViewModel: viewmodel is working...");
    }

    public LiveData<RepoResource<List<RepoClassModel>>> observePosts(){

            posts = new MediatorLiveData<>();
            posts.setValue(RepoResource.loading(null));

            final LiveData<RepoResource<List<RepoClassModel>>> source = LiveDataReactiveStreams.fromPublisher(

                    mainApi.getRepositories()

                            .onErrorReturn(throwable -> {
                                Log.e(TAG, "apply: ", throwable);
                                RepoClassModel post = new RepoClassModel();
                                post.setAuthor("");
                                ArrayList<RepoClassModel> posts = new ArrayList<>();
                                posts.add(post);
                                return posts;
                            })

                            .map(posts -> RepoResource.success(posts))

                            .subscribeOn(Schedulers.io())
            );

        return source;
    }


}




















