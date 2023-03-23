package com.cledsonleite.servicos;

import static com.cledsonleite.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import com.cledsonleite.entidades.Filme;
import com.cledsonleite.entidades.Locacao;
import com.cledsonleite.entidades.Usuario;

public class LocacaoService {
	
	private final LocacaoDao dao;

	public LocacaoService(LocacaoDao dao) {
		
		this.dao = dao;
	}

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws Exception {
		
		Locacao locacao = new Locacao();
		
		if(filmes.size() >= 7) {
			throw new Exception("Já ultrapassou o limite de filmes alugados");
		}
		
		for(Filme filme: filmes) {			
			if(filme.getEstoque() == 0) {
				throw new Exception("Filme sem estoque");
			}
			
		}
		double valorTotal = 0.0;
		
		for(int index = 0; index < filmes.size(); index++) {
			Filme filme = filmes.get(index);
			double precoComDesconto = 0.0;
			switch (index) {
			case 2:
				precoComDesconto = filme.getPrecoLocacao() * 0.75;
				filme.setPrecoLocacao(precoComDesconto);
				break;
			case 3:
				precoComDesconto = filme.getPrecoLocacao() * 0.50;
				filme.setPrecoLocacao(precoComDesconto);
				break;
			case 4:
				precoComDesconto = filme.getPrecoLocacao() * 0.25;
				filme.setPrecoLocacao(precoComDesconto);
				break;
			case 5:
				filme.setPrecoLocacao(0.0);
				break;
			}
			valorTotal += filme.getPrecoLocacao();
			
		}
		
		locacao.setValor(valorTotal);
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());


		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		dao.salvar(locacao);
		
		return locacao;
	}
}