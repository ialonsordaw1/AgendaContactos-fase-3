package agenda.modelo;

import java.time.LocalDate;
/**
 * Clase Personal que hereda de Contacto
 * Genera una fecha de Nacimiento, la relacion con una persona
 * y todos los atributos heredados de la clase Contacto
 * @author Iñigo Alonso, David Burguete
 * @version 1.0
 */
public class Personal extends Contacto{
	
	//Atributos
		//Constantes
		//Variables
		private LocalDate fechaNacimiento;
		private Relacion relacion;

	/**
	 * Constructor de la clase Personal
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param email
	 * @param fechaNacimiento
	 * @param relacion
	 */
	public Personal(String nombre, String apellidos, String telefono, String email, String fechaNacimiento,
			Relacion relacion) {
		super(nombre, apellidos, telefono, email);
		String[] formato = fechaNacimiento.split("/");
		fechaNacimiento = formato[2] + "-" + formato[1] + "-" + formato[0];
		this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
		this.relacion = relacion;
	}
	/**
	 * Método redefinido para generar la firma
	 * @return firma
	 */
	@Override
	public String generarFirmaEmail() {
		return "Un abrazo!!";
	}
	/**
	 * Devuelve un booleano si es el dia del cumpleaños de esta persona
	 * @return true si es, false si no
	 */
	public boolean esCumpleaños() {
		return (LocalDate.now().getDayOfMonth() == fechaNacimiento.getDayOfMonth() && LocalDate.now().getMonthValue() == fechaNacimiento.getMonthValue());
	}
	/**
	 * Accesor para la fecha de nacimiento
	 * @return la fecha de nacimiento
	 */
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	/**
	 * Mutador para la fecha de nacimiento
	 * @param fechaNacimiento
	 */
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	/**
	 * Accesor para la relacion
	 * @return la relacion con una persona
	 */
	public Relacion getRelacion() {
		return relacion;
	}
	/**
	 * Mutador para la relacion
	 * @param relacion
	 */
	public void setRelacion(Relacion relacion) {
		this.relacion = relacion;
	}
	/**
	 * Método toString de la clase Personal
	 * @retrun toString
	 */
	@Override
	public String toString() {
		int dia = fechaNacimiento.getDayOfMonth();
		String mes = fechaNacimiento.getMonth().name().toLowerCase().substring(0, 3) + ".";
		int año = fechaNacimiento.getYear();
		return super.toString() + "Fecha nacimiento: " + dia + " " + mes + " " + año + "\nRelacion: " + relacion.name().toUpperCase() + "\n\n";
	}
	
}
