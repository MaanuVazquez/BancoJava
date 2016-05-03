package entidad.bancaria.excepciones;

public class CostoDeMantenimientoNoPositivoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5065468299944195887L;

	public CostoDeMantenimientoNoPositivoException(double costoDeMantenimiento){
		super("El costo de mantenimiento ingresado :" + costoDeMantenimiento + ". Debe ser positivo");
	}
}
