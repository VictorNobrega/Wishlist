package br.com.challenger.wishlist.service;

import br.com.challenger.wishlist.WishlistApplication;
import br.com.challenger.wishlist.dto.MessageResponse;
import br.com.challenger.wishlist.dto.WishlistRequest;
import br.com.challenger.wishlist.dto.WishlistResponse;
import br.com.challenger.wishlist.exception.ClientDoesntHaveProductException;
import br.com.challenger.wishlist.exception.ExceptionHandler;
import br.com.challenger.wishlist.mapper.WishlistMapper;
import br.com.challenger.wishlist.model.Wishlist;
import br.com.challenger.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = WishlistApplication.class)
public class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistMapper wishlistMapper;

    @InjectMocks
    private WishlistService wishlistService;

    @Test
    public void addProductInWishlistMaxValueProductsExceptionTest() {
        Mockito.when(wishlistRepository.countWishedProducts(Mockito.anyLong())).thenReturn(20L);

        assertThrows(ExceptionHandler.class, () -> {
            wishlistService.addProductInWishlist(getWishlistRequest());
        });
    }

    @Test
    public void addProductInWishlistProductAddedExceptionTest() {
        Mockito.when(wishlistRepository.countWishedProducts(Mockito.anyLong())).thenReturn(0L);
        Mockito.when(wishlistRepository.verifyProductsInWishlist(Mockito.anyLong(), Mockito.anyLong())).thenReturn(1L);

        assertThrows(ExceptionHandler.class, () -> {
            wishlistService.addProductInWishlist(getWishlistRequest());
        });
    }

    @Test
    public void addProductInWishlistTest() throws ExceptionHandler {
        Mockito.when(wishlistRepository.countWishedProducts(Mockito.anyLong())).thenReturn(0L);
        Mockito.when(wishlistRepository.verifyProductsInWishlist(Mockito.anyLong(), Mockito.anyLong())).thenReturn(0L);

        MessageResponse messageResponse = wishlistService.addProductInWishlist(getWishlistRequest());

        assertEquals(Constant.PRODUCT_ADD_SUCCESS, messageResponse.getMessage());
    }

    @Test
    public void findByClientIdEmptyWishlistExceptionTest() {
        Mockito.when(wishlistRepository.findByClientId(Mockito.anyLong())).thenReturn(Collections.emptyList());

        assertThrows(ExceptionHandler.class, () -> {
            wishlistService.findByClientId(Mockito.anyLong());
        });
    }

    @Test
    public void findByClientIdTest() throws ExceptionHandler {
        Mockito.when(wishlistRepository.findByClientId(Mockito.anyLong())).thenReturn(getWishlist());

        WishlistResponse wishlistResponseExpected = getWishlistResponse();
        WishlistResponse wishlistResponse = wishlistService.findByClientId(1L);

        assertEquals(wishlistResponseExpected.getClientId(), wishlistResponse.getClientId());
        assertEquals(wishlistResponseExpected.getProductId().get(0), wishlistResponse.getProductId().get(0));
    }

    @Test
    public void verifyProductNotInClientWishlistTest() {
        Mockito.when(wishlistRepository.verifyProductsInWishlist(Mockito.anyLong(), Mockito.anyLong())).thenReturn(0L);

        MessageResponse messageResponse = wishlistService.verifyProductInClientWishlist(Mockito.anyLong(), Mockito.anyLong());

        assertEquals(Constant.PRODUCT_NOT_LINK, messageResponse.getMessage());
    }

    @Test
    public void verifyProductInClientWishlistTest() {
        Mockito.when(wishlistRepository.verifyProductsInWishlist(Mockito.anyLong(), Mockito.anyLong())).thenReturn(1L);

        MessageResponse messageResponse = wishlistService.verifyProductInClientWishlist(Mockito.anyLong(), Mockito.anyLong());

        assertEquals(Constant.PRODUCT_LINK, messageResponse.getMessage());
    }

    @Test
    public void deleteWishClientDoesntHaveProductExceptionTest() {
        Mockito.when(wishlistRepository.verifyProductsInWishlist(Mockito.anyLong(), Mockito.anyLong())).thenReturn(0L);

        assertThrows(ExceptionHandler.class, () -> {
            wishlistService.deleteWish(Mockito.anyLong(), Mockito.anyLong());
        });
    }

    @Test
    public void deleteWishTest() throws ClientDoesntHaveProductException {
        Mockito.when(wishlistRepository.verifyProductsInWishlist(Mockito.anyLong(), Mockito.anyLong())).thenReturn(1L);

        MessageResponse messageResponse = wishlistService.deleteWish(Mockito.anyLong(), Mockito.anyLong());

        assertEquals(Constant.PRODUCT_REMOVE_SUCCESS, messageResponse.getMessage());
    }

    private WishlistRequest getWishlistRequest() {
        return WishlistRequest.builder()
                .clientId(1L)
                .productId(1L)
                .build();
    }

    private List<Wishlist> getWishlist() {
        return List.of(Wishlist.builder()
                .clientId(1L)
                .productId(1L)
                .build());
    }

    private WishlistResponse getWishlistResponse() {
        return WishlistResponse.builder()
                .clientId(1L)
                .productId(List.of(1L))
                .build();
    }

}
