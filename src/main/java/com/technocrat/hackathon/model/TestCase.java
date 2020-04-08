package com.technocrat.hackathon.model;

import com.technocrat.hackathon.constants.ExecutionStatus;
import lombok.Data;

/**
 * Created by vikas on 08-04-2020.
 */
@Data
public class TestCase {

    private String testCaseId;
    private ExecutionStatus executionStatus;


    public String getTestCaseId() {
        return testCaseId;
    }
    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }
    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }
    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }


}
