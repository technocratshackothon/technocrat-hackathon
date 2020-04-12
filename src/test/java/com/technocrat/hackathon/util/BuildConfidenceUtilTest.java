package com.technocrat.hackathon.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technocrat.hackathon.model.ReleaseReport;
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
        List<Map<String,List<Map<String,Object>>>> barChartData = new ArrayList<>();
        Map<String,List<Map<String,Object>>> barMap = new HashMap<>();
        List<Map<String,Object>> barMapList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();

        map.put("Delivery",80);
        map.put("Test Converage",60);
        map.put("Defects",100);
        barMapList.add(map);
        barMap.put("seriesA",barMapList);
        barChartData = Arrays.asList(

                new HashMap<String,List<Map<String,Object>>>(){{
                    put("seriesA",Arrays.asList(
                            map
                    ));
                }}

        );
        String barChartResponse = objectMapper.writeValueAsString(barChartData);


        System.out.println(barChartResponse);

    }


}
