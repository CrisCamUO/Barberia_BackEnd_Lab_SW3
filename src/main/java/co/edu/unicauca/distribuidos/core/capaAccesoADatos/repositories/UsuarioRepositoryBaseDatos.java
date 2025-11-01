package co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.CategoriaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ClienteEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.conexion.ConexionBD;

@Repository //El objeto creado se almacena en el contenedor de Spring
public class UsuarioRepositoryBaseDatos {
    private final ConexionBD conexionABaseDeDatos;

    public UsuarioRepositoryBaseDatos() {
        conexionABaseDeDatos = new ConexionBD();
    }

    /**
     * 
     * @author: Daniel Eduardo Paz Perafán
     * @version: 09/12/2024
     * @param cliente El parámetro encapsula la información del cliente a registrar
     *                en el sistema
     * @return si el cliente se registro correctamente, el método retorna el cliente
     *         con los datos registrados,null en caso contrario
     */

     public ClienteEntity save(ClienteEntity objCliente) {
        System.out.println("registrando cliente en base de datos");
        ClienteEntity objClienteAlmacenado = null;
        int resultado = -1;

        try {

            conexionABaseDeDatos.conectar();

            PreparedStatement sentencia = null;
            String consulta = "insert into clientes(nombre, apellido, email, createAt, idCategoria) values(?,?,?,?,?)";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, objCliente.getNombre());
            sentencia.setString(2, objCliente.getApellido());
            sentencia.setString(3, objCliente.getEmail());
            sentencia.setDate(4, new java.sql.Date(objCliente.getCreateAt().getTime()));
            sentencia.setInt(5, objCliente.getObjCategoria().getId());
            resultado = sentencia.executeUpdate();
            
            ResultSet generatedKeys = sentencia.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idGenerado = generatedKeys.getInt(1); 
                objCliente.setId(idGenerado); 
                System.out.println("ID generado: " + idGenerado);
                if (resultado == 1) {
                    objClienteAlmacenado = this.findById(idGenerado).get();
                }
            }
            else {
                System.out.println("No se pudo obtener el ID generado.");
            }

            generatedKeys.close();
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la inserción: " + e.getMessage());
        }

       
        return objClienteAlmacenado;
    }

    public Optional<Collection<ClienteEntity>> findAll() {
        System.out.println("listando clientes de base de datos");        
        Collection<ClienteEntity> clientes = new LinkedList<ClienteEntity>();

        conexionABaseDeDatos.conectar();
        try {
            PreparedStatement sentencia = null;
            String consulta = "select * from clientes join categorias on clientes.idCategoria=categorias.id";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            ResultSet res = sentencia.executeQuery();
            while (res.next()) {
                ClienteEntity objCliente = new ClienteEntity();
                objCliente.setId(res.getInt("id"));
                objCliente.setNombre(res.getString("nombre"));
                objCliente.setApellido(res.getString("apellido"));
                objCliente.setEmail(res.getString("email"));
                objCliente.setCreateAt(res.getDate("createAt"));
                objCliente.setObjCategoria(new CategoriaEntity(res.getInt("idCategoria"), res.getString("nombreCategoria")));
                clientes.add(objCliente);
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la consulta: " + e.getMessage());
        }

        return clientes.isEmpty() ? Optional.empty() : Optional.of(clientes);        
    }

    public Optional<ClienteEntity> findById(Integer idCliente) {
        System.out.println("consultar cliente de base de datos");
        ClienteEntity objCliente = null;

        conexionABaseDeDatos.conectar();
        try {
            PreparedStatement sentencia = null;
            String consulta = "select * from clientes join categorias on clientes.idCategoria=categorias.id where clientes.id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, idCliente);
            ResultSet res = sentencia.executeQuery();
            while (res.next()) {
                System.out.println("cliente encontrado");
                objCliente = new ClienteEntity();
                objCliente.setId(res.getInt("id"));
                objCliente.setNombre(res.getString("nombre"));
                objCliente.setApellido(res.getString("apellido"));
                objCliente.setEmail(res.getString("email"));
                objCliente.setCreateAt(res.getDate("createAt"));
                objCliente.setObjCategoria(new CategoriaEntity(res.getInt("idCategoria"), res.getString("nombreCategoria")));
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la consulta: " + e.getMessage());
        }

        return objCliente==null ? Optional.empty() : Optional.of(objCliente); 
    }
   
    public Optional<ClienteEntity> update(Integer idCliente, ClienteEntity objCliente) {
        System.out.println("actualizar cliente de base de datos");
        ClienteEntity objClienteActualizado = null;
        conexionABaseDeDatos.conectar();
        int resultado = -1;
        try {
            PreparedStatement sentencia = null;
            String consulta = "update clientes set clientes.nombre=?,"
                    + "clientes.apellido=?,"
                    + "clientes.email=? ,"
                    + "clientes.createAt=? ,"
                    + "clientes.idCategoria=? "
                    + "where clientes.id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);

            sentencia.setString(1, objCliente.getNombre());
            sentencia.setString(2, objCliente.getApellido());
            sentencia.setString(3, objCliente.getEmail());

            sentencia.setDate(4, new java.sql.Date(objCliente.getCreateAt().getTime()));
            sentencia.setInt(5, objCliente.getObjCategoria().getId());
            sentencia.setInt(6, idCliente);
            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la actualización: " + e.getMessage());
        }

        if (resultado == 1) {
            objClienteActualizado = this.findById(idCliente).get();
        }
        return objClienteActualizado==null ? Optional.empty() : Optional.of(objClienteActualizado); 

    }

    public boolean delete(Integer idCliente) {
        System.out.println("eliminar cliente de base de datos");
        conexionABaseDeDatos.conectar();
        int resultado = -1;
        try {
            PreparedStatement sentencia = null;
            String consulta = "delete from clientes where clientes.id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, idCliente);
            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la eliminación: " + e.getMessage());
        }

        return resultado == 1;
    }
}
