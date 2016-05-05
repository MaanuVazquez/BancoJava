package entidad.bancaria.tests;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import entidad.bancaria.banco.*;
import entidad.bancaria.clientes.*;
import entidad.bancaria.cuentas.*;
import entidad.bancaria.excepciones.*;

public class TestBancoJava {

	private static Domicilio domicilio;

	@BeforeClass
	public static void fixture() throws CUITInvalidoException,
			CUITYaAsignadoException, DepositoInicialInvalidoException,
			SinClientesException, ClienteInexistenteException,
			TasaDeInteresNegativaException, SobregiroNegativoException {

		/* Creamos un Domicilio */
		domicilio = new Domicilio("42 Wallaby", "2222", "P Sherman", "Sidney");

		/* Creamos una Persona Fisica */
		Banco.agregarPersonaFisica("20000000001", "Roberto Gomez Bolaños",
				domicilio, "0303456", "DNI", "00000000", "Soltero",
				"Psicólogo", "Doña Florinda");

		/* Creamos una Persona Jurídica */
		Banco.agregarPersonaJuridica("20000000002", "Noganet", domicilio,
				"52753700", "01/1996");

		/* Creamos una Caja de Ahorro en PESOS */
		String[] clientesCajaDeAhorro = new String[] { "20000000001" };
		Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 5000.0, 0.1,
				TipoDeMoneda.PESO);

