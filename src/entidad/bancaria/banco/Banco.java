package entidad.bancaria.banco;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import entidad.bancaria.clientes.*;
import entidad.bancaria.cuentas.*;
import entidad.bancaria.excepciones.*;

public class Banco {
	// Agregue las inicializaciones
	private static HashSet<CuentaDeCliente> cuentas = new HashSet<CuentaDeCliente>();
	private static HashSet<Cliente> clientes = new HashSet<Cliente>();
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
	 *            : Numero de cuenta.
	 * @param monto
	 *            : Cantidad de dinero a depositar en moneda de la cuenta.
	 * @throws CBUInexistenteException
	 * @throws CuentaInhabilitadaException
	 */

	public static void depositoEnEfectivo(int cbu, Double monto)
			throws CBUInexistenteException, CuentaInhabilitadaException {
		CuentaDeCliente cuentaDestino;
		cuentaDestino = Banco.buscarCuenta(cbu);
		if (!cuentaDestino.isHabilitada()) {
			throw new CuentaInhabilitadaException(cbu);
		}
		cuentaDestino.acreditar(monto, MotivoDeTransaccion.DEPOSITO_POR_VENTANILLA);
	}

	/**
	 * Debita en la cuenta con el CBU indicado un monto de dinero. Solo
	 * permitido para Cajas De Ahorro.
	 * 
	 * @param cbu
	 *            Numero de cuenta.
	 * @param monto
	 *            : Cantidad de dinero a retirar en moneda de la cuenta.
	 * @throws CBUInexistenteException
	 * @throws CuentaInhabilitadaException
	 * @throws SaldoInsuficienteException
	 */

