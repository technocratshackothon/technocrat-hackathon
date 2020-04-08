package com.technocrat.hackathon.model;

import lombok.Data;

import java.util.List;

@Data
public class ReleaseReport {
	
	String projectName;
	String version;
	List<Story> storyList;
}
