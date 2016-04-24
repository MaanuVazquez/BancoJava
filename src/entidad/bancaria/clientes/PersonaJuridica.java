package entidad.bancaria.clientes;

import entidad.bancaria.excepciones.CUITInvalidoException;

public class PersonaJuridica extends Cliente{

	private String fechaDelContratoSocial;

	public PersonaJuridica(String CUIT, String nombreORazonSocial, Domicilio domicilio, String telefono, 
			String fechaDelContratoSocial) throws CUITInvalidoException{
		super(CUIT, nombreORazonSocial, domicilio, telefono);
		this.fechaDelContratoSocial = fechaDelContratoSocial;
	}

	public String getFechaDelContratoSocial() {
		return fechaDelContratoSocial;
	}

}
