package banco;

import java.util.HashSet;
import java.util.Iterator;

import clientes.Cliente;
import clientes.Domicilio;
import clientes.PersonaFisica;
import clientes.PersonaJuridica;
import cuentas.CajaDeAhorro;
import cuentas.Cuenta;
import cuentas.CuentaEspecial;
import cuentas.MotivoDeTransaccion;
import cuentas.Transaccion;
import exepciones.CBUInexistenteException;
import exepciones.CuentaInhabilitadaException;
import exepciones.SaldoInsuficienteException;
import exepciones.SinClientesException;

public class Banco {

	private HashSet<Cuenta> cuentas;
	private HashSet<Cliente> clientes;
	private CuentaEspecial retenciones;
	private CuentaEspecial mantenimiento;
	private Double tipoDeCambioVigente;

	public Banco(){
		retenciones = new CuentaEspecial();
		mantenimiento  = new CuentaEspecial();
	}
	
	// Operaciones Por Ventanilla
	
	public void depositoEnEfectivo(Cuenta cuentaDestino, Double monto, String fecha) throws CuentaInhabilitadaException{
		cuentaDestino.acreditar(monto, fecha, MotivoDeTransaccion.DEPOSITO_POR_VENTANILLA);
	}
	
	public void extraccionEnEfectivo(Cuenta cuentaDestino, Double monto, String fecha) throws CuentaInhabilitadaException{
		try {
			cuentaDestino.debitar(monto, fecha, MotivoDeTransaccion.EXTRACCION_POR_VENTANILLA);
		} catch (SaldoInsuficienteException e) {
			e.printStackTrace();
		}
	}
	
	public Transaccion[] listarMovimientosDeCuenta(Cuenta cuenta){
		return (Transaccion[]) cuenta.getTransacciones().toArray();
	}
	
	public Transaccion[] listarUltimosMovimientosDeCuenta(Cuenta cuenta, Integer cantidadDeMovimientos){
		Transaccion[] transacciones;
		if (cantidadDeMovimientos > cuenta.getTransacciones().size()){
			cantidadDeMovimientos = cuenta.getTransacciones().size();
		}
		transacciones = new Transaccion[cantidadDeMovimientos];
		int ultimoElemento = cuenta.getTransacciones().lastIndexOf(transacciones);
		for (int i = ultimoElemento; i > (ultimoElemento - cantidadDeMovimientos); i--){
			transacciones[(ultimoElemento - cantidadDeMovimientos) + i] = cuenta.getTransacciones().get(i);
		}
		return transacciones;
	}

	// Gestion De Cuentas

	public int aperturaDeCajaDeAhorro(Cliente[] clientes, Double saldo, Double tasaDeInteres){
		int cbu = 0;
		try {
			CajaDeAhorro cuenta = new CajaDeAhorro(clientes, saldo, tasaDeInteres);
			cbu = cuenta.getCBU();
			this.cuentas.add(cuenta);
		} catch (SaldoInsuficienteException | SinClientesException e) {
			e.printStackTrace();
		}
		return cbu;
	}

	public int aperturaDeCuentaCorriente(Cliente[] clientes, Double saldo, Double sobregiro){
		int cbu = 0;
		try {
			CajaDeAhorro cuenta = new CajaDeAhorro(clientes, saldo, sobregiro);
			cbu = cuenta.getCBU();
			this.cuentas.add(cuenta);
		} catch (SaldoInsuficienteException | SinClientesException e) {
			e.printStackTrace();
		}
		return cbu;
	}

	public boolean inhabilitarCuenta(int cbu){
		try {
			buscarCuenta(cbu).setHabilitada(false);
			return true;
		} catch (CBUInexistenteException e) {
			return false;
		}
	}

	public boolean habilitarCuenta(int cbu){
		try {
			buscarCuenta(cbu).setHabilitada(true);
			return true;
		} catch (CBUInexistenteException e) {
			return false;
		}
	}

	private Cuenta buscarCuenta(int cbu) throws CBUInexistenteException{
		if(cbu <= 0 || cuentas.size() < cbu){
			throw new CBUInexistenteException();
		}
		Iterator<Cuenta> iterador = cuentas.iterator();
		Cuenta cuenta = iterador.next();
		while(iterador.hasNext() && cuenta.getCBU() != cbu){
			cuenta = iterador.next();
		}
		return cuenta;
	}

	// Gestion de clientes

	public void agregarCliente(String CUIT, String nombreORazonSocial, Domicilio domicilio, String telefono,String tipoDeDocumento,
			String numeroDeDocumento, String estadoCivil, String profesion ,String nombreYApellidoDelConyuge){
		this.clientes.add(new PersonaFisica(CUIT, nombreORazonSocial, domicilio, telefono, tipoDeDocumento, numeroDeDocumento, 
				estadoCivil, profesion, nombreYApellidoDelConyuge));
	}

	public void agregarCliente(String CUIT, String nombreORazonSocial, Domicilio domicilio, String telefono,
			String fechaDelContratoSocial){
		this.clientes.add(new PersonaJuridica(CUIT, nombreORazonSocial, domicilio, telefono, fechaDelContratoSocial));
	}

	public void bajaCliente(Cliente cliente){
		cliente.deshabilitar();
	}
	
	public void activarCliente(Cliente cliente){
		cliente.habilitar();
	}
	
	// Proceso Bach
	
	public void cobroDeMantenimiento(){
		
	}
}