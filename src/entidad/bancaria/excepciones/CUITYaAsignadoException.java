package entidad.bancaria.excepciones;

public class CUITYaAsignadoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4781161970929841252L;

	public CUITYaAsignadoException(String cuit) {
		super("La cuenta que intenta crear con el CUIT: " + cuit + " ya existe");
	}

}
