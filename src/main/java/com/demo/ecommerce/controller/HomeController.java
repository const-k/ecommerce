package com.demo.ecommerce.controller;

import com.demo.ecommerce.domain.Cart;
import com.demo.ecommerce.repository.CartRepository;
import com.demo.ecommerce.repository.ItemRepository;
import com.demo.ecommerce.service.CartService;
import com.demo.ecommerce.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;
    private CartService cartService;
    private InventoryService inventoryService;

    public HomeController(ItemRepository itemRepository, CartRepository cartRepository, CartService cartService, InventoryService inventoryService) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.inventoryService = inventoryService;
    }

    @GetMapping
    Mono<Rendering> home() {
        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", this.itemRepository.findAll())
                .modelAttribute("cart", this.cartRepository.findById("My Cart")
                        .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }

//    @PostMapping("/add/{id}")
//    Mono<String> addToCart(@PathVariable String id) {
//        return this.cartRepository.findById("My Cart")
//                .defaultIfEmpty(new Cart("My Cart"))
//                .flatMap(cart -> cart.getCartItems().stream()
//                        .filter(cartItem -> cartItem.getItem()
//                                .getId().equals(id))
//                        .findAny()
//                        .map(cartItem -> {
//                            cartItem.increment();
//                            return Mono.just(cart);
//                        })
//                        .orElseGet(() -> {
//                            return this.itemRepository.findById(id)
//                                    .map(item -> new CartItem(item))
//                                    .map(cartItem -> {
//                                        cart.getCartItems().add(cartItem);
//                                        return cart;
//                                    });
//                        }))
//                .flatMap(cart -> this.cartRepository.save(cart))
//                .thenReturn("redirect:/");
//    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@PathVariable String id) {
        return this.cartService.addToCart("My Cart", id)
                .thenReturn("redirect:/");
    }

    @GetMapping("/search")
    Mono<Rendering> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam boolean useAnd) {

        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", inventoryService.searchByExample(name, description, useAnd))
                .modelAttribute("cart", this.cartRepository.findById("My Cart")
                        .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }
}
