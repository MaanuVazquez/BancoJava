package entidad.bancaria.cuentas;

import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public class CuentaDeCliente extends Cuenta{

	private static Integer CBU_MAX = 1;
	private Integer cbu;
	private boolean habilitada;
	private Cliente[] clientes;
	
	public CuentaDeCliente(Cliente[] clientes){
		cbu = CBU_MAX;
		CBU_MAX++;
		this.clientes = clientes;
	}

	public Integer getCBU() {
		return cbu;
	}
	
	public Cliente[] getClientes(){
		return clientes;
	}

	public boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(boolean habilitada) {
		if(this.habilitada == false){
			for(Cliente cliente : clientes){
				cliente.activar();
			}
		}
		this.habilitada = habilitada;
	}
	
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
	
	public void acreditar(Double monto, MotivoDeTransaccion motivo, String observaciones) {
		this.saldo += monto;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo, observaciones));
	}

}
