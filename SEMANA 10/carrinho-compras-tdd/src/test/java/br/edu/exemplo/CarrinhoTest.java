package br.edu.exemplo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * Os testes NÃO estão implementados.
 *
 * Para cada requisito apresentado em REQUISITOS.md:
 *
 * 1. escreva primeiro o teste;
 * 2. execute e observe a falha (RED);
 * 3. implemente somente o necessário em Carrinho;
 * 4. execute novamente até o teste passar (GREEN);
 * 5. refatore, se necessário;
 * 6. avance para o próximo requisito.
 */
class CarrinhoTest {

    @Test
    public void deveRetornarZeroSeForVazio() {
        Carrinho carrinho = new Carrinho();
        double total = carrinho.getTotal();

        assertEquals(0.0, total, 0.001);
    }

    @Test
    public void DeveAdicionarProdutoAoCarrinho(){
        Carrinho carrinho = new Carrinho();
        Produto produto = new Produto("Teclado",100.0);

        carrinho.adicionarProduto(produto);

        assertEquals(100.0,carrinho.getTotal(),0.001);
    }

    @Test
    public void deveSomarPrecosDeVariosProdutos(){
        Carrinho carrinho = new Carrinho();
        Produto teclado = new Produto("Teclado", 100.0);
        Produto mouse = new Produto("Mouse", 50.0);
        Produto monitor = new Produto("Monitor", 800.0);

        carrinho.adicionarProduto(teclado);
        carrinho.adicionarProduto(mouse);
        carrinho.adicionarProduto(monitor);

        assertEquals(950.0,carrinho.getTotal(),0.001);
    }


    @Test
    public void deveRemoverProdutoDoCarrinho() {
        Carrinho carrinho = new Carrinho();
        Produto teclado = new Produto("Teclado", 100.0);
        Produto mouse = new Produto("Mouse", 50.0);

        carrinho.adicionarProduto(teclado);
        carrinho.adicionarProduto(mouse);
        carrinho.removerProduto(mouse);

        assertEquals(100.0, carrinho.getTotal(), 0.001);
    }

    @Test
    public void naoDevePermitirPrecoNegativo(){
        String nome = "Teclado";
        double preco = -100.0;

        assertThrows(IllegalArgumentException.class, () -> {
            new Produto(nome, preco);
        });
    }


    @Test
    void deveAplicarDescontoDeDezPorCento() {
        Carrinho carrinho = new Carrinho();
        Produto monitor = new Produto("Monitor", 600.0);

        carrinho.adicionarProduto(monitor);

        assertEquals(540.0, carrinho.getTotal(), 0.001);
    }


    @Test
    void deveAplicarDescontoEmQuinhentos() {
        Carrinho carrinho = new Carrinho();
        Produto produto = new Produto("Produto", 500.0);

        carrinho.adicionarProduto(produto);

        assertEquals(450.0, carrinho.getTotal(), 0.001);
    }






}