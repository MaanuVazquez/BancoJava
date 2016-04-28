package entidad.bancaria.clientes;

import entidad.bancaria.excepciones.CUITInvalidoException;

public class PersonaJuridica extends Cliente{

	private String fechaDelContratoSocial;

	/**
	 * Crea el registro de una Persona Juridica
	 * @param CUIT
	 * @param nombreORazonSocial
	 * @param domicilio
	 * @param telefono
	 * @param fechaDelContratoSocial
	 * @throws CUITInvalidoException
	 */
	public PersonaJuridica(String CUIT, String nombreORazonSocial, Domicilio domicilio, String telefono, 
			String fechaDelContratoSocial) throws CUITInvalidoException{
		super(CUIT, nombreORazonSocial, domicilio, telefono);
		this.fechaDelContratoSocial = fechaDelContratoSocial;
	}

	public String getFechaDelContratoSocial() {
		return fechaDelContratoSocial;
	}

}
