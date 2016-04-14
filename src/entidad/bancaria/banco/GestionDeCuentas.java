package entidad.bancaria.banco;

import entidad.bancaria.cuentas.TipoDeMoneda;
import entidad.bancaria.excepciones.CBUInexistenteException;
import entidad.bancaria.excepciones.ClienteInexistenteException;
import entidad.bancaria.excepciones.DepositoInicialInvalidoException;
import entidad.bancaria.excepciones.SinClientesException;

public class GestionDeCuentas {
	
	/**
	 * Abre una Caja De Ahorro asociada al banco
	 * @param clientes : arreglo de CUITs de los titulares de la cuenta.
	 * @param saldo : Deposito inicial para crear la cuenta.
	 * @param tasaDeInteres : ¿?
	 * @param tipoDeMoneda : Tipo de moneda de la cuenta creada, puede ser PESO o DOLAR.
	 * @return : Numero de CBU de la cuenta creada. Devuelve 0 en caso de no poder crear la cuenta.
	 */
	
	public static int aperturaDeCajaDeAhorro(String[] cuits, Double saldo,
			Double tasaDeInteres, TipoDeMoneda tipoDeMoneda){
		// falta validacion de persona fisica
		try {
			return Banco.crearCajaDeAhorro(cuits, saldo, tasaDeInteres, tipoDeMoneda);
		} catch (DepositoInicialInvalidoException e) {
			System.out.println(e);
		} catch (SinClientesException e) {
			System.out.println(e);
		} catch (ClienteInexistenteException e) {
			System.out.println(e);
		}
		return 0;
	}
	
	/**
	 * Abre una Cuenta Corriente asociada al banco
	 * @param cuits : Arreglo de CUITs de los titulares de la cuenta. Deben ser personas fisicas
	 * @param saldo : Deposito inicial para crear la cuenta.
	 * @param sobregiro : Valor de descubierto que puede tener la cuenta.
	 * @return : Numero de CBU de la cuenta creada. Devuelve 0 en caso de no poder crear la cuenta.
	 * @throws DepositoInicialInvalidoException
	 * @throws ClienteInexistenteException
	 * @throws SinClientesException
	 */
	
	public int aperturaDeCuentaCorriente(String[] cuits, Double saldo, Double sobregiro) {
		
		try {
			return Banco.crearCuentaCorriente(cuits, saldo, sobregiro);
		} catch (DepositoInicialInvalidoException e) {
			System.out.println(e);
		} catch (ClienteInexistenteException e) {
			System.out.println(e);
		} catch (SinClientesException e) {
			System.out.println(e);
		}
		return 0;
	}
	
	/**
	 * Inhabilita una cuenta para impedir que realize movimientos.
	 * @param cbu : CBU de la cuenta que se desea inhabilitar.
	 */
	
	public void inhabilitarCuenta(int cbu){
		try {
			Banco.inhabilitarCuenta(cbu);
		} catch (CBUInexistenteException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Habilita una cuenta para permitir que realize movimientos. Activa los titulares que hayan sido marcados como inactivos.
	 * @param cbu : CBU de la cuenta que se desea habilitar.
	 * @throws CBUInexistenteException 
	 */
	
	public void habilitarCuenta(int cbu){
		try {
			Banco.habilitarCuenta(cbu);
		} catch (CBUInexistenteException e) {
			System.out.println(e);
		}
	}
}
