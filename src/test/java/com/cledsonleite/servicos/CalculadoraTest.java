package com.cledsonleite.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {
	private Calculadora sut;
	int numero1, numero2;
	
	@Before
	public void makeSut() {
		numero1 = 10;
		numero2 = 5;
		sut = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisNumeros() {
		int resultado = sut.somar(numero1, numero2);
		Assert.assertEquals(15, resultado);
	}
	
	@Test
	public void deveSubtrairDoisNumeros() {
		int resultado = sut.subtrair(numero1, numero2);
		Assert.assertEquals(5, resultado);
	}
	
	@Test
	public void deveMultiplicarDoisNumeros() {
		int resultado = sut.multiplicar(numero1, numero2);
		Assert.assertEquals(50, resultado);
	}
	
	@Test
	public void deveLançarUmExcecaoQuandoDivisorForZero() {
		numero2 = 0;
		int resultado;
		try {
			resultado = sut.dividir(numero1, numero2);
			Assert.fail();
		} catch (Exception error) {			// TODO Auto-generated catch block			e.printStackTrace();
			Assert.assertEquals(error.getMessage(), "O divisor deve ser diferente de zero");
		}
	}
	
	@Test
	public void deveDividirDoisNumeros() throws Exception {
		int resultado = sut.dividir(numero1, numero2);
		Assert.assertEquals(2, resultado);
	}

}
