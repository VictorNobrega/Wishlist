package br.com.challenger.wishlist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WishlistRequest {

    private Long clientId;
    private Long productId;

}
