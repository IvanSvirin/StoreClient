package com.example.isvirin.storeclient.data.event;


public class BrandsLoadedEvent {
    public final boolean isSuccess;
    public final String message;

    public BrandsLoadedEvent(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
