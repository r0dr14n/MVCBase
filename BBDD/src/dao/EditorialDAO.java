/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.ConexionBD;
import modelo.Editorial;

/**
 * @author David
 * Clase que implementa un CRUD de la base batos
 * (Create, Read, update y delete)
 * DAO = (Data Access Object)
 */
public class EditorialDAO {

	private ConexionBD conexion;
	
    public EditorialDAO() {
        this.conexion = new ConexionBD();
    }
    
    /*	
     *  Metodo de la clase DAO que recupera todas las editoriales de la base de datos
     *  y las añade a un arraylist de editoriales
     */
    
    public ArrayList<Editorial> obtenerEditoriales() {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		Statement consulta = null;
		ResultSet resultado = null;
		ArrayList<Editorial> lista = new ArrayList<Editorial>();
		
		try {
			consulta = con.createStatement();
			resultado = consulta.executeQuery("select * from editoriales");
			
			// Bucle para recorrer todas las filas que devuelve la consulta
			while(resultado.next()) {
				int codEditorial = resultado.getInt("codeditorial");
				String nombre = resultado.getString("nombre");
				int año = resultado.getInt("anio");
				
				Editorial ed = new Editorial(codEditorial, nombre,año);
				lista.add(ed);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al realizar la consulta: "+e.getMessage());
		} finally {
			try {
				resultado.close();
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return lista;
    }


    public Editorial obtenerEditorial(int codEditorial) {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		PreparedStatement consulta = null;
		ResultSet resultado = null;
		Editorial ed=null;
		
		try {
			consulta = con.prepareStatement("select * from editoriales "
					+ "where codEditorial = ?");
			consulta.setInt(1, codEditorial);
			resultado = consulta.executeQuery();
			
			// Bucle para recorrer todas las filas que devuelve la consulta
			if (resultado.next()) {
				String nombre = resultado.getString("nombre");
				int anio = resultado.getInt("anio");
				
				ed = new Editorial(codEditorial, nombre,anio);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al realizar la consulta: "+e.getMessage());
		} finally {
			try {
				resultado.close();
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return ed;
    }


    public int insertarEditorial(Editorial editorial) {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		PreparedStatement consulta = null;
		int resultado=0;
		
		try {
			consulta = con.prepareStatement("INSERT INTO editoriales (nombre,anio)"
					+ " VALUES (?,?) ");
			
			consulta.setString(1, editorial.getNombre());
			consulta.setInt(2, editorial.getAño());
			resultado=consulta.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error al realizar la consulta: "+e.getMessage());
		} finally {
			try {
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return resultado;
    }

    public int actualizarEditorial(Editorial editorial) {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		PreparedStatement consulta = null;
		int resultado=0;
		
		try {
			consulta = con.prepareStatement("UPDATE `biblioteca`.`editoriales`\r\n"
					+ "SET `nombre` = ?, `anio` = ?\r\n"
					+ "WHERE `codEditorial` = ?;");
			
			consulta.setString(1, editorial.getNombre());
			consulta.setInt(2, editorial.getAño());
			consulta.setInt(3, editorial.getCodEditorial());
			resultado=consulta.executeUpdate();
			
			System.out.println(consulta);

		} catch (SQLException e) {
			System.out.println("Error al realizar la actualizacion: "+e.getMessage());
		} finally {
			try {
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return resultado;
    }


    public int eliminarEditorial(int codEditorial) {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		PreparedStatement consulta = null;
		int resultado=0;
		
		try {
			consulta = con.prepareStatement("DELETE FROM `biblioteca`.`editoriales`\r\n"
					+ "WHERE codEditorial = ?");
			
			consulta.setInt(1, codEditorial);
			resultado=consulta.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error al realizar la actualizacion: "+e.getMessage());
		} finally {
			try {
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return resultado;
    }

}
