package entidad.bancaria.excepciones;

public class CBUInexistenteException extends Exception {

	private static final long serialVersionUID = 8816846858701038786L;

	public CBUInexistenteException(int cbu) {
		super("El CBU indicado: " + cbu + ". No corresponde a una cuenta en servicio");
	}
}
