package com.portfolio.mgmtsys;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.mgmtsys.domain.Stock;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

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
    public void testGetAllStocks() throws Exception{
        String page = "3";
        String pageSize = "100";
        MvcResult result = mockMvc.perform(get("/stock/getallstocks")
                        .param("page", page)
                        .param("pageSize", pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<Stock> stocks= objectMapper.readValue(responseContent, new TypeReference<List<Stock>>() {});
        for (Stock stock : stocks) {
            System.out.println(stock);
        }
    }
    @Test
    public void testGetAllStocksDefault() throws Exception{

        MvcResult result = mockMvc.perform(get("/stock/getallstocks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<Stock> stocks= objectMapper.readValue(responseContent, new TypeReference<List<Stock>>() {});
        for (Stock stock : stocks) {
            System.out.println(stock);
        }
    }

    @Test
    public void testGetStockByTicker() throws Exception{
        String ticker = "300415.SZ";
        MvcResult result = mockMvc.perform(get("/stock/getstockbyticker/{ticker}",ticker))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        Stock stock= objectMapper.readValue(responseContent, new TypeReference<Stock>() {});
        System.out.println(stock);
    }

    @Test
    public void testGetStockByTickerFail() throws Exception{
        String ticker = "300415";
        MvcResult result = mockMvc.perform(get("/stock/getstockbyticker/{ticker}",ticker))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testSearchStock() throws Exception{
        String ticker = "11";
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("ticker", ticker);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : searchMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            params.add(key, value.toString());
        }
        MvcResult result = mockMvc.perform(get("/stock/searchstock")
                        .queryParams(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<Stock> stocks= objectMapper.readValue(responseContent, new TypeReference<List<Stock>>() {});
        for (Stock stock : stocks) {
            System.out.println(stock);
        }
    }

    @Test
    public void testSearchStockFail() throws Exception{
        String ticker = "9999";
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("ticker", ticker);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : searchMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            params.add(key, value.toString());
        }
        MvcResult result = mockMvc.perform(get("/stock/searchstock")
                        .queryParams(params))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();

    }

    @Test
    public void testGetStockHis() throws Exception{
        // 设置请求参数
        String ticker = "300412.SZ";
        String startTime = "2023-06-15";
        String endTime = "2023-08-15";

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stock/getstockhis")
                        .param("ticker", ticker)
                        .param("startTime", startTime)
                        .param("endTime", endTime))
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
        // 设置请求参数
        String ticker = "300412.SZ";

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stock/getstockhis")
                        .param("ticker", ticker))
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
        // 设置请求参数
        String ticker = "300412.SZ";
        String startTime = "2023-07-15";
        String endTime = "2023-06-15";

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stock/getstockhis")
                        .param("ticker", ticker)
                        .param("startTime", startTime)
                        .param("endTime", endTime))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStockHisFail2() throws Exception{
        // 设置请求参数
        String ticker = "300412.";
        String startTime = "2023-05-15";
        String endTime = "2023-06-15";

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/stock/getstockhis")
                        .param("ticker", ticker)
                        .param("startTime", startTime)
                        .param("endTime", endTime))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }
}
