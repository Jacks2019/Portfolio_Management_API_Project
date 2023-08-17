package com.portfolio.mgmtsys;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */

import com.portfolio.mgmtsys.domain.Trade;
import com.portfolio.mgmtsys.model.BuyAndSellStockRequest;
import com.portfolio.mgmtsys.model.GetStockTrendResponse;
import com.portfolio.mgmtsys.model.GetTradesRequest;
import com.portfolio.mgmtsys.model.MyStockResponse;
import com.portfolio.mgmtsys.utils.TimeUtil;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
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
        Integer accountId = 0;
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stockhold/getallstockhold/{accountId}", accountId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        LinkedList<MyStockResponse> allStockHold = objectMapper.readValue(responseContent, new TypeReference<LinkedList<MyStockResponse>>() {});

        // 可以在这里对 allStockHold 进行断言或其他操作
        // 例如，检查列表长度、访问特定索引处的对象等
        assertEquals(1, allStockHold.size());

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

    @Transactional
    @Commit
    @Test
    public void testBuyStock() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyAndSellStockRequest request = new BuyAndSellStockRequest();
        request.setAccountId(4);
        request.setTicker("000010.SZ");
        request.setAmount(99);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/buystock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功.S
        assertTrue(isBuySuccessful);
    }

    @Transactional
    @Commit
    @Test
    public void testBuyNewStock() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyAndSellStockRequest request = new BuyAndSellStockRequest();
        request.setAccountId(4);
        request.setTicker("000017.SZ");
        request.setAmount(1);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/buystock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功
        assertTrue(isBuySuccessful);
    }

    @Transactional
    @Commit
    @Test
    public void testBuyStockFail() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyAndSellStockRequest request = new BuyAndSellStockRequest();
        request.setAccountId(4);
        request.setTicker("000010");
        request.setAmount(99);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/buystock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功
        assertFalse(isBuySuccessful);
//        testGetAllStockHoldExist();
    }

    @Transactional
    @Commit
    @Test
    public void testBuyStockFail2() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyAndSellStockRequest request = new BuyAndSellStockRequest();
        request.setAccountId(111);
        request.setTicker("000010.SZ");
        request.setAmount(99);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/buystock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功
        assertFalse(isBuySuccessful);
//        testGetAllStockHoldExist();
    }

    @Transactional
    @Commit
    @Test
    public void testBuyStockFail3() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyAndSellStockRequest request = new BuyAndSellStockRequest();
        request.setAccountId(4);
        request.setTicker("000010.SZ");
        request.setAmount(999999999);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/buystock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功
        assertFalse(isBuySuccessful);
//        testGetAllStockHoldExist();
    }

    @Transactional
    @Commit
    @Test
    public void testSellStock() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyAndSellStockRequest request = new BuyAndSellStockRequest();
        request.setAccountId(4);
        request.setTicker("000010.SZ");
        request.setAmount(1);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/sellstock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功
        assertTrue(isBuySuccessful);
    }


    @Transactional
    @Commit
    @Test
    public void testSellStockFail() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyAndSellStockRequest request = new BuyAndSellStockRequest();
        request.setAccountId(4);
        request.setTicker("000010.");
        request.setAmount(1);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/sellstock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功
        assertFalse(isBuySuccessful);
    }
    @Transactional
    @Commit
    @Test
    public void testSellStockFail2() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyAndSellStockRequest request = new BuyAndSellStockRequest();
        request.setAccountId(7);
        request.setTicker("000010.SZ");
        request.setAmount(1);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/sellstock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功
        assertFalse(isBuySuccessful);
    }

    @Transactional
    @Commit
    @Test
    public void testSellStockFail3() throws Exception {
        // 创建 BuyStockRequest 对象
        BuyAndSellStockRequest request = new BuyAndSellStockRequest();
        request.setAccountId(7);
        request.setTicker("000017.SZ");
        request.setAmount(999999);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/stockhold/sellstock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);

        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 例如，验证购买是否成功
        assertFalse(isBuySuccessful);
    }

    @Transactional
    @Commit
    @Test
    public void testGetTradeDefaultTime() throws Exception{
        GetTradesRequest request = new GetTradesRequest();
        request.setAccountId(4);
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stockhold/gettrades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<Trade> trades = objectMapper.readValue(responseContent, new TypeReference<LinkedList<Trade>>() {});
        for (Trade trade : trades) {
            System.out.println("trade："+trade);
        }

    }

    @Transactional
    @Commit
    @Test
    public void testGetTrade() throws Exception{
        GetTradesRequest request = new GetTradesRequest();
        request.setAccountId(4);
        request.setStartTime(new Date(123, 7, 17));;
        request.setEndTime(TimeUtil.getNowTime());
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stockhold/gettrades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<Trade> trades = objectMapper.readValue(responseContent, new TypeReference<LinkedList<Trade>>() {});
        for (Trade trade : trades) {
            System.out.println("trade："+trade);
        }
    }

    @Transactional
    @Commit
    @Test
    public void testGetTradeFail() throws Exception{
        GetTradesRequest request = new GetTradesRequest();
        request.setAccountId(4);
        request.setStartTime(new Date(123, 7, 17));;
        request.setEndTime(new Date(123, 7, 16));
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stockhold/gettrades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

    }

    @Transactional
    @Commit
    @Test
    public void testGetStockTrendDefaultTime() throws Exception{
        GetTradesRequest request = new GetTradesRequest();
        request.setAccountId(4);
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stockhold/getallstockholdtrend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<GetStockTrendResponse> stockTrendResponses = objectMapper.readValue(responseContent, new TypeReference<LinkedList<GetStockTrendResponse>>() {});
        for (GetStockTrendResponse stockTrendRespons : stockTrendResponses) {
            System.out.println(stockTrendRespons);
        }

    }
}



