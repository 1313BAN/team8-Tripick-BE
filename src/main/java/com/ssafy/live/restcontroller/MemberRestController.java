package com.ssafy.live.restcontroller;

import com.ssafy.live.model.dto.MemberDto;
import com.ssafy.live.model.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {

    private final MemberService memberService;

    /** 🔐 로그인 */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email,
                                   @RequestParam String password,
                                   HttpSession session) throws Exception {
        MemberDto member = memberService.login(email, password);
        if (member != null) {
            session.setAttribute("loginMember", member);
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(401).body("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    /** 🚪 로그아웃 */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    /** 👤 회원 상세 조회 */
    @GetMapping("/{email}")
    public ResponseEntity<MemberDto> getMemberDetail(@PathVariable String email) throws Exception {
        MemberDto member = memberService.findByEmail(email);
        if (member != null) return ResponseEntity.ok(member);
        throw new NoSuchElementException("해당 이메일의 사용자가 없습니다.");
    }

    /** 📝 회원 가입 */
    @PostMapping
    public ResponseEntity<?> register(@RequestBody MemberDto user) throws Exception {
        memberService.addMember(user);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /** ✏️ 회원 수정 */
    @PutMapping("/{email}")
    public ResponseEntity<?> modifyUser(@PathVariable String email,
                                        @RequestBody MemberDto user,
                                        HttpSession session) throws Exception {
        user.setEmail(email);  // URL 경로의 이메일을 사용
        memberService.updateMember(user);
        session.setAttribute("loginMember", user);
        return ResponseEntity.ok("회원정보가 수정되었습니다.");
    }

    /** ❌ 회원 탈퇴 */
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email, HttpSession session) throws Exception {
        memberService.deleteMemberByEmail(email);
        session.invalidate();
        return ResponseEntity.ok("회원탈퇴 완료");
    }
}
