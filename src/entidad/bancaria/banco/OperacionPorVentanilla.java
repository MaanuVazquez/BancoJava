package entidad.bancaria.banco;

import entidad.bancaria.cuentas.CajaDeAhorro;
import entidad.bancaria.cuentas.Cuenta;
import entidad.bancaria.cuentas.MotivoDeTransaccion;
import entidad.bancaria.cuentas.TipoDeMoneda;
import entidad.bancaria.cuentas.Transaccion;
import entidad.bancaria.excepciones.CBUInexistenteException;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.NumeroDeMovimientosInvalidosException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public class OperacionPorVentanilla {

	/**
	 * Acredita en la cuenta con el CBU indicado un monto de dinero.
	 * 
	 * @param cbu
	 *            : Numero de cuenta
	 * @param monto
	 *            : Cantidad de dinero a depositar en moneda de la cuenta.
	 * @throws CBUInexistenteException
	 * @throws CuentaInhabilitadaException
	 */

	public static void depositoEnEfectivo(int cbu, Double monto)
			throws CBUInexistenteException, CuentaInhabilitadaException {
		Cuenta cuentaDestino;
		cuentaDestino = Banco.buscarCuenta(cbu);
		if (!cuentaDestino.isHabilitada()) {
			throw new CuentaInhabilitadaException(cbu);
		}
		cuentaDestino.acreditar(monto,
				MotivoDeTransaccion.DEPOSITO_POR_VENTANILLA);
	}

	/**
	 * Debita en la cuenta con el CBU indicado un monto de dinero. Solo
	 * permitido para Cajas De Ahorro.
	 * 
	 * @param cbu
	 *            Numero de cuenta.
	 * @param monto
	 *            : Cantidad de dinero a retirar en moneda de la cuenta.
	 * @throws CuentaInhabilitadaException
	 * @throws SaldoInsuficienteException
	 * @throws CBUInexistenteException
	 */

	public static void extraccionEnEfectivoEnCajaDeAhorro(int cbu, Double monto)
			throws SaldoInsuficienteException, CuentaInhabilitadaException,
			CBUInexistenteException {
		CajaDeAhorro cuentaDestino;
		cuentaDestino = Banco.buscarCajaDeAhorro(cbu);
		cuentaDestino.debitar(monto,
				MotivoDeTransaccion.EXTRACCION_POR_VENTANILLA);
	}

	/**
	 * Transfiere una suma de dinero de una cuenta a otra, en caso de que las
	 * cuentas esten valoradas en distinto tipo de moneda se realizara la
	 * conversion utilizando la cotizacion actual, utilizando como base el monto
	 * de la cuenta de origen de la transacción.
	 * 
	 * @param CBUdelOrigen
	 *            : CBU de la cuenta que transfiere el dinero.
	 * @param CBUdelDestino
	 *            : CBU de la cuenta que recibe el dinero.
	 * @param monto
	 *            : Cantidad de dinero a transferir en moneda de la cuenta de
	 *            origen.
	 * @throws CBUInexistenteException
	 * @throws CuentaInhabilitadaException
	 * @throws SaldoInsuficienteException
	 */

	public static void transferencia(int cbuDelOrigen, int cbuDelDestino,
			Double monto) throws CBUInexistenteException,
			CuentaInhabilitadaException, SaldoInsuficienteException {

		Cuenta cuentaOrigen;
		Cuenta cuentaDestino;
		cuentaOrigen = Banco.buscarCuenta(cbuDelOrigen);
		cuentaDestino = Banco.buscarCuenta(cbuDelDestino);

		if (!cuentaDestino.isHabilitada())
			throw new CuentaInhabilitadaException(cbuDelDestino);

		cuentaOrigen.debitar(monto, MotivoDeTransaccion.TRANSFERENCIA);

		if (cuentaDestino.getTipoDeMoneda() == cuentaOrigen.getTipoDeMoneda()) {
			cuentaDestino.acreditar(monto, MotivoDeTransaccion.TRANSFERENCIA);
		} else if (cuentaOrigen.getTipoDeMoneda() == TipoDeMoneda.PESO) {
			monto = monto / Banco.getCotizacion();
			cuentaDestino.acreditar(monto, MotivoDeTransaccion.TRANSFERENCIA,
					"Conversión de Peso Argentino(ARS) a Dolar(USD): " + monto
							+ " Cotización: 1 ARS - " + Banco.getCotizacion()
							+ " USD");
		} else {
			monto = monto * Banco.getCotizacion();
			cuentaDestino.acreditar(monto, MotivoDeTransaccion.TRANSFERENCIA,
					"Conversión de Dolar(USD) a Peso Argentino(ARS): " + monto
							+ " Cotización: 1 ARS - " + Banco.getCotizacion()
							+ " USD");
		}
	}

	/**
	 * Lista todos los movimientos realizados de la cuenta.
	 * 
	 * @param CBU
	 *            : CBU de la cuenta que se desea obtener los movimientos.
	 * @return Arreglo de todas las transacciones de la cuenta. Devuelve un
	 *         arreglo vacio en caso de recibir un cbu invalido.
	 * @throws CBUInexistenteException
	 */

	public static void listarTodosLosMovimientosDeCuenta(int cbu)
			throws CBUInexistenteException {
		Cuenta cuenta;
		cuenta = Banco.buscarCuenta(cbu);
		for (Transaccion transaccion : cuenta.getTransacciones()) {
			System.out.println(transaccion.toString());
		}
	}

	/**
	 * Lista los ultimos CANTIDADDEMOVIMIENTOS movimientos de la cuenta.
	 * 
	 * @param CBU
	 *            : CBU de la cuenta que se desea obtener los movimientos.
	 * @param cantidadDeMovimientos
	 *            : cantidad de movimientos que se desea obtener.
	 * @return : Arreglo de las ultimas CANTIDADDEMOVIMIENTOS transacciones de
	 *         la cuenta. En caso de tener menos transacciones que
	 *         CANTIDADDEMOVIMIENTOS devuelve todas sus transacciones. Devuelve
	 *         un arreglo vacio en caso de recibir un cbu invalido.
	 * @throws CBUInexistenteException
	 * @throws NumeroDeMovimientosInvalidosException
	 */

	public static void listarLosUltimosMovimientosDeCuenta(int cbu,
			int cantidadDeMovimientos) throws CBUInexistenteException,
			NumeroDeMovimientosInvalidosException {

		Cuenta cuenta;
		cuenta = Banco.buscarCuenta(cbu);
		if (cantidadDeMovimientos < 1) {
			throw new NumeroDeMovimientosInvalidosException(
					cantidadDeMovimientos);
		}
		if (cantidadDeMovimientos > cuenta.getTransacciones().size()) {
			cantidadDeMovimientos = cuenta.getTransacciones().size();
		}

		for (Transaccion transaccion : cuenta.getTransacciones().subList(
				cuenta.getTransacciones().size() - cantidadDeMovimientos,
				cuenta.getTransacciones().size())) {
			System.out.println(transaccion.toString());
		}
	}
}
