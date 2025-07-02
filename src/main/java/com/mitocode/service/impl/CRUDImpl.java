package com.mitocode.service.impl;

import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.service.interfaces.ICRUD;

import java.lang.reflect.Method;
import java.util.List;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    // Generar un método que nos devuelva un repo, dependiendo de quien lo invoque
    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T save(T t) throws Exception {
        return getRepo().save(t);
    }

    @Override
    public T update(ID id, T t) throws Exception {
        // Java Reflection
        // t.setIdCategory(id); ejemplo de lo que queremos lograr, ya que genéricos no tienen el setALGO
        String className = t.getClass().getSimpleName();

        // setIDENTIDAD
        String methodName = "setId" + className;
        Method setIdMethod = t.getClass().getMethod(methodName, id.getClass()); // getMethod(metodo, parametro)
        setIdMethod.invoke(t, id);

        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID not found "+ id));
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        // orElse(null) se usó de momento, luego se cambió con el uso de exceptions
        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID not found "+ id));
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID not found "+ id));
        getRepo().deleteById(id);
    }
}
