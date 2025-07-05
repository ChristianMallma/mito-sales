package com.mitocode.service.interfaces;

import com.mitocode.model.Sale;

import java.util.Map;

public interface ISaleService extends ICRUD<Sale, Integer> {

    Sale getSaleMostExpensive(); // obtener la venta mas cara
    String getBestSellerUsername();
    Map<String, Long> getSaleCountBySeller();
    Map<String, Double> getMostSellerProduct();

}
