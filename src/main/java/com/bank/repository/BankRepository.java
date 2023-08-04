package com.bank.repository;

import com.bank.domain.TypeOfCard;
import com.bank.domain.Users;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BankRepository {

    private final Map<Integer, Users> users = new HashMap<>();

    {
        Users users1 = new Users();
        users1.setId(1);
        users1.setFirstName("Petya");
        users1.setLastName("Konovalov");
        users1.setLogin("PetKon");
        users1.setPassword("1506denRojdenia");
        users1.setTypeOfCard(TypeOfCard.MASTERCARD);
        users1.setNumberCard("4555455566667878");
        users1.setExpirationMonth(15);
        users1.setExpirationYear(25);
        users1.setBalance(4210);

        Users users2 = new Users();
        users2.setId(2);
        users2.setFirstName("Anton");
        users2.setLastName("Shapovalov");
        users2.setLogin("shapka");
        users2.setPassword("12345687");
        users2.setTypeOfCard(TypeOfCard.VISA);
        users2.setNumberCard("7412369852876874");
        users2.setExpirationMonth(15);
        users2.setExpirationYear(25);
        users2.setBalance(3145);

        users.put(1, users1);
        users.put(2, users2);
    }

    public List<Users> getAllUsers() {
        return users.values().stream().toList();
    }

    public Users getUser(Integer id) {
        return users.get(id);
    }

    public void save(Users user) {
        users.put(user.getId(), user);
    }

    public void delete(Integer id) {
        users.remove(id);
    }

    public void transfer(Users moneyFrom, Users moneyTo) {
        users.put(moneyFrom.getId(), moneyFrom);
        users.put(moneyTo.getId(), moneyTo);
    }

    public Users findUserCard(String searchNumberCard) {
        Users search = null;
        for (Users user : users.values()) {
            if (user.getNumberCard().equals(searchNumberCard)) {
                search = user;
            }
        }
        return search;
    }
}
