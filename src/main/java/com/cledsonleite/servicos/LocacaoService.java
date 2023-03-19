package com.cledsonleite.servicos;

import static com.cledsonleite.utils.DataUtils.adicionarDias;

import java.util.Date;

import com.cledsonleite.entidades.Filme;
import com.cledsonleite.entidades.Locacao;
import com.cledsonleite.entidades.Usuario;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
		
		if(filme.getEstoque() == 0) {
			throw new Exception("Filme sem estoque");
		}
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
}