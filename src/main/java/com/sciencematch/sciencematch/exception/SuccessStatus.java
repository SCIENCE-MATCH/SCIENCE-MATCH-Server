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
    WITHDRAWAL_SUCCESS(HttpStatus.OK, "회원 탈퇴에 성공했습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, "토큰 재발행에 성공했습니다."),
    CHECK_DUPL_EMAIL_SUCCESS(HttpStatus.OK, "사용 가능한 이메일 주소입니다."),
    CHANGE_LOGO_SUCCESS(HttpStatus.OK, "로고 이미지를 성공적으로 교체하였습니다."),
    GET_MYPAGE_SUCCESS(HttpStatus.OK, "마이 페이지를 성공적으로 불러왔습니다."),
    STUDENT_INFO_UPDATE_SUCCESS(HttpStatus.OK, "학생 정보를 성공적으로 교체하였습니다."),
    DELETE_STUDENT_SUCCESS(HttpStatus.OK, "학생을 성공적으로 삭제하였습니다."),
    GET_MY_GROUPS_SUCCESS(HttpStatus.OK, "반 목록을 성공적으로 조회했습니다."),
    GET_GROUP_DETAIL_SUCCESS(HttpStatus.OK, "반 상세정보를 성공적으로 조회했습니다."),
    UPDATE_GROUP_DETAIL_SUCCESS(HttpStatus.OK, "반 상세정보를 성공적으로 수정했습니다."),
    GET_ALL_STUDENT_SUCCESS(HttpStatus.OK, "전체 학생 정보를 성공적으로 조회했습니다."),
    GET_ALL_WAITING_TEACHERS_SUCCESS(HttpStatus.OK, "가입 대기중인 선생 리스트를 성공적으로 조회했습니다."),
    GET_ALL_TEACHERS_SUCCESS(HttpStatus.OK, "선생 리스트를 성공적으로 조회했습니다."),
    GET_ALL_STUDENTS_SUCCESS(HttpStatus.OK, "학생 리스트를 성공적으로 조회했습니다."),
    GET_TEAMS_SUCCESS(HttpStatus.OK, "반 리스트를 성공적으로 조회했습니다."),
    DELETE_TEACHER_SUCCESS(HttpStatus.OK, "선생을 성공적으로 삭제하였습니다."),
    DELETE_TEAM_SUCCESS(HttpStatus.OK, "반을 성공적으로 삭제하였습니다."),
    ASSIGN_TEACHER_SUCCESS(HttpStatus.OK, "회원가입을 승인하였습니다."),
    GET_CHAPTER_SUCCESS(HttpStatus.OK, "단원 목록을 성공적으로 조회하였습니다."),
    PATCH_CHAPTER_SUCCESS(HttpStatus.OK, "단원 목록을 성공적으로 수정하였습니다."),
    POST_QUESTION_SUCCESS(HttpStatus.OK, "문제를 성공적으로 추가하였습니다."),

    /*
     * 201 created
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    CREATE_STUDENT_SUCCESS(HttpStatus.CREATED, "학생을 성공적으로 생성하였습니다."),
    CREATE_GROUP_SUCCESS(HttpStatus.CREATED, "반을 성공적으로 생성하였습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
