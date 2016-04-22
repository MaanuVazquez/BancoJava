package entidad.bancaria.banco;

import java.io.IOException;
import java.util.HashMap;

import entidad.bancaria.clientes.*;
import entidad.bancaria.cuentas.*;
import entidad.bancaria.excepciones.*;

public class Banco {

	private static HashMap<Integer, CuentaDeCliente> cuentas = new HashMap<Integer, CuentaDeCliente>();
	private static HashMap<Integer, CajaDeAhorro> cajasDeAhorro = new HashMap<Integer, CajaDeAhorro>();
	private static HashMap<String, Cliente> clientes = new HashMap<String, Cliente>();
	private static HashMap<String, PersonaFisica> personasFisicas = new HashMap<String, PersonaFisica>();
	private static Cuenta retenciones = new Cuenta();
	private static Cuenta mantenimiento = new Cuenta();
	private static double cotizacionDolar = 14.8;

	/**
	 * privado para evitar la creacion de instancias.
	 */

	private Banco() {
	}

	/*
	 * Operaciones por ventanilla
	 */

	/**
	 * Acredita en la cuenta con el CBU indicado un monto de dinero.
	 * 
	 * @param cbu
	 *            : Numero de cuenta
	 * @param monto
	 *            : Cantidad de dinero a depositar en moneda de la cuenta.
	 */

	public static void depositoEnEfectivo(int cbu, Double monto) {
		try {
			OperacionPorVentanilla.depositoEnEfectivo(cbu, monto);
		} catch (CBUInexistenteException | CuentaInhabilitadaException e) {
			System.err.println(e);
		}
	}

	/**
	 * Debita en la CajaDeAhorro con el CBU indicado un monto de dinero.
	 * 
	 * @param cbu
	 *            Numero de cuenta.
	 * @param monto
	 *            : Cantidad de dinero a retirar en moneda de la cuenta.
	 */

