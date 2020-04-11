package com.technocrat.hackathon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by vikas on 08-04-2020.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UiResponse {

    private int statusCode;

    private String statusMessage;

    private int confidencePercentage;

    private Boolean currentRelease;

    private List<Application> applicationList;

    private List<Map<String,List<Map<String,Object>>>> barChartData;

}
