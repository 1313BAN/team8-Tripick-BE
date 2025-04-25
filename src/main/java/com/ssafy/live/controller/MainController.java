package com.ssafy.live.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public Object index() {
		System.out.println("들어왔어~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return "index";
	}

//    private static final long serialVersionUID = 1L;
//    private MemberService memberService = new MemberService();
//
//    @Override
//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = preProcessing(request, response);
//        switch (action) {
//        case "index" -> forward(request, response, "/index.jsp");
//        case "make-cookie" -> makeCookie(request, response);
//        case "check-cookie" -> forward(request, response, "/status/check-cookie.jsp");
//        case "member-problem" -> makeProblem(request, response);
//        case "join" -> doRegist(request, response);
//        case "login" -> doLogIn(request, response);
//        case "logout" -> doLogOut(request, response);
//        case "mypage" -> showMyPage(request, response);
//        case "update" -> doUpdate(request, response);
//        case "delete" -> doDelete(request, response);
//        default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//
//    private void makeCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        setupCookie("for-domain", "container_root", 60 * 10, "/", response);
//        setupCookie("just-1-min", "1분-유지-쿠키", 60 * 1, null, response);
//        setupCookie("status-path", URLEncoder.encode("status 경로에 저장된 쿠키", "UTF-8"), -1, "/mvc3/status", response);
//        setupCookie("immediate-del", "010-1234-5678", 0, "/", response);
//        //forward(request, response, "/status/check-cookie.jsp"); // forward로 했을 때의 문제점 - 응답이 내려간적이 없음
//        redirect(request, response, "/main?action=check-cookie");
//    }
//
//    private void makeProblem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // ArithmeticException 발생
//        @SuppressWarnings("unused")
//        int i = 1 / 0;
//        forward(request, response, "/member/member-list.jsp");
//    }
//    
//    private void doRegist(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            request.setCharacterEncoding("UTF-8");
//
//            String password = request.getParameter("password");
//            String username = request.getParameter("username");
//            String email = request.getParameter("email");
//            String phone = request.getParameter("phone");
//            
//            //해싱
//            String rawPassword = request.getParameter("password");
//            String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());  // 해싱
//            
//            MemberDto user = new MemberDto();
//            user.setPassword(hashedPassword);
//            user.setUsername(username); // DB에 저장할 때는 hashedPassword
//            user.setEmail(email);
//            user.setPhone(phone);
//
//            memberService.addMember(user);
//
//            request.setAttribute("username", username);
//            request.setAttribute("email", email);
//            request.setAttribute("phone", phone);
//            request.setAttribute("message", "회원가입이 완료되었습니다!");
//            request.getRequestDispatcher("/regist_result.jsp").forward(request, response);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            request.setAttribute("error", e.getMessage());
//            forward(request, response, "/member/member-regist-form.jsp");
//        }
//    }
//
//    private void doLogIn(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            request.setCharacterEncoding("UTF-8");
//
//            // 사용자가 입력한 로그인 정보
//            String email = request.getParameter("email");
//            String password = request.getParameter("password");
//
//            // 이메일 + 비밀번호로 사용자 조회
//            MemberDto user = memberService.findByEmail(email);
//            HttpSession session = request.getSession();
//
//            if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
//                // 로그인 실패
//                request.setAttribute("msg", "이메일 또는 비밀번호가 잘못되었습니다.");
//                request.getRequestDispatcher("/index.jsp").forward(request, response); // 로그인 모달 다시 보이게 할 수도 있음
//            } else {
//                // 로그인 성공
//            	session.setAttribute("loginUser", user);
//                response.sendRedirect(request.getContextPath() + "/index.jsp");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            request.setAttribute("error", "로그인 중 오류 발생: " + e.getMessage());
//            forward(request, response, "/index.jsp");
//        }
//    }
//
//    private void showMyPage(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        MemberDto user = (MemberDto) session.getAttribute("loginUser");
//
//        if (user == null) {
//            // 로그인 안 돼 있으면 로그인 페이지로 보내거나 경고 메시지
//            session.setAttribute("alertMsg", "로그인이 필요합니다.");
//            response.sendRedirect(request.getContextPath() + "/index.jsp");
//            return;
//        }
//
//        // 로그인돼 있으면 마이페이지로 이동
//        request.setAttribute("user", user); // 필요 시 request에도 넣어줄 수 있음
//        forward(request, response, "/mypage.jsp");
//    }
//
//    private void doLogOut(HttpServletRequest request, HttpServletResponse response)
//            throws IOException {
//        HttpSession session = request.getSession();
//        session.invalidate();
//        //redirect로 ㄱㄱ "redirect:/main?action=home"
//        response.sendRedirect(request.getContextPath() + "/main?action=index");
////        response.sendRedirect(request.getContextPath() + "/index.jsp");
//       
//    }
//    
//    private void doUpdate(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
//
//        if (loginUser == null) {
//            response.sendRedirect(request.getContextPath() + "/index.jsp");
//            return;
//        }
//
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String phone = request.getParameter("phone");
//
//        try {
//            // 기존 정보 유지하면서 변경된 정보만 세팅
//            loginUser.setUsername(username);
//            loginUser.setPhone(phone);
//            if (password != null && !password.isBlank()) {
//                loginUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
//            }
//
//            memberService.updateMember(loginUser);
//            session.setAttribute("loginUser", loginUser);
//            request.setAttribute("message", "회원 정보가 수정되었습니다.");
//            forward(request, response, "/mypage.jsp");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            request.setAttribute("error", "회원 정보 수정 중 오류 발생");
//            forward(request, response, "/mypage.jsp");
//        }
//    }
//    public void doDelete(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            String email = request.getParameter("email");
//            memberService.deleteMemberByEmail(email);
//
//            // 세션 초기화
//            HttpSession session = request.getSession();
//            session.invalidate();
//
//            request.setAttribute("msg", "회원 탈퇴가 완료되었습니다.");
//            forward(request, response, "/index.jsp");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            request.setAttribute("error", "회원 탈퇴 중 오류가 발생했습니다.");
//            forward(request, response, "/mypage.jsp");
//        }
//    }


}
