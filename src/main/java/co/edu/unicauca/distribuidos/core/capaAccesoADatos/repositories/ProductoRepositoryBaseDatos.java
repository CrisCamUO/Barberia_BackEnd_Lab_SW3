package co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.conexion.ConexionBD;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.CategoriaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ProductoEntity;

@Repository //El objeto creado se almacena en el contenedor de Spring
public class ProductoRepositoryBaseDatos {
    
    private final ConexionBD conexionABaseDeDatos;

    public ProductoRepositoryBaseDatos() {
        conexionABaseDeDatos = new ConexionBD();
    }
    //Consultas que se pueden hacer a la base de datos
    //Crear un producto
    public ProductoEntity save(ProductoEntity objProducto) {
        System.out.println("registrando un producto en base de datos");
        ProductoEntity objProductoAlmacenado = null;
        int resultado = -1;
        try {
            conexionABaseDeDatos.conectar();

            PreparedStatement sentencia = null;
            String consulta = "insert into productos(nombre,descripcion, idCategoria, estado, precio, imagen) values(?,?,?,?,?,?)";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, objProducto.getNombre());
            sentencia.setString(2, objProducto.getDescripcion());
            sentencia.setInt(3, objProducto.getCategoria().getId());
            sentencia.setBoolean(4, objProducto.getEstado());
            sentencia.setDouble(5, objProducto.getPrecio());
            sentencia.setString(6, objProducto.getImagen());
            resultado = sentencia.executeUpdate();

           ResultSet generatedKeys = sentencia.getGeneratedKeys();

            if (generatedKeys.next()) {
                int idGenerado = generatedKeys.getInt(1);
                objProducto.setId(idGenerado);
                System.out.println("ID generado: " + idGenerado);
                if(resultado == 1){
                    objProductoAlmacenado = this.findById(idGenerado).get();//ToDo
                }
            } else {
                System.out.println("No se pudo registrar el producto");
            }
            generatedKeys.close();
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (Exception e) {
            System.out.println("error en la insercción: " + e.getMessage());
        }

        return objProductoAlmacenado;
    }
    //Buscar un producto por id
    public Optional<ProductoEntity> findById(Integer idProducto){
        System.out.println("Consultar producto de base de datos por id");
        ProductoEntity objProducto = null;
        conexionABaseDeDatos.conectar();
        try{
            PreparedStatement sentencia = null;
            // Buscar por el id del producto (antes filtraba por categorias.id lo cual era incorrecto)
            String consulta = "select * from productos join categorias on productos.idCategoria=categorias.id where productos.id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, idProducto);
            ResultSet res = sentencia.executeQuery();
            while (res.next()){
                System.out.println("Producto encontrado");
                //Recupera todos los datos de la tabla de productos
                objProducto = new ProductoEntity();
                objProducto.setId(res.getInt("id"));
                objProducto.setDescripcion(res.getString("descripcion"));
                objProducto.setNombre(res.getString("nombre"));
                objProducto.setCategoria(new CategoriaEntity(res.getInt("idCategoria"), res.getString("nombreCategoria")));
                objProducto.setEstado(res.getBoolean("estado"));
                objProducto.setPrecio(res.getDouble("precio"));
                objProducto.setImagen(res.getString("imagen"));
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch(SQLException e){
            System.out.println("error en la consulta: " + e.getMessage());
        }
        return objProducto == null ? Optional.empty() : Optional.of(objProducto);
        
    }
    //Listar todos los productos
    public Optional<Collection<ProductoEntity>> findAll(){
        System.out.println("listando productos de base de datos");
        Collection<ProductoEntity> productos = new LinkedList<ProductoEntity>();
        conexionABaseDeDatos.conectar();
        try{
            PreparedStatement sentencia = null;
            String consulta = "select * from productos join categorias on productos.idCategoria=categorias.id";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            ResultSet res = sentencia.executeQuery();
            while (res.next()){
                ProductoEntity objProducto = new ProductoEntity();
                objProducto.setId(res.getInt("id"));
                objProducto.setNombre(res.getString("nombre"));
                objProducto.setDescripcion(res.getString("descripcion"));
                objProducto.setCategoria(new CategoriaEntity(res.getInt("idCategoria"), res.getString("nombreCategoria")));
                objProducto.setEstado(res.getBoolean("estado"));
                objProducto.setPrecio(res.getDouble("precio"));
                objProducto.setImagen(res.getString("imagen"));
                productos.add(objProducto);
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch(SQLException e){
            System.out.println("error en la consulta: " + e.getMessage());
        }
        return productos.isEmpty() ? Optional.empty() : Optional.of(productos);
    }
    //Actualizar un producto
    public Optional<ProductoEntity> update(Integer idInteger, ProductoEntity objProducto){
        System.out.println("Actualizando un producto en base de datos");
        ProductoEntity objProductoActualizado = null;
        conexionABaseDeDatos.conectar();
        int resultado = -1;
        try{
            PreparedStatement sentencia = null;
            String consulta = "update productos set nombre=?, descripcion=?, idCategoria=?, estado=?, precio=?, imagen=? where productos.id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setString(1, objProducto.getNombre());
            sentencia.setString(2, objProducto.getDescripcion());
            sentencia.setInt(3, objProducto.getCategoria().getId());
            sentencia.setBoolean(4, objProducto.getEstado());
            sentencia.setDouble(5, objProducto.getPrecio());
            sentencia.setString(6, objProducto.getImagen());
            sentencia.setInt(7, idInteger);
            resultado = sentencia.executeUpdate();
            conexionABaseDeDatos.desconectar();
            
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch(SQLException e){
            System.out.println("error en la actualización: " + e.getMessage());
        }
        if(resultado == 1){
            objProductoActualizado = this.findById(idInteger).get();
        }

        return objProductoActualizado == null ? Optional.empty() : Optional.of(objProductoActualizado);
    }
    //Eliminar un producto
    public boolean delete(Integer idProducto){
        System.out.println("Eliminando un producto de base de datos");
        conexionABaseDeDatos.conectar();
        int resultado = -1;
        try{
            PreparedStatement sentencia = null;
            String consulta = "delete from productos where productos.id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, idProducto);
            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        }catch(SQLException e){
            System.out.println("error en la eliminación: " + e.getMessage());
        }

        return resultado == 1;
    }

}
