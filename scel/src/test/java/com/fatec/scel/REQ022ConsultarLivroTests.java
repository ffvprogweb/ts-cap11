package com.fatec.scel;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@SpringBootTest
class REQ022ConsultarLivroTests {

	@Autowired
	LivroRepository repository;

	@Test
	void ct01_consulta_lviro_com_sucesso() {
		// Dado – que o livro está cadastrado
		repository.deleteAll();
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);
		// Quando – o usuário consulta o livro pelo isbn
		Livro ro = repository.findByIsbn("3333");
		// Então – o resultado obtido da consulta ao db deve ser igual a do objeto java
		// cadastrado
		assertThat(ro).isEqualTo(livro);
	}

	@Test
	void ct02_quando_consulta_titulo_parcial_retorna3() {
		// Dado – que existem 3 livros cadastrados com titulo teste e 1 com titulo
		// engenharia
		repository.deleteAll();
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);
		livro = new Livro("4444", "Teste Software", "Delamaro");
		repository.save(livro);
		livro = new Livro("5555", "Teste", "Delamaro");
		repository.save(livro);
		livro = new Livro("6666", "Engenharia de Software", "Pressman");
		repository.save(livro);
		// Quando – o usuário consulta pelo titulo teste
		List<Livro> ro = repository.findAllByTituloIgnoreCaseContaining("Teste");
		// Então – retorna 3 livros
		assertThat(ro.size()).isEqualTo(3);
	}

	@Test
	void ct03_quando_solicita_consulta_ordenada_por_titulo_primeiro_registro_retorna_engenharia() {
		// Dado – que existem 4 livros cadastrados
		repository.deleteAll();
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);
		livro = new Livro("4444", "Teste Software", "Delamaro");
		repository.save(livro);
		livro = new Livro("5555", "Teste", "Delamaro");
		repository.save(livro);
		livro = new Livro("6666", "Engenharia de Software", "Pressman");
		repository.save(livro);
		// Quando – consulta ordenado pelo titulo
		List<Livro> ro = repository.buscaTodosLivrosOrdenadosPorTitulo();
		// Então – retorna engenharia
		assertThat(ro.get(0).getTitulo()).isEqualTo("Engenharia de Software");
	}

}
