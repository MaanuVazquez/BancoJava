package entidad.bancaria.cuentas;

import java.util.ArrayList;
import java.util.Arrays;

import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;
import entidad.bancaria.excepciones.SinClientesException;

public abstract class Cuenta implements Comparable<Cuenta> {

	protected ArrayList<Transaccion> transacciones;
	protected Double saldo;
	protected TipoDeMoneda tipoDeMoneda;
	protected static Integer CBU_MAX = 1;
	private Integer cbu;
	private boolean habilitada;
	private Cliente[] clientes;

	/**
	 * Crea una cuenta, asignandole un cbu unico.
	 * @param clientes : arreglo de Clientes
	 * @throws SinClientesException
	 */
	
	public Cuenta(Cliente[] clientes) throws SinClientesException {
		if(clientes == null){
			throw new SinClientesException();
		}
		this.transacciones = new ArrayList<Transaccion>();
		cbu = CBU_MAX;
		this.clientes = clientes;
		this.habilitada = true;
		for(Cliente cliente : clientes){
			cliente.asignarCuenta(this);
		}
	}
	
	/**
	 * Si la cuenta esta habilitada y tiene saldo suficiente debita el monto recibido.
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a debitar
	 * @param motivo : motivo del debito
	 * @throws SaldoInsuficienteException
	 * @throws CuentaInhabilitadaException
	 */
	
	public void debitar(Double monto, MotivoDeTransaccion motivo)
			throws SaldoInsuficienteException, CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.cbu);
		}
		if (monto > this.saldo) {
			throw new SaldoInsuficienteException(this.cbu, monto, this.saldo);
		}
		this.transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto, motivo));
		this.saldo -= monto;
	}
	
	/**
	 * Si la cuenta esta habilitada y tiene saldo suficiente debita el monto recibido.
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a debitar
	 * @param motivo : motivo del debito
	 * @param observaciones : Informacion adicional para registrar sobre la transaccion.
	 * @throws SaldoInsuficienteException
	 * @throws CuentaInhabilitadaException
	 */
	
	public void debitar(Double monto, MotivoDeTransaccion motivo, String observaciones)
			throws SaldoInsuficienteException, CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.cbu);
		}
		if (monto > this.saldo) {
			throw new SaldoInsuficienteException(this.cbu, monto, this.saldo);
		}
		this.transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto, motivo, observaciones));
		this.saldo -= monto;
	}
	
	/**
	 * Si la cuenta esta habilitada acredita el monto recibido.
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a acreditar
	 * @param motivo : motivo del credito
	 * @throws CuentaInhabilitadaException
	 */
	
	public void acreditar(Double monto, MotivoDeTransaccion motivo) throws CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.cbu);
		}
		this.saldo += monto;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo));
	}
	
	/**
	 * Si la cuenta esta habilitada acredita el monto recibido.
	 * Crea el registro de la transaccion.
	 * @param monto : cantidad a acreditar
	 * @param motivo : motivo del credito
	 * @param observaciones : Informacion adicional para registrar sobre la transaccion.
	 * @throws CuentaInhabilitadaException
	 */
	
	public void acreditar(Double monto, MotivoDeTransaccion motivo, String observaciones) throws CuentaInhabilitadaException {
		if (!this.isHabilitada()) {
			throw new CuentaInhabilitadaException(this.cbu);
		}
		this.saldo += monto;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo, observaciones));
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

	public Integer getCBU() {
		return cbu;
	}

	public Cliente[] getClientes() {
		return clientes;
	}

	public boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(boolean habilitada) {
		if (this.habilitada == false) {
			for (Cliente cliente : clientes) {
				cliente.activar();
			}
		}
		this.habilitada = habilitada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cbu == null) ? 0 : cbu.hashCode());
		result = prime * result + Arrays.hashCode(clientes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta otra = (Cuenta) obj;
		if (this.cbu != otra.cbu)
			return false;
		return true;
	}
	
	public int compareTo(Cuenta obj){
		if (obj == null){
			throw new NullPointerException();
		}
		if(this.cbu < obj.cbu){
			return 1;
		} else if (this.cbu > obj.cbu) {
			return -1;
		} else {
			return 0;
		}
	}
}
