package entidad.bancaria.cuentas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

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

	public void cobrarMantenimiento() throws IOException {

		File cobrados = new File("C//", "CobroDeMantenimiento " + new Date().toLocaleString() + ".txt");
		File errores = new File("C//", "CobroDeMantenimiento " + new Date().toLocaleString() + ".txt");
		BufferedWriter cobradosBW = new BufferedWriter(new FileWriter(cobrados));
		BufferedWriter erroresBW = new BufferedWriter(new FileWriter(errores));
		double monto;
		
		for (int i = 0; i < cuentas.size(); i++) {
			
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
		cobradosBW.close();
		erroresBW.close();
	}

	public Double getCOSTO_DE_MANTENIMIENTO() {
		return costoDeMantenimiento;
	}

	public void setCOSTO_DE_MANTENIMIENTO(Double costoDeMantenimiento) {
		this.costoDeMantenimiento = costoDeMantenimiento;
	}

}
