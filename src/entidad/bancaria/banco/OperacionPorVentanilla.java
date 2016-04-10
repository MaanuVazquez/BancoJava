package entidad.bancaria.banco;

import entidad.bancaria.cuentas.Cuenta;
import entidad.bancaria.cuentas.CuentaCorriente;
import entidad.bancaria.cuentas.MotivoDeTransaccion;
import entidad.bancaria.cuentas.Transaccion;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.OperacionNoPermitidaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

/*
 * Operatoria Bancaria: Operaciones por ventanilla
 */

public class OperacionPorVentanilla {

	/*
	 * 1. Depósito en efectivo
	 */

	public static void depositoEnEfectivo(Cuenta cuentaDestino, Double monto, String fecha)
			throws CuentaInhabilitadaException {

		cuentaDestino.acreditar(monto, MotivoDeTransaccion.DEPOSITO_POR_VENTANILLA);
	}

	/*
	 * 2. Extracción en efectivo de Caja de Ahorro.
	 * 
	 * 3. Extracción en efectivo de Cuenta Corriente - No permitida
	 */

	public static void extraccionEnEfectivo(Cuenta cuentaDestino, Double monto, String fecha)
			throws CuentaInhabilitadaException, SaldoInsuficienteException, OperacionNoPermitidaException {

		if (cuentaDestino instanceof CuentaCorriente) {
			throw new OperacionNoPermitidaException();
		}

		cuentaDestino.debitar(monto, MotivoDeTransaccion.EXTRACCION_POR_VENTANILLA);

	}

	public static void transaccion() {
		// ToDo jeje
	}

	public static Transaccion[] listarMovimientosDeCuenta(Cuenta cuenta) {
		return (Transaccion[]) cuenta.getTransacciones().toArray();
	}

	public static Transaccion[] listarUltimosMovimientosDeCuenta(Cuenta cuenta, Integer cantidadDeMovimientos) {
		Transaccion[] transacciones;
		if (cantidadDeMovimientos > cuenta.getTransacciones().size()) {
			cantidadDeMovimientos = cuenta.getTransacciones().size();
		}
		transacciones = new Transaccion[cantidadDeMovimientos];
		int ultimoElemento = cuenta.getTransacciones().lastIndexOf(transacciones);
		for (int i = ultimoElemento; i > (ultimoElemento - cantidadDeMovimientos); i--) {
			transacciones[(ultimoElemento - cantidadDeMovimientos) + i] = cuenta.getTransacciones().get(i);
		}
		return transacciones;
	}

}
