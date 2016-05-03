package entidad.bancaria.clientes;

public class Domicilio {
	
	private String direcci�n;
	private String codigoPostal;
	private String localidad;
	private String provincia;
	
	/**
	 * Domicilio de Cliente
	 * @param direcci�n
	 * @param codigoPostal
	 * @param localidad
	 * @param provincia
	 */
	
	public Domicilio(String direcci�n, String codigoPostal, String localidad, String provincia){
		this.direcci�n = direcci�n;
		this.codigoPostal = codigoPostal;
		this.localidad = localidad;
		this.provincia = provincia;
	}

	public String getDirecci�n() {
		return direcci�n;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	@Override
	public String toString() {
		return "Domicilio : direcci�n=" + direcci�n + ", codigoPostal="
				+ codigoPostal + ", localidad=" + localidad + ", provincia="
				+ provincia;
	}

}
