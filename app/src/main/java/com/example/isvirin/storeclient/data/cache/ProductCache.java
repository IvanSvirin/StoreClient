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
package com.example.isvirin.storeclient.data.cache;


import com.example.isvirin.storeclient.data.entity.ProductEntity;

import java.util.List;

import rx.Observable;

/**
 * An interface representing a Product Cache.
 */
public interface ProductCache {
    /**
     * Gets an {@link Observable} which will emit a {@link ProductEntity}.
     *
     * @param id The Product id to retrieve data.
     */
    Observable<ProductEntity> get(final int id);

    /**
     * Puts and element into the cache.
     *
     * @param productEntity Element to insert in the cache.
     */
    void put(ProductEntity productEntity);

    /**
     * Checks if an element (Product) exists in the cache.
     *
     * @param id The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached(final int id);

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();



    Observable<List<ProductEntity>> getProducts();

    void putProducts(List<ProductEntity> productEntities);

    boolean isProductsCached();
}
