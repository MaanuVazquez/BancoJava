package entidad.bancaria.clientes;

import java.util.HashSet;
import java.util.Iterator;

import entidad.bancaria.cuentas.CuentaDeCliente;
import entidad.bancaria.excepciones.CUITInvalidoException;

public abstract class Cliente {

	private String CUIT;
	private String nombreORazonSocial;
	private String telefono;
	private Domicilio domicilio;
	private HashSet<CuentaDeCliente> cuentas;
	private boolean activo;

	protected Cliente(String CUIT, String nombreORazonSocial, Domicilio domicilio, String telefono)
			throws CUITInvalidoException {
		this.CUIT = chequearCUIT(CUIT);
		this.nombreORazonSocial = nombreORazonSocial;
		this.setDomicilio(domicilio);
		this.setTelefono(telefono);
		this.activo = true;
	}

	public static String chequearCUIT(String CUIT) throws CUITInvalidoException {

		CUIT = CUIT.replaceAll("[^\\d]", "");

		if (CUIT.length() != 11) {
			throw new CUITInvalidoException();
		}

		return CUIT;

	}

	public boolean isActivo() {
		return activo;
	}

	public boolean darDeBaja() {
		boolean cuentaHabilitada = false;
		if (!cuentas.isEmpty()) {
			Iterator<CuentaDeCliente> iterador = cuentas.iterator();
			CuentaDeCliente cuenta;
			while (iterador.hasNext() && !cuentaHabilitada) {
				cuenta = iterador.next();
				cuentaHabilitada = cuenta.isHabilitada();
			}
		}
		this.activo = cuentaHabilitada;
		return !activo;
	}

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

	public HashSet<CuentaDeCliente> getCuentas() {
		return cuentas;
	}

	public void asignarCuenta(CuentaDeCliente cuenta) {
		cuentas.add(cuenta);
	}

	public void removerCuenta(CuentaDeCliente cuenta) {
		Iterator<CuentaDeCliente> iterador = cuentas.iterator();
		CuentaDeCliente cuentaTemporal = iterador.next();
		while (iterador.hasNext() && cuentaTemporal != cuenta) {
			cuenta = iterador.next();
		}
		if (cuenta.equals(cuentaTemporal)) {
			iterador.remove();
		}
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (CUIT == null) {
			if (other.CUIT != null)
				return false;
		} else if (!CUIT.equals(other.CUIT))
			return false;
		if (cuentas == null) {
			if (other.cuentas != null)
				return false;
		} else if (!cuentas.equals(other.cuentas))
			return false;
		if (domicilio == null) {
			if (other.domicilio != null)
				return false;
		} else if (!domicilio.equals(other.domicilio))
			return false;
		if (activo != other.activo)
			return false;
		if (nombreORazonSocial == null) {
			if (other.nombreORazonSocial != null)
				return false;
		} else if (!nombreORazonSocial.equals(other.nombreORazonSocial))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

}
