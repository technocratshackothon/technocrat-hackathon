package com.technocrat.hackathon.util;

import com.technocrat.hackathon.constants.ExecutionStatus;
import com.technocrat.hackathon.constants.Severity;
import com.technocrat.hackathon.constants.Status;
import com.technocrat.hackathon.model.Defect;
import com.technocrat.hackathon.model.ReleaseReport;
import com.technocrat.hackathon.model.Story;
import com.technocrat.hackathon.model.TestCase;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vikas on 08-04-2020.
 * At confident level	all parameters should be in confident level
 * Between confident and worried level	Two parameters should be in confident level and one should be in worried
 * At worried level	Two or more parameters are in worried level
 * Between worried and pessimitic level	Two parameters should be in worried level and one should be in pessimistic
 * At pessimstic level	All parameters should be in pessimistic level
 */
public class BuildConfidenceUtil {

    public static List<Map<String,Object>> listOfMapOfParameterAndConfidence = new ArrayList<>();

    {
        this.listOfMapOfParameterAndConfidence = new ArrayList<>();
    }

    enum OverallConfidenceIndex {
        CONFIDENT(100),CONFIDENT_WORRIED(75),WORRIED(50)
        ,WORRIED_PESSIMISTIC(25),PESSIMISTIC(25);

        private final int confidencePercentage;

        OverallConfidenceIndex(int confidencePercentage){
            this.confidencePercentage = confidencePercentage;
        }

        public int getConfidencePercentage() {
            return confidencePercentage;
        }
    }

    enum Categories{
        DELVIERY("Delivery"),TEST_COVERAGE("Test Coverage"),DEFECTS("Defects");
        private final String getUiName;

        Categories(String getUiName){
            this.getUiName = getUiName;
        }

        public String getGetUiName() {
            return getUiName;
        }
    }

    enum ConfidenceCategorizationMatrixIdentifier{
        DELIVERY_CONFIDENT,DELIVERY_WORRIED,DELIVERY_PESSIMISTIC,
        TESTCOVERAGE_CONFIDENT,TESTCOVERAGE_WORRIED,TESTCOVERAGE_PESSIMISTIC,
        DEFECTS_CONFIDENT,DEFECTS_WORRIED,DEFECTS_PESSIMISTIC
    }


    public static Map<String,Integer> getBuildConfidenceMatrix(){
        final Map<String,Integer> mapMatrix = new HashMap<>();
        EnumSet.allOf(ConfidenceCategorizationMatrixIdentifier.class)
                .forEach(conf -> mapMatrix.put(conf.name(),0));
        return mapMatrix;
    }


   static IConfidenceCategorization deliverableCategorization = (x) -> {
        double val  = (double)x;
        if(val > 90){
            return ConfidenceCategorizationMatrixIdentifier.DELIVERY_CONFIDENT.name();
        }else if(val < 90 && val > 50){
            return ConfidenceCategorizationMatrixIdentifier.DELIVERY_WORRIED.name();
        }else if (val < 50){
            return ConfidenceCategorizationMatrixIdentifier.DELIVERY_PESSIMISTIC.name();
        }
        return null;
    };

   static IConfidenceCategorization testConfiguaration = (x) -> {
        double val  = (double)x;
        if(val == 100){
            return ConfidenceCategorizationMatrixIdentifier.TESTCOVERAGE_CONFIDENT.name();
        }else if(val <= 99 && val >= 80){
            return ConfidenceCategorizationMatrixIdentifier.TESTCOVERAGE_WORRIED.name();
        }else if (val < 80){
            return ConfidenceCategorizationMatrixIdentifier.TESTCOVERAGE_PESSIMISTIC.name();
        }
        return null;
    };

    static IConfidenceCategorization defectConfiguration = (x) -> {
        List<Defect> defectList  = (List<Defect>)x;
        int openDefects = (int)defectList.stream().filter(defect -> defect.getStatus() == Status.OPEN).count();
        int openDefectsAndLowPriority = (int)defectList.stream().filter(defect -> defect.getSeverity() == Severity.LOW && defect.getStatus() == Status.OPEN).count();
        int openDefectsCritical = (int)defectList.stream().filter(defect -> defect.getSeverity() == Severity.HIGH && defect.getStatus() == Status.OPEN).count();
        listOfMapOfParameterAndConfidence.add(new HashMap<String,Object>(){{
            put("key",Categories.DEFECTS.getGetUiName());
            put("value",NumberUtil.getPerc(openDefects,defectList.size()));
        }});
        if(openDefects == 0){
            return ConfidenceCategorizationMatrixIdentifier.DEFECTS_CONFIDENT.name();
        }else if(openDefectsAndLowPriority > 0){
            return ConfidenceCategorizationMatrixIdentifier.DEFECTS_WORRIED.name();
        }else if(openDefectsCritical > 0){
            return ConfidenceCategorizationMatrixIdentifier.DEFECTS_PESSIMISTIC.name();
        }

        return null;
    };


