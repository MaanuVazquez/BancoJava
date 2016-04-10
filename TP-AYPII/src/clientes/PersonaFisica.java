package clientes;

public class PersonaFisica extends Cliente{

	private String tipoDeDocumento;
	private String numeroDeDocumento;
	private String estadoCivil;
	private String profesion;
	private String nombreYApellidoDelConyuge;

	public PersonaFisica(String CUIT, String nombreORazonSocial, Domicilio domicilio, String telefono,
			String tipoDeDocumento, String numeroDeDocumento, String estadoCivil, String profesion ,String nombreYApellidoDelConyuge){
		super(CUIT, nombreORazonSocial, domicilio, telefono);
		this.tipoDeDocumento = tipoDeDocumento;
		this.numeroDeDocumento = numeroDeDocumento;
		this.setEstadoCivil(estadoCivil);
		this.setProfesion(profesion);
		this.setNombreYApellidoDelConyuge(nombreYApellidoDelConyuge);
	}

	public String getTipoDeDocumento() {
		return tipoDeDocumento;
	}

	public String getNumeroDeDocumento() {
		return numeroDeDocumento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getNombreYApellidoDelConyuge() {
		return nombreYApellidoDelConyuge;
	}

	public void setNombreYApellidoDelConyuge(String nombreYApellidoDelConyuge) {
		this.nombreYApellidoDelConyuge = nombreYApellidoDelConyuge;
	}

}
