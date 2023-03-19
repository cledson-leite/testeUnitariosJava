package com.cledsonleite.servicos;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.cledsonleite.entidades.Filme;
import com.cledsonleite.entidades.Locacao;
import com.cledsonleite.entidades.Usuario;
import com.cledsonleite.utils.DataUtils;

public class LocacaoServiceTest {
	private LocacaoService service;
	private Usuario user;
	private List<Filme> filmes;
	
	@Before
	public void makeSut() {
		service = new LocacaoService();
		user = new Usuario("usuario 1");
	}

	@Test
	public void deveAlugarUmFilme() throws Exception {
		
		// arrange - cenário
		filmes = Arrays.asList(new Filme("filme 1", 2, 5.0));
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		Assert.assertEquals(5.0, location.getValor(), 0.01);
		Assert.assertTrue(DataUtils.isMesmaData(location.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(location.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
	
	}
	
	@Test(expected = Exception.class)
	public void execptionEleganteFilmeSemEstoque() throws Exception {
		
		// arrange - cenário
		filmes = Arrays.asList(new Filme("filme 1", 0, 5.0));
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		
	
	}
	
	@Test
	//usar essa forma como padrão
	public void execptionRobustaFilmeSemEstoque() { 
		
		// arrange - cenário
		filmes = Arrays.asList(new Filme("filme 1", 0, 5.0));
		
		//act - ação - execução
		Locacao location;
		try {
			location = service.alugarFilme(user, filmes);
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
		filmes = Arrays.asList(new Filme("filme 1", 0, 5.0));
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		//act - ação - execução
		service.alugarFilme(user, filmes);
		
		//assert - verificação
		
	
	}

	@Test
	public void deveAlugarMaisDeUmFilme() throws Exception {
		
		// arrange - cenário
		filmes = Arrays.asList(
				new Filme("filme 1", 2, 5.0),
				new Filme("filme 2", 2, 5.0)
				);
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		Assert.assertEquals(10.0, location.getValor(), 0.01);
		
	}
	
	@Test
	public void deveAlugarTerceiroFilmeCom75pcDoValor() throws Exception {
		
		// arrange - cenário
		filmes = Arrays.asList(
				new Filme("filme 1", 2, 4.0),
				new Filme("filme 2", 2, 4.0),
				new Filme("filme 3", 2, 4.0)
				);
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		Assert.assertEquals(11.0, location.getValor(), 0.01);
		
	}
	
	@Test
	public void deveAlugarQuartoFilmeCom50pcDoValor() throws Exception {
		
		// arrange - cenário
		filmes = Arrays.asList(
				new Filme("filme 1", 2, 4.0),
				new Filme("filme 2", 2, 4.0),
				new Filme("filme 3", 2, 4.0),
				new Filme("filme 4", 2, 4.0)
				);
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		Assert.assertEquals(13.0, location.getValor(), 0.01);
		
	}
	
	@Test
	public void deveAlugarQuintoFilmeCom25pcDoValor() throws Exception {
		
		// arrange - cenário
		filmes = Arrays.asList(
				new Filme("filme 1", 2, 4.0),
				new Filme("filme 2", 2, 4.0),
				new Filme("filme 3", 2, 4.0),
				new Filme("filme 4", 2, 4.0),
				new Filme("filme 5", 2, 4.0)
				);
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		Assert.assertEquals(14.0, location.getValor(), 0.01);
		
	}
	
	@Test
	public void deveAlugarSextoFilmeGratis() throws Exception {
		
		// arrange - cenário
		filmes = Arrays.asList(
				new Filme("filme 1", 2, 4.0),
				new Filme("filme 2", 2, 4.0),
				new Filme("filme 3", 2, 4.0),
				new Filme("filme 4", 2, 4.0),
				new Filme("filme 5", 2, 4.0),
				new Filme("filme 6", 2, 4.0)
				);
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		Assert.assertEquals(14.0, location.getValor(), 0.01);
		
	}
	
	@Test
	public void deveLancarExcecaoCasoAlugueMaisDeSeisFilmes() {
		
		// arrange - cenário
		filmes = Arrays.asList(
				new Filme("filme 1", 2, 4.0),
				new Filme("filme 2", 2, 4.0),
				new Filme("filme 3", 2, 4.0),
				new Filme("filme 4", 2, 4.0),
				new Filme("filme 5", 2, 4.0),
				new Filme("filme 6", 2, 4.0),
				new Filme("filme 7", 2, 4.0)
				);
		
		//act - ação - execução
		try {
			service.alugarFilme(user, filmes);
			Assert.fail();
		} catch (Exception error) {
			Assert.assertThat(error.getMessage(), CoreMatchers.is("Já ultrapassou o limite de filmes alugados"));
		}
		
		//assert - verificação
		
	}

}
