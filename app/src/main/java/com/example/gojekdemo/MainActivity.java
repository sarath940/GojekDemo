package com.example.gojekdemo;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.gojekdemo.main.MainViewModel;
import com.example.gojekdemo.main.RepoResource;
import com.example.gojekdemo.main.adapter.ReposRecyclerAdapter;
import com.example.gojekdemo.model.RepoClassModel;
import com.example.gojekdemo.util.RepoWorkerManager;
import com.example.gojekdemo.util.VerticalSpacingItemDecoration;
import com.example.gojekdemo.viewmodelfactory.ViewModelProviderFactory;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Flowable;

public class MainActivity extends DaggerAppCompatActivity {


    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    private ShimmerFrameLayout shimmerViewContainer;
    @Inject
    ReposRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, providerFactory).get(MainViewModel.class);
        recyclerView = findViewById(R.id.image_list);
        shimmerViewContainer = findViewById(R.id.shimmer_view_container);
        initRecyclerView();
        subscribeObervers();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .setRequiresStorageNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                RepoWorkerManager.class, 22, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance().enqueue(periodicWorkRequest);
    }

    private void subscribeObervers(){
        viewModel.observePosts().removeObservers(this);
        viewModel.observePosts().observe(this, listRepoResource -> {

                switch (listRepoResource.status){

                    case LOADING:{
                        showProgressBar(true);
                        break;
                    }

                    case SUCCESS:{
                        showProgressBar(false);
                        initRecyclerView();
                        adapter.setRepoClassModelArrayList(listRepoResource.data);
                        break;
                    }

                    case ERROR:{
                        showProgressBar(false);
                        break;
                    }

                }
            });
    }

    private void showProgressBar(boolean active) {
        if (active) {
            shimmerViewContainer.setVisibility(View.VISIBLE);
            shimmerViewContainer.startShimmerAnimation();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    shimmerViewContainer.stopShimmerAnimation();
                    shimmerViewContainer.setVisibility(View.GONE);
                }
            }, 500);

        }
    }

    private void initRecyclerView(){
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sort_by_stars:
                viewModel.observePosts().observe(this, listRepoResource -> {

                    switch (listRepoResource.status){
                        case SUCCESS:{
                            final List<RepoClassModel> repoClassModels = Flowable.fromIterable(listRepoResource.data)
                                    .sorted((x, y) -> Integer.compare(x.getStars(), y.getStars())).toList().blockingGet();
                            adapter.setRepoClassModelArrayList(repoClassModels);
                            break;
                        }

                    }
                });

                return true;

            case R.id.sort_by_name:{
                viewModel.observePosts().observe(this, listRepoResource -> {

                    switch (listRepoResource.status){
                        case SUCCESS:{
                            final List<RepoClassModel> repoClassModels = Flowable.fromIterable(listRepoResource.data)
                                    .sorted((x, y) -> x.getAuthor().compareTo(y.getAuthor())).toList().blockingGet();
                            adapter.setRepoClassModelArrayList(repoClassModels);
                            break;
                        }

                    }
                });

                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
