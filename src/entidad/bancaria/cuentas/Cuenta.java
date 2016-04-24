package entidad.bancaria.cuentas;

import java.util.ArrayList;

public class Cuenta {

	protected ArrayList<Transaccion> transacciones;
	protected Double saldo;
	protected TipoDeMoneda tipoDeMoneda;

	public Cuenta() {
		saldo = 0.0;
		tipoDeMoneda = TipoDeMoneda.PESO;
		this.transacciones = new ArrayList<Transaccion>();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((saldo == null) ? 0 : saldo.hashCode());
		result = prime * result + ((tipoDeMoneda == null) ? 0 : tipoDeMoneda.hashCode());
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
		if (saldo == null) {
			if (other.saldo != null)
				return false;
		} else if (!saldo.equals(other.saldo))
			return false;
		if (tipoDeMoneda != other.tipoDeMoneda)
			return false;
		return true;
	}
}
