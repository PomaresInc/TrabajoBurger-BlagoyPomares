package ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.DependienteDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

public class CategoriaDao implements IDao<Categoria> {

    private DataBaseConnection conn;

    private final String table_name = "categoria";

    private final String selectall = "select * from " + table_name;

    private final String selectbyid = "select * from " + table_name + " where id=?";

    private final String findbyname = "select * from " + table_name + " where name=?";

    private final String deletebyid = "delete from " + table_name + " where id=?";

    private final String insert = "INSERT INTO " + table_name + " (id, name, description, image_path, enabled) " +
            "VALUES (?, ?, ?, ?, ?)";

    private final String update =
            "UPDATE " + table_name + " SET name = ?, description = ?, image_path = ?, enabled = ? " +
                    "WHERE id = ?";
    public CategoriaDao() {
    }

    public DataBaseConnection getConn() {
        return this.conn;
    }

    public void setConn(final DataBaseConnection conn) {
        this.conn = conn;}

    @Override
    public Categoria getById(String id) {
        Categoria ct = null;
        try{
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ct = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info("Ejecutando SQL: " + selectbyid + " | Parametros: [id=" + id + "]");
            return ct;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

        @Override
    public List<Categoria> getAll() {
        final ArrayList<Categoria> scl = new ArrayList<>();
        Categoria tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = conn.getConnection().prepareStatement(selectall);
            } catch (final SQLException ex) {
                Logger.getLogger(DependienteDao.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = registerToObject(rs);
                scl.add(tempo);
            }

            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall+ " | Parametros: ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scl;
    }

    @Override
    public void update(final Categoria item) {
        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(update);
            pst.setString(1, item.getName());
            pst.setString(2, item.getDescription());
            pst.setString(3, item.getImagePath());
            pst.setBoolean(4, item.getEnabled());
            pst.setString(5, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getName() +
                            ", [2]=" + item.getDescription() +
                            ", [3]=" + item.getImagePath() +
                            ", [4]=" + item.getEnabled() +
                            ", [5]=" + item.getId() +
                    "]"
            );
        } catch (final SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void delete(Categoria item) {
        try{
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(deletebyid);

            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    @Override
    public void insert(Categoria item) {
        final PreparedStatement pst;
        try {
            pst = conn.getConnection().prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getId());
            pst.setString(2, item.getName());
            pst.setString(3, item.getDescription());
            pst.setString(4, item.getImagePath());
            pst.setBoolean(5, item.getEnabled());

            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info(() ->
                    "Ejecutando SQL: " + insert +
                            " | Params: [1]=" + item.getId() +
                            ", [2]=" + item.getName() +
                            ", [3]=" + item.getDescription() +
                            ", [4]=" + item.getImagePath() +
                            ", [5]=" + item.getEnabled() +
                            "]"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Categoria findByName(final String name) {
        Categoria ct = null;
        try{
            final PreparedStatement pst = conn.getConnection().prepareStatement(findbyname);
            pst.setString(1, name);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ct = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(CategoriaDao.class.getName());
            logger.info("Ejecutando SQL: " + findbyname + " | Parametros: [name=" + name + "]");
            return ct;
        } catch (SQLException e) {
            Logger.getLogger(DependienteDao.class.getName()).log(Level.SEVERE,
                    null, e);
        }
        return ct;
    }

    private Categoria registerToObject(final ResultSet r) {
        Categoria ct = null;
        try {
            ct = new Categoria(
                    r.getString("ID"),
                    r.getString("NAME"),
                    r.getString("DESCRIPTION"),
                    r.getString("IMAGE_PATH"),
                    r.getBoolean("ENABLED"));

        } catch (final SQLException ex) {
            Logger.getLogger(DependienteDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return ct;
    }
     public boolean removeById(String id) {
            try {
                Logger logger = Logger.getLogger(CategoriaDao.class.getName());
                logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + id + "]");
                final PreparedStatement pst = conn.getConnection().prepareStatement(deletebyid);
                pst.setString(1, id);
                int affected = pst.executeUpdate();
                pst.close();
                return affected > 0;
            } catch (SQLException e) {
                Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE,
                        null, e);
                return false;
            }
        }
}
