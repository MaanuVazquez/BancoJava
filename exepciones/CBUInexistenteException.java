package exepciones;

public class CBUInexistenteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8816846858701038786L;

	public CBUInexistenteException(){
		super("El cbu indicado no corresponde a una cuenta en servicio");
	}
}
