package com.technocrat.hackathon.util;

import com.technocrat.hackathon.config.ConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by vikas on 04-04-2020.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ConfigTest.class)
public class WebUtilTest {

    @Inject
    private WebUtil<String,String> webUtil;

    @Test
    public void webUtilExecute_getShouldSucceed() throws Exception{
        //given
        String requestUrl = "https://www.google.com/";

        //when
        webUtil.execute(requestUrl, "",String.class, HttpMethod.GET);

        //then
        assertThat(webUtil.getStatusCode(),equalTo(200));
    }

}
