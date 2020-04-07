package com.technocrat.hackathon.constants;

/**
 * Created by vikas on 07-04-2020.
 */
public enum ConfigProperties {
    jira_simulator_url_releasereport("jira.simulator.url.releasereport"),
    jira_simulator_url_supported_project("jira.simulator.url.supported.project");

    private final String configProperty;

    ConfigProperties(String configProperty){
        this.configProperty = configProperty;
    }

    public String getConfigProperty() {
        return configProperty;
    }
}
