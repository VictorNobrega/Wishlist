package br.com.challenger.wishlist.controller;

import br.com.challenger.wishlist.dto.MessageResponse;
import br.com.challenger.wishlist.dto.WishlistRequest;
import br.com.challenger.wishlist.dto.WishlistResponse;
import br.com.challenger.wishlist.exception.ClientDoesntHaveProductException;
import br.com.challenger.wishlist.exception.ExceptionHandler;
import br.com.challenger.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @RequestMapping(method = RequestMethod.POST)
    @Operation(summary = "Adiciona um produto na lista de desejo.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Quando o produto é adicionado com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Caso o produto já exista na lista ou a quantidade de produtos na lista seja igual a 20.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseStatusException.class)))})
    public ResponseEntity<MessageResponse> addWish(@Valid @RequestBody WishlistRequest wishlistRequest) {
        try {
            return new ResponseEntity<>(wishlistService.addProductInWishlist(wishlistRequest), HttpStatus.OK);
        } catch (ExceptionHandler e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET)
    @Operation(summary = "Lista os produto na lista de desejo de um cliente",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Quando o cliente possui uma lista de desejo.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WishlistResponse.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Caso o cliente não possua uma lista de desejo.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseStatusException.class)))})
    public ResponseEntity<WishlistResponse> findByClientId(@PathVariable("clientId") Long clientId) {
        try {
            return new ResponseEntity<>(wishlistService.findByClientId(clientId), HttpStatus.OK);
        } catch (ExceptionHandler e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @RequestMapping(value = "/{clientId}/{productId}", method = RequestMethod.GET)
    @Operation(summary = "Verifica se um produto já existe na lista de desejo de um cliente.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Retorna uma mensagem informando se o produto existe ou não na lista de desejos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponse.class)))})
    public ResponseEntity<MessageResponse> verifyProductInClientWishlist(
            @PathVariable("clientId") Long clientId,
            @PathVariable("productId") Long productId) {
        return new ResponseEntity<>(wishlistService.verifyProductInClientWishlist(clientId, productId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{clientId}/{productId}", method = RequestMethod.DELETE)
    @Operation(summary = "Exclui um produto da lista de desejos de um cliente",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Caso o produto tenha sido excluido com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Caso o produto não exista na Lista de Desejos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseStatusException.class)))})
    public ResponseEntity<MessageResponse> deleteWish(@PathVariable("clientId") Long clientId, @PathVariable("productId") Long productId) {
        try {
            return new ResponseEntity<>(wishlistService.deleteWish(clientId, productId), HttpStatus.OK);
        } catch (ClientDoesntHaveProductException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
