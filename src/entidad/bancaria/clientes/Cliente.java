package entidad.bancaria.clientes;

import java.util.HashSet;
import java.util.Iterator;

import entidad.bancaria.cuentas.Cuenta;
import entidad.bancaria.excepciones.CUITInvalidoException;

public abstract class Cliente {

	private String CUIT;
	private String nombreORazonSocial;
	private String telefono;
	private Domicilio domicilio;
	private HashSet<Cuenta> cuentas;
	private boolean activo;

	/**
	 * Crea el registro de un nuevo cliente
	 * 
	 * @param CUIT
	 *            : Clave Única de Identificación Tributaria.
	 * @param nombreORazonSocial
	 * @param domicilio
	 * @param telefono
	 * @throws CUITInvalidoException
	 */

	protected Cliente(String CUIT, String nombreORazonSocial,
			Domicilio domicilio, String telefono) throws CUITInvalidoException {
		this.CUIT = chequearCUIT(CUIT);
		this.nombreORazonSocial = nombreORazonSocial;
		this.setDomicilio(domicilio);
		this.setTelefono(telefono);
		this.activo = true;
	}

	/**
	 * Modifica el ingreso del CUIT. Remueve los caracteres que no sean digitos
	 * y en caso de que no tenga 11 numeros lanza una excepcion.
	 * 
	 * @param CUIT
	 *            : Clave Única de Identificación Tributaria.
	 * @return CUIT validado.
	 * @throws CUITInvalidoException
	 */

	public static String chequearCUIT(String CUIT) throws CUITInvalidoException {

		CUIT = CUIT.replaceAll("[^\\d]", "");

		if (CUIT.length() != 11) {
			throw new CUITInvalidoException();
		}

		return CUIT;

	}

	/**
	 * @return	true si el cliente esta activo.
	 */
	
	public boolean isActivo() {
		return activo;
	}

	/**
	 * 
	 * @return
	 */
	
	public boolean darDeBaja() {
		boolean cuentaHabilitada = false;
		if (!cuentas.isEmpty()) {
			Iterator<Cuenta> iterador = cuentas.iterator();
			Cuenta cuenta;
			while (iterador.hasNext() && !cuentaHabilitada) {
				cuenta = iterador.next();
				cuentaHabilitada = cuenta.isHabilitada();
			}
		}
		this.activo = cuentaHabilitada;
		return !activo;
	}

	/**
	 * Asigna una cuenta al cliente
	 * @param cuenta cuenta a asignar
	 */
	public void asignarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}
	
	/**
	 * Activa un cliente dado de baja anteriormente
	 */
	
	public void activar() {
		this.activo = true;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public String getNombreORazonSocial() {
		return nombreORazonSocial;
	}

	public String getCUIT() {
		return CUIT;
	}

	public HashSet<Cuenta> getCuentas() {
		return cuentas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CUIT == null) ? 0 : CUIT.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (this.CUIT != ((Cliente) obj).CUIT)
			return false;
		return true;
	}

	public int compareTo(Cliente obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		return this.CUIT.compareTo(obj.CUIT);
	}
}
