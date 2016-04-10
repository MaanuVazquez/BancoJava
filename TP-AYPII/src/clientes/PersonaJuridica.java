package clientes;

public class PersonaJuridica extends Cliente{

	private String fechaDelContratoSocial;

	public PersonaJuridica(String CUIT, String nombreORazonSocial, Domicilio domicilio, String telefono, 
			String fechaDelContratoSocial){
		super(CUIT, nombreORazonSocial, domicilio, telefono);
		this.fechaDelContratoSocial = fechaDelContratoSocial;
	}

	public String getFechaDelContratoSocial() {
		return fechaDelContratoSocial;
	}

}
