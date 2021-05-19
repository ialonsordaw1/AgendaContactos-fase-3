package agenda.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import agenda.modelo.*;

/**
 * Utilidades para cargar la agenda
 */
public class AgendaIO {
	/**
	 * Metodo para importar una linea de datos a la agenda
	 * 
	 * @param agenda
	 */
	public static int importar(AgendaContactos agenda, String fichero) {
		int cant = 0;
		BufferedReader f = null;
		try {
			f = new BufferedReader(new FileReader(fichero));
			String local = f.readLine();
			while (local != null) {
				try {
					Contacto c = parsearLinea(local);
					agenda.añadirContacto(c);
				} catch (IllegalArgumentException i) {
					cant++;
				} catch (DateTimeParseException i) {
					cant++;
				}
				local = f.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		} catch (IOException e) {
			System.out.println("No hay más líneas para leer");
		} finally {
			try {
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cant;
	}

	/**
	 * Metodo privado para parsear la linea a importar
	 * 
	 * @param linea
	 * @return Contacto
	 */
	private static Contacto parsearLinea(String linea) throws IllegalArgumentException, DateTimeParseException{
		String[] formateo = linea.split(",");
		Contacto c = null;
		if (formateo[0].trim().equals("1")) {
			c = new Profesional(formateo[1].trim(), formateo[2].trim(), formateo[3].trim(), formateo[4].trim(),
					formateo[5].trim());
		} else {
			c = new Personal(formateo[1].trim(), formateo[2].trim(), formateo[3].trim(), formateo[4].trim(),
					formateo[5].trim(), Relacion.valueOf(formateo[6].toUpperCase().trim()));
		}
		return c;
	}
	
	
	/**
	 * Crea un fichero externo al programa con todos los tipos de relación que existen entre
	 * los contactos. Si no se puede crear el fichero o escribir en el, o cerrarlo, lanza una excepción
	 * @param agenda
	 * @param fichero
	 * @throws IOException
	 */
	public static void exportarContactos(AgendaContactos agenda, String fichero) throws IOException{
		FileWriter fW = new FileWriter(fichero);
		fW.write(personalesEnRelacion(agenda));
		fW.close();
	}
	
	
	/**
	 * Método privado que escribe los contactos en relación
	 * @param agenda
	 * @return Los contactos clasificados en el tipo de relación
	 */
	private static String personalesEnRelacion(AgendaContactos agenda) {
		Map<Relacion, List<String>> map = agenda.personalesPorRelacion();
		Set<Relacion> r = map.keySet();
		Iterator<Relacion> it = r.iterator();
		String lineas = "";
		while (it.hasNext()) {
			Relacion relacion = (Relacion) it.next();
			lineas += relacion + "\n\t" + map.get(relacion) + "\n";
		}
		return lineas;
	}
}
