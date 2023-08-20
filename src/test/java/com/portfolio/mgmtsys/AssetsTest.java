package com.portfolio.mgmtsys;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.mgmtsys.domain.Assets;
import com.portfolio.mgmtsys.domain.Fund;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/*
 * Name: xiaoyu
 * Date: 2023/8/20
 * Description:
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AssetsTest {

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
    public void testGetAssetsById() throws Exception{
        String id = "4";
        MvcResult result = mockMvc.perform(get("/assets/getassetsbyId/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        Assets asset= objectMapper.readValue(responseContent, new TypeReference<Assets>() {});
        System.out.println(asset);
    }

    @Test
    public void testGetAssetsByIdFaile() throws Exception{
        String id = "999";
        MvcResult result = mockMvc.perform(get("/assets/getassetsbyId/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testTransferIn() throws Exception{
        Map<String, Object> request = new HashMap<>();
        // Add request parameters as needed
        request.put("id", 4);
        request.put("amount", 1000.0);
        String requestBody = objectMapper.writeValueAsString(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/assets/transferin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        Assets asset= objectMapper.readValue(responseContent, new TypeReference<Assets>() {});
        System.out.println(asset);

    }

    @Test
    public void testTransferInFail() throws Exception{
        Map<String, Object> request = new HashMap<>();
        // Add request parameters as needed
        request.put("id", 99999);
        request.put("amount", 1000.0);
        String requestBody = objectMapper.writeValueAsString(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/assets/transferin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testTransferOut() throws Exception{
        Map<String, Object> request = new HashMap<>();
        // Add request parameters as needed
        request.put("id", 4);
        request.put("amount", 1100.0);
        String requestBody = objectMapper.writeValueAsString(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/assets/transferout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        Assets asset= objectMapper.readValue(responseContent, new TypeReference<Assets>() {});
        System.out.println(asset);

    }

    @Test
    public void testTransferOutFail() throws Exception{
        Map<String, Object> request = new HashMap<>();
        // Add request parameters as needed
        request.put("id", 99999);
        request.put("amount", 1100.0);
        String requestBody = objectMapper.writeValueAsString(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/assets/transferout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())
                .andReturn();

    }
    @Test
    public void testTransferOutFail2() throws Exception{
        Map<String, Object> request = new HashMap<>();
        // Add request parameters as needed
        request.put("id", 4);
        request.put("amount", 99999999.0);
        String requestBody = objectMapper.writeValueAsString(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/assets/transferout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(print())
                .andReturn();

    }

}
