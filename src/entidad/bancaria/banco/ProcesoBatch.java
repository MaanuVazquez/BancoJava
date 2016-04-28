package entidad.bancaria.banco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import entidad.bancaria.cuentas.CajaDeAhorro;
import entidad.bancaria.cuentas.MotivoDeTransaccion;
import entidad.bancaria.cuentas.TipoDeMoneda;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public class ProcesoBatch {

	private static Double costoDeMantenimiento = 0.0;

	/**
	 * Cobra los mantenimientos de todas las cajas de ahorro. Crea el registro
	 * de las cuentas a las que se les debito, y el registro de errores.
	 * 
	 * @throws IOException
	 */

	public static void cobroDeMantenimientos(
			HashMap<Integer, CajaDeAhorro> cuentas) throws IOException {
		String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		File cobrados = new File("MantenimientosCobrados " + fecha + ".txt");
		File errores = new File("ErroresMantenimiento " + fecha + ".txt");
		cobrados.createNewFile();
		errores.createNewFile();
		BufferedWriter cobradosBW = new BufferedWriter(new FileWriter(cobrados));
		BufferedWriter erroresBW = new BufferedWriter(new FileWriter(errores));

		for (Entry<Integer, CajaDeAhorro> entry : cuentas.entrySet()) {

			debitarMantenimiento(entry.getValue(), cobradosBW, erroresBW);

		}

		cobradosBW.close();
		erroresBW.close();
	}

	/**
	 * Debita el mantenimiento de una CajaDeAhorro
	 * 
	 * @param cuenta
	 * @param cobradosBW
	 * @param erroresBW
	 * @throws IOException
	 */

	private static void debitarMantenimiento(CajaDeAhorro cuenta,
			BufferedWriter cobradosBW, BufferedWriter erroresBW)
			throws IOException {

		double monto;
		if (cuenta.getTipoDeMoneda() == TipoDeMoneda.PESO) {
			monto = costoDeMantenimiento;
		} else {
			monto = costoDeMantenimiento / Banco.getCotizacion();
		}

		try {
			cuenta.debitar(monto, MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);
			cobradosBW.write("CBU: " + cuenta.getCBU()
					+ ", Tipo De Cuenta: Caja De Ahorro, " + monto + ", "
					+ cuenta.getTipoDeMoneda() + ", " + Banco.getCotizacion()
					+ ".\n");

			Banco.mantenimiento.acreditar(costoDeMantenimiento,
					MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);

		} catch (SaldoInsuficienteException e) {

			erroresBW.write("CBU: " + cuenta.getCBU()
					+ ", Tipo De Cuenta: Caja De Ahorro, " + monto
					+ ", Motivo : Saldo insuficiente.\n");

		} catch (CuentaInhabilitadaException e) {

			erroresBW.write("CBU: " + cuenta.getCBU()
					+ ", Tipo De Cuenta: Caja De Ahorro, " + monto
					+ ", Motivo : CuentaInhabilitada.\n");
		}
	}

	/**
	 * Paga los intereses de las CajasDeAhorro habilitadas, segun el interes
	 * acordado al abrir la cuenta y el saldo actual.
	 */

	public static void pagarInteres(HashMap<Integer, CajaDeAhorro> cuentas) {
		for (Entry<Integer, CajaDeAhorro> entry : cuentas.entrySet()) {
			try {
				CajaDeAhorro cuenta = entry.getValue();
				double monto = cuenta.getTasaDeInteres() * cuenta.getSaldo();
				cuenta.acreditar(monto, MotivoDeTransaccion.PAGO_DE_INTERES);
				Banco.interesesPagados += monto;
			} catch (CuentaInhabilitadaException e) {

			}
		}
	}

	/**
	 * @return Costo de mantenimiento de las CajasDeAhorro.
	 */

	public static double getCOSTO_DE_MANTENIMIENTO() {
		return costoDeMantenimiento;
	}

	/**
	 * Asigna el costo de mantenimiento para las CajasDeAhorro.
	 * 
	 * @param costoDeMantenimiento
	 *            nuevo costo de mantenimiento.
	 */

	public static void setCOSTO_DE_MANTENIMIENTO(Double costoDeMantenimiento) {
		ProcesoBatch.costoDeMantenimiento = costoDeMantenimiento;
	}

}
