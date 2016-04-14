package entidad.bancaria.banco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import entidad.bancaria.cuentas.CajaDeAhorro;
import entidad.bancaria.cuentas.MotivoDeTransaccion;
import entidad.bancaria.cuentas.TipoDeMoneda;
import entidad.bancaria.excepciones.CBUInexistenteException;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public class ProcesoBatch {
	
	private static ArrayList<CajaDeAhorro> cuentas;
	private static Double costoDeMantenimiento = 0.0;

	public static void agregarCuenta(CajaDeAhorro cuenta){
		try {
			if(cuenta.equals(Banco.buscarCuenta(cuenta.getCBU()))){
				cuentas.add(cuenta);
			}
		} catch (CBUInexistenteException e) {
			System.out.println(e);
		}
	}

	public static void cobroDeMantenimientos() throws IOException  {
		Calendar calendar = Calendar.getInstance();
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH + 1);
		int año = calendar.get(Calendar.YEAR);
		File cobrados = new File("MantenimientosCobrados " + año + "-" + mes + "-" + dia + ".txt");
		File errores = new File("ErroresMantenimiento " + año + "-" + mes + "-" + dia + ".txt");
		cobrados.createNewFile();
		errores.createNewFile();
		BufferedWriter cobradosBW = new BufferedWriter(new FileWriter(cobrados));
		BufferedWriter erroresBW = new BufferedWriter(new FileWriter(errores));
		
		for (int i = 0; i < cuentas.size(); i++) {
			
			debitarMantenimiento(i, cobradosBW, erroresBW);
			
		} 
		
		cobradosBW.close();
		erroresBW.close();
	}
	
	private static void debitarMantenimiento(int i, BufferedWriter cobradosBW, BufferedWriter erroresBW) throws IOException{
		
		double monto;
		
		if(cuentas.get(i).getTipoDeMoneda() == TipoDeMoneda.PESO){
			monto = costoDeMantenimiento;
		} else {
			monto = costoDeMantenimiento / Banco.getCotizacion();
		}
		
		try {
			cuentas.get(i).debitar(monto, MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);
			cobradosBW.write("CBU: " + cuentas.get(i).getCBU() + ", Tipo De Cuenta: Caja De Ahorro, " +
					monto + ", " + cuentas.get(i).getTipoDeMoneda() + ", " + Banco.getCotizacion() + ".\n");
			
			Banco.acreditarMantenimiento();
			
		} catch (SaldoInsuficienteException e) {
			
			erroresBW.write("CBU: " + cuentas.get(i).getCBU() + ", Tipo De Cuenta: Caja De Ahorro, " +
					monto + ", Motivo : Saldo insuficiente.\n");
			
		} catch (CuentaInhabilitadaException e) {
			
			erroresBW.write("CBU: " + cuentas.get(i).getCBU() + ", Tipo De Cuenta: Caja De Ahorro, " +
					monto + ", Motivo : CuentaInhabilitada.\n");
		}
	}
	

	public static double getCOSTO_DE_MANTENIMIENTO() {
		return costoDeMantenimiento;
	}

	public static void setCOSTO_DE_MANTENIMIENTO(Double costoDeMantenimiento) {
		ProcesoBatch.costoDeMantenimiento = costoDeMantenimiento;
	}

}
