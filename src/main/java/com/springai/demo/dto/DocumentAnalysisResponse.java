package com.springai.demo.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentAnalysisResponse {
    private String summary;
    private List<String> keyPoints;
    private String sentiment;
    private Integer documentCount;
}
