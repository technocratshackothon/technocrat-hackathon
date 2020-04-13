package com.technocrat.hackathon.service;

import com.technocrat.hackathon.constants.ConfigProperties;
import com.technocrat.hackathon.model.Application;
import com.technocrat.hackathon.model.ReleaseReport;
import com.technocrat.hackathon.model.UiResponse;
import com.technocrat.hackathon.util.BuildConfidenceUtil;
import com.technocrat.hackathon.util.WebUtil;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vikas on 08-04-2020.
 */
@Service
public class JiraService {

    @Inject
    private WebUtil webUtil;

    @Inject
    private Environment env;

    public UiResponse fetchStatisticsFromJiraForProjectNameAndReleaseVersion(String projectname, String releaseversion) throws Exception {
        String releaseReportUrl = webUtil.generatePathVariableUrl(env.getProperty(ConfigProperties.jira_simulator_url_releasereport.getConfigProperty()),
                Arrays.asList(projectname, releaseversion));
        ReleaseReport releaseReport = (ReleaseReport) webUtil.execute(
                releaseReportUrl
                , "", ReleaseReport.class, HttpMethod.GET);
        String confidenceRate = BuildConfidenceUtil.fetchConfidenceIndex(releaseReport);
        List<Map<String,Object>> listOfConfidenceMap = BuildConfidenceUtil.listOfMapOfParameterAndConfidence;
        List<Map<String,List<Map<String,Object>>>> barChartData =
        Arrays.asList(

                new HashMap<String,List<Map<String,Object>>>(){{
                    put(releaseversion,listOfConfidenceMap);
                }}

        );
        boolean isCurentRelease = confidenceRate.equalsIgnoreCase("100");
        UiResponse uiResponse = UiResponse.builder().confidencePercentage(Integer.parseInt(confidenceRate)).currentRelease(isCurentRelease).barChartData(barChartData).build();
        return uiResponse;
    }

    public UiResponse fetchSupportedProjects() throws Exception {
        List<Application> projectNames = (List<Application>) webUtil.execute(
                env.getProperty(ConfigProperties.jira_simulator_url_supported_project.getConfigProperty())
                , "", List.class, HttpMethod.GET);
        UiResponse uiResponse = UiResponse.builder().applicationList(projectNames).build();
        return uiResponse;

    }

}
