package br.com.challenger.wishlist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WishlistRequest {

    @NotNull(message = "Cliente não pode ser nulo.")
    private Long clientId;

    @NotNull(message = "Produto não pode ser nulo.")
    private Long productId;

}
