/**
 * 
 */
package controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import dao.EditorialDAO;
import modelo.Editorial;
import vista.DialogoEditoriales;
import vista.NuevaEditorial;
import vista.VentanaPpal;

/**
 * @author David
 *
 */	// El controlador tiene acceso a las ventanas y al modelo
public class Controlador {

	// Ventanas del sistema
	private VentanaPpal ventanaPpal;
	private DialogoEditoriales dialogoEditoriales;
	
	private NuevaEditorial NuevaEditorial;
	
	// Objetos DAO o CRUD de la base de datos
	private EditorialDAO editorialDAO;
	// El controlador es un nexo de union entre el modelo DAO y la vista
	// Modelo DAO contiene clases
	// Vista contiene la interfaz
	
	public Controlador() {
		// Creamos las ventanas de la aplicaci�n
		this.ventanaPpal = new VentanaPpal();
		this.dialogoEditoriales = new DialogoEditoriales();
		this.NuevaEditorial = new NuevaEditorial();
		
		// Dando acceso al controlador desde las vistas
		ventanaPpal.setControlador(this);
		dialogoEditoriales.setControlador(this);
		NuevaEditorial.setControlador(this);
		
		// Creamos los objetos DAO
		editorialDAO = new EditorialDAO();
	}
	
	public void iniciarPrograma() {
		ventanaPpal.setVisible(true);
	}
	
	public void mostrarEditoriales() {
		ArrayList<Editorial> lista = editorialDAO.obtenerEditoriales();
		dialogoEditoriales.setListaEditoriales(lista);
		dialogoEditoriales.setVisible(true);
	}
	
	public void mostrarNuevaEditorial() {
		NuevaEditorial.setEditorial(null);
		NuevaEditorial.setVisible(true);
	}
	
	public void insertarEditorial(Editorial ed) {
		int res=editorialDAO.insertarEditorial(ed);
		if (res==0) {
			JOptionPane.showMessageDialog(NuevaEditorial, "Error no se ha podido insertar");
		} else {
			JOptionPane.showMessageDialog(NuevaEditorial, "Editorial a�adido correctamente.");
			NuevaEditorial.setVisible(false);
		}
	}

	public void mostrarActualizarEditorial(int codEditorial) {
		Editorial e = editorialDAO.obtenerEditorial(codEditorial);
		NuevaEditorial.setEditorial(e);
		NuevaEditorial.setVisible(true);
	}

	public void actualizarEditorial(Editorial ed) {
		int res=editorialDAO.actualizarEditorial(ed);
		if (res==0) {
			JOptionPane.showMessageDialog(NuevaEditorial, "Error no se ha podido actualizar");
		} else {
			JOptionPane.showMessageDialog(NuevaEditorial, "Editorial actualizado correctamente.");
			NuevaEditorial.setVisible(false);
		}
		mostrarEditoriales();
	}
}
