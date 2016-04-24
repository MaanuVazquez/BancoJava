package entidad.bancaria.excepciones;

public class CUITInvalidoException extends Exception {

	private static final long serialVersionUID = 7332346748572984964L;

	public CUITInvalidoException() {
		super("El CUIT ingresado es incorrecto");
	}

}
