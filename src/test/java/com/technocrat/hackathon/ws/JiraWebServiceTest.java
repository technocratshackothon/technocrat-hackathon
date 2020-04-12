package com.technocrat.hackathon.ws;

import com.technocrat.hackathon.config.ConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by vikas on 12-04-2020.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(JiraWebService.class)
@ContextConfiguration(classes = ConfigTest.class)
public class JiraWebServiceTest
{

    @Inject
    private MockMvc mvc;

    @Test
    public void getAllEmployeesAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .get("/api/server")
                .header("Origin","http://localhost:4200")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
