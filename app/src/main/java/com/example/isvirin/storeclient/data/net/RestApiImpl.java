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
package com.example.isvirin.storeclient.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.example.isvirin.storeclient.data.entity.CategoryEntity;
import com.example.isvirin.storeclient.data.entity.ProductEntity;
import com.example.isvirin.storeclient.data.entity.UserEntity;
import com.example.isvirin.storeclient.data.entity.mapper.EntityJsonMapper;
import com.example.isvirin.storeclient.data.exception.NetworkConnectionException;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.net.MalformedURLException;
import java.util.List;

import rx.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    private final EntityJsonMapper entityJsonMapper;

    /**
     * Constructor of the class
     *
     * @param context              {@link android.content.Context}.
     * @param userEntityJsonMapper {@link EntityJsonMapper}.
     */
    public RestApiImpl(Context context, EntityJsonMapper userEntityJsonMapper) {
        if (context == null || userEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.entityJsonMapper = userEntityJsonMapper;
    }

    @RxLogObservable
    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseUserEntities = getUserEntitiesFromApi();
                    if (responseUserEntities != null) {
                        subscriber.onNext(entityJsonMapper.transformUserEntityCollection(
                                responseUserEntities));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    @RxLogObservable
    @Override
    public Observable<UserEntity> userEntityById(final int userId) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseUserDetails = getUserDetailsFromApi(userId);
                    if (responseUserDetails != null) {
                        subscriber.onNext(entityJsonMapper.transformUserEntity(responseUserDetails));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    @RxLogObservable
    @Override
    public Observable<List<ProductEntity>> productEntityList() {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseProductEntities = getProductEntitiesFromApi();
                    if (responseProductEntities != null) {
                        subscriber.onNext(entityJsonMapper.transformProductEntityCollection(responseProductEntities));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    @RxLogObservable
    @Override
    public Observable<List<CategoryEntity>> categoryEntityList() {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseCategoryEntities = getCategoryEntitiesFromApi();
                    if (responseCategoryEntities != null) {
                        subscriber.onNext(entityJsonMapper.transformCategoryEntityCollection(responseCategoryEntities));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    @RxLogObservable
    @Override
    public Observable<ProductEntity> productEntityById(int id) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseProductById = getProductByIdFromApi(id);
                    if (responseProductById != null) {
                        subscriber.onNext(entityJsonMapper.transformProductEntity(responseProductById));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    private String getUserEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_USER_LIST).requestSyncCall();
    }

    private String getProductEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_PRODUCTS).requestSyncCall();
    }

    private String getCategoryEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_CATEGORIES).requestSyncCall();
    }

    private String getUserDetailsFromApi(int userId) throws MalformedURLException {
        String apiUrl = API_URL_GET_USER_DETAILS + userId + ".json";
        return ApiConnection.createGET(apiUrl).requestSyncCall();
    }

    private String getProductByIdFromApi(int id) throws MalformedURLException {
        String apiUrl = API_URL_GET_PRODUCT + id + "}";
        return ApiConnection.createGET(apiUrl).requestSyncCall();
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        return isConnected;
    }
}
