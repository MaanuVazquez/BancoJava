package entidad.bancaria.banco;

import entidad.bancaria.cuentas.Transaccion;
import entidad.bancaria.excepciones.CBUInexistenteException;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.NumeroDeMovimientosInvalidosException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

/*
 * Operatoria Bancaria: Operaciones por ventanilla
 */

	public class OperacionPorVentanilla {

	/**
	 * Acredita en la cuenta con el CBU indicado un monto de dinero.
	 * @param cbu : Numero de cuenta
	 * @param monto : Cantidad de dinero a depositar en moneda de la cuenta.
	 */

	public void depositoEnEfectivo(int cbu, Double monto) {
		try {
			Banco.depositoEnEfectivo(cbu, monto);
		} catch (CBUInexistenteException e) {
			System.out.println(e);
		} catch (CuentaInhabilitadaException e) {
			System.out.println(e);
		}
	}

	/**
	 * Debita en la cuenta con el CBU indicado un monto de dinero. Solo permitido para Cajas De Ahorro.
	 * @param cbu Numero de cuenta
	 * @param monto : Cantidad de dinero a retirar en moneda de la cuenta.
	 */

	public void extraccionEnEfectivo(int cbu, Double monto) {
		try {
			Banco.extraccionEnEfectivo(cbu, monto);
		} catch (CBUInexistenteException e) {
			System.out.println(e);
		} catch (CuentaInhabilitadaException e) {
			System.out.println(e);
		} catch (SaldoInsuficienteException e) {
			System.out.println(e);
		}
	}

	/**
	 * Transfiere una suma de dinero de una cuenta a otra, en caso de que las cuentas esten valoradas 
	 * en distinto tipo de moneda se realizara la conversion utilizando la cotizacion acutal,
	 * utilizando como base el monto de la cuenta de origen de la transaccion.
	 * @param CBUdelOrigen : CBU de la cuenta que transfiere el dinero.
	 * @param CBUdelDestino : CBU de la cuenta que recibe el dinero.
	 * @param monto : Cantidad de dinero a transferir en moneda de la cuenta de origen.
	 */

	public void transferenciaAOtraCuenta (int CBUDelOrigen, int CBUDelDestino, Double monto){
		try {
			Banco.transferencia(CBUDelOrigen, CBUDelDestino, monto);
		} catch (CBUInexistenteException e) {
			System.out.println(e);
		} catch (CuentaInhabilitadaException e) {
			System.out.println(e);;
		} catch (SaldoInsuficienteException e) {
			System.out.println(e);
		}
	}

	/**
	 * Lista todos los movimientos realizados de la cuenta
	 * @param CBU : CBU de la cuenta que se desea obtener los movimientos
	 * @return Arreglo de todas las transacciones de la cuenta. Devuelve un arreglo vacio en caso de recibir un cbu invalido.
	 */

	public static Transaccion[] listarMovimientosDeCuenta(int cbu) {
		try {
			return Banco.listarTodosLosMovimientosDeCuenta(cbu);
		} catch (CBUInexistenteException e) {
			System.out.println(e);
		}
		return new Transaccion[0];
	}

	/**
	 * Lista los ultimos CANTIDADDEMOVIMIENTOS movimientos de la cuenta.
	 * @param CBU : CBU de la cuenta que se desea obtener los movimientos.
	 * @param cantidadDeMovimientos : cantidad de movimientos que se desea obtener.
	 * @return Arreglo de las ultimas CANTIDADDEMOVIMIENTOS transacciones de la cuenta. 
	 * En caso de tener menos transacciones que CANTIDADDEMOVIMIENTOS devuelve todas sus transacciones.
	 * Devuelve un arreglo vacio en caso de recibir un cbu invalido.
	 */

	public static Transaccion[] listarMovimientosDeCuenta(int cbu, int cantidadDeMovimientos) {
		Transaccion[] ultimasTransacciones = new Transaccion[0];
		try {
			ultimasTransacciones = Banco.listarLosUltimosMovimientosDeCuenta(cbu, cantidadDeMovimientos);
		} catch (CBUInexistenteException e) {
			System.out.println(e);
		} catch (NumeroDeMovimientosInvalidosException e) {
			System.out.println(e);
		}
		return ultimasTransacciones;
	}
}
