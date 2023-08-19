package com.portfolio.mgmtsys;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.model.GetStockHisRequest;
import com.portfolio.mgmtsys.model.GetStockTrendResponse;
import com.portfolio.mgmtsys.model.GetTradesRequest;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.LinkedList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/*
 * Name: xiaoyu
 * Date: 2023/8/17
 * Description:
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class StockTest {

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
    public void testGetStockHis() throws Exception{
        GetStockHisRequest request = new GetStockHisRequest();
        request.setTicker("300412.SZ");
        request.setStartTime(new Date(123, 7, 15));;
        request.setEndTime(TimeUtil.getNowTime());

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stock/getstockhis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<StockHis> stockHis = objectMapper.readValue(responseContent, new TypeReference<LinkedList<StockHis>>() {});
        for (StockHis stockH : stockHis) {
            System.out.println(stockH);
        }
    }

    @Test
    public void testGetStockHisDefault() throws Exception{
        GetStockHisRequest request = new GetStockHisRequest();
        request.setTicker("300412.SZ");
//        request.setStartTime(new Date(123, 7, 15));;
//        request.setEndTime(TimeUtil.getNowTime());

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stock/getstockhis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<StockHis> stockHis = objectMapper.readValue(responseContent, new TypeReference<LinkedList<StockHis>>() {});
        for (StockHis stockH : stockHis) {
            System.out.println(stockH);
        }

    }

    @Test
    public void testGetStockHisFail() throws Exception{
        GetStockHisRequest request = new GetStockHisRequest();
        request.setTicker("300412.SZ");
        request.setStartTime(new Date(123, 7, 15));;
        request.setEndTime(new Date(123, 6, 15));

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stock/getstockhis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStockHisFail2() throws Exception{
        GetStockHisRequest request = new GetStockHisRequest();
        request.setTicker("300412");
//        request.setStartTime(new Date(123, 7, 15));;
//        request.setEndTime(TimeUtil.getNowTime());

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stock/getstockhis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }
}
