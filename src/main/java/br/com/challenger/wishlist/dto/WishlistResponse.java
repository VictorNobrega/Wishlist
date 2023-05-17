package br.com.challenger.wishlist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class WishlistResponse {

    private Long clientId;
    private List<Long> productId;

}
