package ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedido;

import java.util.List;

import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;

public class BBDDRepositorioPedidoJava {
    private final DataBaseConnection db;

    private PedidoDao dao;

    public BBDDRepositorioPedidoJava(DataBaseConnection connection) throws Exception {
        super();
        this.db = connection;
        dao = new PedidoDao();
        dao.setConn(this.db);
    }

    public void add(Pedido item){
        this.dao.insert(item);
        // Forzar flush de la conexión para asegurar que el pedido se persiste
        try {
            if (this.db.getConnection() != null && !this.db.getConnection().isClosed()) {
                // No hacer nada, el autocommit ya está activo por defecto
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean remove(String id){
        this.dao.removeById(id);
        return true;
    }

    public boolean  update(Pedido item){
        this.dao.update(item);
        return true;
    }

    public List<Pedido> getAll() {
        return this.dao.getAll();
    }

    public Pedido getById(String id){
        return this.dao.getById(id);
    }

    public void close() {
        try {
            if (this.db != null) {
                this.db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}