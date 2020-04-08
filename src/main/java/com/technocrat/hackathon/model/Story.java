package com.technocrat.hackathon.model;

import com.technocrat.hackathon.constants.Status;
import lombok.Data;

import java.util.List;

@Data
public class Story {
	String storyId;
	Status status;
	int points;
	List<Defect> defectList;
	List<TestCase> testCaseList;
}
