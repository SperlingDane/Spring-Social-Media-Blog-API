package com.example.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    @Autowired
    public AccountRepository accountRepository;

    public Account registerAccount(Account account){
            
        return accountRepository.save(account);
    }

    public Boolean accountExists(Account account){
        Account possibleAccount = accountRepository.findByUsername(account.getUsername());
        if(possibleAccount != null){
            return true;
        }
        return false;
    }

    public Boolean accountExists(int id){
        Account possibleAccount = accountRepository.findById(id);
        if(possibleAccount != null){
            return true;
        }
        return false;
    }

    public Account loginToAccount(Account account){
        Account possibleAccount = accountRepository.findByUsername(account.getUsername());
        if(possibleAccount != null && possibleAccount.getPassword() == account.getPassword()){
            return accountRepository.findByUsername(account.getUsername());
        }
        return null;
    }
}
