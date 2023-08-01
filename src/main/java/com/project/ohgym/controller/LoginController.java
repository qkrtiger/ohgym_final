package com.project.ohgym.controller;

import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.MemberDto;
import com.project.ohgym.service.LoginService;
import com.project.ohgym.service.RegisterMail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginServ;

    @Autowired
    private RegisterMail rServ;

    @GetMapping("joinForm")
    public String joinForm(){
        log.info("joinForm()");
        return "joinForm";
    }

    @GetMapping("idCheck")
    @ResponseBody
    public String idCheck(String mid){
        log.info("idCheck()");
        String res = loginServ.idCheck(mid);

        return res;
    }

    @GetMapping("nicknameCheck")
    @ResponseBody
    public String nicknameCheck(String mnickname){
        log.info("nicknameCheck()");
        String res = loginServ.nicknameCheck(mnickname);

        return res;
    }

    @GetMapping("mailDoubleCheck")
    @ResponseBody
    public String mailDoubleCheck(String mmail){
        log.info("mailDoubleCheck()");
        String res = loginServ.mailDoubleCheck(mmail);

        return res;
    }

    @PostMapping("mailCheck")
    @ResponseBody
    public String mailCheck (String email) throws Exception{
        log.info("mailCheck()");

        String code = rServ.sendSimpleMessage(email);
        return code;
    }

    //회원가입(회원)
    @PostMapping("joinProc")
    public String joinProc(MemberDto member, RedirectAttributes rttr){
        log.info("joinProc()");
        String view = loginServ.memberJoin(member, rttr);
        return view;
    }

    //회원가입(헬스장)
    @GetMapping("joinGymForm")
    public String joinGymForm(){
        log.info("joinGymForm()");
        return "joinGymForm";
    }

    @GetMapping("gidCheck")
    @ResponseBody
    public String gidCheck(String gid){
        log.info("idCheck()");
        String res = loginServ.gidCheck(gid);

        return res;
    }

    @PostMapping("joinGymProc")
    public String joinGymProc(GymDto gym, RedirectAttributes rttr){
        log.info("joinGymProc()");
        String view = loginServ.gymJoin(gym, rttr);

        return view;
    }

    @GetMapping("loginfind")
    public String loginfind(){
        log.info("loginfind()");
        return "loginfind";
    }

    @PostMapping("idFind")
    @ResponseBody
    public String idFind (String mmail, String mname) throws Exception{
        log.info("idFind()");

        String res = loginServ.idFind(mmail, mname);

        return res;
    }

    @PostMapping("findProc")
    public String findProc(MemberDto member, HttpSession session, RedirectAttributes rttr){
        log.info("findProc()");
        String view = loginServ.loginFind(member, session, rttr);

        return view;
    }

    @GetMapping("idComplete")
    public String idComplete(){
        log.info("idComplete()");
        return "idComplete";
    }

    @GetMapping("loginIdOut")
    public String loginIdOut(HttpSession session){
        log.info("loginIdOut()");
        String view = loginServ.loginIdOut(session);
        return view;
    }

    @GetMapping("passChange")
    public String passChange(){
        log.info("passChange()");
        return "passChange";
    }

    @PostMapping("passProc")
    public String passProc(MemberDto member, HttpSession session, RedirectAttributes rttr){
        log.info("passProc()");
        String view = loginServ.passChange(member, session, rttr);
        return view;
    }

    @GetMapping("loginGymFind")
    public String loginGymFind(){
        log.info("loginGymFind()");
        return "loginGymFind";
    }

    @PostMapping("findGymProc")
    public String findGymProc(GymDto gym, HttpSession session, RedirectAttributes rttr){
        log.info("findGymProc()");
        String view = loginServ.findGym(gym, session, rttr);
        return view;
    }

    @GetMapping("idGymComplete")
    public String idGymComplete(){
        log.info("idGymComplete()");
        return "idGymComplete";
    }

    @GetMapping("passGymChange")
    public String passGymChange(){
        log.info("passGymChange()");
        return "passGymChange";
    }

    @PostMapping("passGymProc")
    public String passGymProc(GymDto gym, HttpSession session, RedirectAttributes rttr){
        log.info("passGymProc()");
        String view = loginServ.passGymProc(gym, session, rttr);
        return view;
    }

    @GetMapping("login")
    public String login(){
        log.info("login()");
        return "login";
    }

    @GetMapping("memberLoginForm")
    public String memberLoginForm(){
        log.info("memberLoginForm()");
        return "memberLoginForm";
    }

    @PostMapping("meberLoginProc")
    public String meberLoginProc(MemberDto member, HttpSession session, RedirectAttributes rttr){
        log.info("meberLoginProc()");
        String view = loginServ.meberLoginProc(member, session, rttr);
        return view;
    }

    @GetMapping("gymLoginForm")
    public String gymLoginForm(){
        log.info("gymLoginForm()");
        return "gymLoginForm";
    }

    @PostMapping("gymLoginProc")
    public String gymLoginProc(GymDto gym, HttpSession session, RedirectAttributes rttr){
        log.info("gymLoginProc()");
        String view = loginServ.gymLoginProc(gym, session, rttr);
        return view;
    }

    @GetMapping("homeLogout")
    public String homeLogout(HttpSession session){
        log.info("homeLogout()");
        String view = loginServ.homeLogout(session);
        return view;
    }

    @RequestMapping(value = "/memberLoginForm/getKakaoAuthUrl")
    public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {

        String reqUrl =
                "https://kauth.kakao.com/oauth/authorize?client_id=e762f553da5154a3348fed056e2a0b57&redirect_uri=http://localhost:8880/auth_kakao&response_type=code";

        return reqUrl;
    }

    @RequestMapping(value = "/auth_kakao")
    public String oauthKakao(
            @RequestParam(value = "code",required = false) String code
            , Model model, HttpSession session) throws Exception {

        System.out.println("#######" + code);
        String access_Token = loginServ.getAccessToken(code);
        HashMap<String, Object> member = loginServ.getuserinfo(access_Token);

        System.out.println("###access_Token### : " + access_Token);
        System.out.println("###id#### : " + member.get("id"));
        System.out.println("###nickname#### : " + member.get("nickname"));
        System.out.println("###email#### : " + member.get("email"));


        model.addAttribute("member", member);
        session.setAttribute("access_Token", access_Token);

        return "redirect:/";
    }

}
