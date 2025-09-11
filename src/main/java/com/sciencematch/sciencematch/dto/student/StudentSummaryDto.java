package com.sciencematch.sciencematch.dto.student;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentSummaryDto {
  private Integer totalQuestions;
  private Integer correctAnswers;
  private Integer ungradedAnswers;

  private List<Integer> quesNumPerWeek;
  private List<Integer> correctNumPerWeek; 
  private List<Integer> ungradedNumPerWeek;

  private List<Double> accuracyPerLevel;

}
