package entidad.bancaria.cuentas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import entidad.bancaria.banco.Banco;
import entidad.bancaria.excepciones.CuentaInhabilitadaException;
import entidad.bancaria.excepciones.SaldoInsuficienteException;

public class CuentaMantenimiento extends Cuenta{

	private ArrayList<CajaDeAhorro> cuentas;
	private Double costoDeMantenimiento = 0.0;

	public CuentaMantenimiento(){
		super();
	}

	public void agregarCuenta(CajaDeAhorro cuenta){
		cuentas.add(cuenta);
	}

	public void retirarCuenta(CajaDeAhorro cuenta){
		cuentas.add(cuenta);
	}

	public void cobroDeMantenimientos() throws IOException {
		
		Calendar calendar = Calendar.getInstance();
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH + 1);
		int año = calendar.get(Calendar.YEAR);
		File cobrados = new File("MantenimientosCobrados " + año + "-" + mes + "-" + dia + ".txt");
		File errores = new File("ErroresMantenimiento " + año + "-" + mes + "-" + dia + ".txt");
		BufferedWriter cobradosBW = new BufferedWriter(new FileWriter(cobrados));
		BufferedWriter erroresBW = new BufferedWriter(new FileWriter(errores));
		
		for (int i = 0; i < cuentas.size(); i++) {
			
			debitarMantenimiento(i, cobradosBW, erroresBW);
			
		} 
		
		cobradosBW.close();
		erroresBW.close();
	}
	
	private void debitarMantenimiento(int i, BufferedWriter cobradosBW, BufferedWriter erroresBW) throws IOException{
		
		double monto;
		
		if(cuentas.get(i).getTipoDeMoneda() == TipoDeMoneda.PESO){
			monto = costoDeMantenimiento;
		} else {
			monto = costoDeMantenimiento / Banco.getCotizacion();
		}
		
		try {
			cuentas.get(i).debitar(monto);
			cobradosBW.write("CBU: " + cuentas.get(i).getCBU() + ", Tipo De Cuenta: Caja De Ahorro, " +
					monto + ", " + cuentas.get(i).getTipoDeMoneda() + ", " + Banco.getCotizacion() + ".\n");
			
			this.acreditar(costoDeMantenimiento);
			
		} catch (SaldoInsuficienteException e) {
			
			erroresBW.write("CBU: " + cuentas.get(i).getCBU() + ", Tipo De Cuenta: Caja De Ahorro, " +
					monto + ", Motivo : Saldo insuficiente.\n");
			
		} catch (CuentaInhabilitadaException e) {
			
			erroresBW.write("CBU: " + cuentas.get(i).getCBU() + ", Tipo De Cuenta: Caja De Ahorro, " +
					monto + ", Motivo : CuentaInhabilitada.\n");
		}
	}
	

	public Double getCOSTO_DE_MANTENIMIENTO() {
		return costoDeMantenimiento;
	}

	public void setCOSTO_DE_MANTENIMIENTO(Double costoDeMantenimiento) {
		this.costoDeMantenimiento = costoDeMantenimiento;
	}
}