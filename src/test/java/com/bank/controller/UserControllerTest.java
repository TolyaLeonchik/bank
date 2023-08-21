package com.bank.controller;

import com.bank.domain.CardInfo;
import com.bank.domain.TypeOfCard;
import com.bank.domain.Users;
import com.bank.service.BankService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    BankService bankService;

    static Users user;
    static Users createUser;
    static List<Users> usersList;
    static CardInfo cardInfo;

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll() {
        user = new Users();
        user.setId(5);
        user.setFirstName("Petya");
        user.setLastName("Dorohov");
        user.setLogin("PetyaRich");
        user.setPassword("hhggrr");
        user.setTypeOfCard(TypeOfCard.MASTERCARD);
        user.setNumberCard("4789658745236985");
        user.setBalance(4741);

        usersList = new ArrayList<>();
        usersList.add(user);

        cardInfo = new CardInfo();
        cardInfo.setFirstName(user.getFirstName());
        cardInfo.setLastName(user.getLastName());
        cardInfo.setNumberCard(user.getNumberCard());

        createUser = new Users();
        user.setId(6);
        user.setFirstName("Nikita");
        user.setLastName("Prohorov");
        user.setLogin("Nikki");
        user.setPassword("rrgghh");
        user.setTypeOfCard(TypeOfCard.VISA);
        user.setNumberCard("4789658745234785");
        user.setBalance(1478);
    }

    @Test
    public void getBankUsers() throws Exception {
        Mockito.when(bankService.getUsers()).thenReturn(usersList);

        mockMvc.perform(get("/users")).
                andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Petya")));
    }

    @Test
    public void getUserTest() throws Exception {
        Mockito.when(bankService.getUser(anyInt())).thenReturn(user);

        mockMvc.perform(get("/users/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.is("Petya")));
    }

    @Test
    public void getCardInfoTest() throws Exception {
        Mockito.when(bankService.getCardInfo(anyInt())).thenReturn(cardInfo);

        mockMvc.perform(get("/users/card-info/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberCard", Matchers.is("4789658745236985")));
    }

//    @Test
//    public void createBankUserTest() throws Exception {
//        Mockito.when(bankService.createUser(new Users())).thenReturn(createUser);
//
//        mockMvc.perform(post("/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName", Matchers.is("Nikita")));
//    }
}
