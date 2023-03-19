package com.cledsonleite.servicos;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.cledsonleite.entidades.Filme;
import com.cledsonleite.entidades.Locacao;
import com.cledsonleite.entidades.Usuario;
import com.cledsonleite.utils.DataUtils;

public class LocacaoServiceTest {

	@Test
	public void test() throws Exception {
		
		// arrange - cenário
		LocacaoService service = new LocacaoService();
		Usuario user = new Usuario("usuario 1");
		Filme filme = new Filme("filme 1", 2, 5.0);
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filme);
		
		//assert - verificação
		Assert.assertEquals(5.0, location.getValor(), 0.01);
		Assert.assertTrue(DataUtils.isMesmaData(location.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(location.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
	
	}
	
	@Test(expected = Exception.class)
	public void execptionEleganteFilmeSemEstoque() throws Exception {
		
		// arrange - cenário
		LocacaoService service = new LocacaoService();
		Usuario user = new Usuario("usuario 1");
		Filme filme = new Filme("filme 1", 0, 5.0);
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filme);
		
		//assert - verificação
		
	
	}
	
	@Test
	//usar essa forma como padrão
	public void execptionRobustaFilmeSemEstoque() { 
		
		// arrange - cenário
		LocacaoService service = new LocacaoService();
		Usuario user = new Usuario("usuario 1");
		Filme filme = new Filme("filme 1", 0, 5.0);
		
		//act - ação - execução
		Locacao location;
		try {
			location = service.alugarFilme(user, filme);
			Assert.fail("Deveria lançar ua exception");
		} catch (Exception error) {
			Assert.assertThat(error.getMessage(), CoreMatchers.is("Filme sem estoque"));
		}
	
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void execptionComRuleFilmeSemEstoque() throws Exception {
		
		// arrange - cenário
		LocacaoService service = new LocacaoService();
		Usuario user = new Usuario("usuario 1");
		Filme filme = new Filme("filme 1", 0, 5.0);
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		//act - ação - execução
		service.alugarFilme(user, filme);
		
		//assert - verificação
		
	
	}

}
