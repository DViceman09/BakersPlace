package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.Order;
import com.sandesh.Online_Bakery.Response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Override
    public PaymentResponse createPaymentLink(Order order) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder().addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD).
                setMode(SessionCreateParams.Mode.PAYMENT).
                setSuccessUrl("http://localhost:3000/payment/success/"+ order.getOrder_id()).
                setCancelUrl("http://localhost:3000/payment/fail/"+ order.getOrder_id()).
                addLineItem(SessionCreateParams.LineItem.builder().
                        setQuantity(1L).setPriceData(SessionCreateParams.LineItem.PriceData.builder().
                                setCurrency("usd").
                                setUnitAmount((long) order.getTotal_price()*100).
                                setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().
                                setName("Anne's bakery").build())
                        .build()).build()).build();

        Session session = Session.create(params);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentURL(session.getUrl());
        return paymentResponse;
    }
}
