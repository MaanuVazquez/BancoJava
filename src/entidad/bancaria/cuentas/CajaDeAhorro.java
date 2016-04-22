package entidad.bancaria.cuentas;

import entidad.bancaria.clientes.PersonaFisica;

public class CajaDeAhorro extends CuentaDeCliente {

	private Double tasaDeInteres;

	public CajaDeAhorro(PersonaFisica[] clientes, Double saldo, Double tasaDeInteres, TipoDeMoneda tipoDeMoneda) {
		super(clientes);
		this.saldo = saldo;
		this.tasaDeInteres = tasaDeInteres;
		this.tipoDeMoneda = tipoDeMoneda;
	}

	public Double getTasaDeInteres() {
		return tasaDeInteres;
	}
}
