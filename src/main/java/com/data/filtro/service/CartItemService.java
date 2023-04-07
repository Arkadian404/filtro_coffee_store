package com.data.filtro.service;

import com.data.filtro.model.CartItem;
import com.data.filtro.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;


    public List<CartItem> getCartItemByCartId(int id) {
        List<CartItem> cartItemList = cartItemRepository.findCartItemByCart(id);
        return cartItemList;
    }

    @Transactional
    public void removeCartItemByProductId(int cartId, int productId) {
        cartItemRepository.removeCartItemByCartIdAndProductId(cartId, productId);
    }

    public List<CartItem> findAllByCartIdAndProduct(int cartId, int productId) {
        return cartItemRepository.getByCartAndProduct(cartId, productId);
    }
}
