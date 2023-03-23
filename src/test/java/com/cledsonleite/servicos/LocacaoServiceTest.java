package com.cledsonleite.servicos;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cledsonleite.entidades.Filme;
import com.cledsonleite.entidades.Locacao;
import com.cledsonleite.entidades.Usuario;
import com.cledsonleite.utils.DataUtils;

public class LocacaoServiceTest {
	@Mock
	private LocacaoDao dao;
	@InjectMocks
	private LocacaoService service;
	private Usuario user;
	private List<Filme> filmes;
	
	@Before
	public void makeSut() {
		MockitoAnnotations.openMocks(this);
//		dao = Mockito.mock(LocacaoDao.class);
		service = new LocacaoService(dao);
		user = new Usuario("usuario 1");
	}

	@Test
	public void deveAlugarUmFilme() throws Exception {
		
		// arrange - cenário
		filmes = asList(new Filme("filme 1", 2, 5.0));
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
//		Mockito.when(dao.salvar(location)).thenReturn(true);
		when(dao.salvar(location)).thenReturn(true);
		
		//assert - verificação
		//Assert.assertEquals(5.0, location.getValor(), 0.01);
		assertEquals(5.0, location.getValor(), 0.01);
		assertTrue(DataUtils.isMesmaData(location.getDataLocacao(), new Date()));
		assertTrue(DataUtils.isMesmaData(location.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
		Mockito.verify(dao).salvar(Mockito.any(Locacao.class));
//		Mockito.verify(dao).salvar(location);
//		Mockito.verify(dao, Mockito.times(1)).salvar(location);
//		Mockito.verify(dao, Mockito.never()).salvar(location2);
//		Mockito.verifyNoInteractions(dao);
	
	}
	
	@Test(expected = Exception.class)
	public void execptionEleganteFilmeSemEstoque() throws Exception {
		
		// arrange - cenário
		filmes = asList(new Filme("filme 1", 0, 5.0));
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		
	
	}
	
	@Test
	//usar essa forma como padrão
	public void execptionRobustaFilmeSemEstoque() { 
		
		// arrange - cenário
		filmes = asList(new Filme("filme 1", 0, 5.0));
		
		//act - ação - execução
		Locacao location;
		try {
			location = service.alugarFilme(user, filmes);
			fail("Deveria lançar ua exception");
		} catch (Exception error) {
			assertThat(error.getMessage(), is("Filme sem estoque"));
		}
	
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void execptionComRuleFilmeSemEstoque() throws Exception {
		
		// arrange - cenário
		filmes = asList(new Filme("filme 1", 0, 5.0));
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		//act - ação - execução
		service.alugarFilme(user, filmes);
		
		//assert - verificação
		
	
	}

	@Test
	public void deveAlugarMaisDeUmFilme() throws Exception {
		
		// arrange - cenário
		filmes = asList(
				new Filme("filme 1", 2, 5.0),
				new Filme("filme 2", 2, 5.0)
				);
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		assertEquals(10.0, location.getValor(), 0.01);
		
	}
	
	@Test
	public void deveAlugarTerceiroFilmeCom75pcDoValor() throws Exception {
		
		// arrange - cenário
		filmes = asList(
				new Filme("filme 1", 2, 4.0),
				new Filme("filme 2", 2, 4.0),
				new Filme("filme 3", 2, 4.0)
				);
		
		//act - ação - execução
		Locacao location = service.alugarFilme(user, filmes);
		
		//assert - verificação
		assertEquals(11.0, location.getValor(), 0.01);
		
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
		assertEquals(13.0, location.getValor(), 0.01);
		
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
		assertEquals(14.0, location.getValor(), 0.01);
		
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
		assertEquals(14.0, location.getValor(), 0.01);
		
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
			fail();
		} catch (Exception error) {
			assertThat(error.getMessage(), is("Já ultrapassou o limite de filmes alugados"));
		}
		
		//assert - verificação
		
	}

}