		/* Creamos una Caja de Ahorro en DOLARES */
		Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 500.0, 0.1,
				TipoDeMoneda.DOLAR);

		/* Creamos una Cuenta Corriente */
		String[] clientesCuentaCorriente = new String[] { "20000000002" };
		Banco.crearCuentaCorriente(clientesCuentaCorriente, 10000.0, 10000.0);
	}

	/*
	 * Prueba proceso batch
	 */

	@Test
	public void ProcesoBatch() {
		try {
			ProcesoBatch.setCOSTO_DE_MANTENIMIENTO(50.0);
			Banco.cobroDeMantenimientos();
		} catch (IOException | CostoDeMantenimientoNoInicializadoException
				| CostoDeMantenimientoNoPositivoException e) {
			System.err.println(e);
		}
	}

	/*
	 * Prueba Comparar cuentas
	 */

	@Test
	public void compararCuentas() throws TasaDeInteresNegativaException,
			DepositoInicialInvalidoException, SinClientesException,
			CUITInvalidoException {
		PersonaFisica persona = new PersonaFisica("20000000001",
				"Roberto Gomez Bolaños", domicilio, "0303456", "DNI",
				"00000000", "Soltero", "Psicólogo", "Doña Florinda");
		PersonaFisica[] personas = new PersonaFisica[1];
		personas[0] = persona;
		CajaDeAhorro cuenta1 = new CajaDeAhorro(personas, 0.1, 0.1,
				TipoDeMoneda.PESO);
		CajaDeAhorro cuenta2 = new CajaDeAhorro(personas, 0.1, 0.1,
				TipoDeMoneda.PESO);
		Assert.assertFalse(cuenta1.equals(cuenta2));
	}

	/*
	 * Acreditar saldo a caja de ahorro.
	 */

	@Test
	public void acreditarSaldoEnCajaDeAhorro() throws CBUInexistenteException,
			CuentaInhabilitadaException {
		Banco.depositoEnEfectivo(1, 10000.0);
	}

	/*
	 * Acreditar saldo a cuenta corriente
	 */

	@Test
	public void acreditarSaldoEnCuentaCorriente()
			throws DepositoInicialInvalidoException,
			ClienteInexistenteException, SinClientesException,
			CUITInvalidoException, SobregiroNegativoException,
			CBUInexistenteException, CuentaInhabilitadaException {

		String[] clientesCuentaCorriente = new String[] { "20000000001" };
		Banco.crearCuentaCorriente(clientesCuentaCorriente, 100000.0, 10.0);
		Banco.depositoEnEfectivo(1, 1000.0);

	}

	/*
	 * Acreditar en cuenta especial y cobro de mantenimiento
	 */

	@Test
	public void acreditarSaldoEnCuentaEspecial() throws IOException,
			CostoDeMantenimientoNoInicializadoException,
			CostoDeMantenimientoNoPositivoException {
		ProcesoBatch.setCOSTO_DE_MANTENIMIENTO(100.0);
		Banco.cobroDeMantenimientos();

	}

	/*
	 * Extraccion en caja de ahorro
	 */

	@Test
	public void extraccionSaldoEnCajaDeAhorro()
			throws SaldoInsuficienteException, CuentaInhabilitadaException,
			CBUInexistenteException {

		Banco.extraccionEnEfectivoEnCajaDeAhorro(1, 1000.0);
	}

	/*
	 * Extraccion en cuenta corriente
	 */

	@Test
	public void extraccionSaldoEnCuentaCorriente()
			throws SaldoInsuficienteException, CuentaInhabilitadaException,
			CBUInexistenteException, DepositoInicialInvalidoException,
			ClienteInexistenteException, SinClientesException,
			CUITInvalidoException, SobregiroNegativoException {

		String[] clientesCuentaCorriente = new String[] { "20000000001" };
		Banco.crearCuentaCorriente(clientesCuentaCorriente, 100000.0, 10.0);
		Banco.extraccionEnEfectivoEnCajaDeAhorro(1, 1000.0);
	}

	/*
	 * Prueba buscar un cliente
	 */

	@Test
	public void buscarCliente() throws ClienteInexistenteException,
			CUITInvalidoException, CUITYaAsignadoException {
		Domicilio domicilio = new Domicilio("Calle Falsa 123", "1234asdf",
				"Un lugar", "Una provincia");
		Banco.agregarPersonaFisica("20034002501", "Jose perez", domicilio,
				"1234-4321", "DNI", "12345678", "Casado", "ingeniero", "amalia");
		Cliente cliente = Banco.buscarCliente("20034002501");
		Assert.assertTrue("Jose perez".equals(cliente.getNombreORazonSocial()));
		Assert.assertTrue("20034002501".equals(cliente.getCUIT()));
		Assert.assertTrue(domicilio.equals(cliente.getDomicilio()));
	}

	/*
	 * Prueba tranferencia
	 */

	@Test
	public void testTransferecias() throws CBUInexistenteException,
			CuentaInhabilitadaException, SaldoInsuficienteException {

		Banco.transferencia(1, 2, 1000.0);

	}

	/*
	 * Prueba buscar persona fisica
	 */

	@Test
	public void testBuscarPersonaFisica() throws CUITInvalidoException,
			CUITYaAsignadoException {

		Domicilio domicilio = new Domicilio("Calle Falsa 123", "1234asdf",
				"Un lugar", "Una provincia");
		Banco.agregarPersonaFisica("20034002601", "Jose schewer", domicilio,
				"1234-4321", "DNI", "12345678", "Casado", "ingeniero", "amalia");

	}

	/*
	 * Prueba pagar interes
	 */

	@Test
	public void testPagarInteres() throws DepositoInicialInvalidoException,
			SinClientesException, ClienteInexistenteException,
			CUITInvalidoException, TasaDeInteresNegativaException,
			CBUInexistenteException {

		HashMap<Integer, CajaDeAhorro> cuentas = new HashMap<Integer, CajaDeAhorro>();
		cuentas.put(Banco.buscarCajaDeAhorro(1).getCBU(),
				Banco.buscarCajaDeAhorro(1));

		ProcesoBatch.pagarInteres(cuentas);
	}

	/*
	 * Prueba cobrar Retenciones
	 */

	@Test
	public void testCobrarRetenciones() {
		Banco.cobrarRetenciones(100.0);
	}
	
	

}
