package entidad.bancaria.cuentas;

import java.util.ArrayList;
import java.util.Arrays;

import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public abstract class Cuenta {

	protected ArrayList<Transaccion> transacciones;
	protected Double saldo;
	protected TipoDeMoneda tipoDeMoneda;
	private static Integer CBU_MAX = 1;
	private Integer cbu;
	private boolean habilitada;
	private Cliente[] clientes;

	public Cuenta(Cliente[] clientes) {
		saldo = 0.0;
		tipoDeMoneda = TipoDeMoneda.PESO;
		this.transacciones = new ArrayList<Transaccion>();
		cbu = CBU_MAX;
		CBU_MAX++;
		this.clientes = clientes;
		this.habilitada = true;
	}
	
	public ArrayList<Transaccion> getTransacciones() {
		return transacciones;
	}

	public Double getSaldo() {
		return saldo;
	}

	public TipoDeMoneda getTipoDeMoneda() {
		return this.tipoDeMoneda;
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
	
	public void debitar(Double monto, MotivoDeTransaccion motivo, String observaciones)
			throws SaldoInsuficienteException, CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.cbu);
		}
		if (monto > this.saldo) {
			throw new SaldoInsuficienteException(this.cbu, monto, this.saldo);
		}
		this.transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto, motivo, observaciones));
		this.saldo -= monto;
	}
	
	public void acreditar(Double monto, MotivoDeTransaccion motivo) throws CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.cbu);
		}
		this.saldo += monto;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo));
	}
	
	public void acreditar(Double monto, MotivoDeTransaccion motivo, String observaciones) throws CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.cbu);
		}
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
		Cuenta other = (Cuenta) obj;
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
