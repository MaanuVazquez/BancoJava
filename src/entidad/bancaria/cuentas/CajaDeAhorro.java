package entidad.bancaria.cuentas;

import entidad.bancaria.clientes.Cliente;

public class CajaDeAhorro extends CuentaDeCliente {

	private Cliente[] clientes;
	private Double tasaDeInteres;

	public CajaDeAhorro(Cliente[] clientes, Double saldo, Double tasaDeInteres, TipoDeMoneda tipoDeMoneda) {
		super();
		this.clientes = clientes;
		this.saldo = saldo;
		this.tasaDeInteres = tasaDeInteres;
		this.tipoDeMoneda = tipoDeMoneda;
	}

	public Double getTasaDeInteres() {
		return tasaDeInteres;
	}

	public Cliente[] getClientes() {
		return clientes;
	}
}
