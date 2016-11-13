/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.isvirin.storeclient.presentation;

import android.app.Application;

import com.example.isvirin.storeclient.BuildConfig;
import com.example.isvirin.storeclient.domain.interactor.DefaultSubscriber;
import com.example.isvirin.storeclient.domain.interactor.GetBrandList;
import com.example.isvirin.storeclient.domain.interactor.GetProductList;
import com.example.isvirin.storeclient.presentation.internal.di.components.ApplicationComponent;
import com.example.isvirin.storeclient.presentation.internal.di.components.DaggerApplicationComponent;
import com.example.isvirin.storeclient.presentation.internal.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;


/**
 * Android Main Application
 */
public class AndroidApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();

        new GetBrandList(this.applicationComponent.brandRepository(), this.applicationComponent.threadExecutor(),
                this.applicationComponent.postExecutionThread()).execute(new DefaultSubscriber());

        new GetProductList(this.applicationComponent.productRepository(), this.applicationComponent.threadExecutor(),
                this.applicationComponent.postExecutionThread()).execute(new DefaultSubscriber());
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}
