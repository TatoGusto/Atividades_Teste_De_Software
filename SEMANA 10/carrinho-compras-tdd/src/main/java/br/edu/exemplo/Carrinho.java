package br.edu.exemplo;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private double total = 0.0;
    private List<Produto> produtos = new ArrayList<>();

    public void adicionarProduto(Produto produto){
        produtos.add(produto);
    }

    public void removerProduto(Produto produto){
        produtos.remove(produto);
    }

    public double getTotal(){
        for(Produto produto : produtos){
            total += produto.getPreco();
        }

        if (total >= 500.0) {
            total = total * 0.90;
        }

        return total;
    }


}
