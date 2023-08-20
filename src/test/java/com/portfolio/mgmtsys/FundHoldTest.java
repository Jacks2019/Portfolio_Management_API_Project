package com.portfolio.mgmtsys;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */

import com.portfolio.mgmtsys.domain.Fund;
import com.portfolio.mgmtsys.domain.FundHold;
import com.portfolio.mgmtsys.domain.FundTrade;
import com.portfolio.mgmtsys.domain.Trade;
import com.portfolio.mgmtsys.model.*;
import com.portfolio.mgmtsys.utils.TimeUtil;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class FundHoldTest {

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
    public void testGetAllFundHoldExist() throws Exception {
        Integer accountId = 1;
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fundhold/getallfundhold/{accountId}", accountId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        LinkedList<FundHold> allFundHold = objectMapper.readValue(responseContent, new TypeReference<LinkedList<FundHold>>() {});

        // 例如，检查列表长度、访问特定索引处的对象等
        assertEquals(1, allFundHold.size());

        for (FundHold fundResponse : allFundHold) {
            System.out.println(fundResponse.toString());
        }
    }

    @Test
    public void testGetAllFundHoldNotExist() throws Exception {
        Integer accountId = 2;

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fundhold/getallfundhold/{accountId}", accountId))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assert responseContent.isEmpty();
    }

    @Transactional
    @Commit
    @Test
    public void testBuyFund() throws Exception {
        BuyAndSellFundRequest request = new BuyAndSellFundRequest();
        request.setAccountId(4);
        request.setCode("002583");
        request.setAmount(10);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/fundhold/buyfund")
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
    public void testBuyNewFund() throws Exception {
        BuyAndSellFundRequest request = new BuyAndSellFundRequest();
        request.setAccountId(4);
        request.setCode("012723");
        request.setAmount(11);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/fundhold/buyfund")
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
    public void testBuyFundFail() throws Exception {
        BuyAndSellFundRequest request = new BuyAndSellFundRequest();
        request.setAccountId(99);
        request.setCode("002583");
        request.setAmount(10);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/fundhold/buyfund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);
        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 验证购买是否成功
        assertFalse(isBuySuccessful);
    }

    @Transactional
    @Commit
    @Test
    public void testBuyFundFail2() throws Exception {
        BuyAndSellFundRequest request = new BuyAndSellFundRequest();
        request.setAccountId(4);
        request.setCode("002583");
        request.setAmount(100000000);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/fundhold/buyfund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);
        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 验证购买是否成功
        assertFalse(isBuySuccessful);
    }

    @Transactional
    @Commit
    @Test
    public void testBuyFundFail3() throws Exception {
        BuyAndSellFundRequest request = new BuyAndSellFundRequest();
        request.setAccountId(4);
        request.setCode("008888");
        request.setAmount(10);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/fundhold/buyfund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Boolean isBuySuccessful = objectMapper.readValue(responseContent, Boolean.class);
        // 可以在这里对 isBuySuccessful 进行断言或其他操作
        // 验证购买是否成功
        assertFalse(isBuySuccessful);
    }

    @Transactional
    @Commit
    @Test
    public void testSellFund() throws Exception {
        BuyAndSellFundRequest request = new BuyAndSellFundRequest();
        request.setAccountId(4);
        request.setCode("002583");
        request.setAmount(1);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/fundhold/sellfund")
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
    public void testSellFundFail() throws Exception {
        BuyAndSellFundRequest request = new BuyAndSellFundRequest();
        request.setAccountId(4);
        request.setCode("000010.");
        request.setAmount(1);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/fundhold/sellfund")
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
    public void testSellFundFail2() throws Exception {
        BuyAndSellFundRequest request = new BuyAndSellFundRequest();
        request.setAccountId(7);
        request.setCode("002583");
        request.setAmount(1);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/fundhold/sellfund")
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
    public void testSellFundFail3() throws Exception {
        BuyAndSellFundRequest request = new BuyAndSellFundRequest();
        request.setAccountId(4);
        request.setCode("002583");
        request.setAmount(999999);

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(post("/fundhold/sellfund")
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
    public void testGetFundTradeDefaultTime() throws Exception{

        Integer accountId = 4;
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fundhold/gettrades")
                        .param("accountId", String.valueOf(accountId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<FundTrade> trades = objectMapper.readValue(responseContent, new TypeReference<LinkedList<FundTrade>>() {});
        for (FundTrade trade : trades) {
            System.out.println("trade："+trade);
        }
    }

    @Transactional
    @Commit
    @Test
    public void testGetFundTrade() throws Exception{
        Integer accountId = 4;
        String startTime = "2023-07-17";
        String endTime = "2023-08-20";
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fundhold/gettrades")
                        .param("accountId", String.valueOf(accountId))
                        .param("startTime", startTime)
                        .param("endTime",endTime))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<FundTrade> trades = objectMapper.readValue(responseContent, new TypeReference<LinkedList<FundTrade>>() {});
        for (FundTrade trade : trades) {
            System.out.println("trade："+trade);
        }
    }

    @Transactional
    @Commit
    @Test
    public void testGetFundTradeFail() throws Exception{
        Integer accountId = 4;
        String startTime = "2023-07-17";
        String endTime = "2023-07-15";
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fundhold/gettrades")
                        .param("accountId", String.valueOf(accountId))
                        .param("startTime", startTime)
                        .param("endTime",endTime))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();
    }

    @Transactional
    @Commit
    @Test
    public void testGetFundTrendDefaultTime() throws Exception{
        Integer accountId = 4;
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fundhold/getallfundholdtrend")
                        .param("accountId", String.valueOf(accountId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<GetFundTrendResponse> fundTrendResponses = objectMapper.readValue(responseContent, new TypeReference<LinkedList<GetFundTrendResponse>>() {});
        for (GetFundTrendResponse fundTrendResponse : fundTrendResponses) {
            System.out.println(fundTrendResponse);
        }
    }

    @Transactional
    @Commit
    @Test
    public void testGetFundTrend() throws Exception{
        String accountId = "4";
        String startTime = "2023-07-17";
        String endTime = "2023-08-20";
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fundhold/getallfundholdtrend")
                        .param("accountId", accountId)
                        .param("startTime", startTime)
                        .param("endTime",endTime))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<GetFundTrendResponse> fundTrendResponses = objectMapper.readValue(responseContent, new TypeReference<LinkedList<GetFundTrendResponse>>() {});
        for (GetFundTrendResponse fundTrendResponse : fundTrendResponses) {
            System.out.println(fundTrendResponse);
        }
    }

    @Transactional
    @Commit
    @Test
    public void testGetFundTrendFail() throws Exception{
        String accountId = "4";
        String startTime = "2023-07-17";
        String endTime = "2023-06-20";
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fundhold/getallfundholdtrend")
                        .param("accountId", accountId)
                        .param("startTime", startTime)
                        .param("endTime",endTime))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();

    }
}



