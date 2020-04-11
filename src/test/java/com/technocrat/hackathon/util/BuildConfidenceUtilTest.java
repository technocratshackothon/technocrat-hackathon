package com.technocrat.hackathon.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technocrat.hackathon.model.Application;
import com.technocrat.hackathon.model.ReleaseReport;
import com.technocrat.hackathon.model.UiResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by vikas on 08-04-2020.
 */
@RunWith(SpringRunner.class)

public class BuildConfidenceUtilTest {

    private ReleaseReport releaseReport;

    private ObjectMapper objectMapper;

    @Before
    public void initResources() throws Exception{
        this.objectMapper = new ObjectMapper();
        this.releaseReport = objectMapper.readValue(
                ResourceUtils.getFile(this.getClass().getResource("/jira-simulator.json"))
        ,ReleaseReport.class);
    }


    @Test
    public void fetchConfidenceIndexTest() throws Exception{
        String output  = BuildConfidenceUtil.fetchConfidenceIndex(this.releaseReport);
        assertThat(output,equalTo("25"));
    }

    @Test
    public void sampleTest() throws Exception{
        Map<String,String> m = new HashMap<>();
        List<Map<String,String>> releasesAvailable = new ArrayList<>();
        m.put("releaseName","August_RELEASE");
        releasesAvailable.add(m);
        m.clear();
        m.put("releaseName","September_RELEASE");
        releasesAvailable.add(m);
        Application app = Application.builder().name("DMSP").releasesAvailable(releasesAvailable).build();
        m.clear();
        //Bar chart generation
        List<Map<String,Object>> nestedBar = new ArrayList<>();
        Map<String,Object> barMap = new HashMap<>();
        releasesAvailable.clear();
        barMap.put("key","2006");
        barMap.put("value",32);
        nestedBar.add(barMap);
        barMap.clear();
        barMap.put("key","2007");
        barMap.put("value",30);
        nestedBar.add(barMap);
        Map<String,List<Map<String,Object>>> s = new HashMap<>();
        s.put("seriesA",nestedBar);
        //end
        UiResponse u = UiResponse.builder().confidencePercentage(69).currentRelease(false).applicationList(Arrays.asList(app)).barChartData(Arrays.asList(s)).build();
        String res = objectMapper.writeValueAsString(u);

        System.out.println(res);
    }


}
