package com.technocrat.hackathon.ws;

import com.technocrat.hackathon.constants.ConfigProperties;
import com.technocrat.hackathon.model.ReleaseReport;
import com.technocrat.hackathon.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vikas on 04-04-2020.
 * Logic of consuming api will go here
 */

@RestController
@Slf4j
@RequestMapping(value = "/api")
public class JiraWebService {

    @Inject
    private WebUtil webUtil;

    @Inject
    private Environment env;


    @GetMapping(value = "/fetch-story/{projectname}/{releaseversion}")
    public ResponseEntity<ReleaseReport> fetchReleaseReport(@PathVariable String projectname, @PathVariable String releaseversion){
            try{

                String releaseReportUrl = webUtil.generatePathVariableUrl(env.getProperty(ConfigProperties.jira_simulator_url_releasereport.getConfigProperty()),
                        Arrays.asList(projectname,releaseversion));
                log.debug("Release report url ****"+releaseReportUrl);
                ReleaseReport releaseReport = (ReleaseReport)webUtil.execute(
                        releaseReportUrl
                        ,"",ReleaseReport.class, HttpMethod.GET);
                return new ResponseEntity<>(releaseReport,HttpStatus.OK);
            }catch(Exception e){
                log.error("Error in fetchReleaseReport with",e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }


    @GetMapping(value = "/fetch-project")
    public ResponseEntity<?> fetchProject(){
        try{

            List<String> projectNames = (List<String>)webUtil.execute(
                    env.getProperty(ConfigProperties.jira_simulator_url_supported_project.getConfigProperty())
                    ,"",List.class, HttpMethod.GET);
            return new ResponseEntity<>(projectNames,HttpStatus.OK);
        }catch(Exception e){
            log.error("Error in fetchProject with",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
