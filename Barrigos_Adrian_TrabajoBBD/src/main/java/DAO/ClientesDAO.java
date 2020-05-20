package DAO;

import POJO.POJO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author adrian
 */
    
public class ClientesDAO {
    
    private static Connection conexion = null;

    public ClientesDAO() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/neptuno", "root", "");
        } catch (SQLException ex) {
            System.err.println("No se pudo conectar: " + ex.getMessage());
        }
    }

    public static Connection getConexion() {
        return conexion;
    }
    
    public static POJO read(Integer id) {
        POJO cliente = null;
        PreparedStatement stmt = null;

        if (ClientesDAO.conexion == null) {
            return null;
        }

        try {
            String query = "SELECT * FROM clientes WHERE id = ?";
            stmt = conexion.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new POJO(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("empresa"),
                        rs.getString("contacto"),
                        rs.getString("cargo_contacto"),
                        rs.getString("direccion"),
                        rs.getString("ciudad"),
                        rs.getString("region"),
                        rs.getString("cp"),
                        rs.getString("pais"),
                        rs.getString("telefono"),
                        rs.getString("fax")
                );
            }

            stmt.close();

        } catch (SQLException e) {

            System.err.println("Select incorrecto: " + e.getMessage() + "\nQuery: " + stmt.toString());
        }

        return cliente;
    }
    
    public Boolean insert(POJO cliente) {
        PreparedStatement stmt = null;
        Boolean resultado = false;

        if (this.conexion == null || cliente == null){
            return null;
        }
        try {
            String sql = "INSERT INTO clientes "
                    + "(id, codigo, empresa, contacto, cargo_contacto, direccion, ciudad, region, cp, pais, telefono, fax) "
                    + "VALUES ((SELECT Max(id)+1 FROM clientes E), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1,cliente.getCodigo());
            stmt.setString(2,cliente.getEmpresa());
            stmt.setString(3,cliente.getContacto());
            stmt.setString(4,cliente.getCargo_contacto());
            stmt.setString(5,cliente.getDireccion());
            stmt.setString(6,cliente.getCiudad());
            stmt.setString(7,cliente.getRegion());
            stmt.setString(8,cliente.getCp());
            stmt.setString(9,cliente.getPais());
            stmt.setString(10,cliente.getTelefono());
            stmt.setString(11,cliente.getFax());
            
            if (stmt.executeUpdate() > 0) {
                resultado = true;
            }
            
        } catch (SQLException e) {
            System.out.println("Insert incorrecto: "+e.getMessage()+"\nQuery: "+stmt.toString());
        
        } finally {
            
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.err.println("La conexion no se pudo cerrar: " + ex.getMessage());
            }
        }
        return resultado;
    }
    
    public Boolean update(POJO cliente) {
        Boolean resultado = null;
        PreparedStatement stmt = null;

        if (this.conexion == null || cliente == null) {
            return false;
        }

        try {

            String sql = "UPDATE clientes SET codigo = ?, empresa = ?, contacto = ?, cargo_contacto = ?"
                    + ", direccion = ?, ciudad = ?, region = ?, cp = ?, pais = ?, telefono = ?, "
                    + "fax = ? WHERE id = ?";

            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, cliente.getCodigo());
            stmt.setString(2, cliente.getEmpresa());
            stmt.setString(3, cliente.getContacto());
            stmt.setString(4, cliente.getCargo_contacto());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getCiudad());
            stmt.setString(7, cliente.getRegion());
            stmt.setString(8, cliente.getCp());
            stmt.setString(9, cliente.getPais());
            stmt.setString(10, cliente.getTelefono());
            stmt.setString(11, cliente.getFax());
            stmt.setInt(12, cliente.getId());
            
            if (stmt.executeUpdate() > 0) {
                resultado = true;

            }
        } catch (SQLException e) {
            System.err.println("Update incorrecto: " + e.getMessage()+ " SQL:" + stmt.toString());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.err.println("no se pudo cerrar la conexion: " + ex.getMessage());
            }
        }

        return resultado;
    }
    
    public Boolean delete(Integer id) {
        Boolean resultado = false;
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM clientes WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);

            resultado = stmt.execute();

            stmt.close();

            System.out.println();

        } catch (SQLException e) {

            System.err.println("Delete incorrecto: " + e.getMessage() + " " + stmt.toString());
        }

        return resultado;

    }

    public ArrayList<POJO> listar(Integer start, Integer fin) {
        ArrayList<POJO> lista = new ArrayList<>();

        POJO cliente = null;
        PreparedStatement stmt = null;

        if (ClientesDAO.conexion == null) {
            return null;
        }

        try {
            String query = "SELECT * FROM clientes WHERE id BETWEEN ? AND ?";
            stmt = conexion.prepareStatement(query);
            stmt.setInt(1, start);
            stmt.setInt(2, fin);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cliente = new POJO(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("empresa"),
                        rs.getString("contacto"),
                        rs.getString("cargo_contacto"),
                        rs.getString("direccion"),
                        rs.getString("ciudad"),
                        rs.getString("region"),
                        rs.getString("cp"),
                        rs.getString("pais"),
                        rs.getString("telefono"),
                        rs.getString("fax")
                );
                
                lista.add(cliente);
            }

            stmt.close();

        } catch (SQLException e) {

            System.err.println("Select incorrecto: " + e.getMessage() + "\nQuery: " + stmt.toString());
        }
        return lista;
    }
    
}