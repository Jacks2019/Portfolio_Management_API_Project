package com.portfolio.mgmtsys;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.mgmtsys.domain.Fund;
import com.portfolio.mgmtsys.domain.FundHis;
import com.portfolio.mgmtsys.domain.Stock;
import com.portfolio.mgmtsys.domain.StockHis;
import com.portfolio.mgmtsys.model.GetFundHisRequest;
import com.portfolio.mgmtsys.model.GetStockHisRequest;
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
public class FundTest {

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
    public void testGetAllFunds() throws Exception{
        String page = "3";
        String pageSize = "100";
        MvcResult result = mockMvc.perform(get("/fund/getallfunds")
                        .param("page", page)
                        .param("pageSize", pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<Fund> funds= objectMapper.readValue(responseContent, new TypeReference<List<Fund>>() {});
        for (Fund fund : funds) {
            System.out.println(fund);
        }
    }

    @Test
    public void testGetAllFundsDefault() throws Exception{

        MvcResult result = mockMvc.perform(get("/fund/getallfunds"))
                   .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<Fund> funds= objectMapper.readValue(responseContent, new TypeReference<List<Fund>>() {});
        for (Fund fund : funds) {
            System.out.println(fund);
        }
    }

    @Test
    public void testGetFndByCode() throws Exception{
        String code = "012447";
        MvcResult result = mockMvc.perform(get("/fund/getfundbycode")
                        .param("code",code))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("responseContent = " + responseContent);
        Fund fund= objectMapper.readValue(responseContent, new TypeReference<Fund>() {});
        System.out.println(fund);
    }

    @Test
    public void testGetFndByCodeFail() throws Exception{
        String code = "99999";
        MvcResult result = mockMvc.perform(get("/fund/getfundbycode")
                        .param("code",code))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testSearchFund() throws Exception{
        String code = "01";
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("code", code);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : searchMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            params.add(key, value.toString());
        }
        MvcResult result = mockMvc.perform(get("/fund/searchfund")
                        .queryParams(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<Fund> funds= objectMapper.readValue(responseContent, new TypeReference<List<Fund>>() {});
        for (Fund fund : funds) {
            System.out.println(fund);
        }
    }

    @Test
    public void testSearchFundFail() throws Exception{
        String code = "99999";
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("code", code);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : searchMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            params.add(key, value.toString());
        }
        MvcResult result = mockMvc.perform(get("/fund/searchfund")
                        .queryParams(params))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();

    }


    @Test
    public void testGetFundHis() throws Exception{

        String code = "000081";
        String startTime = "2023-07-15";
        String endTime = "2023-08-15";

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fund/getfundhis")
                        .param("code", code)
                        .param("startTime",startTime)
                        .param("endTime", endTime))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<FundHis> fundHis = objectMapper.readValue(responseContent, new TypeReference<LinkedList<FundHis>>() {});
        fundHis.forEach(System.out::println);
    }

    @Test
    public void testGetFundHisDefault() throws Exception{
        String code = "000001";
        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fund/getfundhis")
                        .param("code",code))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        LinkedList<FundHis> fundHis = objectMapper.readValue(responseContent, new TypeReference<LinkedList<FundHis>>() {});
        fundHis.forEach(System.out::println);
    }

    @Test
    public void testGetFundHisFail() throws Exception{
        String code = "000081";
        String startTime = "2023-07-15";
        String endTime = "2022-08-15";


        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fund/getfundhis")
                        .param("code", code)
                        .param("startTime",startTime)
                        .param("endTime", endTime))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetFundHisFail2() throws Exception{
        String code = "0009999";
        String startTime = "2023-07-15";
        String endTime = "2024-08-15";

        // 模拟请求并验证响应
        MvcResult result = mockMvc.perform(get("/fund/getfundhis")
                        .param("code", code)
                        .param("startTime",startTime)
                        .param("endTime", endTime))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }
}
