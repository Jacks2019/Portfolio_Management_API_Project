package com.portfolio.mgmtsys;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */

import com.portfolio.mgmtsys.model.BuyStockRequest;
import com.portfolio.mgmtsys.model.MyStockResponse;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


import static org.springframework.web.servlet.function.ServerResponse.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class StockHoldTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void testGetAllStockHoldExist() throws Exception {
        Integer accountId = 4;
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stockhold/getallstockhold/{accountId}", accountId))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        LinkedList<MyStockResponse> allStockHold = objectMapper.readValue(responseContent, new TypeReference<LinkedList<MyStockResponse>>() {});

        // 可以在这里对 allStockHold 进行断言或其他操作
        // 例如，检查列表长度、访问特定索引处的对象等
        assertEquals(2, allStockHold.size());

        for (MyStockResponse stockResponse : allStockHold) {
            System.out.println("Stock Name: " + stockResponse.getStockName());
            System.out.println("Ticker: " + stockResponse.getTicker());
            System.out.println("Amount: " + stockResponse.getAmount());
            System.out.println("Current Price: " + stockResponse.getCurrentPrice());
        }
    }

    @Test
    public void testGetAllStockHoldNotExist() throws Exception {
        Integer accountId = 2;

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stockhold/getallstockhold/{accountId}", accountId))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assert responseContent.isEmpty();
    }

    @Test
    public void testBuyStock() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyStockRequest request = new BuyStockRequest();
        request.setAccountId(4);
        request.setTicker("000010.SZ");
        request.setAmount(10);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/buystock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功
        assertTrue(isBuySuccessful);
//        testGetAllStockHoldExist();
    }



}



