package com.mitocode.repository.interfaces;

import com.mitocode.dto.IProcedureSaleDTO;
import com.mitocode.dto.ProcedureSaleDTO;
import com.mitocode.model.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface ISaleRepo extends IGenericRepo<Sale, Integer> {

    @Query(value = "select * from fn_sales()",  nativeQuery = true)
    List<ProcedureSaleDTO> callProcedure1();

    @Query(value = "select * from fn_sales()",  nativeQuery = true)
    List<IProcedureSaleDTO> callProcedure2();

    @Query(name = "Sale.fn_sales",  nativeQuery = true)
    List<ProcedureSaleDTO> callProcedure3();

    @Procedure(procedureName = "pr_sales")
    void callProcedure4();

    /*
    * Código de la función en PostgreSQL
    * =================================
    create or replace function fn_sales()
    returns table(
        quantityFn INT,
        datetimeFn TEXT
    )
    as $$
    declare
        var_r record;
    begin
    for var_r IN(
        select (count(*)::int) as quantity, to_char(s.date_time, 'dd/MM/yyyy') as datetime from sale s
        group by to_char(s.date_time, 'dd/MM/yyyy') order by to_char(s.date_time, 'dd/MM/yyyy') asc
        )
    loop
        quantityFn := var_r.quantity;
        datetimeFn := var_r.datetime;
        return NEXT;
    end loop;
    end; $$
    language 'plpgsql';

    select * from fn_sales();
    */

    /*
    * Código del procedure en PostgreSQL:
    * ==================================
    create or replace procedure pr_sales()
    language plpgsql
    as $$
    begin
        update sale set enabled = true;
    end;$$

    call pr_sales();
    */

}
