package entidad.bancaria.banco;

import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.excepciones.CUITInvalidoException;
import entidad.bancaria.excepciones.ClienteInexistenteException;

public class GestionDeClientes {

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

	public static boolean bajaDeCliente(String cuit)
			throws ClienteInexistenteException, CUITInvalidoException {
		Cliente cliente = Banco.buscarCliente(cuit);
		return cliente.darDeBaja();
	}

	/**
	 * Activa un cliente que habia sido dado de baja anteriormente.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @throws CUITInvalidoException
	 */

	public static void altaCliente(String cuit)
			throws ClienteInexistenteException, CUITInvalidoException {
		Banco.buscarCliente(cuit).activar();
	}

	/**
	 * Busca el cliente con el cuit especificado y lo devuelve en caso de que
	 * exista. En caso de no haber registrado un cliente con ese cuit devuelve
	 * una referencia vacia.
	 * 
	 * @param cuit
	 *            : Clave Única de Identificación Tributaria.
	 * @return : Devuelve el cliente con el cuit recibido o null si no hay un
	 *         cliente con ese cuit
	 * @throws ClienteInexistenteException
	 */

	public static Cliente buscarCliente(String cuit) {
		try {
			return Banco.buscarCliente(cuit);
		} catch (ClienteInexistenteException | CUITInvalidoException e) {
			System.out.println(e);
			return null;
		}
	}
}
