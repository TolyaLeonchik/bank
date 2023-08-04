package com.bank.service;

import com.bank.domain.CardInfo;
import com.bank.domain.TransferOperations;
import com.bank.domain.Users;
import com.bank.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private Integer countOfAccounts = 2;

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Users> getUsers() {
        return bankRepository.getAllUsers();
    }

    public Users getUser(Integer id) {
        return bankRepository.getUser(id);
    }

    public Users createUser(Users user) {
        user.setId(++countOfAccounts);
        bankRepository.save(user);
        return user;
    }

    public void updateUser(Users user) {
        Users userRedactor = getUser(user.getId());
        userRedactor.setFirstName(user.getFirstName());
        userRedactor.setLastName(userRedactor.getLastName());
        userRedactor.setLogin(user.getLogin());
        userRedactor.setPassword(user.getPassword());
        bankRepository.save(userRedactor);
    }

    public void deleteUser(Integer id) {
        bankRepository.delete(id);
    }

    public void transferMoney(Users userWhoTransfers, Users userWillGetMoney, Integer sum) {
        Users moneyFrom = getUser(userWhoTransfers.getId());
        Users moneyTo = getUser(userWillGetMoney.getId());

        moneyFrom.setBalance(userWhoTransfers.getBalance() - sum);
        moneyTo.setBalance(userWillGetMoney.getBalance() + sum);

        bankRepository.transfer(moneyFrom, moneyTo);
    }

    public CardInfo getCardInfo(Integer id) {
        CardInfo cardInfo = new CardInfo();
        Users user = bankRepository.getUser(id);
        cardInfo.setFirstName(user.getFirstName().toUpperCase());
        cardInfo.setLastName(user.getLastName().toUpperCase());
        cardInfo.setNumberCard(user.getNumberCard());
        cardInfo.setExpirationMonth(user.getExpirationMonth());
        cardInfo.setExpirationYear(user.getExpirationYear());
        return cardInfo;
    }

    public Users findUser(String numberCard) {
        return bankRepository.findUserCard(numberCard);
    }

    public void transferMoneyTwo(Users userFrom, Users userTo, TransferOperations trans) {
        Users moneyFrom = getUser(userFrom.getId());
        Users moneyTo = getUser(userTo.getId());

        moneyFrom.setBalance(moneyFrom.getBalance() - trans.getSumma());
        moneyTo.setBalance(moneyTo.getBalance() + trans.getSumma());

        bankRepository.transfer(moneyFrom, moneyTo);
    }
}
