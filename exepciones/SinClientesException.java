package exepciones;

public class SinClientesException extends Exception{
	
	private static final long serialVersionUID = -8115244719665854668L;

	public SinClientesException(){
		super("No se puede crear la cuenta sin asignarle clientes");
	}

}
