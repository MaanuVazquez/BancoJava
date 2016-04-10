package entidad.bancaria.cuentas;

import java.util.Date;

public class Transaccion {

	/*
	 * Cambio de fecha a Date
	 */
	private Date fecha;
	private TipoDeMovimiento movimiento;
	private Double monto;
	private MotivoDeTransaccion motivo;
	private String observaciones;

	/*
	 * Constructor sin observaciones
	 * 
	 * Removido String fecha
	 */

	public Transaccion(TipoDeMovimiento movimiento, Double monto, MotivoDeTransaccion motivo) {
		this.fecha = new Date();
		this.movimiento = movimiento;
		this.monto = monto;
		this.motivo = motivo;
		this.observaciones = "";
	}

	/*
	 * Constructor con observaciones
	 * 
	 * Removido String fecha
	 */

	public Transaccion(TipoDeMovimiento movimiento, Double monto, MotivoDeTransaccion motivo, String observaciones) {
		this.fecha = new Date();
		this.movimiento = movimiento;
		this.monto = monto;
		this.motivo = motivo;
		this.observaciones = observaciones;
	}

	/*
	 * Cambio de retorno String->Date
	 */

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

	/*
	 * Método toString para mostrar la transacción
	 * 
	 * Agregado saltos de linea y chequeo de observación
	 */

	@Override
	public String toString() {

		String toString = "Transaccion: " + "\nFecha: " + this.fecha + "\nMovimiento: " + this.movimiento.toString()
				+ "\nMonto: " + this.monto + "\nMotivo: " + this.motivo.toString();

		if (this.observaciones != "")
			toString += "\nObservaciones: " + this.observaciones;

		return toString;
	}

}