package entidad.bancaria.banco;

import java.util.ArrayList;

import entidad.bancaria.cuentas.Cuenta;
import entidad.bancaria.cuentas.CuentaCorriente;
import entidad.bancaria.cuentas.MotivoDeTransaccion;
import entidad.bancaria.cuentas.TipoDeMoneda;
import entidad.bancaria.cuentas.TipoDeMovimiento;
import entidad.bancaria.cuentas.Transaccion;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.NumeroDeMovimientosInvalidosException;
import entidad.bancaria.excepciones.OperacionNoPermitidaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

/*
 * Operatoria Bancaria: Operaciones por ventanilla
 */

public class OperacionPorVentanilla {

	/*
	 * 1. Depósito en efectivo
	 */

	public static void depositoEnEfectivo(Cuenta cuentaDestino, Double monto)
			throws CuentaInhabilitadaException {

		cuentaDestino.acreditar(monto);
		cuentaDestino.crearTransaccion(TipoDeMovimiento.CREDITO, monto,
				MotivoDeTransaccion.DEPOSITO_POR_VENTANILLA);
	}

	/**
	 * Extraccion en efectivo, para cuenta corriente esta operación no está
	 * permitida
	 * 
	 * @param cuentaDestino
	 * @param monto
	 * @throws CuentaInhabilitadaException
	 * @throws SaldoInsuficienteException
	 * @throws OperacionNoPermitidaException
	 */

	public static void extraccionEnEfectivo(Cuenta cuentaDestino, Double monto)
			throws CuentaInhabilitadaException, SaldoInsuficienteException,
			OperacionNoPermitidaException {

		if (cuentaDestino instanceof CuentaCorriente) {
			throw new OperacionNoPermitidaException();
		}

		cuentaDestino.debitar(monto);
		cuentaDestino.crearTransaccion(TipoDeMovimiento.DEBITO, monto,
				MotivoDeTransaccion.EXTRACCION_POR_VENTANILLA);

	}

	/**
	 * Transacción
	 * 
	 * @param cuentaOrigen
	 * @param cuentaDestino
	 * @param monto
	 * @throws SaldoInsuficienteException
	 * @throws CuentaInhabilitadaException
	 */

	public static void transaccion(Cuenta cuentaOrigen, Cuenta cuentaDestino,
			Double monto) throws SaldoInsuficienteException,
			CuentaInhabilitadaException {

		cuentaOrigen.debitar(monto);
		cuentaOrigen.crearTransaccion(TipoDeMovimiento.DEBITO, monto,
				MotivoDeTransaccion.TRANSFERENCIA);

		if (cuentaDestino.getTipoDeMoneda() == cuentaOrigen.getTipoDeMoneda()) {
			cuentaDestino.acreditar(monto);
			cuentaDestino.crearTransaccion(TipoDeMovimiento.DEBITO, monto,
					MotivoDeTransaccion.TRANSFERENCIA);
		} else if (cuentaOrigen.getTipoDeMoneda() == TipoDeMoneda.PESO) {
			monto = monto / Banco.cotizacionDolar;
			cuentaDestino.acreditar(monto);
			cuentaDestino.crearTransaccion(TipoDeMovimiento.DEBITO, monto,
					MotivoDeTransaccion.TRANSFERENCIA,
					"Conversión de Peso Argentino(ARS) a Dolar(USD): " + monto
							+ " Cotización: 1 ARS - " + Banco.cotizacionDolar
							+ " USD");
		} else {
			monto = monto * Banco.cotizacionDolar;
			cuentaDestino.acreditar(monto);
			cuentaDestino.crearTransaccion(TipoDeMovimiento.DEBITO, monto,
					MotivoDeTransaccion.TRANSFERENCIA,
					"Conversión de Dolar(USD) a Peso Argentino(ARS): " + monto
							+ " Cotización: 1 ARS - " + Banco.cotizacionDolar
							+ " USD");
		}

	}

	/**
	 * Listar movimientos de la cuenta
	 * 
	 * @param cuenta
	 * @return
	 */

	public static ArrayList<Transaccion> listarMovimientosDeCuenta(Cuenta cuenta) {

		ArrayList<Transaccion> array = new ArrayList<Transaccion>();

		for (Transaccion t : cuenta.getTransacciones()) {
			array.add(t);
		}

		return array;
	}

	/**
	 * Listar ultimos CANTIDADDEMOVIMIENTOS movimientos
	 * 
	 * @param cuenta
	 * @param cantidadDeMovimientos
	 * @return
	 * @throws NumeroDeMovimientosInvalidosException
	 */

	public static ArrayList<Transaccion> listarUltimosMovimientosDeCuenta(
			Cuenta cuenta, Integer cantidadDeMovimientos)
			throws NumeroDeMovimientosInvalidosException {

		if (cantidadDeMovimientos < 1
				|| cantidadDeMovimientos > cuenta.getTransacciones().size()) {
			throw new NumeroDeMovimientosInvalidosException();
		}

		ArrayList<Transaccion> ultimosMovimientos = new ArrayList<Transaccion>();

		int i = (cuenta.getTransacciones().size() - 1) - cantidadDeMovimientos;

		while (i < cuenta.getTransacciones().size()) {
			ultimosMovimientos.add(cuenta.getTransacciones().get(i));
			i++;
		}
		return ultimosMovimientos;
	}
}
