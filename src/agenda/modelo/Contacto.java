package agenda.modelo;

import java.util.Comparator;
/**
 * Clase abstracta para manejar los contactos de la agenda
 * @author Iñigo Alonso
 * @version 1.0
 * 
 * */
public abstract class Contacto {
	//Variables
	private String nombre;
	private String apellidos;
	private String telefono;
	private String email;
	/**
	 * Constructor de la clase Contacto que inicia las variables:
	 * nombre, email, apellidos, teléfono
	 * @param nombre, apellidos, teléfono, email
	 * 
	 * */
	public Contacto(String nombre, String apellidos, String telefono, String email) {
		this.nombre = nombre.toUpperCase();
		this.apellidos = apellidos.toUpperCase();
		this.telefono = telefono;
		this.email = email.toLowerCase();
	}
	/**
	 * Accesor para el nombre
	 * @return nombre
	 * */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Mutador para el nombre
	 * @param nombre
	 * */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Accesor para los apellidos
	 * @return apellidos
	 * */
	public String getApellidos() {
		return apellidos;
	}
	/**
	 * Mutador para los apellidos
	 * @param apellidos
	 * */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	/**
	 * Accesor para el teléfono
	 * @return teléfono
	 * */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * Mutador para el teléfono
	 * @param teléfono
	 * 
	 * */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * Accesor para el email
	 * @return email
	 * */
	public String getEmail() {
		return email;
	}
	/**
	 * Mutador para el email
	 * @param email
	 * */ 
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Accesor para el hashcode del email
	 * @return hashCode
	 * */
	@Override
	public int hashCode() {
		return email.hashCode();

	}
	/**
	 * Accesor para la primera letra del apellido
	 * @return primera letra del apellido
	 * */
	public char getPrimeraLetra() {
		return apellidos.charAt(0);
	}
	/**
	 * Método equals redefinido para poder comparar contactos
	 * @param el objeto a comparar
	 * @return boolean
	 * */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Contacto)) return false;
		return getApellidos().equals(((Contacto) obj).getApellidos()) && getNombre().equals(((Contacto) obj).getNombre()) && getEmail().equals(((Contacto) obj).getEmail());
	}
	
	/**
	 * Método abstracto para generar la firma
	 * @return Firma
	 */
	public abstract String generarFirmaEmail ();
	/**
	 * Método toString de la clase Contacto
	 * @return toString
	 */
	@Override
	public String toString() {
		return getApellidos() + ", " + getNombre()+ " (" + this.getClass().getSimpleName().toUpperCase() + ")\n"
				+ "Tfno: " + getTelefono() + " | email: " + getEmail() + "\n";
	}

}
