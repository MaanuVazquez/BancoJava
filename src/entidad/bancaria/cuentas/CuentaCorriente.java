package entidad.bancaria.cuentas;

import entidad.bancaria.banco.Banco;
import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.DepositoInicialInvalidoException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;
import entidad.bancaria.excepciones.SinClientesException;
import entidad.bancaria.excepciones.SobregiroNegativoException;

public class CuentaCorriente extends Cuenta {

	private Double sobregiro;
	private static Double COMISION = 0.03;

	public CuentaCorriente(Cliente[] clientes, Double saldo, Double sobregiro) throws DepositoInicialInvalidoException, SinClientesException, SobregiroNegativoException {
		super(clientes);
		if (saldo < 10000) {
			throw new DepositoInicialInvalidoException(saldo, 10000.0);
		}
		if (sobregiro < 0){
			throw new SobregiroNegativoException(sobregiro);
		}
		Cuenta.CBU_MAX++;
		this.tipoDeMoneda = TipoDeMoneda.PESO;
		this.saldo = saldo;
		this.sobregiro = sobregiro;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, saldo, MotivoDeTransaccion.DEPOSITO_INICIAL));
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
	
	/**
	 * Si la cuenta esta habilitada acredita el monto recibido. Y debita la comision
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a acreditar
	 * @param motivo : motivo del credito
	 * @throws CuentaInhabilitadaException
	 */
	
	@Override
	public void acreditar(Double monto, MotivoDeTransaccion motivo) throws CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.getCBU());
		}
		transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo));
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto * COMISION,
				MotivoDeTransaccion.RETENCION_DE_IMPUESTOS));
		this.saldo += monto * (1 - COMISION);
		Banco.cobrarRetenciones(monto * (COMISION));
	}
	
	/**
	 * Si la cuenta esta habilitada acredita el monto recibido. Y debita la comision
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a acreditar
	 * @param motivo : motivo del credito
	 * @param observaciones : Informacion adicional para registrar sobre la transaccion.
	 * @throws CuentaInhabilitadaException
	 */
	
	@Override
	public void acreditar(Double monto, MotivoDeTransaccion motivo, String observaciones) throws CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.getCBU());
		}
		transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo, observaciones));
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto * COMISION,
				MotivoDeTransaccion.RETENCION_DE_IMPUESTOS, observaciones));
		this.saldo += monto * (1 - COMISION);
		Banco.cobrarRetenciones(monto * (COMISION));
	}
	
	/**
	 * Si la cuenta esta habilitada y tiene saldo suficiente debita el monto recibido y la comision.
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a debitar
	 * @param motivo : motivo del debito
	 * @throws SaldoInsuficienteException
	 * @throws CuentaInhabilitadaException
	 */

	@Override
	public void debitar(Double monto, MotivoDeTransaccion motivo) throws SaldoInsuficienteException, CuentaInhabilitadaException {

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
	
	/**
	 * Si la cuenta esta habilitada y tiene saldo suficiente debita el monto recibido y la comision.
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a debitar
	 * @param motivo : motivo del debito
	 * @param observaciones : Informacion adicional para registrar sobre la transaccion.
	 * @throws SaldoInsuficienteException
	 * @throws CuentaInhabilitadaException
	 */
	
	@Override
	public void debitar(Double monto, MotivoDeTransaccion motivo, String observaciones) throws SaldoInsuficienteException, CuentaInhabilitadaException {

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
