package com.mitocode.service.interfaces;

import com.mitocode.dto.IProcedureSaleDTO;
import com.mitocode.dto.ProcedureSaleDTO;
import com.mitocode.model.Sale;

import java.util.List;
import java.util.Map;

public interface ISaleService extends ICRUD<Sale, Integer> {

    Sale getSaleMostExpensive(); // obtener la venta mas cara
    String getBestSellerUsername();
    Map<String, Long> getSaleCountBySeller();
    Map<String, Double> getMostSellerProduct();
    List<ProcedureSaleDTO> callProcedure1(); // usando clase de retorno (ProcedureSaleDTO) es la forma moderna
    List<IProcedureSaleDTO> callProcedure2(); // Forma antigua pero común
    List<ProcedureSaleDTO> callProcedure3(); // Forma muy legacy
    void callProcedure4();
}
