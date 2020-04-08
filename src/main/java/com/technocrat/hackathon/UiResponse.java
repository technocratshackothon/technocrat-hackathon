package com.technocrat.hackathon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vikas on 08-04-2020.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UiResponse {

    private int statusCode;

    private String statusMessage;

    private String confidenceIndex;

    private List<HashMap<String, String>> dataNested;

    private List<String> dataArray;


}
