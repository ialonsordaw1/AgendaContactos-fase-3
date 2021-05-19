package agenda.test;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import agenda.io.*;
import agenda.modelo.*;

public class TestAgenda {

	public static void main(String[] args) {
		AgendaContactos agenda = new AgendaContactos();
		System.out.println(AgendaIO.importar(agenda, "agenda.csv") + " líneas erróneas");
		System.out.println(agenda);
		System.out.println("(" + agenda.totalContactos() + " contacto/s)");
		separador();
		buscarContactos(agenda, "acos");
		separador();

		buscarContactos(agenda, "don");
		separador();

		felicitar(agenda);
		separador();

		personalesOrdenadosPorFecha(agenda, 'M');
		separador();
		personalesOrdenadosPorFecha(agenda, 'E');
		separador();
		personalesOrdenadosPorFecha(agenda, 'W');
		separador();

		personalesPorRelacion(agenda);
		System.out.println("Exportados personales agrupados por relación");
		separador();

	}

	private static void buscarContactos(AgendaContactos agenda, String texto) {
		List<Contacto> resultado = agenda.buscarContactos(texto);
		System.out.println("Contactos que contienen \"" + texto + "\"");
		if (resultado.isEmpty()) {
			System.out.println("No hay contactos coincidentes");
		} else {
			resultado.forEach(contacto -> System.out.println(contacto));
		}

	}

	private static void felicitar(AgendaContactos agenda) {
		System.out.println("Fecha actual: " + LocalDate.now());
		List<Personal> resultado = agenda.felicitar();
		if (resultado.isEmpty()) {
			System.out.println("Hoy no cumple nadie");
		} else {
			System.out.println("Hoy hay que felicitar a ");
			resultado.forEach(contacto -> System.out.println(contacto));
		}

	}

	private static void personalesOrdenadosPorFecha(AgendaContactos agenda,
			char letra) {
		System.out.println("Personales en letra " + letra
				+ " ordenados de < a > fecha de nacimiento");
		List<Personal> personales = agenda.personalesEnLetra(letra);
		if (personales == null) {
			System.out.println(letra + " no está en la agenda");
		} else {
			agenda.personalesOrdenadosPorFechaNacimiento(letra)
					.forEach(contacto -> System.out.println(contacto));
		}

	}

	@SuppressWarnings("static-access")
	private static void personalesPorRelacion(AgendaContactos agenda) {
		AgendaIO aIO = new AgendaIO();
		try {
			aIO.exportarContactos(agenda, "personales-relacion.txt");
		} catch (IOException e) {
			System.out.println("No se ha podido exportar");
		}
	}

	private static void separador() {
		System.out.println(
				"------------------------------------------------------------");

	}

}
