package cuentas;

public class Transaccion {

	private String fecha;
	private TipoDeMovimiento movimiento;
	private Double monto;
	private MotivoDeTransaccion motivo;
	private String observaciones;

	public Transaccion(String fecha, TipoDeMovimiento movimiento, Double monto,
			MotivoDeTransaccion motivo) {
		this.fecha = fecha;
		this.movimiento = movimiento;
		this.monto = monto;
		this.motivo = motivo;
		this.observaciones = "";
	}

	public Transaccion(String fecha, TipoDeMovimiento movimiento, Double monto,
			MotivoDeTransaccion motivo, String observaciones) {
		this.fecha = fecha;
		this.movimiento = movimiento;
		this.monto = monto;
		this.motivo = motivo;
		this.observaciones = observaciones;
	}

	public String getFecha() {
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

	public String toString() {
		return "Transaccion: " + "Fecha: " + this.fecha + " Movimiento: "
				+ this.movimiento.toString() + " Monto: " + this.monto
				+ " Motivo: " + this.motivo.toString() + " Observaciones: "
				+ this.observaciones;
	}

}