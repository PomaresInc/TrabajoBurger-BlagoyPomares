package ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido;

import java.util.List;

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedido.PedidoDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;

public class BBDDRepositorioLineaPedidoJava {
    private final DataBaseConnection db;

    private LineaPedidoDao dao;

    public BBDDRepositorioLineaPedidoJava(DataBaseConnection connection) throws Exception {
        super();
        this.db = connection;
        dao = new LineaPedidoDao();
        dao.setConn(this.db);
    }

    public void add(LineaPedido item){
        this.dao.insert(item);
    }

    public boolean remove(String id){
        this.dao.removeById(id);
        return true;
    }

    public boolean delete(LineaPedido item){
        this.dao.delete(item);
        return true;
    }

    public boolean  update(LineaPedido item){
        this.dao.update(item);
        return true;
    }

    public List<LineaPedido> getAll() {
        return this.dao.getAll();
    }

    public LineaPedido getById(String id){
        return this.dao.getById(id);
    }

    public List<LineaPedido> findByPedidoId(String pedidoId) {
        return this.dao.findByPedidoId(pedidoId);
    }

    public List<LineaPedido> findByProductoId(String productoId) {
        return this.dao.findByProductoId(productoId);
    }

    public LineaPedido findByLineaPedidoId(String lineaPedidoId) {
        return this.dao.findByLineaPedidoId(lineaPedidoId);
    }
}

