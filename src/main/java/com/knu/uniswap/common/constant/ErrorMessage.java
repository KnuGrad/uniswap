package com.knu.uniswap.common.constant;

public class ErrorMessage {

    /* Validation */
    public static final String VALIDATION_ERROR = "입력 데이터의 유효성을 검사하던 중 문제가 발생했습니다.";

    public static final String LOGIN_FAILED = "이메일 또는 비밀번호가 올바르지 않습니다.";
    public static final String LOGIN_REQUIRED = "로그인 후 이용해 주세요.";

    /* MemberService */
    public static final String PASSWORD_CONFIRMATION_FAILED = "비밀번호 확인에 실패했습니다. 동일한 비밀번호를 입력해 주세요.";
    public static final String NICKNAME_DUPLICATED = "이미 존재하는 닉네임입니다.";
    public static final String EMAIL_DUPLICATED = "이미 존재하는 이메일입니다.";
    public static final String EMAIL_NOT_CERTIFIED = "이메일 인증이 완료되지 않았습니다.";
    public static final String NO_ACCESS_PERMISSION = "접근 권한이 없습니다.";
    public static final String SAME_NICKNAME = "현재 닉네임과 변경할 닉네임이 동일합니다.";
    public static final String SAME_PASSWORD = "현재 비밀번호와 변경할 비밀번호가 동일합니다.";

    /* Member */
    public static final String MEMBER_NOT_FOUND = "존재하지 않는 아이디입니다.";

}
