package com.example.gojekdemo.di;


import com.example.gojekdemo.MainActivity;
import com.example.gojekdemo.main.MainModule;
import com.example.gojekdemo.main.MainScope;
import com.example.gojekdemo.main.adapter.MainViewModlesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @MainScope
    @ContributesAndroidInjector(
            modules = {MainViewModlesModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();

}
