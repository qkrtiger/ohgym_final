package com.project.ohgym.service;

import com.project.ohgym.dao.LoginDao;
import com.project.ohgym.dto.GymConvenientDto;
import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.GymMachineDto;
import com.project.ohgym.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private LoginDao loginnDao;

    private BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder();

    public String idCheck(String mid) {
        //리턴 타입과 같은 변수를 먼저 선언할 것
        String res = null;

        //아이디가 중복이면 1, 아니면 0
        int cnt = loginnDao.idCheck(mid);

        if(cnt == 0){
            res = "ok";
        } else {
            res = "fail";
        }

        return res;
    }

    public String nicknameCheck(String mnickname) {
        //리턴 타입과 같은 변수를 먼저 선언할 것
        String res = null;

        //아이디가 중복이면 1, 아니면 0
        int cnt = loginnDao.nicknameCheck(mnickname);

        if(cnt == 0){
            res = "ok";
        } else {
            res = "fail";
        }

        return res;
    }

    public String mailDoubleCheck(String mmail) {
        //리턴 타입과 같은 변수를 먼저 선언할 것
        String res = null;

        //아이디가 중복이면 1, 아니면 0
        int cnt = loginnDao.mailDoubleCheck(mmail);

        if(cnt == 0){
            res = "ok";
        } else {
            res = "fail";
        }

        return res;
    }

    public String memberJoin(MemberDto member, RedirectAttributes rttr) {
        log.info("memberJoin()");
        String view = null;
        String msg = null;

        //비밀번호 암호화 처리
        String encpwd = pEncoder.encode(member.getMpass());
        log.info(encpwd);
        //평문인 비밀번호를 암호문으로 덮어씀.
        member.setMpass(encpwd);

        try {
            loginnDao.memberInsert(member);
            view = "redirect:memberLoginForm";
            msg = member.getMname() + "고객님! " + "회원 가입이 완료되었습니다.";
        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:joinFrom";
            msg = "가입 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    public String gidCheck(String gid) {
        //리턴 타입과 같은 변수를 먼저 선언할 것
        String res = null;

        //아이디가 중복이면 1, 아니면 0
        int cnt = loginnDao.gidCheck(gid);

        if(cnt == 0){
            res = "ok";
        } else {
            res = "fail";
        }

        return res;
    }

    public String gymJoin(GymDto gym, RedirectAttributes rttr) {
        log.info("gymJoin()");
        String view = null;
        String msg = null;

        //비밀번호 암호화 처리
        String encpwd = pEncoder.encode(gym.getGpass());
        log.info(encpwd);
        //평문인 비밀번호를 암호문으로 덮어씀.
        gym.setGpass(encpwd);

        try {
            loginnDao.gymInsert(gym);
            //int gymnum =
            GymMachineDto gm = new GymMachineDto();
            gm.setGymnum(gym.getGymnum());
            GymConvenientDto gc = new GymConvenientDto();
            gc.setGymnum(gym.getGymnum());
            loginnDao.insertCheckC(gc);
            loginnDao.insertCheckM(gm);
            view = "redirect:gymLoginForm";
            msg = gym.getGname() + " 관리자님! 회원 가입이 완료되었습니다.";
        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:joinGymFrom";
            msg = "가입 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    public String idFind(String mmail, String mname) {
        log.info("idFind()");
        String res;


        String db = loginnDao.idFind(mmail);

        if (db.equals(mname)){
            res = "ok";
        } else {
            res = "notok";
        }

        log.info(res);

        return res;
    }

    public String loginFind(MemberDto member, HttpSession session, RedirectAttributes rttr) {
        log.info("loginFind()");

        String view = null;
        String id = null;
        String msg = null;

        try {
            id = loginnDao.loginFind(member);
            view = "redirect:idComplete";
        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:loginfind";
            msg = "id 찾기 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        session.setAttribute("id", id);

        return view;
    }

    public String loginIdOut(HttpSession session) {
        log.info("loginIdOut()");
        String view = null;
        session.invalidate();
        view = "redirect:login";
        return view; //첫페이지로 이동
    }

    public String passChange(MemberDto member, HttpSession session, RedirectAttributes rttr) {
        log.info("passChange()");

        String view = null;
        String msg = null;

        //비밀번호 암호화 처리
        String encpwd = pEncoder.encode(member.getMpass());
        log.info(encpwd);
        //평문인 비밀번호를 암호문으로 덮어씀.
        member.setMpass(encpwd);

        try {
            loginnDao.passChange(member);
            msg = "비밀번호가 재설정 되었습니다. 다시 로그인하세요.";
            view = "redirect:memberLoginForm";
        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:passChange";
            msg = "비밀번호 변경에 실패했습니다. 다시 시도해주세요.";
        }

        rttr.addFlashAttribute("msg", msg);
        session.invalidate();

        return view;
    }

    public String findGym(GymDto gym, HttpSession session, RedirectAttributes rttr) {
        log.info("findGym()");

        String view = null;
        String id = null;
        String msg = null;

        try {
            id = loginnDao.findGym(gym);
            if (id == null){
                view = "redirect:terms";
                msg = "등록된 헬스장 회원이 아닙니다. 회원가입 해 주세요.";
            } else {
                view = "redirect:idGymComplete";
            }
        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:loginGymFind";
            msg = "id 찾기를 다시 시도해 주세요.";
        }

        session.setAttribute("id", id);
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    public String passGymProc(GymDto gym, HttpSession session, RedirectAttributes rttr) {
        log.info("passGymProc()");

        String view = null;
        String msg = null;

        //비밀번호 암호화 처리
        String encpwd = pEncoder.encode(gym.getGpass());
        log.info(encpwd);
        //평문인 비밀번호를 암호문으로 덮어씀.
        gym.setGpass(encpwd);

        try{
            loginnDao.passGymChange(gym);
            msg = "비밀번호가 재설정 되었습니다. 다시 로그인하세요.";
            view = "redirect:gymLoginForm";
        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:passGymChange";
            msg = "비밀번호 변경에 실패했습니다. 다시 시도해주세요.";
        }

        rttr.addFlashAttribute("msg", msg);
        session.invalidate();

        return view;
    }

    public String meberLoginProc(MemberDto member, HttpSession session, RedirectAttributes rttr) {
        log.info("meberLoginProc()");
        String view = null;
        String msg = null;

        //DB에서 회원의 비밀번호 구하기(암호문)
        String encPwd = loginnDao.mSelectPass(member.getMid());
        //encPwd에 담겨있을 수 있는 데이터
        // 1) null : 비회원인 경우
        // 2) 암호화된 비밀번호 문자열 : 회원인 경우
        if(encPwd != null){
            //아이디는 맞음.(회원의 아이디)
            if(pEncoder.matches(member.getMpass(), encPwd)){
                //matches 메소드 : Spring Security 에서 제공하는
                //암호문과 평문 비교 메소드.
                //matches(평문, 암호문) 형식으로 작성하면,
                //같은 값일 때 true, 다르면 false를 출력.

                //비밀번호가 맞는 경우
                //세션에 로그인 성공정보(접속자 정보) 저장.
                //저장할 회원 정보 : id, name, point, g_name
                member = loginnDao.selectMember(member.getMid());
                //세션에 DTO 저장.
                session.setAttribute("member", member);

                //로그인 성공 후 페이지 이동처리
                //테스트 페이지로 이동(나중에 변경하기)
                view = "redirect:/";
                msg = "로그인 성공";

            } else {
                //비밀번호가 틀린 경우
                view = "redirect:memberLoginForm";
                msg = "비밀번호 오류";
            }
        } else {
            //아이디 없음(비회원)
            view = "redirect:memberLoginForm";
            msg = "아이디가 존재하지 않습니다.";
        }

        rttr.addFlashAttribute("msg", msg);
        return view;
    }



    public String gymLoginProc(GymDto gym, HttpSession session, RedirectAttributes rttr) {
        String view = null;
        String msg = null;

        //DB에서 회원의 비밀번호 구하기(암호문)
        String encPwd = loginnDao.gSelectPass(gym.getGid());
        //encPwd에 담겨있을 수 있는 데이터
        // 1) null : 비회원인 경우
        // 2) 암호화된 비밀번호 문자열 : 회원인 경우
        if(encPwd != null){
            //아이디는 맞음.(회원의 아이디)
            if(pEncoder.matches(gym.getGpass(), encPwd)){
                //matches 메소드 : Spring Security 에서 제공하는
                //암호문과 평문 비교 메소드.
                //matches(평문, 암호문) 형식으로 작성하면,
                //같은 값일 때 true, 다르면 false를 출력.

                //비밀번호가 맞는 경우
                //세션에 로그인 성공정보(접속자 정보) 저장.
                //  저장할 회원 정보 : id, name, point, g_name
                gym = loginnDao.selectGym(gym.getGid());
                //세션에 DTO 저장.
                session.setAttribute("gym", gym);

                //로그인 성공 후 페이지 이동처리
                //테스트 페이지로 이동(나중에 변경하기)
                view = "redirect:/";
                msg = "로그인 성공";

            } else {
                //비밀번호가 틀린 경우
                view = "redirect:gymLoginForm";
                msg = "비밀번호 오류";
            }
        } else {
            //아이디 없음(비회원)
            view = "redirect:gymLoginForm";
            msg = "아이디가 존재하지 않습니다.";
        }

        rttr.addFlashAttribute("msg", msg);
        return view;

    }

    public String homeLogout(HttpSession session) {
        log.info("homeLogout()");
        String view = null;
        session.invalidate();
        view = "redirect:/";
        return view; //첫페이지로 이동
    }
}
