package br.com.challenger.wishlist.service;

public interface Constant {

    int MAX_VALUE_PRODUCTS = 20;
    int VALUE_CHECK_EXISTS = 0;

    String PRODUCT_ADD_SUCCESS = "Produto adicionado com sucesso.";
    String PRODUCT_REMOVE_SUCCESS = "Produto removido com sucesso.";
    String PRODUCT_LINK = "Produto está na Lista de Desejos.";
    String PRODUCT_NOT_LINK = "Produto não está na Lista de Desejos.";

    String PRODUCT_ADDED_EXCEPTION_MESSAGE = "O produto já existe na Lista de Desejos.";
    String MAX_VALUE_PRODUCTS_EXCEPTION_MESSAGE = "Quantidade maxima atingida na Lista de Desejos.";
    String EMPTY_WISHLIST_EXCEPTION_MESSAGE = "A Lista de Desejos está vazia.";
    String CLIENT_DOESNT_HAVE_PRODUCT_EXCEPTION_MESSAGE = "O cliente não possui o produto informado na Lista de Desejos.";
}
