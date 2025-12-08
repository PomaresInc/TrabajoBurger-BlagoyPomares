package ies.sequeros.com.dam.pmdm.administrador.infraestructura.producto;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.DependienteDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDao implements IDao<Producto>{
    private DataBaseConnection conn;
    private final String table_name = "producto";
    private final String selectall = "select * from " + table_name;

    private final String selectbyid = "select * from " + table_name + " where id=?";

    private final String findbyname = "select * from " + table_name + " where name=?";

    private final String deletebyid = "delete from " + table_name + " where id=?";

    private final String insert = "INSERT INTO " + table_name + " (id, name, price, image_path, description, enabled, categoriaId) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String update =
            "UPDATE " + table_name + " SET name = ?, price = ?, image_path = ?, description = ?, enabled = ?, is_admin = ? " +
                    "WHERE id = ?";

    public ProductoDao() {
    }

    public DataBaseConnection getConn() {
        return this.conn;
    }

    public void setConn(final DataBaseConnection conn) {
        this.conn = conn;
    }

    @Override
    public Producto getById(final String id) {
        Producto sp = null; // = new Producto();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectbyid + " | Parametros: [id=" + id + "]");
            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }

    public Producto findByName(final String name) {
        Producto sp = null;// = new Producto();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(findbyname);
            pst.setString(1, name);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + findbyname + " | Parametros: [name=" + name + "]");

            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }
    @Override
    public List<Producto> getAll() {
        final ArrayList<Producto> scl = new ArrayList<>();
        Producto tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = conn.getConnection().prepareStatement(selectall);
            } catch (final SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = registerToObject(rs);
                scl.add(tempo);
            }

            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall+ " | Parametros: ");

        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return scl;
    }

    @Override
    public void update(final Producto item) {

        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(update);
            pst.setString(1, item.getName());
            pst.setDouble(2, Double.parseDouble(item.getPrice()));// Convierto el String a Double
            pst.setString(3, item.getImagePath());
            pst.setString(4, item.getDescription());
            pst.setBoolean(5, item.getEnabled());
            pst.setString(6, item.getId());
            pst.setString(7, item.getCategoriaId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getName() +
                            ", [2]=" + item.getPrice() +
                            ", [3]=" + item.getImagePath() +
                            ", [4]=" + item.getDescription() +
                            ", [5]=" + item.getEnabled() +
                            ", [6]=" + item.getId() +
                            ", [7]=" + item.getCategoriaId() +
                            "]"
            );
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    @Override
    public void delete(final Producto item) {
        try {
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
            //Logger logger = Logger.getLogger(ProductoDao.class.getName());
            //logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void insert(final Producto item) {

        final PreparedStatement pst;
        try {
            pst = conn.getConnection().prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getName());
            pst.setDouble(2, Double.parseDouble(item.getPrice()));
            pst.setString(3, item.getImagePath());
            pst.setString(4, item.getDescription());
            pst.setBoolean(5, item.getEnabled());
            pst.setString(6, item.getId());
            pst.setString(7, item.getCategoriaId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getName() +
                            ", [2]=" + item.getPrice() +
                            ", [3]=" + item.getImagePath() +
                            ", [4]=" + item.getDescription() +
                            ", [5]=" + item.getEnabled() +
                            ", [6]=" + item.getId() +
                            ", [7]=" + item.getCategoriaId() +
                            "]"
            );

        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    //pasar de registro a objeeto
    private Producto registerToObject(final ResultSet r) {
        Producto sc =null;

        try {

            sc=new Producto(
                    r.getString("id"),
                    r.getString("name"),
                    String.valueOf(r.getDouble("price")), // Convierto el número de la BBDD a String para el objeto
                    r.getString("image_path"),
                    r.getString("description"),
                    r.getBoolean("enabled"),
                    r.getString("categoriaId"));
            return sc;
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sc;
    }
}