	public static void extraccionEnEfectivo(int cbu, Double monto)
			throws CBUInexistenteException, CuentaInhabilitadaException, SaldoInsuficienteException {
		CuentaDeCliente cuentaDestino;
		cuentaDestino = Banco.buscarCuenta(cbu);
		// hay que encontrar un mejor modo de hacer esto, el instanceof no
		// habria que usarlo nunca
		if (cuentaDestino instanceof CuentaCorriente) {
		}
		cuentaDestino.debitar(monto, MotivoDeTransaccion.EXTRACCION_POR_VENTANILLA);
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
	 * @throws CBUInexistenteException
	 * @throws CuentaInhabilitadaException
	 * @throws SaldoInsuficienteException
	 */

	public static void transferencia(int cbuDelOrigen, int cbuDelDestino, Double monto)
			throws CBUInexistenteException, CuentaInhabilitadaException, SaldoInsuficienteException {

		CuentaDeCliente cuentaOrigen;
		CuentaDeCliente cuentaDestino;
		cuentaOrigen = Banco.buscarCuenta(cbuDelOrigen);
		cuentaDestino = Banco.buscarCuenta(cbuDelDestino);

		if (!cuentaDestino.isHabilitada())
			throw new CuentaInhabilitadaException(cbuDelDestino);

		cuentaOrigen.debitar(monto, MotivoDeTransaccion.TRANSFERENCIA);

		if (cuentaDestino.getTipoDeMoneda() == cuentaOrigen.getTipoDeMoneda()) {
			cuentaDestino.acreditar(monto, MotivoDeTransaccion.TRANSFERENCIA);
		} else if (cuentaOrigen.getTipoDeMoneda() == TipoDeMoneda.PESO) {
			monto = monto / Banco.getCotizacion();
			cuentaDestino.acreditar(monto, MotivoDeTransaccion.TRANSFERENCIA,
					"Conversión de Peso Argentino(ARS) a Dolar(USD): " + monto + " Cotización: 1 ARS - "
							+ Banco.getCotizacion() + " USD");
		} else {
			monto = monto * Banco.getCotizacion();
			cuentaDestino.acreditar(monto, MotivoDeTransaccion.TRANSFERENCIA,
					"Conversión de Dolar(USD) a Peso Argentino(ARS): " + monto + " Cotización: 1 ARS - "
							+ Banco.getCotizacion() + " USD");
		}
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
		Cuenta cuenta;
		cuenta = Banco.buscarCuenta(cbu);
		return (Transaccion[]) cuenta.getTransacciones().toArray();
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
	 * @throws CBUInexistenteException
	 * @throws NumeroDeMovimientosInvalidosException
	 */

	public static Transaccion[] listarLosUltimosMovimientosDeCuenta(int cbu, int cantidadDeMovimientos)
			throws CBUInexistenteException, NumeroDeMovimientosInvalidosException {

		Cuenta cuenta = Banco.buscarCuenta(cbu);
		if (cantidadDeMovimientos < 1) {
			throw new NumeroDeMovimientosInvalidosException(cantidadDeMovimientos);
		}
		if (cantidadDeMovimientos > cuenta.getTransacciones().size()) {
			cantidadDeMovimientos = cuenta.getTransacciones().size();
		}

		Transaccion[] ultimosMovimientos = new Transaccion[cantidadDeMovimientos];

		for (int i = (cuenta.getTransacciones().size()) - cantidadDeMovimientos; i < cuenta.getTransacciones()
				.size(); i++) {
			ultimosMovimientos[i] = cuenta.getTransacciones().get(i);
		}

		return ultimosMovimientos;
	}

	// Gestion De Cuentas

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
	 * @throws DepositoInicialInvalidoException
	 * @throws SinClientesException
	 * @throws ClienteInexistenteException
	 */

	public static int crearCajaDeAhorro(String[] cuits, Double saldo, Double tasaDeInteres, TipoDeMoneda tipoDeMoneda)
			throws DepositoInicialInvalidoException, SinClientesException, ClienteInexistenteException {
		if (cuits.length == 0) {
			throw new SinClientesException();
		}
		Cliente[] titulares = new Cliente[cuits.length];
		for (int i = 0; i < cuits.length; i++) {
			titulares[i] = buscarCliente(cuits[i]);
		}
		if (saldo <= 0) {
			throw new DepositoInicialInvalidoException(saldo, 1.0);
		}
		CajaDeAhorro cuenta = new CajaDeAhorro(titulares, saldo, tasaDeInteres, tipoDeMoneda);
		Banco.cuentas.add(cuenta);
		ProcesoBatch.agregarCuenta(cuenta);
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
	 * @throws DepositoInicialInvalidoException
	 * @throws ClienteInexistenteException
	 * @throws SinClientesException
	 */

	public static int crearCuentaCorriente(String[] cuits, Double saldo, Double sobregiro)
			throws DepositoInicialInvalidoException, ClienteInexistenteException, SinClientesException {
		if (cuits.length == 0) {
			throw new SinClientesException();
		}
		Cliente[] titulares = new Cliente[cuits.length];
		for (int i = 0; i < cuits.length; i++) {
			titulares[i] = buscarCliente(cuits[i]);
		}
		if (saldo < 10000) {
			throw new DepositoInicialInvalidoException(saldo, 10000.0);
		}
		CuentaCorriente cuenta = new CuentaCorriente(titulares, saldo, sobregiro);
		Banco.cuentas.add(cuenta);
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
		Banco.buscarCuenta(cbu).setHabilitada(false);
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
		CuentaDeCliente cuenta = Banco.buscarCuenta(cbu);
		cuenta.setHabilitada(true);
		// falta activar los clientes que podrian estar inactivos.
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
		Iterator<CuentaDeCliente> iterador = cuentas.iterator();
		CuentaDeCliente cuenta = iterador.next();
		while (iterador.hasNext() && cuenta.getCBU() != cbu) {
			cuenta = iterador.next();
		}
		if (cuenta.getCBU() != cbu) {
			throw new CBUInexistenteException(cbu);
		}
		return cuenta;
	}

	// Gestion de clientes

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
	 */

	public static void agregarPersonaFisica(String CUIT, String nombre, Domicilio domicilio, String telefono,
			String tipoDeDocumento, String numeroDeDocumento, String estadoCivil, String profesion,
			String nombreYApellidoDelConyuge) {
		Banco.clientes.add(new PersonaFisica(CUIT, nombre, domicilio, telefono, tipoDeDocumento, numeroDeDocumento,
				estadoCivil, profesion, nombreYApellidoDelConyuge));
	}

	/**
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
	 */

	public static void agregarPersonaJuridica(String CUIT, String razonSocial, Domicilio domicilio, String telefono,
			String fechaDelContratoSocial) {
		Banco.clientes.add(new PersonaJuridica(CUIT, razonSocial, domicilio, telefono, fechaDelContratoSocial));
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

	public static boolean bajaCliente(String cuit) throws ClienteInexistenteException {
		Cliente cliente = buscarCliente(cuit);
		return cliente.darDeBaja();
	}

	/**
	 * Activa un cliente que habia sido dado de baja anteriormente.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @throws ClienteInexistenteException
	 */

	public static void activarCliente(String cuit) throws ClienteInexistenteException {
		Cliente cliente = buscarCliente(cuit);
		cliente.activar();
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

	public static Cliente buscarCliente(String cuit) throws ClienteInexistenteException {
		Iterator<Cliente> iterador = clientes.iterator();
		Cliente cliente = iterador.next();
		while (iterador.hasNext() && cliente.getCUIT() != cuit) {
			cliente = iterador.next();
		}
		if (cliente.getCUIT() != cuit) {
			throw new ClienteInexistenteException(cuit);
		}
		return cliente;
	}

	// Proceso Bach

	public static void cobroDeMantenimientos() {
		try {
			ProcesoBatch.cobroDeMantenimientos();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	static void acreditarMantenimiento() {
		mantenimiento.acreditar(ProcesoBatch.getCOSTO_DE_MANTENIMIENTO(), MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);
	}

	// Cobrar Retenciones

	public static void cobrarRetenciones(double monto) {
		Banco.retenciones.acreditar(monto, MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO);
	}

	public static double getCotizacion() {
		return cotizacionDolar;
	}
}