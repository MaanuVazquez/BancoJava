package entidad.bancaria.clientes;

public class Domicilio {
	
	private String dirección;
	private String codigoPostal;
	private String localidad;
	private String provincia;
	
	/**
	 * Domicilio de Cliente
	 * @param dirección
	 * @param codigoPostal
	 * @param localidad
	 * @param provincia
	 */
	
	public Domicilio(String dirección, String codigoPostal, String localidad, String provincia){
		this.dirección = dirección;
		this.codigoPostal = codigoPostal;
		this.localidad = localidad;
		this.provincia = provincia;
	}

	public String getDirección() {
		return dirección;
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
		return "Domicilio : dirección=" + dirección + ", codigoPostal="
				+ codigoPostal + ", localidad=" + localidad + ", provincia="
				+ provincia;
	}

}
