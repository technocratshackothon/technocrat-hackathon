package com.technocrat.hackathon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by vikas on 11-04-2020.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
    private String name;
    private List<Map<String,String>> releasesAvailable;
}
