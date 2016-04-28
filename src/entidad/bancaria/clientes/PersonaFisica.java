package entidad.bancaria.clientes;

import entidad.bancaria.excepciones.CUITInvalidoException;

public class PersonaFisica extends Cliente{

	private String tipoDeDocumento;
	private String numeroDeDocumento;
	private String estadoCivil;
	private String profesion;
	private String nombreYApellidoDelConyuge;

	/**
	 * Crea el registro de una Persona Fisica
	 * @param CUIT
	 * @param nombreORazonSocial
	 * @param domicilio
	 * @param telefono
	 * @param tipoDeDocumento
	 * @param numeroDeDocumento
	 * @param estadoCivil
	 * @param profesion
	 * @param nombreYApellidoDelConyuge
	 * @throws CUITInvalidoException
	 */
	
	public PersonaFisica(String CUIT, String nombreORazonSocial, Domicilio domicilio, String telefono,
			String tipoDeDocumento, String numeroDeDocumento, String estadoCivil, String profesion ,String nombreYApellidoDelConyuge) throws CUITInvalidoException{
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
