package clientes;

public class Domicilio {
	
	private String dirección;
	private String codigoPostal;
	private String localidad;
	private String provincia;
	
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

}
