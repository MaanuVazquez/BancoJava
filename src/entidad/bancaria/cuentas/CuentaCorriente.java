package entidad.bancaria.cuentas;

import entidad.bancaria.banco.Banco;
import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public class CuentaCorriente extends CuentaDeCliente {

	private Double sobregiro;
	private static Double COMISION = 0.03;

	public CuentaCorriente(Cliente[] clientes, Double saldo, Double sobregiro) {
		super(clientes);
		this.saldo = saldo;
		this.sobregiro = sobregiro;
	}

	public Double getSobregiro() {
		return sobregiro;
	}

	public static Double getComision() {
		return COMISION;
	}

	public static void setComision(Double comision) {
		COMISION = comision;
	}

	public void acreditar(Double monto, String fecha, MotivoDeTransaccion motivo) throws CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.getCBU());
		}
		transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo));
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto * COMISION,
				MotivoDeTransaccion.RETENCION_DE_IMPUESTOS));
		this.saldo += monto * (1 - COMISION);
		Banco.cobrarRetenciones(monto * (COMISION));
	}
	
	public void acreditar(Double monto, String fecha, MotivoDeTransaccion motivo, String observaciones) throws CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.getCBU());
		}
		transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo, observaciones));
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto * COMISION,
				MotivoDeTransaccion.RETENCION_DE_IMPUESTOS, observaciones));
		this.saldo += monto * (1 - COMISION);
		Banco.cobrarRetenciones(monto * (COMISION));
	}

	public void debitar(Double monto, String fecha, MotivoDeTransaccion motivo) throws SaldoInsuficienteException, CuentaInhabilitadaException {

		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.getCBU());
		}
		if ((monto * (1 + COMISION)) > (this.saldo + this.sobregiro)) {
			throw new SaldoInsuficienteException(this.getCBU(), monto, this.saldo);
		}
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto, motivo));
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto * COMISION,
				MotivoDeTransaccion.RETENCION_DE_IMPUESTOS));
		this.saldo -= monto * (1 + COMISION);
		Banco.cobrarRetenciones(monto * (COMISION));
	}
	
	public void debitar(Double monto, String fecha, MotivoDeTransaccion motivo, String observaciones) throws SaldoInsuficienteException, CuentaInhabilitadaException {

		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.getCBU());
		}
		if ((monto * (1 + COMISION)) > (this.saldo + this.sobregiro)) {
			throw new SaldoInsuficienteException(this.getCBU(), monto, this.saldo);
		}
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto, motivo, observaciones));
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto * COMISION,
				MotivoDeTransaccion.RETENCION_DE_IMPUESTOS, observaciones));
		this.saldo -= monto * (1 + COMISION);
		Banco.cobrarRetenciones(monto * (COMISION));
	}

}
