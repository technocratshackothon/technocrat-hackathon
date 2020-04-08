package com.technocrat.hackathon.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technocrat.hackathon.model.ReleaseReport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by vikas on 08-04-2020.
 */
@RunWith(SpringRunner.class)

public class BuildConfidenceUtilTest {

    private ReleaseReport releaseReport;

    @Before
    public void initResources() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        this.releaseReport = objectMapper.readValue(
                ResourceUtils.getFile(this.getClass().getResource("/jira-simulator.json"))
        ,ReleaseReport.class);
    }


    @Test
    public void fetchConfidenceIndexTest() throws Exception{
        String output  = BuildConfidenceUtil.fetchConfidenceIndex(this.releaseReport);
        assertThat(output,equalTo("25"));
    }


}
