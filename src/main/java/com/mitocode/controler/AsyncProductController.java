package com.mitocode.controler;

import com.mitocode.model.Product;
import com.mitocode.service.impl.ProductAsyncServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@RestController
@RequestMapping("/async")
@RequiredArgsConstructor
public class AsyncProductController {

    private final ProductAsyncServiceImpl service;

    @GetMapping
    public List<Product> getAllProducts() throws InterruptedException, ExecutionException {
        CompletableFuture<List<Product>> c1 = service.getProducts1();
        CompletableFuture<List<Product>> c2 = service.getProducts2();
        CompletableFuture<List<Product>> c3 = service.getProducts3();

        CompletableFuture.allOf(c1, c2, c3).join();

        return Stream.of(c1.get(), c2.get(), c3.get())
                .flatMap(Collection::stream)
                .toList();
    }
}
