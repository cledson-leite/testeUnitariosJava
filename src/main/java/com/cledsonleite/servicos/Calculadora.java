package com.cledsonleite.servicos;

public class Calculadora {

	public int somar(int numero1, int numero2) {
		return numero1 + numero2;
	}

	public int subtrair(int numero1, int numero2) {
		return numero1 - numero2;
	}

	public int multiplicar(int numero1, int numero2) {
		return numero1 * numero2;
	}

	public int dividir(int numero1, int numero2) throws Exception {
		if(numero2 == 0) {
			throw new Exception("O divisor deve ser diferente de zero");
		}
		return numero1 / numero2;
	}

}
