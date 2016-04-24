package entidad.bancaria.cuentas;

import java.util.Arrays;

import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public class CuentaDeCliente extends Cuenta {

	private static Integer CBU_MAX = 1;
	private Integer cbu;
	private boolean habilitada;
	private Cliente[] clientes;

	public CuentaDeCliente(Cliente[] clientes) {
		cbu = CBU_MAX;
		CBU_MAX++;
		this.clientes = clientes;
		this.habilitada = true;
	}

	public Integer getCBU() {
		return cbu;
	}

	public Cliente[] getClientes() {
		return clientes;
	}

	public boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(boolean habilitada) {
		if (this.habilitada == false) {
			for (Cliente cliente : clientes) {
				cliente.activar();
			}
		}
		this.habilitada = habilitada;
	}

	public void debitar(Double monto, MotivoDeTransaccion motivo)
			throws SaldoInsuficienteException, CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.cbu);
		}
		if (monto > this.saldo) {
			throw new SaldoInsuficienteException(this.cbu, monto, this.saldo);
		}
		this.transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto, motivo));
		this.saldo -= monto;
	}

	public void acreditar(Double monto, MotivoDeTransaccion motivo, String observaciones) {
		this.saldo += monto;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo, observaciones));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cbu == null) ? 0 : cbu.hashCode());
		result = prime * result + Arrays.hashCode(clientes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentaDeCliente other = (CuentaDeCliente) obj;
		if (cbu == null) {
			if (other.cbu != null)
				return false;
		} else if (!cbu.equals(other.cbu))
			return false;
		if (!Arrays.equals(clientes, other.clientes))
			return false;
		return true;
	}

}
