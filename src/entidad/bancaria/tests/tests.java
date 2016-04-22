package entidad.bancaria.tests;

import java.io.IOException;

import entidad.bancaria.banco.Banco;
import entidad.bancaria.clientes.Domicilio;
import entidad.bancaria.cuentas.TipoDeMoneda;
import entidad.bancaria.excepciones.CUITYaAsignadoException;

public class tests {
	public static void main(String[] args) throws IOException, CUITYaAsignadoException {
		Domicilio casaManu = new Domicilio("LT2151", "1678", "Caseros", "Buenos Aires");
		Banco.agregarPersonaFisica("20387878034", "Emmanuel", casaManu, "47595154", "DNI", "38787803", "Soltero",
				"Ing. Computacion", "wut");
			Banco.crearCajaDeAhorro(new String[] { "20387878034" }, 10000.0, 0.1, TipoDeMoneda.PESO);
		Banco.cobroDeMantenimientos();
	}
}
