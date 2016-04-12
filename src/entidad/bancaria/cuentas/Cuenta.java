package entidad.bancaria.cuentas;

import java.util.ArrayList;

import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public abstract class Cuenta {

	private static Integer CBU_MAX = 0;
	private Integer cbu;
	private boolean habilitada;
	protected ArrayList<Transaccion> transacciones;
	protected Double saldo;

	protected Cuenta() {
		cbu = CBU_MAX;
		CBU_MAX++;
		setHabilitada(true);
		saldo = 0.0;
	}

	public Integer getCBU() {
		return cbu;
	}

	public boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(boolean habilitada) {
		this.habilitada = habilitada;
	}

	public ArrayList<Transaccion> getTransacciones() {
		return transacciones;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void acreditar(Double monto) throws CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException();
		}
		this.saldo += monto;
	}

	public void debitar(Double monto) throws SaldoInsuficienteException, CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException();
		}
		if (monto > this.saldo) {
			throw new SaldoInsuficienteException();
		}
		this.saldo -= monto;
	}

	public void crearTransaccion(TipoDeMovimiento movimiento, Double monto, MotivoDeTransaccion motivo) {
		this.transacciones.add(new Transaccion(movimiento, monto, motivo));
	}

	public void crearTransaccion(TipoDeMovimiento movimiento, Double monto, MotivoDeTransaccion motivo,
			String observaciones) {
		this.transacciones.add(new Transaccion(movimiento, monto, motivo, observaciones));
	}
}
