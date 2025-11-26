package ies.sequeros.com.dam.pmdm.administrador.infraestructura.producto;


import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

import java.util.List;

public class LineaPedidoDao implements IDao<Producto> {

    @Override
    public Producto getById(String id) {
        return null;
    }

    @Override
    public List<Producto> getAll() {
        return List.of();
    }

    @Override
    public void update(Producto item) {

    }

    @Override
    public void delete(Producto item) {

    }

    @Override
    public void insert(Producto item) {

    }
}