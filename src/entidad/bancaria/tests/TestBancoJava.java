package entidad.bancaria.tests;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import entidad.bancaria.banco.*;
import entidad.bancaria.clientes.*;
import entidad.bancaria.cuentas.*;
import entidad.bancaria.excepciones.*;

public class TestBancoJava {

	private static Domicilio domicilio;

	/**
	 * Excepciones Esperadas
	 */
	@Rule
	public final ExpectedException excepcionEsperada = ExpectedException.none();

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
			CuentaInhabilitadaException, DepositoInicialInvalidoException,
			SinClientesException, ClienteInexistenteException,
			CUITInvalidoException, TasaDeInteresNegativaException {
		String[] clientesCajaDeAhorro = new String[] { "20000000001" };
		int cbu = Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 5000.0, 0.1,
				TipoDeMoneda.PESO);
		Banco.depositoEnEfectivo(cbu, 10000.0);
		Assert.assertEquals(15000, Banco.buscarCuenta(cbu).getSaldo(), 0.1);
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
		int cbu = Banco.crearCuentaCorriente(clientesCuentaCorriente, 100000.0,
				10.0);
		Banco.depositoEnEfectivo(cbu, 1000.0);
		double calculoComision = 1000 * 0.03;
		Assert.assertEquals((101000.0 - calculoComision),
				Banco.buscarCuenta(cbu).getSaldo(), 0.1);
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
	 * Extraccion en caja de ahorro
	 */

	@Test
	public void extraccionSaldoEnCajaDeAhorro()
			throws SaldoInsuficienteException, CuentaInhabilitadaException,
			CBUInexistenteException, DepositoInicialInvalidoException,
			SinClientesException, ClienteInexistenteException,
			CUITInvalidoException, TasaDeInteresNegativaException {
		String[] clientesCajaDeAhorro = new String[] { "20000000001" };
		int cbu = Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 5000.0, 0.1,
				TipoDeMoneda.PESO);
		Banco.extraccionEnEfectivoEnCajaDeAhorro(cbu, 1000.0);

		Assert.assertEquals(4000, Banco.buscarCuenta(cbu).getSaldo(), 0.1);
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

		excepcionEsperada.expect(CBUInexistenteException.class);
		String[] clientesCuentaCorriente = new String[] { "20000000001" };
		int cbu = Banco.crearCuentaCorriente(clientesCuentaCorriente, 100000.0,
				10.0);
		Banco.extraccionEnEfectivoEnCajaDeAhorro(cbu, 1000.0);
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
		double saldoInicial = Banco.buscarCuenta(1).getSaldo();
		Banco.transferencia(1, 2, 1000.0);

