package com.technocrat.hackathon;

import com.technocrat.hackathon.constants.ConfigProperties;
import com.technocrat.hackathon.model.ReleaseReport;
import com.technocrat.hackathon.util.BuildConfidenceUtil;
import com.technocrat.hackathon.util.WebUtil;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

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
        UiResponse uiResponse = UiResponse.builder().confidenceIndex(BuildConfidenceUtil.fetchConfidenceIndex(releaseReport)).build();
        return uiResponse;
    }

    public UiResponse fetchSupportedProjects() throws Exception {
        List<String> projectNames = (List<String>) webUtil.execute(
                env.getProperty(ConfigProperties.jira_simulator_url_supported_project.getConfigProperty())
                , "", List.class, HttpMethod.GET);
        UiResponse uiResponse = UiResponse.builder().dataArray(projectNames).build();
        return uiResponse;

    }

    public UiResponse fetchReleasesSupported(String projectname) throws Exception {
        String releasesSupportedUrl = webUtil.generatePathVariableUrl(env.getProperty(ConfigProperties.jira_simulator_url_supported_releases_support.getConfigProperty()),
                Arrays.asList(projectname));
        List<String> releasesSupported = (List<String>) webUtil.execute(
                releasesSupportedUrl
                , "", List.class, HttpMethod.GET);
        UiResponse uiResponse = UiResponse.builder().dataArray(releasesSupported).build();
        return uiResponse;

    }


}
