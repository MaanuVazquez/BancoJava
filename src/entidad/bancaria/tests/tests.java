package entidad.bancaria.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import entidad.bancaria.banco.*;
import entidad.bancaria.clientes.*;
import entidad.bancaria.cuentas.*;
import entidad.bancaria.excepciones.*;

public class tests {

	// Excepciones Esperadas
	@Rule
	public final ExpectedException excepcionEsperada = ExpectedException.none();

	// Pre de cada prueba
	@Before
	public void fixture() throws CUITInvalidoException, CUITYaAsignadoException, DepositoInicialInvalidoException,
			SinClientesException, ClienteInexistenteException {
		Banco.agregarPersonaFisica("20387878034", "Emmanuel",
				new Domicilio("LT2151", "1678", "Caseros", "Buenos Aires"), "47595154", "DNI", "38787803", "Soltero",
				"Ing. Computacion", "wut");
		Banco.crearCajaDeAhorro(new String[] { "20387878034" }, 10000.0, 0.1, TipoDeMoneda.PESO);
		Banco.crearCajaDeAhorro(new String[] { "20387878034" }, 10000.0, 0.1, TipoDeMoneda.PESO);
	}

	@Test
	public void testCUITInvalido() throws CUITInvalidoException, CUITYaAsignadoException {

		excepcionEsperada.expect(CUITInvalidoException.class);

		Banco.agregarPersonaFisica("2038", "Emmanuel", new Domicilio("LT2151", "1678", "Caseros", "Buenos Aires"),
				"47595154", "DNI", "38787803", "Soltero", "Ing. Computacion", "wut");
	}
}
