package dev.flab.studytogether.utils;

import javax.servlet.http.HttpSession;

public class SessionUtilV2 {
    private SessionUtilV2(){} //인스턴스화 방지

    public static Long getLoginMemberId(HttpSession httpSession) {
        return (Long) httpSession.getAttribute("memberId");
    }

    public static void setLoginMemberSession(HttpSession httpSession, Long memberId) {
        httpSession.setAttribute("memberId", memberId);
    }

    public static void logout(HttpSession httpSession) {
        httpSession.removeAttribute("memberId");
    }
}
