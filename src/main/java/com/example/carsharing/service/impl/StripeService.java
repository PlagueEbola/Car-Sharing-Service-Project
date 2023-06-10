package com.example.carsharing.service.impl;

import com.example.carsharing.model.Car;
import com.example.carsharing.model.Payment;
import com.example.carsharing.model.Rental;
import com.example.carsharing.service.PaymentService;
import com.example.carsharing.service.RentalService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StripeService {
    private static final String SUCCESS_ENDPOINT = "http://localhost:8080/payments/success/";
    private static final String CANCEL_ENDPOINT = "http://localhost:8080/payments/cancel/";

    private final RentalService rentalService;
    private final PaymentService paymentService;

    static {
        Stripe.apiKey = "sk_test_51NDSqsJH4Qcr0ApldF1YRKWQqxkYbfT4fMOzIcY"
                + "m9PboaREpVeArU8o1AhqU09pt4iKAzEhEdsyWEO9dDJiKTifG00pAinB9sK";
    }

    public void deleteStripeProduct(Payment payment) {
        try {
            Price price = Price.retrieve(payment.getStripePrice());
            Product product = Product.retrieve(price.getProduct());
            price.setDeleted(true);
            product.setDeleted(true);
        } catch (StripeException e) {
            throw new RuntimeException("Unable to delete stripe price", e);
        }
    }

    public String getPaymentSessionUrl(Payment payment, Long totalPriceInUnits) {
        Rental rental = payment.getRental();
        Car car = rental.getCar();

        try {
            ProductCreateParams productCreateParams =
                    ProductCreateParams.builder()
                            .setName((car.getBrand()) + " " + car.getModel())
                            .setDescription("You have returned the rental on "
                                    + rental.getActualReturnDate())
                            .build();
            Product product = Product.create(productCreateParams);

            PriceCreateParams priceCreateParams =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(totalPriceInUnits)
                            .setProduct(product.getId())
                            .build();
            Price price = Price.create(priceCreateParams);
            payment.setStripePrice(price.getId());

            SessionCreateParams sessionCreateParams =
                    SessionCreateParams
                            .builder()
                            .addLineItem(
                                    SessionCreateParams.LineItem
                                            .builder()
                                            .setPrice(price.getId())
                                            .setQuantity(1L)
                                            .build()
                            )
                            .setSuccessUrl(SUCCESS_ENDPOINT
                                    + payment.getId())
                            .setCancelUrl(CANCEL_ENDPOINT
                                    + payment.getId())
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .build();
            Session session = Session.create(sessionCreateParams);
            return session.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException("Unable to create a Stripe session ", e);
        }
    }
}
