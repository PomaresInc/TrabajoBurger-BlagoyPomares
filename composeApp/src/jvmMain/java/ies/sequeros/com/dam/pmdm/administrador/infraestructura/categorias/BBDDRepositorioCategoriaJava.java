package ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias;

import java.util.List;

import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;

public class BBDDRepositorioCategoriaJava {

    private final DataBaseConnection db;

    private CategoriaDao dao;

    public BBDDRepositorioCategoriaJava(DataBaseConnection connection) throws Exception {
        super();
        this.db = connection;
        dao = new CategoriaDao();
        dao.setConn(this.db);
    }

    public void add(Categoria item){
        this.dao.insert(item);
    }

    public boolean remove(String id){
        this.dao.removeById(id);
        return true;
    }

    public boolean  update(Categoria item){
        this.dao.update(item);
        return true;
    }

    public List<Categoria> getAll() {
        return this.dao.getAll();
    }

    public Categoria findByName(String name){
        return this.dao.findByName(name);
    }

    public Categoria  getById(String id){
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
