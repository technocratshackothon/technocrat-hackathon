package com.technocrat.hackathon.ws;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vikas on 04-04-2020.
 * Logic of consuming api will go here
 */

@RestController
@RequestMapping(value = "/jira")
public class JiraWebService {

    @GetMapping(value = "/fetch-story")
    public void fetchStory(){

    }
}
