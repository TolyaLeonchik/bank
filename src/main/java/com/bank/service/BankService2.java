package com.bank.service;

import com.bank.domain.CardInfo;
import com.bank.domain.TransferOperations;
import com.bank.domain.Users;
import com.bank.domain.Users2;
import com.bank.repository.BankRepository;
import com.bank.repository.BankRepository2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService2 {
    private Integer countOfAccounts = 2;

    private final BankRepository2 bankRepository;

    public BankService2(BankRepository2 bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Users2> getUsers() {
        return bankRepository.getAllUsers();
    }

    public Users2 getUser(Integer id) {
        return bankRepository.getUser(id);
    }

    public Users2 createUser(Users2 user) {
        user.setId(++countOfAccounts);
        bankRepository.save(user);
        return user;
    }

    public void updateUser(Users2 user) {
        Users2 userRedactor = getUser(user.getId());
        userRedactor.setFirstName(user.getFirstName());
        userRedactor.setLastName(userRedactor.getLastName());
        userRedactor.setLogin(user.getLogin());
        userRedactor.setPassword(user.getPassword());
        bankRepository.save(userRedactor);
    }

    public void deleteUser(Integer id) {
        bankRepository.delete(id);
    }

    public void transferMoney(Users2 userWhoTransfers, Users2 userWillGetMoney, Integer sum) {
        Users2 moneyFrom = getUser(userWhoTransfers.getId());
        Users2 moneyTo = getUser(userWillGetMoney.getId());

        moneyFrom.setBalance(userWhoTransfers.getBalance() - sum);
        moneyTo.setBalance(userWillGetMoney.getBalance() + sum);

        bankRepository.transfer(moneyFrom, moneyTo);
    }

    public CardInfo getCardInfo(Integer id) {
        CardInfo cardInfo = new CardInfo();
        Users2 user = bankRepository.getUser(id);
        cardInfo.setFirstName(user.getFirstName().toUpperCase());
        cardInfo.setLastName(user.getLastName().toUpperCase());
        cardInfo.setNumberCard(user.getNumberCard());
        cardInfo.setExpirationMonth(user.getExpirationMonth());
        cardInfo.setExpirationYear(user.getExpirationYear());
        return cardInfo;
    }

    public Users2 findUser(String numberCard) {
        return bankRepository.findUserCard(numberCard);
    }

    public void transferMoneyTwo(Users2 userFrom, Users2 userTo, TransferOperations trans) {
        Users2 moneyFrom = getUser(userFrom.getId());
        Users2 moneyTo = getUser(userTo.getId());

        moneyFrom.setBalance(moneyFrom.getBalance() - trans.getSumma());
        moneyTo.setBalance(moneyTo.getBalance() + trans.getSumma());

        bankRepository.transfer(moneyFrom, moneyTo);
    }
}
