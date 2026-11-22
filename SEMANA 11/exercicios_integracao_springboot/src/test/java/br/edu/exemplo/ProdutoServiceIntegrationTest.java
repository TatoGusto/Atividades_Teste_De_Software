package br.edu.exemplo;
import br.edu.exemplo.model.Produto;
import br.edu.exemplo.repository.ProdutoRepository;
import br.edu.exemplo.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest @Transactional
class ProdutoServiceIntegrationTest {
 @Autowired ProdutoService service;
 @Autowired ProdutoRepository repository;

 @Test void deveCadastrarERecuperarProduto(){
  String nome = "Teclado";
  double preco = 150.0;
  Produto produto = service.cadastrar(nome, preco);

  assertNotNull(produto.getId());

  Produto produtoSalvo = repository
          .findById(produto.getId())
          .orElseThrow();

  assertEquals("Teclado", produtoSalvo.getNome());
  assertEquals(150.0, produtoSalvo.getPreco(), 0.001);
 }


 @Test void naoDevePersistirProdutoComNomeEmBranco(){
  long quantidadeAntes = repository.count();

  assertThrows(IllegalArgumentException.class, () -> {
   service.cadastrar(" ", 80.0);
  });

  long quantidadeDepois = repository.count();

  assertEquals(quantidadeAntes, quantidadeDepois);
 }


 @Test void deveAtualizarPrecoPersistido(){
  Produto produto = service.cadastrar("Mouse", 80.0);
  Long id = produto.getId();

  service.atualizarPreco(id, 95.0);

  Produto produtoAtualizado = repository
          .findById(id)
          .orElseThrow();

  assertEquals(95.0, produtoAtualizado.getPreco(), 0.001);
 }
}
