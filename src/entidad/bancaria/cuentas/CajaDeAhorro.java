package entidad.bancaria.cuentas;

import entidad.bancaria.clientes.PersonaFisica;
import entidad.bancaria.excepciones.DepositoInicialInvalidoException;
import entidad.bancaria.excepciones.SinClientesException;
import entidad.bancaria.excepciones.TasaDeInteresNegativaException;

public class CajaDeAhorro extends Cuenta {

	private Double tasaDeInteres;

	/**
	 * Crea una CajaDeAhorro
	 * @param clientes : arreglo de clientes que deben ser PersonasFisicas
	 * @param depositoInicial : valor inicial de la cuenta 
	 * @param tasaDeInteres : Interes obtenido en base al saldo de la cuenta
	 * @param tipoDeMoneda
	 * @throws TasaDeInteresNegativaException
	 * @throws DepositoInicialInvalidoException
	 * @throws SinClientesException
	 */
	
	public CajaDeAhorro(PersonaFisica[] clientes, Double depositoInicial, Double tasaDeInteres, TipoDeMoneda tipoDeMoneda) throws TasaDeInteresNegativaException, DepositoInicialInvalidoException, SinClientesException {
		super(clientes);
		if(tasaDeInteres < 0){
			throw new TasaDeInteresNegativaException(tasaDeInteres);
		}
		if (depositoInicial <= 0) {
			throw new DepositoInicialInvalidoException(depositoInicial, 1.0);
		}
		Cuenta.CBU_MAX++;
		this.saldo = depositoInicial;
		this.tasaDeInteres = tasaDeInteres;
		this.tipoDeMoneda = tipoDeMoneda;
		this.transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, depositoInicial, MotivoDeTransaccion.DEPOSITO_INICIAL));
	}

	public double getTasaDeInteres() {
		return tasaDeInteres;
	}
}
