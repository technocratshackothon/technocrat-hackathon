package com.technocrat.hackathon.model;

import com.technocrat.hackathon.constants.Priority;
import com.technocrat.hackathon.constants.Severity;
import com.technocrat.hackathon.constants.Status;
import lombok.Data;

@Data
public class Defect {

	String defectId;
	Status status;
	Severity severity;
	Priority priority;
	int points;

}
