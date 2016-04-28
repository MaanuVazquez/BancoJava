package entidad.bancaria.cuentas;

import java.util.Date;

public class Transaccion {

	private Date fecha;
	private TipoDeMovimiento movimiento;
	private Double monto;
	private MotivoDeTransaccion motivo;
	private String observaciones;

	/**
	 * Crea el registro de la transaccion
	 * @param movimiento : tipo de movimiento (credito o debito)
	 * @param monto : valor de la transaccion.
	 * @param motivo : razon de la transaccion.
	 */
	
	public Transaccion(TipoDeMovimiento movimiento, Double monto, MotivoDeTransaccion motivo) {
		this.fecha = new Date();
		this.movimiento = movimiento;
		this.monto = monto;
		this.motivo = motivo;
		this.observaciones = "";
	}

	/**
	 * Crea el registro de la transaccion
	 * @param movimiento : tipo de movimiento (credito o debito)
	 * @param monto : valor de la transaccion.
	 * @param motivo : razon de la transaccion.
	 * @param observaciones : Informacion adicional para registrar sobre la transaccion.
	 */
	
	public Transaccion(TipoDeMovimiento movimiento, Double monto, MotivoDeTransaccion motivo, String observaciones) {
		this.fecha = new Date();
		this.movimiento = movimiento;
		this.monto = monto;
		this.motivo = motivo;
		this.observaciones = observaciones;
	}

	/**
	 * @return Informacion de la transaccion en forma de String.
	 */
	
	@Override
	public String toString() {
		
		String toString = "Transaccion: " + "\nFecha: " + this.fecha + "\nMovimiento: " + this.movimiento.toString()
				+ "\nMonto: " + this.monto + "\nMotivo: " + this.motivo.toString();
		
		if (this.observaciones != "")
			toString += "\nObservaciones: " + this.observaciones;
		
		return toString;
	}

	public Date getFecha() {
		return fecha;
	}

	public TipoDeMovimiento getMovimiento() {
		return movimiento;
	}

	public Double getMonto() {
		return monto;
	}

	public MotivoDeTransaccion getMotivo() {
		return motivo;
	}
	public String getObservaciones() {
		return observaciones;
	}

}