package com.mitocode.service.impl;

import com.mitocode.model.Product;
import com.mitocode.repository.interfaces.IProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductAsyncServiceImpl {
    private final IProductRepo repo;

    @Async
    public CompletableFuture<List<Product>> getProducts1() throws InterruptedException  {
        Thread.sleep(1000);
        List<Product> products = repo.findAll();

        return CompletableFuture.completedFuture(products);
    }

    @Async
    public CompletableFuture<List<Product>> getProducts2() throws InterruptedException  {
        Thread.sleep(3000);
        List<Product> products = repo.findAll();

        return CompletableFuture.completedFuture(products);
    }

    @Async
    public CompletableFuture<List<Product>> getProducts3() throws InterruptedException  {
        Thread.sleep(2000);
        List<Product> products = repo.findAll();

        return CompletableFuture.completedFuture(products);
    }
}
