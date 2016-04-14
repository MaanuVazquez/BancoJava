package entidad.bancaria.banco;

import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.clientes.Domicilio;
import entidad.bancaria.excepciones.ClienteInexistenteException;

public class GestionDeClientes {

	/**
	 * Agrega a una persona fisica como cliente de el banco.
	 * @param CUIT : Clave Única de Identificación Tributaria.
	 * @param nombre : Nombres y Apellidos del cliente
	 * @param domicilio : el domicilio incluye : dirección, codigoPostal, localidad y provincia.
	 * @param telefono
	 * @param tipoDeDocumento
	 * @param numeroDeDocumento
	 * @param estadoCivil
	 * @param profesion
	 * @param nombreYApellidoDelConyuge
	 */
	
	public static void agregarCliente(String CUIT, String nombre,
			Domicilio domicilio, String telefono, String tipoDeDocumento,
			String numeroDeDocumento, String estadoCivil, String profesion,
			String nombreYApellidoDelConyuge){
		Banco.agregarPersonaFisica(CUIT, nombre, domicilio, telefono, tipoDeDocumento,
				numeroDeDocumento, estadoCivil, profesion, nombreYApellidoDelConyuge);
	}
	
	/**
	 * @param CUIT : Clave Única de Identificación Tributaria
	 * @param razonSocial : Nombre completo de la organizacion.
	 * @param domicilio :  el domicilio incluye : dirección, codigoPostal, localidad y provincia.
	 * @param telefono
	 * @param fechaDelContratoSocial : fecha de creacion de la organizacion.
	 */
	
	public static void agregarCliente(String CUIT, String razonSocial,
			Domicilio domicilio, String telefono, String fechaDelContratoSocial) {
		Banco.agregarPersonaJuridica(CUIT, razonSocial, domicilio, telefono, fechaDelContratoSocial);
	}
	
	/**
	 * Da de baja a un cliente si no tiene cuentas activas. El cliente pasa a estado inactivo.
	 * @param cuit : Clave Única de Identificación Tributaria.
	 * @return : true si el cliente paso a estado inactivo, false si tenia una cuenta activa.
	 */
	
	public static boolean bajaDeCliente(String cuit){
		try {
			return Banco.bajaCliente(cuit);
		} catch (ClienteInexistenteException e) {
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Activa un cliente que habia sido dado de baja anteriormente.
	 * @param cuit : Clave Única de Identificación Tributaria.
	 */
	
	public static void altaCliente(String cuit) throws ClienteInexistenteException{
		Banco.activarCliente(cuit);
	}
	
	/**
	 * Busca el cliente con el cuit especificado y lo devuelve en caso de que exista. 
	 * En caso de no haber registrado un cliente con ese cuit devuelve una referencia vacia.
	 * @param cuit : Clave Única de Identificación Tributaria.
	 * @return : Devuelve el cliente con el cuit recibido o null si no hay un cliente con ese cuit
	 * @throws ClienteInexistenteException
	 */
	
	public static Cliente buscarCliente(String cuit){
		try {
			return Banco.buscarCliente(cuit);
		} catch (ClienteInexistenteException e) {
			System.out.println(e);
			return null;
		}
	}
}
