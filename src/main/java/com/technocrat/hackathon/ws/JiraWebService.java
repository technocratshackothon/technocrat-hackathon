package com.technocrat.hackathon.ws;

import com.technocrat.hackathon.model.UiResponse;
import com.technocrat.hackathon.service.JiraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by vikas on 04-04-2020.
 * Logic of consuming api will go here
 */

@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping(value = "/api")
public class JiraWebService {

    @Inject
    private JiraService jiraService;

    @GetMapping(value = "/server")
    public ResponseEntity<UiResponse> server(){
        return new ResponseEntity<>(UiResponse.builder().statusCode(200).statusMessage("server is up").build(), HttpStatus.OK);
    }

    @GetMapping(value = "/fetch-release-report/{projectname}/{releaseversion}")
    public ResponseEntity<UiResponse> fetchReleaseReport(@PathVariable String projectname, @PathVariable String releaseversion) {
        UiResponse uiResponse = new UiResponse();
        try {
            uiResponse = jiraService.fetchStatisticsFromJiraForProjectNameAndReleaseVersion(projectname,releaseversion);
            uiResponse.setStatusCode(HttpStatus.OK.value());
            uiResponse.setStatusMessage(HttpStatus.OK.getReasonPhrase());
            return new ResponseEntity<>(uiResponse, HttpStatus.OK);
        } catch (Exception e) {
            uiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            uiResponse.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Error in fetchReleaseReport with", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/fetch-supported-project")
    public ResponseEntity<?> fetchProject() {
        UiResponse uiResponse = new UiResponse();
        try {
            uiResponse = jiraService.fetchSupportedProjects();
            uiResponse.setStatusCode(HttpStatus.OK.value());
            uiResponse.setStatusMessage(HttpStatus.OK.getReasonPhrase());
            return new ResponseEntity<>(uiResponse, HttpStatus.OK);
        } catch (Exception e) {
            uiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            uiResponse.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Error in fetchProject with", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
