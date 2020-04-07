package com.technocrat.hackathon.model;

import com.technocrat.hackathon.constants.Status;
import lombok.Data;

@Data
public class Story {
	String storyId;
	Status status;
	int points;
}
