package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.Order;
import com.sandesh.Online_Bakery.Response.PaymentResponse;
import com.stripe.exception.StripeException;
import lombok.Data;


public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