	public static void extraccionEnEfectivoEnCajaDeAhorro(int cbu, Double monto) {
		try {
			OperacionPorVentanilla.extraccionEnEfectivoEnCajaDeAhorro(cbu,
					monto);
		} catch (SaldoInsuficienteException | CuentaInhabilitadaException
				| CBUInexistenteException e) {
			System.err.println(e);
		}
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
	 */

	public static void transferencia(int cbuDelOrigen, int cbuDelDestino,
			Double monto) {
		try {
			OperacionPorVentanilla.transferencia(cbuDelOrigen, cbuDelDestino,
					monto);
		} catch (CBUInexistenteException | CuentaInhabilitadaException
				| SaldoInsuficienteException e) {
			System.err.println(e);
		}
	}

	/**
	 * Lista todos los movimientos realizados de la cuenta.
	 * 
	 * @param CBU
	 *            : CBU de la cuenta que se desea obtener los movimientos.
	 * @return Arreglo de todas las transacciones de la cuenta. Devuelve un
	 *         arreglo vacio en caso de recibir un cbu invalido.
	 */

	public static Transaccion[] listarTodosLosMovimientosDeCuenta(int cbu) {
		try {
			return OperacionPorVentanilla
					.listarTodosLosMovimientosDeCuenta(cbu);
		} catch (CBUInexistenteException e) {
			System.err.println(e);
			return null;
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
	 */

	public static Transaccion[] listarLosUltimosMovimientosDeCuenta(int cbu,
			int cantidadDeMovimientos) {
		try {
			return OperacionPorVentanilla.listarLosUltimosMovimientosDeCuenta(
					cbu, cantidadDeMovimientos);
		} catch (CBUInexistenteException
				| NumeroDeMovimientosInvalidosException e) {
			System.err.println(e);
		}
		return null;
	}

	/*
	 * Gestion De Cuentas
	 */

	/**
	 * Crea una Caja De Ahorro asociada al banco
	 * 
	 * @param cuits
	 *            : Arreglo de CUITs de los titulares de la cuenta. Deben ser
	 *            personas fisicas
	 * @param saldo
	 *            : Deposito inicial para crear la cuenta.
	 * @param tasaDeInteres
	 *            : ¿?
	 * @param tipoDeMoneda
	 *            : Tipo de moneda de la cuenta creada, puede ser PESO o DOLAR.
	 * @return : Numero de CBU de la cuenta creada. Devuelve 0 en caso de no
	 *         poder crear la cuenta.
	 */

	public static int crearCajaDeAhorro(String[] cuits, Double saldo,
			Double tasaDeInteres, TipoDeMoneda tipoDeMoneda) {
		CajaDeAhorro cuenta;
		try {
			cuenta = GestionDeCuentas.crearCajaDeAhorro(cuits, saldo,
					tasaDeInteres, tipoDeMoneda);
		} catch (DepositoInicialInvalidoException | SinClientesException
				| ClienteInexistenteException e) {
			System.err.println(e);
			return 0;
		}
		if (Banco.cuentas.containsKey(cuenta.getCBU())){
			return 0;
		}
		Banco.cuentas.put(cuenta.getCBU(), cuenta);
		Banco.cajasDeAhorro.putIfAbsent(cuenta.getCBU(), cuenta);
		return cuenta.getCBU();
	}

	/**
	 * Crea una Cuenta Corriente asociada al banco
	 * 
	 * @param cuits
	 *            : Arreglo de CUITs de los titulares de la cuenta. Deben ser
	 *            personas fisicas
	 * @param saldo
	 *            : Deposito inicial para crear la cuenta.
	 * @param sobregiro
	 *            : Valor de descubierto que puede tener la cuenta.
	 * @return : Numero de CBU de la cuenta creada. Devuelve 0 en caso de no
	 *         poder crear la cuenta.
	 */

	public static int crearCuentaCorriente(String[] cuits, Double saldo,
			Double sobregiro) {
		CuentaCorriente cuenta;
		try {
			cuenta = GestionDeCuentas.crearCuentaCorriente(cuits, saldo,
					sobregiro);
		} catch (DepositoInicialInvalidoException | ClienteInexistenteException
				| SinClientesException e) {
			System.err.println(e);
			return 0;
		}
		Banco.cuentas.putIfAbsent(cuenta.getCBU(), cuenta);
		return cuenta.getCBU();
	}

	/**
	 * Inhabilita una cuenta para impedir que realize movimientos.
	 * 
	 * @param cbu
	 *            : CBU de la cuenta que se desea inhabilitar.
	 */

	public static void inhabilitarCuenta(int cbu) {
		try {
			GestionDeCuentas.inhabilitarCuenta(cbu);
		} catch (CBUInexistenteException e) {
			System.err.println(e);
		}
	}

	/**
	 * Habilita una cuenta para permitir que realize movimientos. Activa los
	 * titulares que hayan sido marcados como inactivos.
	 * 
	 * @param cbu
	 *            : CBU de la cuenta que se desea habilitar.
	 */

	public static void habilitarCuenta(int cbu) {
		try {
			GestionDeCuentas.habilitarCuenta(cbu);
		} catch (CBUInexistenteException e) {
			System.err.println(e);
		}
	}

	/**
	 * Busca una cuenta en el banco usando su cbu.
	 * 
	 * @param cbu
	 *            : CBU de la cuenta que se desea buscar.
	 * @return : La cuenta buscada.
	 * @throws CBUInexistenteException
	 */

	static CuentaDeCliente buscarCuenta(int cbu) throws CBUInexistenteException {
		CuentaDeCliente cuenta = cuentas.get(cbu);
		if (cuenta == null) {
			throw new CBUInexistenteException(cbu);
		}
		return cuenta;
	}

	/**
	 * Busca una CajaDeAhorro en el banco usando su cbu.
	 * 
	 * @param cbu
	 *            : CBU de la cuenta que se desea buscar.
	 * @return : La cuenta buscada.
	 * @throws CBUInexistenteException
	 */

	public static CajaDeAhorro buscarCajaDeAhorro(int cbu)
			throws CBUInexistenteException {
		CajaDeAhorro cuenta = cajasDeAhorro.get(cbu);
		if (cuenta == null) {
			throw new CBUInexistenteException(cbu);
		}
		return cuenta;
	}

	/*
	 * Gestion de clientes
	 */

	/**
	 * Agrega a una persona fisica como cliente de el banco.
	 * 
	 * @param CUIT
	 *            : Clave Única de Identificación Tributaria.
	 * @param nombre
	 *            : Nombres y Apellidos del cliente
	 * @param domicilio
	 *            : el domicilio incluye : dirección, codigoPostal, localidad y
	 *            provincia.
	 * @param telefono
	 * @param tipoDeDocumento
	 * @param numeroDeDocumento
	 * @param estadoCivil
	 * @param profesion
	 * @param nombreYApellidoDelConyuge
	 * @throws CUITYaAsignadoException 
	 */

	public static void agregarPersonaFisica(String CUIT, String nombre,
			Domicilio domicilio, String telefono, String tipoDeDocumento,
			String numeroDeDocumento, String estadoCivil, String profesion,
			String nombreYApellidoDelConyuge) throws CUITYaAsignadoException {
		if(Banco.clientes.putIfAbsent(CUIT ,new PersonaFisica(CUIT, nombre, domicilio, telefono,
				tipoDeDocumento, numeroDeDocumento, estadoCivil, profesion,
				nombreYApellidoDelConyuge)) != null){
			throw new CUITYaAsignadoException(CUIT);
		}
	}

	/**
	 * Agrega a una persona juridica como cliente de el banco.
	 * 
	 * @param CUIT
	 *            : Clave Única de Identificación Tributaria.
	 * @param razonSocial
	 *            : Nombre completo de la organizacion.
	 * @param domicilio
	 *            : el domicilio incluye : dirección, codigoPostal, localidad y
	 *            provincia.
	 * @param telefono
	 * @param fechaDelContratoSocial
	 *            : fecha de creacion de la organizacion.
	 * @throws CUITYaAsignadoException 
	 */

	public static void agregarPersonaJuridica(String CUIT, String razonSocial,
			Domicilio domicilio, String telefono, String fechaDelContratoSocial) throws CUITYaAsignadoException {
		if(Banco.clientes.putIfAbsent(CUIT, new PersonaJuridica(CUIT, razonSocial, domicilio,
				telefono, fechaDelContratoSocial)) != null){
			throw new CUITYaAsignadoException(CUIT);
		}
	}

	/**
	 * Da de baja a un cliente si no tiene cuentas activas. El cliente pasa a
	 * estado inactivo.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @return : true si el cliente paso a estado inactivo, false si tenia una
	 *         cuenta activa.
	 * @throws ClienteInexistenteException
	 */

	public static boolean bajaCliente(String cuit) {
		try {
			return GestionDeClientes.bajaDeCliente(cuit);
		} catch (ClienteInexistenteException e) {
			System.err.println(e);
			return false;
		}
	}

	/**
	 * Activa un cliente que habia sido dado de baja anteriormente.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @throws ClienteInexistenteException
	 */

	public static void activarCliente(String cuit) {
		try {
			GestionDeClientes.altaCliente(cuit);
		} catch (ClienteInexistenteException e) {
			System.err.println(e);
		}
	}

	/**
	 * Busca el cliente con el cuit especificado y lo devuelve en caso de que
	 * exista.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @return : Devuelve el cliente con el cuit recibido
	 * @throws ClienteInexistenteException
	 */

	public static Cliente buscarCliente(String cuit)
			throws ClienteInexistenteException {
		Cliente cliente = clientes.get(cuit);
		if(cliente == null){
			throw new ClienteInexistenteException(cuit);
		}
		return cliente;
	}

	/**
	 * Busca la persona fisica con el cuit especificado y lo devuelve en caso de que
	 * exista.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @return : Devuelve el cliente con el cuit recibido
	 * @throws ClienteInexistenteException
	 */
	
	public static PersonaFisica buscarPersonaFisica(String cuit) throws ClienteInexistenteException {
		PersonaFisica cliente = personasFisicas.get(cuit);
		if(cliente == null){
			throw new ClienteInexistenteException(cuit);
		}
		return cliente;
	}
	/*
	 * Gestion Del Banco
	 */

	public static void cobroDeMantenimientos() {
		try {
			ProcesoBatch.cobroDeMantenimientos(cajasDeAhorro);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	static void acreditarMantenimiento() {
		mantenimiento.acreditar(ProcesoBatch.getCOSTO_DE_MANTENIMIENTO(),
				MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);
	}

	public static void cobrarRetenciones(double monto) {
		Banco.retenciones.acreditar(monto,
				MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);
	}
	
	public static double getCotizacion() {
		return cotizacionDolar;
	}

	
}