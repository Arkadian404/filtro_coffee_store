package com.data.filtro.service;

import com.data.filtro.model.*;
import com.data.filtro.repository.CartItemRepository;
import com.data.filtro.repository.CartRepository;
import com.data.filtro.repository.GuestCartRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CartService {


    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    GuestCartRepository guestCartRepository;

    @Autowired
    GuestCartService guestCartService;

    @Autowired
    CartItemRepository cartItemRepository;


    public List<Cart> getAllOrderCartByUserId(int id) {
        return cartRepository.findAllOrderCartByUserId(id);
    }


    public Cart getCurrentOrderCartByUserId(int id) {
        return cartRepository.findCurrentOrderCartByUserId(id);
    }


    public Cart getCartById(int id) {
        return cartRepository.findCartById(id);
    }

    public Cart createCart(User user) {
        System.out.println("create cart for: " + user.getName());
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreatedDate(new Date());
        cart.setStatus(1);
        cart.setCartItemList(new ArrayList<>());
        cartRepository.save(cart);
        return cart;
    }

    public GuestCart createGuestCart() {
        GuestCart guestCart = new GuestCart();
        guestCart.setCreatedDate(new Date());
        guestCart.setCartItemList(new ArrayList<>());
        guestCart = guestCartRepository.save(guestCart);
        return guestCart;
    }

    public Cart getCartByUserId(int userId) {
        return cartRepository.findCartByUserId(userId);
    }

    public Cart getCurrentCartByUserId(int userId) {
        return cartRepository.findCurrentCartByUserId(userId);
    }

    public void addProductToCart(Cart cart, int productId, int quantity) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Không tìm thấy sản phẩm!");
        }
        List<CartItem> cartItemList = cart.getCartItemList();
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProduct().getId() == productId) {
                cartItem.setQuantity(quantity);
                cartItem.setTotal(product.getPrice() * quantity);
                cartItemRepository.save(cartItem);
                return;
            }
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setPrice(product.getPrice());
        newCartItem.setQuantity(quantity);
        newCartItem.setTotal(product.getPrice() * quantity);
        newCartItem.setPurchasedDate(new Date());
        newCartItem.setCart(cart);
        cart.getCartItemList().add(newCartItem);

        cart.setUpdatedDate(newCartItem.getPurchasedDate());
        cartRepository.save(cart);
    }

    public void addProductToGuestCart(GuestCart guestCart, int productId, int quantity) {
        Product product = productService.getProductById(productId);
        List<CartItem> cartItemList = guestCart.getCartItemList();
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProduct().getId() == productId) {
                cartItem.setQuantity(quantity);
                cartItem.setTotal(product.getPrice() * quantity);
                cartItemRepository.save(cartItem);
                return;
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setTotal(product.getPrice() * quantity);
        cartItem.setPurchasedDate(new Date());
        cartItem.setGuestCart(guestCart);
        guestCart.getCartItemList().add(cartItem);
        guestCart.setUpdatedDate(cartItem.getPurchasedDate());
        guestCartRepository.save(guestCart);
    }

    public Cart convertGuestCartToCart(GuestCart guestCart, User user) {
        Cart cart = cartRepository.findCartByUserId(user.getId());
        System.out.println("user la: " + user.getName() + ", id: " + user.getId());
        System.out.println(cart == null ? "null" : "not null");
        if (cart == null) {
            cart = createCart(user);
        }
        cart.setUser(user);
        cart.setStatus(1);
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItem guestCartItem : guestCart.getCartItemList()) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(guestCartItem.getProduct());
            cartItem.setQuantity(guestCartItem.getQuantity());
            cartItem.setPrice(guestCartItem.getPrice());
            cartItem.setTotal(guestCartItem.getTotal());
            cartItem.setPurchasedDate(guestCartItem.getPurchasedDate());
            cartItems.add(cartItem);
            cart.setUpdatedDate(cartItem.getPurchasedDate());
        }
        cart.setCartItemList(cartItems);
        cart = cartRepository.save(cart);
        return cart;
    }


//    public void removeProductFromCart(int cartId, int productId) {
//        Cart cart = cartRepository.findCartByUserId(cartId);
//        Product product = productService.getProductById(productId);
//        cartItemRepository.removeCartItemByCartIdAndProductId(cartId, productId);
//    }

    public int totalOfCartItem(User user) {
        Cart cart = cartRepository.findCurrentCartByUserId(user.getId());
        if (cart != null) {
            List<CartItem> cartItemList = cart.getCartItemList();
            int total = cartItemList.stream().mapToInt(cartItem -> cartItem.getQuantity() * cartItem.getPrice()).sum();
            return total;
        }
        return 0;
    }

    public int totalOfCartItemTemp(int id) {
        GuestCart guestCart = guestCartService.getGuestCartById(id);
        List<CartItem> cartItemList = guestCart.getCartItemList();
        int total = cartItemList.stream().mapToInt(cartItem -> cartItem.getQuantity() * cartItem.getPrice()).sum();
        return total;
    }


    @Transactional
    public void removeCartByCartId(int cartId) {
        cartRepository.deleteByCartId(cartId);
    }

    public int checkCartStatusByCartId(int cartId) {
        return cartRepository.checkCartStatusByCartId(cartId);
    }

}
