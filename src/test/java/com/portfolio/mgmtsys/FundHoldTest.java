package com.portfolio.mgmtsys;

/*
 * Name: xiaoyu
 * Date: 2023/8/15
 * Description:
 */

import com.portfolio.mgmtsys.domain.FundHold;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        Integer accountId = 4;
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fundhold/getallfundhold/{accountId}", accountId))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        LinkedList<FundHold> allFundHold = objectMapper.readValue(responseContent, new TypeReference<LinkedList<FundHold>>() {});

        // 可以在这里对 allStockHold 进行断言或其他操作
        // 例如，检查列表长度、访问特定索引处的对象等
        assertEquals(2, allFundHold.size());

        for (FundHold fundResponse : allFundHold) {
            System.out.println(fundResponse.toString());
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
    public void testBuyFund() throws Exception {

    }



}



