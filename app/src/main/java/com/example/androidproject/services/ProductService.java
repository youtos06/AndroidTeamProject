package com.example.androidproject.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;


import androidx.annotation.Nullable;

import com.example.androidproject.Repo;


public class ProductService extends Service {
    //Repo productRepo;
    HandlerThread handlerThread;
    Handler handler;
    IBinder binder;
    @Override
    public void onCreate() {

        super.onCreate();
        handlerThread = new HandlerThread("productServiceThread");
        binder = new ProductServiceBinder();
        handlerThread = new HandlerThread("background thread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        binder= new ProductServiceBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public void runOnBackThread(Runnable runnable){
        handler.post(runnable);
    }
    //    public void addProduct(Product product){
//         runOnBackThread(()->{
//             try {
//                 System.out.println("from add product " +Thread.currentThread().getName());
//                 Thread.sleep(5000);
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         });
//     }
//     public void addProductToFav(Product product){
//        runOnBackThread(()->productRepo.addProductToFav(product));
//     }
//     public void deleteProductFromFav(Product product){
//        runOnBackThread(()->productRepo.deleteProductFromFav());
//     }
//     public void getProducts(){
//        runOnBackThread(()->productRepo.loadProducts());
//     }
//
    public class ProductServiceBinder extends Binder {
        public ProductService getService(){
            return ProductService.this;
        }
    }

}


