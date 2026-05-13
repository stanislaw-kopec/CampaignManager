package com.campaign.manager.service;

import com.campaign.manager.exception.InsufficientFundsException;
import com.campaign.manager.model.EmeraldAccount;
import com.campaign.manager.repository.EmeraldAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EmeraldAccountService {

    private final EmeraldAccountRepository emeraldAccountRepository;

    public EmeraldAccountService(EmeraldAccountRepository emeraldAccountRepository) {
        this.emeraldAccountRepository = emeraldAccountRepository;
    }

    public void deductFunds(EmeraldAccount account, BigDecimal amount) {

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        emeraldAccountRepository.save(account);
    }

    public void refundFunds(EmeraldAccount account, BigDecimal amount) {

        account.setBalance(account.getBalance().add(amount));

        emeraldAccountRepository.save(account);
    }
}
