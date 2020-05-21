package com.example.gojekdemo.main.adapter;

import com.example.gojekdemo.di.ViewModelKey;
import com.example.gojekdemo.main.MainViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModlesModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);

}


