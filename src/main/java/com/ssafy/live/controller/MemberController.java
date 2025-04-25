package com.ssafy.live.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.live.model.dto.MemberDto;
import com.ssafy.live.model.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {
    
    private final MemberService memberService;
    
    @PostMapping("/login")
    protected String login(@RequestParam("email") String email, 
                           @RequestParam("password") String password,
                           HttpSession session, RedirectAttributes rAttributes) throws Exception {

        MemberDto member = memberService.login(email, password);
        if (member != null) {
            session.setAttribute("loginMember", member);  // 객체 통째로 저장
        } else {
            rAttributes.addFlashAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "redirect:/";   // 또는 로그인 모달 띄우는 방식
        }
        return "redirect:/";
    }
    
    @GetMapping("/logout")
    protected String logout(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        request.getSession().invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("email") String email) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/detail");   // JSP 경로도 member 기준
        System.out.println("++++++++++++++++++++++++++++++++++" + memberService.findByEmail(email).getCreatedAt());
        mav.addObject("member", memberService.findByEmail(email));
        System.out.println(mav.getModel().get("member"));
        return mav;
    }
    
    @GetMapping("/registerForm")
    public String registerForm() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(MemberDto user, RedirectAttributes rAttr) throws Exception {
        // UserDTO → MemberDto 변환 필요
        memberService.addMember(user);
        rAttr.addFlashAttribute("msg", "회원가입이 완료되었습니다. 로그인 해주세요!");
        return "redirect:/";
    }
    
    @GetMapping("/modifyForm")
    public ModelAndView modifyForm(@RequestParam("email") String email) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/modifyForm");  // JSP 경로
        mav.addObject("member", memberService.findByEmail(email));  // 기존 회원정보 조회
        return mav;
    }
    
    @PostMapping("/modify")
    public String modifyUser(MemberDto user, HttpSession session, RedirectAttributes rAttr) throws Exception {
        memberService.updateMember(user);
        session.setAttribute("loginMember", user);  // 수정된 정보 반영
        rAttr.addFlashAttribute("msg", "회원정보가 수정되었습니다.");
        return "redirect:/member/detail?email=" + user.getEmail();
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("email") String email, HttpSession session) throws Exception {
        memberService.deleteMemberByEmail(email);
        session.invalidate();
        return "redirect:/";
    }

}
