package ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias;

import java.util.List;

import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;

public class BBDDRepositorioCategoriaJava {

    private final DataBaseConnection db;

    private CategoriaDao dao;

    public BBDDRepositorioCategoriaJava(String path) throws Exception {
        super();
        this.db = new DataBaseConnection();
        this.db.setConfig_path(path);
        this.db.open();
        dao= new CategoriaDao();
        dao.setConn(this.db);
    }

    public void add(Categoria item){
        this.dao.insert(item);
    }

    public boolean remove(Categoria item){
        this.dao.delete(item);
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

    

}
