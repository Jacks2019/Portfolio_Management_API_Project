package com.portfolio.mgmtsys;

/*
 * Name: xiaoyu
 * Date: 2023/8/18
 * Description:
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.mgmtsys.domain.Account;

import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AccountTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

//    public static String asJsonString(Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Transactional
    @Commit
    @Test
    public void testRegister() throws Exception {
        Account account = new Account();
        account.setName(generateRandomString(3));
        account.setPassword("root");

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Account account1  = objectMapper.readValue(responseContent,new TypeReference<Account>() {});
        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        assertEquals(account.getName(), account1.getName());
        assertEquals(DigestUtils.md5Hex(account.getPassword()), account1.getPassword());
    }

    @Transactional
    @Commit
    @Test
    public void testRegisterFail() throws Exception {
        Account account = new Account();
        account.setName("testRL");
        account.setPassword("root");

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        assertEquals("Account name may already exist.",responseContent);
    }


    @Test
    public void testLogin() throws Exception {
        Account account = new Account();
        account.setName("testRL");
        account.setPassword("root");

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println(responseContent);
        Map<String, Object> body  = objectMapper.readValue(responseContent,new TypeReference<Map<String, Object>>() {});
        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 验证购买是否成功
        for (String s : body.keySet()) {
            System.out.println(s  +" " +body.get(s));
        }
    }

    @Test
    public void testLoginFail() throws Exception {
        Account account = new Account();
        account.setName("testRL");
        account.setPassword("root1");

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        assertEquals("Account name or password may be incorrect.",responseContent);
    }
}
