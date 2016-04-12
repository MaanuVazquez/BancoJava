package entidad.bancaria.clientes;

import java.util.HashSet;
import java.util.Iterator;

import entidad.bancaria.cuentas.Cuenta;

public abstract class Cliente {

	private String CUIT;
	private String nombreORazonSocial;
	private String telefono;
	private Domicilio domicilio;
	private HashSet<Cuenta> cuentas;
	private boolean habilitado;

	protected Cliente(String CUIT, String nombreORazonSocial, Domicilio domicilio, String telefono) {
		this.CUIT = CUIT;
		this.nombreORazonSocial = nombreORazonSocial;
		this.setDomicilio(domicilio);
		this.setTelefono(telefono);
		this.habilitado = true;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void deshabilitar() {
		boolean cuentaHabilitada = false;
		if (!cuentas.isEmpty()) {
			Iterator<Cuenta> iterador = cuentas.iterator();
			Cuenta cuenta;
			while (iterador.hasNext() && !cuentaHabilitada) {
				cuenta = iterador.next();
				cuentaHabilitada = cuenta.isHabilitada();
			}
		}
		this.habilitado = cuentaHabilitada;
	}

	public void habilitar() {
		this.habilitado = true;
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

	public void asignarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}

	public void removerCuenta(Cuenta cuenta) {
		Iterator<Cuenta> iterador = cuentas.iterator();
		Cuenta cuentaTemporal = iterador.next();
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
		if (habilitado != other.habilitado)
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
