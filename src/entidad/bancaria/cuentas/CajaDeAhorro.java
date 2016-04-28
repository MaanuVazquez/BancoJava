package entidad.bancaria.cuentas;

import entidad.bancaria.clientes.PersonaFisica;
import entidad.bancaria.excepciones.TasaDeInteresNegativaException;

public class CajaDeAhorro extends Cuenta {

	private Double tasaDeInteres;

	public CajaDeAhorro(PersonaFisica[] clientes, Double saldo, Double tasaDeInteres, TipoDeMoneda tipoDeMoneda) throws TasaDeInteresNegativaException {
		super(clientes);
		if(tasaDeInteres < 0){
			throw new TasaDeInteresNegativaException(tasaDeInteres);
		}
		this.saldo = saldo;
		this.tasaDeInteres = tasaDeInteres;
		this.tipoDeMoneda = tipoDeMoneda;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, saldo, MotivoDeTransaccion.DEPOSITO_INICIAL));
	}

	public Double getTasaDeInteres() {
		return tasaDeInteres;
	}
}
