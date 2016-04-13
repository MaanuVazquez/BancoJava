package entidad.bancaria.cuentas;

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

		Path cobradosPath = Paths.get("MantenimientosCobrados " + new Date().toLocaleString());
		Path erroresPath = Paths.get("ErroresMantenimiento " + new Date().toLocaleString());
		ArrayList<String> cobradosString = new ArrayList<String>();
		ArrayList<String> erroresString = new ArrayList<String>();
		double monto;
		for (int i = 0; i < cuentas.size(); i++) {
			
			if(cuentas.get(i).getTipoDeMoneda() == TipoDeMoneda.PESO){
				monto = costoDeMantenimiento;
			} else {
				monto = costoDeMantenimiento / Banco.getCotizacion();
			}
			
			try {
				cuentas.get(i).debitar(monto);
				cobradosString.add("CBU:" + cuentas.get(i).getCBU() + ", Tipo De Cuenta: " + "Caja De Ahorro" + ", " +
						monto + ", " + cuentas.get(i).getTipoDeMoneda() + ", " + Banco.getCotizacion() + ".");
				this.acreditar(costoDeMantenimiento);
				
			} catch (SaldoInsuficienteException e) {
				
				cobradosString.add("CBU:" + cuentas.get(i).getCBU() + ", Tipo De Cuenta: " + "Caja De Ahorro" + ", " +
						monto + ", " + "Motivo : Saldo insuficiente.");
			} catch (CuentaInhabilitadaException e) {
				
				cobradosString.add("CBU:" + cuentas.get(i).getCBU() + ", Tipo De Cuenta: " + "Caja De Ahorro" + ", " +
						monto + ", " + "Motivo : CuentaInhabilitada.");
			}
		}
		Files.write(cobradosPath, cobradosString, Charset.forName("UTF-8"));
		Files.write(erroresPath, erroresString, Charset.forName("UTF-8"));
	}

	public Double getCOSTO_DE_MANTENIMIENTO() {
		return costoDeMantenimiento;
	}

	public void setCOSTO_DE_MANTENIMIENTO(Double costoDeMantenimiento) {
		this.costoDeMantenimiento = costoDeMantenimiento;
	}

}
