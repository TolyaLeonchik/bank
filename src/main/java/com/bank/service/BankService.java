package com.bank.service;

import com.bank.domain.CardInfo;
import com.bank.domain.TransferOperations;
import com.bank.domain.Users;
import com.bank.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

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
        bankRepository.save(user);
        return user;
    }

    public void updateUser(Users user) {
        bankRepository.update(user);
    }

    public void deleteUser(Users user) {
        bankRepository.delete(user);
    }

    public CardInfo getCardInfo(Integer id) {
        CardInfo cardInfo = new CardInfo();
        Users user = bankRepository.getUser(id);
        cardInfo.setFirstName(user.getFirstName().toUpperCase());
        cardInfo.setLastName(user.getLastName().toUpperCase());
        cardInfo.setNumberCard(user.getNumberCard());
        return cardInfo;
    }

    public Users findUser(String numberCard) {
        return bankRepository.findUserCard(numberCard);
    }

    public void transferMoney(Users userFrom, Users userTo, TransferOperations trans) {
        Users moneyFrom = getUser(userFrom.getId());
        Users moneyTo = getUser(userTo.getId());

        moneyFrom.setBalance(moneyFrom.getBalance() - trans.getSumma());
        moneyTo.setBalance(moneyTo.getBalance() + trans.getSumma());

        bankRepository.transfer(moneyFrom, moneyTo);
    }
}