    static IConfidenceCategorization overallConfidence = (x) -> {
        Map<String,Integer> matrixMap = (Map<String,Integer>)x;
        Stream<Map.Entry<String,Integer>> confidentStream = matrixMap.entrySet().stream()
                .filter(mapKey -> mapKey.getKey().contains(OverallConfidenceIndex.CONFIDENT.name()));
        Stream<Map.Entry<String,Integer>> worriedStream = matrixMap.entrySet().stream()
                .filter(mapKey -> mapKey.getKey().contains(OverallConfidenceIndex.WORRIED.name()));
        Stream<Map.Entry<String,Integer>> pessimisticStream = matrixMap.entrySet().stream()
                .filter(mapKey -> mapKey.getKey().contains(OverallConfidenceIndex.PESSIMISTIC.name()));
        int confidenceResult = (int)confidentStream.map(mapper->mapper.getValue()).reduce(0,(a,b) -> a+b);

        if(confidenceResult == 3){
            return String.valueOf(OverallConfidenceIndex.CONFIDENT.getConfidencePercentage());
        }else{
            int worriedResult = (int)worriedStream.map(mapper->mapper.getValue()).reduce(0,(a,b) -> a+b);
            int pessimisticResult = (int)pessimisticStream.map(mapper->mapper.getValue()).reduce(0,(a,b) -> a+b);
            if(confidenceResult == 2 && worriedResult == 1){
                return String.valueOf(OverallConfidenceIndex.CONFIDENT_WORRIED.getConfidencePercentage());
            }else if(worriedResult >=2){
                return String.valueOf(OverallConfidenceIndex.WORRIED.getConfidencePercentage());
            }else if(pessimisticResult == 1){
                return String.valueOf(OverallConfidenceIndex.WORRIED_PESSIMISTIC.getConfidencePercentage());
            }else if(pessimisticResult > 1){
                return String.valueOf(OverallConfidenceIndex.PESSIMISTIC.getConfidencePercentage());
            }
        }
        return "";
    };

    public static String fetchConfidenceIndex(ReleaseReport releaseReport){
        if(releaseReport!=null && releaseReport.getStoryList() !=null && releaseReport.getStoryList().size() >0){
            listOfMapOfParameterAndConfidence = new ArrayList<>();
            Map<String,Integer> mapMatrix = getBuildConfidenceMatrix();
            List<TestCase> testCaseList = releaseReport.getStoryList().stream().map(Story::getTestCaseList).flatMap(
                    test->test.stream()).collect(Collectors.toList());
            List<Defect> defectList = releaseReport.getStoryList().stream().map(Story::getDefectList).flatMap(
                    test->test.stream()).collect(Collectors.toList());

            populateDeliverableMatrixMap(releaseReport.getStoryList(),mapMatrix);

            populateTestCoverageMatrixMap(testCaseList,mapMatrix);

            populateDefectCoverageMatrixMap(defectList,mapMatrix);
            return overallConfidence.getCategorization(mapMatrix);
        }
        return "";
    }

    public static void populateDeliverableMatrixMap(List<Story> storyList, Map<String,Integer> mapMatrix){
        int totalDeliverables = storyList.stream().map(Story::getPoints)
                .reduce(0,(a,b) -> a+b);
        int totalCompleted = storyList.stream().filter(story->story.getStatus() == Status.CLOSED).map(Story::getPoints).reduce(0,(a,b) -> a+b);
        String matrixConfig = deliverableCategorization.getCategorization(NumberUtil.getPerc(totalCompleted,totalDeliverables));
        listOfMapOfParameterAndConfidence.add(new HashMap<String,Object>(){{
            put("key",Categories.DELVIERY.getGetUiName());
            put("value",NumberUtil.getPerc(totalCompleted,totalDeliverables));
        }});
        if(mapMatrix.containsKey(matrixConfig)){
            mapMatrix.put(matrixConfig,mapMatrix.get(matrixConfig) + 1);
        }
    }

    public static void populateTestCoverageMatrixMap(List<TestCase> testCases, Map<String,Integer> mapMatrix){
        if(testCases!=null && testCases.size() >0){
            int totalStories = testCases.size();
            int totalStoriesPassed = (int)testCases.stream()
                    .filter(testStory->testStory.getExecutionStatus() == ExecutionStatus.PASSED)
                    .count();
            String matrixConfig = testConfiguaration.getCategorization(NumberUtil.getPerc(totalStoriesPassed,totalStories));
            listOfMapOfParameterAndConfidence.add(new HashMap<String,Object>(){{
                put("key",Categories.TEST_COVERAGE.getGetUiName());
                put("value",NumberUtil.getPerc(totalStoriesPassed,totalStories));
            }});
            if(mapMatrix.containsKey(matrixConfig)){
                mapMatrix.put(matrixConfig,mapMatrix.get(matrixConfig) + 1);
            }
        }
    }

    public static void populateDefectCoverageMatrixMap(List<Defect> defects, Map<String,Integer> mapMatrix){
        if(defects!=null && defects.size() >0){
            String matrixConfig = defectConfiguration.getCategorization(defects);
            if(mapMatrix.containsKey(matrixConfig)){
                mapMatrix.put(matrixConfig,mapMatrix.get(matrixConfig) + 1);
            }
        }
    }


}
