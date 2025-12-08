package ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedido;


import ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.DependienteDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PedidoDao implements IDao<Pedido> {

    private DataBaseConnection conn;

    private final String table_name = "pedido";

    private final String selectall = "select * from " + table_name;


    private final String selectbyid = "select * from " + table_name + " where id=?";


    private final String deletebyid = "delete from " + table_name + " where id=?";

    private final String insert = "INSERT INTO " + table_name + " (id, total, enregado, client_name, dependienteId) " +
        "VALUES (?, ?, ?, ?, ?)";

    private final String update =
        "UPDATE " + table_name + " SET total = ?, enregado = ?, client_name = ?, dependienteId = ? " +
            "WHERE id = ?";

    public PedidoDao() {
    }

    public DataBaseConnection getConn() {
        return this.conn;
    }

    public void setConn(final DataBaseConnection conn) {
        this.conn = conn;
    }



    @Override
    public Pedido getById(String id) {
        Pedido pdl = null;
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pdl = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
        } catch (SQLException e) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return pdl;
    }



    @Override
    public List<Pedido> getAll() {
        final List<Pedido> pedidos = new java.util.ArrayList<>();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectall);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pedidos.add(registerToObject(rs));
            }
            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall + " | Parametros: ");
        } catch (SQLException e) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return pedidos;
    }

    @Override
    public void update(Pedido item) {
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(update);
            pst.setDouble(1, item.getTotal());
            pst.setBoolean(2, item.getEnregado());
            pst.setString(3, item.getClient_name());
            pst.setString(4, item.getDependienteId());
            pst.setString(5, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getTotal() +
                            ", [2]=" + item.getEnregado() +
                            ", [3]=" + item.getClient_name() +
                            ", [4]=" + item.getDependienteId() +
                            ", [5]=" + item.getId() +
                            "]"
            );
        } catch (final SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Pedido item) {
        try {
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");
            final PreparedStatement pst = conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void insert(Pedido item) {
        final PreparedStatement pst;
        try {
            pst = conn.getConnection().prepareStatement(insert, java.sql.Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getId());
            pst.setDouble(2, item.getTotal());
            pst.setBoolean(3, item.getEnregado());
            pst.setString(4, item.getClient_name());
            pst.setString(5, item.getDependienteId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + insert +
                            " | Params: [1]=" + item.getId() +
                            ", [2]=" + item.getTotal() +
                            ", [3]=" + item.getEnregado() +
                            ", [4]=" + item.getClient_name() +
                            ", [5]=" + item.getDependienteId() +
                            "]"
            );
        } catch (SQLException e) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    private Pedido registerToObject(final ResultSet r) {
        Pedido sc = null;
        try {
            sc = new Pedido(
                r.getString("ID"),
                r.getString("FECHA"),
                r.getDouble("TOTAL"),
                r.getBoolean("ENREGADO"),
                r.getString("CLIENT_NAME"),
                r.getString("DEPENDIENTEID")
            );
            return sc;
        } catch (final SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sc;
    }

    public boolean removeById(String id) {
        try {
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + id + "]");
            final PreparedStatement pst = conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, id);
            int affected = pst.executeUpdate();
            pst.close();
            return affected > 0;
        } catch (SQLException e) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
