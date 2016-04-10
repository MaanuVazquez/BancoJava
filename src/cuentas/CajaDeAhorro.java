package cuentas;

import clientes.Cliente;
import exepciones.SaldoInsuficienteException;
import exepciones.SinClientesException;

public class CajaDeAhorro extends Cuenta{
	
	private Cliente[] clientes;
	private Double tasaDeInteres;
	private Integer cbu;
	private static Double COSTO_DE_MANTENIMIENTO_EN_PESOS = 0.0;
	private static Double COSTO_DE_MANTENIMIENTO_EN_DOLARES = 0.0;

	public CajaDeAhorro(Cliente[] clientes, Double saldo, Double tasaDeInteres)
			throws SaldoInsuficienteException, SinClientesException{
		super();
		if(saldo <= 0){
			throw new SaldoInsuficienteException();
		} else if (clientes == null){
			throw new SinClientesException();
		}
		this.clientes = clientes;
		this.saldo = saldo;
		this.tasaDeInteres = tasaDeInteres;
	}
	
	public Integer getCBU() {
		return cbu;
	}

	public Double getTasaDeInteres() {
		return tasaDeInteres;
	}

	public Cliente[] getClientes() {
		return clientes;
	}

	public static Double getCOSTO_DE_MANTENIMIENTO_EN_PESOS() {
		return COSTO_DE_MANTENIMIENTO_EN_PESOS;
	}

	public static void setCOSTO_DE_MANTENIMIENTO_EN_PESOS(
			Double cOSTO_DE_MANTENIMIENTO_EN_PESOS) {
		COSTO_DE_MANTENIMIENTO_EN_PESOS = cOSTO_DE_MANTENIMIENTO_EN_PESOS;
	}

	public static Double getCOSTO_DE_MANTENIMIENTO_EN_DOLARES() {
		return COSTO_DE_MANTENIMIENTO_EN_DOLARES;
	}

	public static void setCOSTO_DE_MANTENIMIENTO_EN_DOLARES(
			Double cOSTO_DE_MANTENIMIENTO_EN_DOLARES) {
		COSTO_DE_MANTENIMIENTO_EN_DOLARES = cOSTO_DE_MANTENIMIENTO_EN_DOLARES;
	}

}
