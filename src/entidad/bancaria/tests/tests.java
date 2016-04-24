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

	private Domicilio domicilio;

	/**
	 * Excepciones Esperadas
	 */
	@Rule
	public final ExpectedException excepcionEsperada = ExpectedException.none();

	/*
	 * Pre de cada test
	 */
	@Before
	public void fixture() throws CUITInvalidoException, CUITYaAsignadoException, DepositoInicialInvalidoException,
			SinClientesException, ClienteInexistenteException {

		/* Creamos un Domicilio */
		domicilio = new Domicilio("42 Wallaby", "2222", "P Sherman", "Sidney");

		/* Creamos una Persona Fisica */
		Banco.agregarPersonaFisica("20000000001", "Roberto Gomez Bolaños", domicilio, "0303456", "DNI", "00000000",
				"Soltero", "Psicólogo", "Doña Florinda");

		/* Creamos una Persona Jurídica */
		Banco.agregarPersonaJuridica("20000000002", "Noganet", domicilio, "52753700", "01/1996");

		/* Creamos una Caja de Ahorro en PESOS */
		String[] clientesCajaDeAhorro = new String[] { "20000000001" };
		Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 5000.0, 0.1, TipoDeMoneda.PESO);

		/* Creamos una Caja de Ahorro en DOLARES */
		Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 500.0, 0.1, TipoDeMoneda.DOLAR);

		/* Creamos una Cuenta Corriente */
		String[] clientesCuentaCorriente = new String[] { "20000000002" };
		Banco.crearCuentaCorriente(clientesCuentaCorriente, 10000.0, 10000.0);
	}

	@Test
	public void testCrearCuentaSinClientes()
			throws DepositoInicialInvalidoException, ClienteInexistenteException, SinClientesException {

		excepcionEsperada.expect(SinClientesException.class);
		String[] cuits = new String[0];
		Banco.crearCuentaCorriente(cuits, 5000.0, 10000.0);

	}

	@Test
	public void testCuentaCorrienteSaldoMenorA10000()
			throws DepositoInicialInvalidoException, ClienteInexistenteException, SinClientesException {

		excepcionEsperada.expect(DepositoInicialInvalidoException.class);
		String[] clientesCuentaCorriente = new String[] { "20000000002" };
		Banco.crearCuentaCorriente(clientesCuentaCorriente, 5000.0, 10000.0);
	}

	@Test
	public void testCUITInvalido() throws CUITInvalidoException, CUITYaAsignadoException {

		excepcionEsperada.expect(CUITInvalidoException.class);
		Banco.agregarPersonaFisica("2001", "Roberto Gomez Bolaños", domicilio, "0303456", "DNI", "00000000", "Soltero",
				"Psicólogo", "Doña Florinda");
	}
}
