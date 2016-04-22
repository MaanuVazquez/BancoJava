package entidad.bancaria.banco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import entidad.bancaria.cuentas.CajaDeAhorro;
import entidad.bancaria.cuentas.MotivoDeTransaccion;
import entidad.bancaria.cuentas.TipoDeMoneda;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public class ProcesoBatch {

	private static Double costoDeMantenimiento = 0.0;

	public static void cobroDeMantenimientos(HashSet<CajaDeAhorro> cuentas) throws IOException {
		String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		File cobrados = new File("MantenimientosCobrados " + fecha + ".txt");
		File errores = new File("ErroresMantenimiento " + fecha + ".txt");
		cobrados.createNewFile();
		errores.createNewFile();
		BufferedWriter cobradosBW = new BufferedWriter(new FileWriter(cobrados));
		BufferedWriter erroresBW = new BufferedWriter(new FileWriter(errores));

		for (CajaDeAhorro cuenta : cuentas) {

			debitarMantenimiento(cuenta, cobradosBW, erroresBW);

		}

		cobradosBW.close();
		erroresBW.close();
	}

	private static void debitarMantenimiento(CajaDeAhorro cuenta, BufferedWriter cobradosBW, BufferedWriter erroresBW)
			throws IOException {

		double monto;
		if (cuenta.getTipoDeMoneda() == TipoDeMoneda.PESO) {
			monto = costoDeMantenimiento;
		} else {
			monto = costoDeMantenimiento / Banco.getCotizacion();
		}

		try {
			cuenta.debitar(monto, MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);
			cobradosBW.write("CBU: " + cuenta.getCBU() + ", Tipo De Cuenta: Caja De Ahorro, " + monto + ", "
					+ cuenta.getTipoDeMoneda() + ", " + Banco.getCotizacion() + ".\n");

			Banco.acreditarMantenimiento();

		} catch (SaldoInsuficienteException e) {

			erroresBW.write("CBU: " + cuenta.getCBU() + ", Tipo De Cuenta: Caja De Ahorro, " + monto
					+ ", Motivo : Saldo insuficiente.\n");

		} catch (CuentaInhabilitadaException e) {

			erroresBW.write("CBU: " + cuenta.getCBU() + ", Tipo De Cuenta: Caja De Ahorro, " + monto
					+ ", Motivo : CuentaInhabilitada.\n");
		}
	}

	public static double getCOSTO_DE_MANTENIMIENTO() {
		return costoDeMantenimiento;
	}

	public static void setCOSTO_DE_MANTENIMIENTO(Double costoDeMantenimiento) {
		ProcesoBatch.costoDeMantenimiento = costoDeMantenimiento;
	}

}