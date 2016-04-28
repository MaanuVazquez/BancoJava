package entidad.bancaria.tests;

import java.io.IOException;

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

	@Test
	public void ProcesoBatch() {
		try {
			Banco.cobroDeMantenimientos();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	@Test
	public void compararCuentas() throws TasaDeInteresNegativaException, DepositoInicialInvalidoException, SinClientesException, CUITInvalidoException{
		PersonaFisica persona = new PersonaFisica("20000000001", "Roberto Gomez Bolaños",
				domicilio, "0303456", "DNI", "00000000", "Soltero",
				"Psicólogo", "Doña Florinda");
		PersonaFisica[] personas = new PersonaFisica[1];
		personas[0] = persona;
		Cuenta cuenta1 = new CajaDeAhorro (personas, 0.1, 0.1, TipoDeMoneda.PESO);
		Cuenta cuenta2 = new CajaDeAhorro (personas, 0.1, 0.1, TipoDeMoneda.PESO);
		Assert.assertFalse(cuenta1.equals(cuenta2));
	}
}
