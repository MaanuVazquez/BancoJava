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
	private static CuentaEspecial retenciones = new CuentaEspecial();
	private static CuentaEspecial mantenimiento = new CuentaEspecial();
	private static Double interesesPagados = 0.0;
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
	 * @throws CuentaInhabilitadaException
	 * @throws CBUInexistenteException
	 */

	public static void depositoEnEfectivo(int cbu, Double monto)
			throws CBUInexistenteException, CuentaInhabilitadaException {

		OperacionPorVentanilla.depositoEnEfectivo(cbu, monto);

	}

	/**
	 * Debita en la CajaDeAhorro con el CBU indicado un monto de dinero.
	 * 
	 * @param cbu
	 *            Numero de cuenta.
	 * @param monto
	 *            : Cantidad de dinero a retirar en moneda de la cuenta.
	 * @throws CBUInexistenteException
	 * @throws CuentaInhabilitadaException
	 * @throws SaldoInsuficienteException
	 */

	public static void extraccionEnEfectivoEnCajaDeAhorro(int cbu, Double monto)
			throws SaldoInsuficienteException, CuentaInhabilitadaException, CBUInexistenteException {

		OperacionPorVentanilla.extraccionEnEfectivoEnCajaDeAhorro(cbu, monto);

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
	 * @throws SaldoInsuficienteException
	 * @throws CuentaInhabilitadaException
	 * @throws CBUInexistenteException
	 */

	public static void transferencia(int cbuDelOrigen, int cbuDelDestino, Double monto)
			throws CBUInexistenteException, CuentaInhabilitadaException, SaldoInsuficienteException {

		OperacionPorVentanilla.transferencia(cbuDelOrigen, cbuDelDestino, monto);

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

	public static Transaccion[] listarTodosLosMovimientosDeCuenta(int cbu) throws CBUInexistenteException {

		return OperacionPorVentanilla.listarTodosLosMovimientosDeCuenta(cbu);

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
	 * @throws NumeroDeMovimientosInvalidosException
	 * @throws CBUInexistenteException
	 */

	public static Transaccion[] listarLosUltimosMovimientosDeCuenta(int cbu, int cantidadDeMovimientos)
			throws CBUInexistenteException, NumeroDeMovimientosInvalidosException {

		return OperacionPorVentanilla.listarLosUltimosMovimientosDeCuenta(cbu, cantidadDeMovimientos);

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
	 *            : Interes obtenido mensualmente
	 * @param tipoDeMoneda
	 *            : Tipo de moneda de la cuenta creada, puede ser PESO o DOLAR.
	 * @return : Numero de CBU de la cuenta creada. Devuelve 0 en caso de no
	 *         poder crear la cuenta.
	 * @throws ClienteInexistenteException
	 * @throws SinClientesException
	 * @throws DepositoInicialInvalidoException
	 * @throws CUITInvalidoException 
	 * @throws TasaDeInteresNegativaException 
	 */

	public static int crearCajaDeAhorro(String[] cuits, Double saldo, Double tasaDeInteres, TipoDeMoneda tipoDeMoneda)
			throws DepositoInicialInvalidoException, SinClientesException, ClienteInexistenteException, CUITInvalidoException, TasaDeInteresNegativaException {

		CajaDeAhorro cuenta;

		cuenta = GestionDeCuentas.crearCajaDeAhorro(cuits, saldo, tasaDeInteres, tipoDeMoneda);
		
		Banco.cuentas.put(cuenta.getCBU(), cuenta);
		Banco.cajasDeAhorro.put(cuenta.getCBU(), cuenta);
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
	 * @throws SinClientesException
	 * @throws ClienteInexistenteException
	 * @throws DepositoInicialInvalidoException
	 * @throws CUITInvalidoException 
	 */

	public static int crearCuentaCorriente(String[] cuits, Double saldo, Double sobregiro)
			throws DepositoInicialInvalidoException, ClienteInexistenteException, SinClientesException, CUITInvalidoException {
		CuentaCorriente cuenta;

		cuenta = GestionDeCuentas.crearCuentaCorriente(cuits, saldo, sobregiro);

		Banco.cuentas.put(cuenta.getCBU(), cuenta);
		return cuenta.getCBU();
	}

	/**
	 * Inhabilita una cuenta para impedir que realize movimientos.
	 * 
	 * @param cbu
	 *            : CBU de la cuenta que se desea inhabilitar.
	 * @throws CBUInexistenteException
	 */

	public static void inhabilitarCuenta(int cbu) throws CBUInexistenteException {

		GestionDeCuentas.inhabilitarCuenta(cbu);

	}

	/**
	 * Habilita una cuenta para permitir que realize movimientos. Activa los
	 * titulares que hayan sido marcados como inactivos.
	 * 
	 * @param cbu
	 *            : CBU de la cuenta que se desea habilitar.
	 * @throws CBUInexistenteException
	 */

	public static void habilitarCuenta(int cbu) throws CBUInexistenteException {

		GestionDeCuentas.habilitarCuenta(cbu);

	}

	/**
	 * Busca una cuenta en el banco usando su cbu.
	 * 
	 * @param cbu
	 *            : CBU de la cuenta que se desea buscar.
	 * @return : La cuenta buscada.
	 * @throws CBUInexistenteException
	 */

	public static CuentaDeCliente buscarCuenta(int cbu) throws CBUInexistenteException {
		CuentaDeCliente cuenta = null;

		cuenta = cuentas.get(cbu);

		if (cuenta == null) {
			throw new CBUInexistenteException(cbu);
		}
		
		return cuenta;
	}

	/**
	 * Busca una CajaDeAhorro en el banco usando su cbu.
	 * 
	 * @param cbu:
	 *            CBU de la cuenta que se desea buscar.
	 * @return : La cuenta buscada.
	 * @throws CBUInexistenteException
	 */

	public static CajaDeAhorro buscarCajaDeAhorro(int cbu) throws CBUInexistenteException {
		CajaDeAhorro cuenta = null;

		cuenta = cajasDeAhorro.get(cbu);

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
	 * @param cuit
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
	 * @throws CUITInvalidoException
	 * @throws CUITYaAsignadoException
	 */

	public static void agregarPersonaFisica(String cuit, String nombre, Domicilio domicilio, String telefono,
			String tipoDeDocumento, String numeroDeDocumento, String estadoCivil, String profesion,
			String nombreYApellidoDelConyuge) throws CUITInvalidoException, CUITYaAsignadoException {

		cuit = Cliente.chequearCUIT(cuit);
		
		if(clientes.get(cuit) != null){
			throw new CUITYaAsignadoException(cuit);
		}
		Banco.clientes.put(cuit, new PersonaFisica(cuit, nombre, domicilio, telefono, tipoDeDocumento,
				numeroDeDocumento, estadoCivil, profesion, nombreYApellidoDelConyuge));
		Banco.personasFisicas.put(cuit, new PersonaFisica(cuit, nombre, domicilio, telefono, tipoDeDocumento,
				numeroDeDocumento, estadoCivil, profesion, nombreYApellidoDelConyuge));

	}

	/**
	 * Agrega a una persona juridica como cliente de el banco.
	 * 
	 * @param cuit
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
	 * @throws CUITInvalidoException
	 */

	public static void agregarPersonaJuridica(String cuit, String razonSocial, Domicilio domicilio, String telefono,
			String fechaDelContratoSocial) throws CUITInvalidoException, CUITYaAsignadoException {
			
		cuit = Cliente.chequearCUIT(cuit);
		
		if(clientes.get(cuit) != null){
			throw new CUITYaAsignadoException(cuit);
		}
		
		Banco.clientes.put(cuit, new PersonaJuridica(cuit, razonSocial, domicilio, telefono, fechaDelContratoSocial)); 
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
	 * @throws CUITInvalidoException 
	 */

	public static boolean bajaCliente(String cuit) throws ClienteInexistenteException, CUITInvalidoException {

		return GestionDeClientes.bajaDeCliente(cuit);

	}

	/**
	 * Activa un cliente que habia sido dado de baja anteriormente.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @throws ClienteInexistenteException
	 * @throws CUITInvalidoException 
	 */

	public static void activarCliente(String cuit) throws ClienteInexistenteException, CUITInvalidoException {

		GestionDeClientes.altaCliente(cuit);

	}

	/**
	 * Busca el cliente con el cuit especificado y lo devuelve en caso de que
	 * exista.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @return : Devuelve el cliente con el cuit recibido
	 * @throws ClienteInexistenteException
	 * @throws CUITInvalidoException 
	 */

	public static Cliente buscarCliente(String cuit) throws ClienteInexistenteException, CUITInvalidoException {
		Cliente cliente = null;
		
		cuit = Cliente.chequearCUIT(cuit);
		
		cliente = clientes.get(cuit);

		if (cliente == null) {
			throw new ClienteInexistenteException(cuit);
		}
		return cliente;
	}

	/**
	 * Busca la persona fisica con el cuit especificado y lo devuelve en caso de
	 * que exista.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @return : Devuelve el cliente con el cuit recibido
	 * @throws ClienteInexistenteException
	 * @throws CUITInvalidoException 
	 */

	public static PersonaFisica buscarPersonaFisica(String cuit) throws ClienteInexistenteException, CUITInvalidoException {
		PersonaFisica cliente = null;
		
		cuit = Cliente.chequearCUIT(cuit);

		cliente = personasFisicas.get(cuit);

		if (cliente == null) {
			throw new ClienteInexistenteException(cuit);
		}

		return cliente;
	}
	/*
	 * Gestion Del Banco
	 */

	public static void cobroDeMantenimientos() throws IOException {

		ProcesoBatch.cobroDeMantenimientos(cajasDeAhorro);

	}

	static void acreditarMantenimiento() {
		mantenimiento.acreditar(ProcesoBatch.getCOSTO_DE_MANTENIMIENTO(), MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);
	}

	static void debitarIntereses(double monto) {
		interesesPagados += monto;
	}
	
	public static void cobrarRetenciones(double monto) {
		Banco.retenciones.acreditar(monto, MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);
	}

	public static double getCotizacion() {
		return cotizacionDolar;
	}

}