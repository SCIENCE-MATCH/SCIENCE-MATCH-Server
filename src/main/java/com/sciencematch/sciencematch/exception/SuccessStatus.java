package com.sciencematch.sciencematch.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessStatus {
    /**
     * 200 OK
     */
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공했습니다."),
    CHECK_PW_SUCCESS(HttpStatus.OK, "비밀번호 확인에 성공했습니다."),
    CHANGE_PW_SUCCESS(HttpStatus.OK, "비밀번호 변경에 성공했습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, "토큰 재발행에 성공했습니다."),
    CHECK_DUPL_EMAIL_SUCCESS(HttpStatus.OK, "사용 가능한 이메일 주소입니다."),
    CHANGE_LOGO_SUCCESS(HttpStatus.OK, "로고 이미지를 성공적으로 교체하였습니다."),
    GET_MYPAGE_SUCCESS(HttpStatus.OK, "마이 페이지를 성공적으로 불러왔습니다."),
    STUDENT_INFO_UPDATE_SUCCESS(HttpStatus.OK, "학생 정보를 성공적으로 교체하였습니다."),
    GET_MY_GROUPS_SUCCESS(HttpStatus.OK, "반 목록을 성공적으로 조회했습니다."),
    GET_GROUP_DETAIL_SUCCESS(HttpStatus.OK, "반 상세정보를 성공적으로 조회했습니다."),
    UPDATE_GROUP_DETAIL_SUCCESS(HttpStatus.OK, "반 상세정보를 성공적으로 수정했습니다."),
    GET_ALL_STUDENT_SUCCESS(HttpStatus.OK, "전체 학생 정보를 성공적으로 조회했습니다."),
    GET_ALL_WAITING_TEACHERS_SUCCESS(HttpStatus.OK, "가입 대기중인 선생 리스트를 성공적으로 조회했습니다."),
    GET_ALL_TEACHERS_SUCCESS(HttpStatus.OK, "선생 리스트를 성공적으로 조회했습니다."),
    GET_ALL_STUDENTS_SUCCESS(HttpStatus.OK, "학생 리스트를 성공적으로 조회했습니다."),
    GET_TEAMS_SUCCESS(HttpStatus.OK, "반 리스트를 성공적으로 조회했습니다."),
    ASSIGN_TEACHER_SUCCESS(HttpStatus.OK, "회원가입을 승인하였습니다."),
    GET_CHAPTER_SUCCESS(HttpStatus.OK, "단원 목록을 성공적으로 조회하였습니다."),
    POST_CHAPTER_SUCCESS(HttpStatus.OK, "단원 목록을 성공적으로 추가하였습니다."),
    PATCH_CHAPTER_SUCCESS(HttpStatus.OK, "단원 목록을 성공적으로 수정하였습니다."),
    POST_QUESTION_SUCCESS(HttpStatus.OK, "문제를 성공적으로 추가하였습니다."),
    GET_QUESTION_SUCCESS(HttpStatus.OK, "문제를 성공적으로 조회하였습니다."),
    GET_QUESTION_PAPER_SUCCESS(HttpStatus.OK, "문제지를 성공적으로 조회하였습니다."),
    GET_QUESTION_CONCEPT_SUCCESS(HttpStatus.OK, "개념을 성공적으로 조회하였습니다."),
    GET_PAPER_TEST_SUCCESS(HttpStatus.OK, "일대일 질문을 성공적으로 조회하였습니다."),
    GET_ASSIGN_QUESTION_PAPER_LIST_SUCCESS(HttpStatus.OK, "학생에게 출제된 문제지들을 성공적으로 조회하였습니다."),
    GET_ASSIGN_PAPER_TEST_LIST_SUCCESS(HttpStatus.OK, "학생에게 출제된 일대일 질문들을 성공적으로 조회하였습니다."),
    GET_QUESTION_PAPER_DETAIL_SUCCESS(HttpStatus.OK, "문제지 상세를 성공적으로 조회하였습니다."),
    GET_QUESTION_FOR_NORMAL_SUCCESS(HttpStatus.OK, "단원 유형별 문제를 성공적으로 조회하였습니다."),
    GET_QUESTION_FOR_NORMAL_BY_CHAPTER_SUCCESS(HttpStatus.OK, "단원 유형별 동일한 수의 문제를 성공적으로 조회하였습니다."),
    SUBMIT_QUESTION_PAPER_SUCCESS(HttpStatus.OK, "문제지를 성공적으로 출제하였습니다."),
    SUBMIT_PAPER_TEST_SUCCESS(HttpStatus.OK, "일대일 질문을 성공적으로 출제하였습니다."),
    SOLVE_QUESTION_PAPER_SUCCESS(HttpStatus.OK, "문제지 답을 성공적으로 제출하였습니다."),
    SOLVE_PAPER_TEST_SUCCESS(HttpStatus.OK, "일대일 질문 답을 성공적으로 제출하였습니다."),
    GET_ASSIGN_QUESTION_PAPER_SUCCESS(HttpStatus.OK, "출제된 문제지를 성공적으로 조회하였습니다."),
    GET_ASSIGN_PAPER_TEST_SUCCESS(HttpStatus.OK, "출제된 일대일 질문을 성공적으로 조회하였습니다."),
    GRADING_ANSWER_SUCCESS(HttpStatus.OK, "출제된 일대일 질문을 성공적으로 조회하였습니다."),
    GET_CONCEPT_SUCCESS(HttpStatus.OK, "개념을 성공적으로 조회하였습니다."),
    RE_ENROLL_STUDENT_SUCCESS(HttpStatus.OK, "학생을 성공적으로 재등록하였습니다."),
    GET_WRONG_SUCCESS(HttpStatus.OK, "오답 문제를 성공적으로 추출하였습니다."),
    GET_BOOK_SUCCESS(HttpStatus.OK, "시중 교재를 성공적으로 조회하였습니다."),
    GET_BOOK_QUESTION_SUCCESS(HttpStatus.OK, "시중 교재 문제를 성공적으로 조회하였습니다."),
    GET_BOOK_CHAPTER_SUCCESS(HttpStatus.OK, "시중 교재 단원을 성공적으로 조회하였습니다."),
    UPDATE_BOOK_SUCCESS(HttpStatus.OK, "시중 교재를 성공적으로 수정하였습니다."),
    QUESTION_PAPER_DOWNLOAD_SUCCESS(HttpStatus.OK, "학습지를 성공적으로 다운로드하였습니다."),
    UPDATE_CHAPTER_ORDER_SUCCESS(HttpStatus.OK, "단원 순서를 성공적으로 변경하였습니다."),
    GET_TEACHER_LEVEL_SUCCESS(HttpStatus.OK, "난이도 비율을 성공적으로 조회하였습니다."),
    UPDATE_TEACHER_LEVEL_SUCCESS(HttpStatus.OK, "난이도 비율을 성공적으로 수정하였습니다."),
    GET_CSAT_IDS_SUCCESS(HttpStatus.OK, "모의고사 ID들을 성공적으로 조회하였습니다."),
    GET_CSAT_QUESTION_SUCCESS(HttpStatus.OK, "모의고사 문제를 성공적으로 조회하였습니다."),
    GET_CSAT_CHAPTER_SUCCESS(HttpStatus.OK, "모의고사 단원을 성공적으로 조회하였습니다."),
    GET_REPORTS_SUCCESS(HttpStatus.OK, "보고서를 성종적으로 조회하였습니다."),
    /*
     * 201 created
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    CREATE_QUESTION_PAPER_SUCCESS(HttpStatus.CREATED, "문제지를 성공적으로 생성하였습니다."),
    CREATE_PAPER_TEST_SUCCESS(HttpStatus.CREATED, "일대일 질문을 성공적으로 생성하였습니다."),
    CREATE_STUDENT_SUCCESS(HttpStatus.CREATED, "학생을 성공적으로 생성하였습니다."),
    CREATE_GROUP_SUCCESS(HttpStatus.CREATED, "반을 성공적으로 생성하였습니다."),
    CREATE_CONCEPT_SUCCESS(HttpStatus.CREATED, "개념을 성공적으로 생성하였습니다."),
    CREATE_BOOK_SUCCESS(HttpStatus.CREATED, "교재를 성공적으로 생성하였습니다."),
    CREATE_CSAT_SUCCESS(HttpStatus.CREATED, "모의고사를 성공적으로 생성하였습니다."),
    CREATE_REPORT_SUCCESS(HttpStatus.CREATED, "보고서를 성공적으로 생성하였습니다."),

    /**
     *  204 no content
     */
    DELETE_GROUP_SUCCESS(HttpStatus.NO_CONTENT, "반을 성공적으로 삭제하였습니다."),
    DELETE_PAPER_TEST_SUCCESS(HttpStatus.NO_CONTENT, "일대일 질문을 성공적으로 삭제하였습니다."),
    DELETE_ASSIGN_PAPER_TEST_SUCCESS(HttpStatus.NO_CONTENT, "출제한 일대일 질문을 성공적으로 삭제하였습니다."),
    DELETE_CONCEPT_SUCCESS(HttpStatus.NO_CONTENT, "개념을 성공적으로 삭제하였습니다."),
    DELETE_QUESTION_PAPER_SUCCESS(HttpStatus.NO_CONTENT, "학습지를 성공적으로 삭제하였습니다."),
    DELETE_TEACHER_SUCCESS(HttpStatus.NO_CONTENT, "선생을 성공적으로 삭제하였습니다."),
    DELETE_TEAM_SUCCESS(HttpStatus.NO_CONTENT, "반을 성공적으로 삭제하였습니다."),
    WITHDRAWAL_SUCCESS(HttpStatus.NO_CONTENT, "회원 탈퇴에 성공했습니다."),
    DELETE_STUDENT_SUCCESS(HttpStatus.NO_CONTENT, "학생을 성공적으로 삭제하였습니다."),
    DELETE_CHAPTER_SUCCESS(HttpStatus.NO_CONTENT, "단원 목록을 성공적으로 삭제하였습니다."),
    DELETE_QUESTION_SUCCESS(HttpStatus.NO_CONTENT, "문제를 성공적으로 삭제하였습니다."),
    DELETE_REPORT_SUCCESS(HttpStatus.NO_CONTENT, "보고서를 성공적으로 삭제하였습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
