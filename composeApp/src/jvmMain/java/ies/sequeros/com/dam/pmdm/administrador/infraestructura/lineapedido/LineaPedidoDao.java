package ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido;


import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

import java.util.List;

public class LineaPedidoDao implements IDao<LineaPedido> {

    @Override
    public LineaPedido getById(String id) {
        return null;
    }

    @Override
    public List<LineaPedido> getAll() {
        return List.of();
    }

    @Override
    public void update(LineaPedido item) {

    }

    @Override
    public void delete(LineaPedido item) {

    }

    @Override
    public void insert(LineaPedido item) {

    }
}