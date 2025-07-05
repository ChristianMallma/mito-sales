package com.mitocode.service.impl;

import com.mitocode.dto.IProcedureSaleDTO;
import com.mitocode.dto.ProcedureSaleDTO;
import com.mitocode.model.Sale;
import com.mitocode.model.SaleDetail;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.repository.interfaces.ISaleRepo;
import com.mitocode.service.interfaces.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor // Esto para solo acepte en la inyección aquellos que tienen "final"
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    private final ISaleRepo repo; // Al heredar JpaRepository, Spring asume que necesitas una instancia aquí para persistir en db

    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return repo;
    }

    @Override
    public Sale getSaleMostExpensive() {
        return repo.findAll()
                .stream()
                .max(Comparator.comparing(Sale::getTotal))
                .orElse(new Sale());
    }

    @Override
    public String getBestSellerUsername() {
         Map<String, Double> byUser = repo.findAll()
                .stream()
                .collect(Collectors.groupingBy(sale -> sale.getUser().getUsername(), Collectors.summingDouble(Sale::getTotal)));

        System.out.println(byUser);
//        return Collections.max(byUser.entrySet(), Map.Entry.comparingByValue()).getKey();
        return Collections.max(byUser.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

    @Override
    public Map<String, Long> getSaleCountBySeller() {
        return repo.findAll()
                .stream()
                .collect(Collectors.groupingBy(sale -> sale.getUser().getUsername(), Collectors.counting()));
    }

    @Override
    public Map<String, Double> getMostSellerProduct() {
        Stream<Sale> saleStream = repo.findAll().stream();

        // lsStream es esto: [ [det1,det2], [det3, det4], [det5, det6], .... ]
        Stream<List<SaleDetail>> lsStream = saleStream.map(Sale::getDetails);

        // Queremos aplanarlo a: [ det1, det2, det3, det4, det5, det6, ... ]
        Stream<SaleDetail> streamDetail = lsStream.flatMap(list -> list.stream()); // Collection::stream

         Map<String, Double> byProduct = streamDetail
                .collect(Collectors.groupingBy( d -> d.getProduct().getName(), Collectors.summingDouble(SaleDetail::getQuantity) ));

         return byProduct.entrySet()
                 .stream()
                 .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                 .collect(Collectors.toMap(
                         Map.Entry::getKey,
                         Map.Entry::getValue,
                         (oldValue, newValue) -> oldValue,
                         LinkedHashMap::new
                 ));
    }

    @Override
    public List<ProcedureSaleDTO> callProcedure1() {
        return repo.callProcedure1();
    }

    @Override
    public List<IProcedureSaleDTO> callProcedure2() {
        return repo.callProcedure2();
    }

    @Override
    public List<ProcedureSaleDTO> callProcedure3() {
        return repo.callProcedure3();
    }

    @Override
    public void callProcedure4() {
        repo.callProcedure4();
    }
}
