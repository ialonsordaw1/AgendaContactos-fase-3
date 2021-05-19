package agenda.modelo;

import java.util.Comparator;

public class ComparatorCon implements Comparator<Contacto>{
	/**
	 * Comparador entre dos contactos
	 * @param Contacto1, Contacto2 
	 * @return orden
	 */
	@Override
	public int compare(Contacto c1, Contacto c2) {
		if (c1.getApellidos().compareTo(c2.getApellidos()) == 0) {
			if (c1.getNombre().compareTo(c2.getNombre()) < 0) return -1;
			else if (c1.getNombre().compareTo(c2.getNombre()) > 0) return 1;
			else return 0;
		}
		else if (c1.getApellidos().compareTo(c2.getApellidos()) < 0) return -1;
		else return 1;
	}
}
