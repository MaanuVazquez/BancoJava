package clientes;

public class Domicilio {
	
	private String direcci�n;
	private String codigoPostal;
	private String localidad;
	private String provincia;
	
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

}
