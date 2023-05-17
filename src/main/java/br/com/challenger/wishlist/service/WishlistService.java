package br.com.challenger.wishlist.service;

import br.com.challenger.wishlist.dto.MessageResponse;
import br.com.challenger.wishlist.dto.WishlistRequest;
import br.com.challenger.wishlist.dto.WishlistResponse;
import br.com.challenger.wishlist.exception.*;
import br.com.challenger.wishlist.mapper.WishlistMapper;
import br.com.challenger.wishlist.model.Wishlist;
import br.com.challenger.wishlist.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public MessageResponse addProductInWishlist(WishlistRequest wishlistRequest) throws ExceptionHandler {

        long wishedProductsQuantity = wishlistRepository.countWishedProducts(wishlistRequest.getClientId());

        validateAddition(wishlistRequest, wishedProductsQuantity);

        Wishlist wishlist = WishlistMapper.mapper(wishlistRequest);

        wishlistRepository.save(wishlist);

        return WishlistMapper.mapper(Constant.PRODUCT_ADD_SUCCESS);
    }

    private void validateAddition(WishlistRequest wishlistRequest, long wishedProductsQuantity) throws ExceptionHandler {
        if (verifyProductClientWishlist(wishlistRequest.getClientId(), wishlistRequest.getProductId())) {
            throw new ProductAddedException(Constant.PRODUCT_ADDED_EXCEPTION_MESSAGE);
        }
        if (wishedProductsQuantity >= Constant.MAX_VALUE_PRODUCTS) {
            throw new MaxValueProductsException(Constant.MAX_VALUE_PRODUCTS_EXCEPTION_MESSAGE);
        }
    }

    public WishlistResponse findByClientId(Long clientId) throws ExceptionHandler {

        List<Wishlist> wishlist = wishlistRepository.findByClientId(clientId);

        validateSearch(wishlist);

        return WishlistMapper.mapper(clientId, wishlist);
    }

    private void validateSearch(List<Wishlist> wishlist) throws EmptyWishlistException {
        if (CollectionUtils.isEmpty(wishlist)) {
            throw new EmptyWishlistException(Constant.EMPTY_WISHLIST_EXCEPTION_MESSAGE);
        }
    }

    public MessageResponse verifyProductInClientWishlist(Long clientId, Long productId) {
        Boolean hasProductInWishList = verifyProductClientWishlist(clientId, productId);

        return hasProductInWishList ? WishlistMapper.mapper(Constant.PRODUCT_LINK) : WishlistMapper.mapper(Constant.PRODUCT_NOT_LINK);
    }

    private Boolean verifyProductClientWishlist(Long clientId, Long productId) {
        long hasProduct = wishlistRepository.verifyProductsInWishlist(clientId, productId);

        return hasProduct > Constant.VALUE_CHECK_EXISTS ? Boolean.TRUE : Boolean.FALSE;
    }


    public MessageResponse deleteWish(Long clientId, Long productId) throws ClientDoesntHaveProductException {

        validateExclusion(clientId, productId);

        wishlistRepository.deleteByClientIdAndProductId(clientId, productId);

        return WishlistMapper.mapper(Constant.PRODUCT_REMOVE_SUCCESS);
    }

    private void validateExclusion(Long clientId, Long productId) throws ClientDoesntHaveProductException {
        if (!verifyProductClientWishlist(clientId, productId)) {
            throw new ClientDoesntHaveProductException(Constant.CLIENT_DOESNT_HAVE_PRODUCT_EXCEPTION_MESSAGE);
        }
    }
}
