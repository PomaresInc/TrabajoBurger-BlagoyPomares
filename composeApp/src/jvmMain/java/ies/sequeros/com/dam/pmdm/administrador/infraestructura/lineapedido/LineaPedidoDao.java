package ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido;


import ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedido.PedidoDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LineaPedidoDao implements IDao<LineaPedido> {

    private DataBaseConnection conn;

    private final String table_name = "lin_ped";

    private final String selectall = "select * from " + table_name;


    private final String selectbyid = "select * from " + table_name + " where id=?";


    private final String deletebyid = "delete from " + table_name + " where id=?";

    private final String insert ="INSERT INTO " + table_name + " (id, unidades, price_unit, pedidoId, productoId) " +
        "VALUES (?, ?, ?, ?, ?)";

    private final String update = "UPDATE " + table_name + " SET unidades = ?, price_unit = ?, pedidoId = ?, productoId = ? " +
        "WHERE id = ?";

    private final String selectByPedidoId = "select * from " + table_name + " where pedidoId=?";
    private final String selectByProductoId = "select * from " + table_name + " where productoId=?";

    public LineaPedidoDao() {
    }

    public DataBaseConnection getConn() {
        return this.conn;
    }
    public void setConn(final DataBaseConnection conn) {
        this.conn = conn;
    }


    @Override
    public LineaPedido getById(String id) {
        LineaPedido pdl = null;
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pdl = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return pdl;
    }

    @Override
    public List<LineaPedido> getAll() {
        final List<LineaPedido> scl = new java.util.ArrayList<>();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectall);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LineaPedido tempo = registerToObject(rs);
                scl.add(tempo);
            }
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall);
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return scl;
    }

    @Override
    public void update(LineaPedido item) {
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(update);
            pst.setInt(1, item.getUnidades());
            pst.setDouble(2, item.getPriceUnit());
            pst.setString(3, item.getPedidoId());
            pst.setString(4, item.getProductoId());
            pst.setString(5, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + update + " | Params: [1]=" + item.getUnidades() + ", [2]=" + item.getPriceUnit() + ", [3]=" + item.getPedidoId() + ", [4]=" + item.getProductoId() + ", [5]=" + item.getId());
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void delete(LineaPedido item) {
        try {
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");
            final PreparedStatement pst = conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void removeById(String id) {
        try {
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + id + "]");
            final PreparedStatement pst = conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void insert(LineaPedido item) {
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(insert);
            pst.setString(1, item.getId());
            pst.setInt(2, item.getUnidades());
            pst.setDouble(3, item.getPriceUnit());
            pst.setString(4, item.getPedidoId());
            pst.setString(5, item.getProductoId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + insert + " | Params: [1]=" + item.getId() + ", [2]=" + item.getUnidades() + ", [3]=" + item.getPriceUnit() + ", [4]=" + item.getPedidoId() + ", [5]=" + item.getProductoId());
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<LineaPedido> findByPedidoId(String pedidoId) {
        final List<LineaPedido> result = new java.util.ArrayList<>();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectByPedidoId);
            pst.setString(1, pedidoId);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.add(registerToObject(rs));
            }
            pst.close();
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public List<LineaPedido> findByProductoId(String productoId) {
        final List<LineaPedido> result = new java.util.ArrayList<>();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectByProductoId);
            pst.setString(1, productoId);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.add(registerToObject(rs));
            }
            pst.close();
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public LineaPedido findByLineaPedidoId(String lineaPedidoId) {
        LineaPedido lp = null;
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, lineaPedidoId);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lp = registerToObject(rs);
            }
            pst.close();
        } catch (SQLException e) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return lp;
    }



    private LineaPedido registerToObject(final ResultSet r) {
        LineaPedido lp = null;
        try {
            lp = new LineaPedido(
                    r.getString("ID"),
                    r.getInt("UNIDADES"),
                    r.getDouble("PRICE_UNIT"),
                    r.getString("PEDIDOID"),
                    r.getString("PRODUCTOID")
            );
            return lp;
        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lp;
    }
}