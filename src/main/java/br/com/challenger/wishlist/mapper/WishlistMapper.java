package br.com.challenger.wishlist.mapper;

import br.com.challenger.wishlist.dto.MessageResponse;
import br.com.challenger.wishlist.dto.WishlistRequest;
import br.com.challenger.wishlist.dto.WishlistResponse;
import br.com.challenger.wishlist.model.Wishlist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WishlistMapper {

    public static Wishlist mapper(WishlistRequest wishlistRequest) {
        return Wishlist.builder()
                .clientId(wishlistRequest.getClientId())
                .productId(wishlistRequest.getProductId())
                .build();
    }

    public static MessageResponse mapper(String message) {
        return MessageResponse.builder()
                .message(message)
                .build();
    }

    public static WishlistResponse mapper(Long clientId, List<Wishlist> wishlist) {
        return WishlistResponse.builder()
                .clientId(clientId)
                .productId(wishlist.stream().map(Wishlist::getProductId).collect(Collectors.toList()))
                .build();
    }
}
