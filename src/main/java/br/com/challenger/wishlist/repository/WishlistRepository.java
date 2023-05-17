package br.com.challenger.wishlist.repository;

import br.com.challenger.wishlist.model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, Long> {

    @Query(value = "{'clientId': ?0}")
    List<Wishlist> findByClientId(Long clientId);

    void deleteByClientIdAndProductId(Long clientId, Long productId);

    @Query(value = "{'clientId': ?0, 'productId': ?1}", count = true)
    long verifyProductsInWishlist(Long clientId, Long productId);

    @Query(value = "{'clientId': ?0}", count = true)
    long countWishedProducts(Long clientId);
}
