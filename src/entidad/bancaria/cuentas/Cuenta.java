package entidad.bancaria.cuentas;

import java.util.ArrayList;

public class Cuenta {

	protected ArrayList<Transaccion> transacciones;
	protected Double saldo;
	protected TipoDeMoneda tipoDeMoneda;

	public Cuenta() {
		saldo = 0.0;
		tipoDeMoneda = TipoDeMoneda.PESO;
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

	public void acreditar(Double monto, MotivoDeTransaccion motivo) {
		this.saldo += monto;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo));
	}
}
