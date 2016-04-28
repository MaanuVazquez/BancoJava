package entidad.bancaria.cuentas;

import java.util.ArrayList;

public class CuentaEspecial {

	protected ArrayList<Transaccion> transacciones;
	protected Double saldo;
	protected TipoDeMoneda tipoDeMoneda;

	/**
	 * Crea una cuenta especial.
	 * Cuentas de control del banco, no pertenecen a ningun cliente.
	 */
	
	public CuentaEspecial() {
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

	/**
	 * Acredita el monto recibido.
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a acreditar
	 * @param motivo : motivo del credito
	 */
	
	public void acreditar(Double monto, MotivoDeTransaccion motivo) {
		this.saldo += monto;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo));
	}
	
	/**
	 * Debita el monto recibido.
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a debitar
	 * @param motivo : motivo del debito
	 */
	
	public void debitar(Double monto, MotivoDeTransaccion motivo) {
		this.saldo -= monto;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto, motivo));
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
		CuentaEspecial other = (CuentaEspecial) obj;
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
