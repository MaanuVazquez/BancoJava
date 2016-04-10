package cuentas;

import java.util.ArrayList;

import exepciones.CuentaInhabilitadaException;
import exepciones.SaldoInsuficienteException;

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

	public void acreditar(Double monto, String fecha, MotivoDeTransaccion motivo) throws CuentaInhabilitadaException{
		if(!this.isHabilitada()){
			throw new CuentaInhabilitadaException();
		}
		transacciones.add(new Transaccion(fecha, TipoDeMovimiento.CREDITO, monto, motivo, ""));
		this.saldo += monto;
	}
	
	public void debitar(Double monto, String fecha, MotivoDeTransaccion motivo) throws SaldoInsuficienteException, CuentaInhabilitadaException{
		if(!this.isHabilitada()){
			throw new CuentaInhabilitadaException();
		}
		if(monto > this.saldo){
			throw new SaldoInsuficienteException();
		}
		transacciones.add(new Transaccion(fecha, TipoDeMovimiento.DEBITO, monto, motivo, ""));
		this.saldo -= monto;
	}
}
