package com.technocrat.hackathon.ws;

import com.technocrat.hackathon.JiraService;
import com.technocrat.hackathon.UiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by vikas on 04-04-2020.
 * Logic of consuming api will go here
 */

@RestController
@Slf4j
@RequestMapping(value = "/api")
public class JiraWebService {

    @Inject
    private JiraService jiraService;


    @GetMapping(value = "/fetch-release-report/{projectname}/{releaseversion}")
    public ResponseEntity<UiResponse> fetchReleaseReport(@PathVariable String projectname, @PathVariable String releaseversion){
            UiResponse uiResponse = new UiResponse();
            try{
                uiResponse = jiraService.fetchStatisticsFromJiraForProjectNameAndReleaseVersion(projectname,releaseversion);
                uiResponse.setStatusCode(HttpStatus.OK.value());
                uiResponse.setStatusMessage(HttpStatus.OK.getReasonPhrase());
                return new ResponseEntity<>(uiResponse,HttpStatus.OK);
            }catch(Exception e){
                uiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                uiResponse.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                log.error("Error in fetchReleaseReport with",e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }


    @GetMapping(value = "/fetch-supported-project")
    public ResponseEntity<?> fetchProject(){
            UiResponse uiResponse = new UiResponse();
        try{
            uiResponse = jiraService.fetchSupportedProjects();
            uiResponse.setStatusCode(HttpStatus.OK.value());
            uiResponse.setStatusMessage(HttpStatus.OK.getReasonPhrase());
            return new ResponseEntity<>(uiResponse,HttpStatus.OK);
        }catch(Exception e){
            uiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            uiResponse.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Error in fetchProject with",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/fetch-releases-supported/{projectname}")
    public ResponseEntity<?> fetchReleasesSupported(@PathVariable String projectname){
        UiResponse uiResponse = new UiResponse();
        try{
            uiResponse = jiraService.fetchReleasesSupported(projectname);
            uiResponse.setStatusCode(HttpStatus.OK.value());
            uiResponse.setStatusMessage(HttpStatus.OK.getReasonPhrase());
            return new ResponseEntity<>(uiResponse,HttpStatus.OK);
        }catch(Exception e){
            uiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            uiResponse.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Error in fetchProject with",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
