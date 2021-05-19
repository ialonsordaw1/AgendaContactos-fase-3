package agenda.modelo;
/**
 * Clase Profesional, hereda de Contacto
 * Genera un nombre de Empresa y todos 
 * los atributos heredados de la
 * clase Contacto
 * 
 * @author David Burguete
 * @version 1.0
 */

public class Profesional extends Contacto{
	//Atributos
		//Constantes
		//Variables
		private String nombreEmpresa;

	/**
	 * Constructor de la clase con super()
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param email
	 * @param nombreEmpresa
	 */
	public Profesional(String nombre, String apellidos, String telefono, String email, String nombreEmpresa) {
		super(nombre, apellidos, telefono, email);
		this.nombreEmpresa = guardarConFormato(nombreEmpresa.toLowerCase());
	}
	/**
	 * Introduce el nombre de la empresa, formateando cada palabra
	 * de forma que la primera letra esta en mayuscula y el resto
	 * estan en minuscula
	 * @param nombreEmpresa
	 * @return el nombre de la Empresa formateado
	 */
	private String guardarConFormato(String nombreEmpresa) {
		String[] palabras = nombreEmpresa.split("\\s+");
		String nombre = "";
		for (int i = 0; i < palabras.length; i++) {
			String nombrea = "";
			nombrea += palabras[i].charAt(0);
			nombrea = nombrea.toUpperCase();
			nombrea += palabras[i].substring(1, palabras[i].length());
			nombre += nombrea + " ";
		}
		return nombre;
	}

	/**
	 * Genera de forma aleatoria, una firma de entre cuatro registradas
	 * @return Una de las cuatro firmas
	 */
	@Override
	public String generarFirmaEmail() {
		String firmas[] = {"Atentamente","Saludos","Saludos cordiales","Mis mejores deseos"};
		return firmas[(int)(Math.random() * 4)];
	}

	/**
	 * Devuelve el nombre de la empresa
	 * @return el nombre de la empresa
	 */
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	/**
	 * Mutador del nombre de la empresa
	 * @param nombreEmpresa
	 */
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	/**
	 * Representacion textual de la clase, heredando tambien la del super
	 */
	@Override
	public String toString() {
		return super.toString() + "Empresa: " + nombreEmpresa + "\n\n";
	}

	
}
