package com.example.carsharing.service.mapper;

import com.example.carsharing.dto.request.CarRequestDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductUpdateParams;
import org.springframework.stereotype.Component;

@Component
public class StripeCarProductService {
    static {
        Stripe.apiKey = "sk_test_51NDSqsJH4Qcr0ApldF1YRKWQqxkYbfT4fMOzIcY"
                + "m9PboaREpVeArU8o1AhqU09pt4iKAzEhEdsyWEO9dDJiKTifG00pAinB9sK";
    }

    public String createStripeProduct(CarRequestDto dto) {
        try {
            ProductCreateParams productCreateParams =
                    ProductCreateParams.builder()
                            .setName(dto.getBrand() + " " + dto.getModel())
                            .setDescription("A car")
                            .build();
            Product product = Product.create(productCreateParams);

            PriceCreateParams priceCreateParams =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(dto.getDailyFee().longValue())
                            .setProduct(product.getId())
                            .build();
            Price price = Price.create(priceCreateParams);

            return price.getId();
        } catch (StripeException e) {
            throw new RuntimeException("Unable to create Stripe objects for car ", e);
        }
    }

    public void deleteStripeProduct(String priceId) {
        try {
            Price price = Price.retrieve(priceId);
            Product product = Product.retrieve(price.getProduct());
            price.setDeleted(true);
            product.setDeleted(true);
        } catch (StripeException e) {
            throw new RuntimeException("Unable to delete stripe objects with id" + priceId, e);
        }
    }

    public String updateStripeProduct(String priceId, CarRequestDto dto) {
        try {
            Price price = Price.retrieve(priceId);
            price.setDeleted(true);
            Product product = Product.retrieve(price.getProduct());
            ProductUpdateParams productUpdateParams =
                    ProductUpdateParams.builder()
                            .setName(dto.getBrand() + " " + dto.getModel())
                            .setDescription("A car")
                            .build();
            product.update(productUpdateParams);
            PriceCreateParams priceCreateParams =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(dto.getDailyFee().longValue())
                            .setProduct(product.getId())
                            .build();
            Price newPrice = Price.create(priceCreateParams);
            return newPrice.getId();
        } catch (StripeException e) {
            throw new RuntimeException("Unable to delete stripe objects with id" + priceId, e);
        }
    }

    public String getPaymentUrl(String priceId, Long quantity, Long paymentId) {
        try {
            PaymentLinkCreateParams.AfterCompletion afterCompletion =
                    PaymentLinkCreateParams.AfterCompletion
                            .builder()
                            .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                            .setRedirect(
                                    PaymentLinkCreateParams.AfterCompletion.Redirect
                                            .builder()
                                            .setUrl("http://localhost:8080/payments/success/"
                                                    + paymentId)
                                            .build())
                            .build();

            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams
                            .builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem
                                            .builder()
                                            .setPrice(priceId)
                                            .setQuantity(quantity < 1 ? 1 : quantity)
                                            .build()
                            )
                            .setAfterCompletion(afterCompletion)
                            .build();

            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
