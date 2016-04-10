package exepciones;

public class CuentaInhabilitadaException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4689323053298810130L;

	public CuentaInhabilitadaException(){
		super("La cuenta a la que se intenta acceder esta inhabilitada");
	}

}
