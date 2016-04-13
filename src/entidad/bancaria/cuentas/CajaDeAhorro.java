package entidad.bancaria.cuentas;

import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.excepciones.SaldoInsuficienteException;
import entidad.bancaria.excepciones.SinClientesException;

public class CajaDeAhorro extends Cuenta {

	private Cliente[] clientes;
	private Double tasaDeInteres;
	private Integer cbu;

	public CajaDeAhorro(Cliente[] clientes, Double saldo, Double tasaDeInteres,
			TipoDeMoneda tipoDeMoneda) throws SaldoInsuficienteException,
			SinClientesException {
		super();
		if (saldo <= 0) {
			throw new SaldoInsuficienteException();
		} else if (clientes == null) {
			throw new SinClientesException();
		}
		this.clientes = clientes;
		this.saldo = saldo;
		this.tasaDeInteres = tasaDeInteres;
		setTipoDeMoneda(tipoDeMoneda);
	}

	public Integer getCBU() {
		return cbu;
	}

	public Double getTasaDeInteres() {
		return tasaDeInteres;
	}

	public Cliente[] getClientes() {
		return clientes;
	}
}
