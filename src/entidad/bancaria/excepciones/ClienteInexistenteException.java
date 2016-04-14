package entidad.bancaria.excepciones;

public class ClienteInexistenteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6664342219568359915L;

	public ClienteInexistenteException(String cuit){
		super("No hay ningun cliente registrado con el CUIT " + cuit + 
				". Compruebe el correcto ingreso del CUIT o registre como un nuevo cliente.");
	}
}
