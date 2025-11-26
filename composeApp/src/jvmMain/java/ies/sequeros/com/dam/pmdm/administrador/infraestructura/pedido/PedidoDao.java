package ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedido;


import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

import java.util.List;

public class PedidoDao implements IDao<Pedido> {


    @Override
    public Pedido getById(String id) {
        return null;
    }

    @Override
    public List<Pedido> getAll() {
        return List.of();
    }

    @Override
    public void update(Pedido item) {

    }

    @Override
    public void delete(Pedido item) {

    }

    @Override
    public void insert(Pedido item) {

    }
}