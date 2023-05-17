package br.com.challenger.wishlist.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "wishlist")
@Data
@Builder
public class Wishlist {

    private Long clientId;
    private Long productId;

}
