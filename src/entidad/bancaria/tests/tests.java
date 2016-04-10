package entidad.bancaria.tests;

import entidad.bancaria.cuentas.MotivoDeTransaccion;
import entidad.bancaria.cuentas.TipoDeMovimiento;
import entidad.bancaria.cuentas.Transaccion;
import entidad.bancaria.excepciones.SaldoInsuficienteException;
import entidad.bancaria.excepciones.SinClientesException;

import java.util.HashSet;

import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.clientes.Domicilio;
import entidad.bancaria.clientes.PersonaJuridica;
import entidad.bancaria.cuentas.*;

public class tests {

	public static void main(String[] args) {
		// pruebaTransacciones();
		pruebaCuentas();
	}

	public static void pruebaTransacciones() {
		// Prueba Transacciones
		System.out.println(new Transaccion(TipoDeMovimiento.CREDITO, 9999.0, MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO)
				.toString());
		System.out.println(new Transaccion(TipoDeMovimiento.CREDITO, 9999.0, MotivoDeTransaccion.COBRO_DE_MANTENIMIENTO,
				"Aguante el Paco").toString());
	}

	public static void pruebaCuentas() {
		Cliente carlos = new PersonaJuridica("CUIT", "NombreORazonSocial",
				new Domicilio("Direccion", "CodigoPostal", "Localidad", "Provincia"), "Telefono", "Fecha");
		Cliente[] listaCuentas = new Cliente[] { carlos };
		try {
			CuentaCorriente cuenta = new CuentaCorriente(listaCuentas, 20000.0, -1000.0);
			CajaDeAhorro cuenta2 = new CajaDeAhorro(listaCuentas, 10000.0, 3.0);
			HashSet<Cuenta> hasheo = new HashSet<Cuenta>();
			hasheo.add(cuenta);
			hasheo.add(cuenta2);

			for (Cuenta c : hasheo) {
				if (c instanceof Cuenta) {
					System.out.println("la cuenta es instancia de Cuenta");
				} else {
					System.out.println("la cuenta no es instancia de Cuenta");
				}
				if (c instanceof CajaDeAhorro) {
					System.out.println("la cuenta es instancia de CajaDeAhorro");
				} else {
					System.out.println("la cuenta no es instancia de CajaDeAhorro");
				}
				if (c instanceof CuentaCorriente) {
					System.out.println("la cuenta es instancia de CuentaCorriente");
				} else {
					System.out.println("la cuenta no es instancia de CuentaCorriente");
				}
			}

		} catch (SaldoInsuficienteException | SinClientesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