		Assert.assertEquals((double) (saldoInicial - 1000.0), (double) Banco
				.buscarCuenta(1).getSaldo(), 0.1);

	}

	/*
	 * Prueba tranferencia
	 */

	@Test
	public void testCambioDeMoneda() throws CBUInexistenteException,
			CuentaInhabilitadaException, SaldoInsuficienteException {
		double saldoInicial = Banco.buscarCuenta(2).getSaldo();
		Banco.transferencia(1, 2, 1000.0);

		Assert.assertEquals(
				(double) (saldoInicial + (1000.0 / Banco.getCotizacion())),
				(double) Banco.buscarCuenta(2).getSaldo(), 50.0);

	}

	/*
	 * Prueba buscar persona fisica
	 */

	@Test
	public void testBuscarPersonaFisica() throws CUITInvalidoException,
			CUITYaAsignadoException, ClienteInexistenteException {

		Domicilio domicilio = new Domicilio("Calle Falsa 123", "1234asdf",
				"Un lugar", "Una provincia");
		Banco.agregarPersonaFisica("20034002601", "Jose schewer", domicilio,
				"1234-4321", "DNI", "12345678", "Casado", "ingeniero", "amalia");

		Assert.assertEquals(Banco.buscarCliente("20034002601").getDomicilio(),
				domicilio);

	}

	/*
	 * Prueba pagar interes
	 */

	@Test
	public void testPagarInteres() throws DepositoInicialInvalidoException,
			SinClientesException, ClienteInexistenteException,
			CUITInvalidoException, TasaDeInteresNegativaException,
			CBUInexistenteException {
		double saldoInicial = Banco.buscarCuenta(1).getSaldo();
		Banco.pagarIntereses();
		Assert.assertEquals((saldoInicial + (saldoInicial * Banco
				.buscarCajaDeAhorro(1).getTasaDeInteres())), Banco
				.buscarCuenta(1).getSaldo(), 0.1);

	}

	/*
	 * Prueba error crar una cuenta sin clientes.
	 */
	@Test
	public void testCrearCuentaSinClientes()
			throws DepositoInicialInvalidoException,
			ClienteInexistenteException, SinClientesException,
			CUITInvalidoException, SobregiroNegativoException {

		excepcionEsperada.expect(SinClientesException.class);
		String[] cuits = new String[0];
		Banco.crearCuentaCorriente(cuits, 50000.0, 10000.0);

	}

	/*
	 * Prueba error crear una cuenta corriente con saldo menor al estipulado
	 */

	@Test
	public void testCuentaCorrienteSaldoMenorA10000()
			throws DepositoInicialInvalidoException,
			ClienteInexistenteException, SinClientesException,
			CUITInvalidoException, SobregiroNegativoException {

		excepcionEsperada.expect(DepositoInicialInvalidoException.class);
		String[] clientesCuentaCorriente = new String[] { "20000000002" };
		Banco.crearCuentaCorriente(clientesCuentaCorriente, 5000.0, 10000.0);
	}

	/*
	 * Prueba error con cuit invalido
	 */

	@Test
	public void testCUITInvalido() throws CUITInvalidoException,
			CUITYaAsignadoException {

		excepcionEsperada.expect(CUITInvalidoException.class);
		Banco.agregarPersonaFisica("2001", "Roberto Gomez Bolaños", domicilio,
				"0303456", "DNI", "00000000", "Soltero", "Psicólogo",
				"Doña Florinda");
	}

	/*
	 * Prueba error crea un cuit ya existente
	 */

	@Test
	public void testCUITYaAsignado() throws CUITInvalidoException,
			CUITYaAsignadoException, CBUInexistenteException {

		excepcionEsperada.expect(CUITYaAsignadoException.class);
		Banco.agregarPersonaFisica("20000000001", "Roberto Gomez Bolaños",
				domicilio, "0303456", "DNI", "00000000", "Soltero",
				"Psicólogo", "Doña Florinda");

	}

	/*
	 * Prueba error buscar un cliente inexistente en la lista
	 */

	@Test
	public void testClienteInexistente() throws ClienteInexistenteException,
			CUITInvalidoException {

		excepcionEsperada.expect(ClienteInexistenteException.class);
		Banco.buscarCliente("06513210326");

	}

	/*
	 * Prueba error setear un valor negativo en una tasa
	 */

	@Test
	public void testTasaDeInteresNegativa()
			throws DepositoInicialInvalidoException, SinClientesException,
			ClienteInexistenteException, CUITInvalidoException,
			TasaDeInteresNegativaException {

		excepcionEsperada.expect(TasaDeInteresNegativaException.class);
		Banco.crearCajaDeAhorro(new String[] { "20000000001" }, 10000.0, -10.0,
				TipoDeMoneda.PESO);

	}

	/*
	 * Prueba error depositar en cuenta inhabilidata
	 */

	@Test
	public void testDepositoCuentaInhabilitada()
			throws CBUInexistenteException, CuentaInhabilitadaException,
			DepositoInicialInvalidoException, ClienteInexistenteException,
			SinClientesException, CUITInvalidoException,
			SobregiroNegativoException {

		excepcionEsperada.expect(CuentaInhabilitadaException.class);

		String[] clientesCuentaCorriente = new String[] { "20000000002" };
		int cbu = Banco.crearCuentaCorriente(clientesCuentaCorriente, 50000.0,
				10000.0);
		Banco.inhabilitarCuenta(cbu);
		Banco.depositoEnEfectivo(cbu, 100.0);
	}

	/*
	 * Prueba error CBU inexistente
	 */

	@Test
	public void testCBUInexistente() throws CBUInexistenteException {

		excepcionEsperada.expect(CBUInexistenteException.class);
		Banco.inhabilitarCuenta(54654);
	}

	/*
	 * Prueba error numero de movimiento invalido
	 */

	@Test
	public void testNumeroDeMovimientoInvalido()
			throws CBUInexistenteException,
			NumeroDeMovimientosInvalidosException {

		excepcionEsperada.expect(NumeroDeMovimientosInvalidosException.class);
		Banco.listarTodosLosMovimientosDeCuenta(2);
		Banco.listarLosUltimosMovimientosDeCuenta(2, -10);
	}

	/*
	 * Prueba error debitar saldo insuficiente
	 */

	@Test
	public void testSaldoInsuficiente() throws SaldoInsuficienteException,
			CuentaInhabilitadaException, CBUInexistenteException {

		excepcionEsperada.expect(SaldoInsuficienteException.class);
		Banco.extraccionEnEfectivoEnCajaDeAhorro(2, 1000000.0);
	}

	/*
	 * Prueba error costo de mantenimiento nevativo
	 */

	@Test
	public void testCostoDeMantenimientonegativo()
			throws CostoDeMantenimientoNoPositivoException {

		excepcionEsperada.expect(CostoDeMantenimientoNoPositivoException.class);
		ProcesoBatch.setCOSTO_DE_MANTENIMIENTO(-10.0);
	}

	/*
	 * prueba extraer mas de lo que tengo
	 */
	
	@Test
	public void testExtraerMasDeLoQueTengo() throws CUITInvalidoException,
			CUITYaAsignadoException, DepositoInicialInvalidoException,
			SinClientesException, ClienteInexistenteException,
			TasaDeInteresNegativaException, SaldoInsuficienteException,
			CuentaInhabilitadaException, CBUInexistenteException {
		excepcionEsperada.expect(SaldoInsuficienteException.class);
		Domicilio domicilio2 = new Domicilio("42 Wallaby", "2222", "P Sherman",
				"Sidney");

		Banco.agregarPersonaFisica("20000000003", "Roberto Gomez Bolaño",
				domicilio2, "0303454", "DNI", "00000000", "Soltero",
				"Psicólogo", "Doña Florinda");

		String[] clientesCajaDeAhorro = new String[] { "20000000003" };
		int cbu = Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 5000.0, 0.1,
				TipoDeMoneda.PESO);

		Banco.extraccionEnEfectivoEnCajaDeAhorro(cbu, 50001.0);

		
	}
	
	/*
	 * prueba debitar
	 */

	@Test
	public void testDebitar() throws SaldoInsuficienteException, CuentaInhabilitadaException, CBUInexistenteException, DepositoInicialInvalidoException, SinClientesException, ClienteInexistenteException, CUITInvalidoException, TasaDeInteresNegativaException, CUITYaAsignadoException{
		Domicilio domicilio2 = new Domicilio("43 Wallaby", "2222", "P Sherman",
				"Sidney");

		Banco.agregarPersonaFisica("20000000004", "Roberto Gomez Bolaño",
				domicilio2, "0303454", "DNI", "00000000", "Soltero",
				"Psicólogo", "Doña Florinda");

		String[] clientesCajaDeAhorro = new String[] { "20000000004" };
		int cbu = Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 50000.0, 0.1,
				TipoDeMoneda.PESO);

		Banco.extraccionEnEfectivoEnCajaDeAhorro(cbu, 50.0);

		Assert.assertEquals(49950.0, Banco.buscarCuenta(cbu).getSaldo(), 0.1);
	
	}
	
}
