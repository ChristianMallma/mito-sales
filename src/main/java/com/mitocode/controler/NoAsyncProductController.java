package com.mitocode.controler;

import com.mitocode.model.Product;
import com.mitocode.service.impl.ProductNoAsyncServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/noasync")
@RequiredArgsConstructor
public class NoAsyncProductController {

    private final ProductNoAsyncServiceImpl service;

    @GetMapping
    public List<Product> getAllProducts() throws InterruptedException {
        List<Product> list1 = service.getProducts1();
        List<Product> list2 = service.getProducts2();
        List<Product> list3 = service.getProducts3();

        List<Product> list4 = new ArrayList<>();
        list4.addAll(list1);
        list4.addAll(list2);
        list4.addAll(list3);

        return list4;
    }
}
