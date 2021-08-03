package io.programminglife.myfinances.service;

import java.util.List;
import java.util.Optional;

import io.programminglife.myfinances.entity.PaymentSystem;
import io.programminglife.myfinances.exception.MyFinancesException;

public interface PaymentSystemService {
    
    List<PaymentSystem> findAll();

    PaymentSystem findPaymentSystemById(Long paymentSystemId) throws MyFinancesException;
    
    PaymentSystem savePaymentSystem(PaymentSystem paymentSystem);

    void deletePaymentSystem(Long paymentSystemId);

    Optional<PaymentSystem> findPaymentSystemByLabel(String label);

}