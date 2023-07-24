package com.project.ohgym.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Slf4j
public class RegisterMail {

    @Autowired
    private JavaMailSender emailsender; //Bean 등록해준 MailConfig를 emailsender 라는 이름으로 autowired

    @Autowired
    private SpringTemplateEngine templateEngine;

    private String authNum;


    //랜덤 인증 코드 전송
    public void createCode(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { //인증코드 8자리
            int index = rnd.nextInt(3); //0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 샐행문

            switch (index){
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98='b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        authNum = key.toString();
    }

    //메일 내용 작성
    public MimeMessage creatMessage(String email) throws MessagingException, UnsupportedEncodingException{

        createCode();//인증 코드 생성
        String setForm = "wandowando12@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "인증번호"; //제목

        MimeMessage message = emailsender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email); //보내는 대상
        message.setSubject("title"); //제목
        message.setFrom(setForm);
        message.setText(setContext(authNum), "utf-8", "html");

        return message;
    }

    //타임리프를 이용한 context 설정
    public String setContext(String code){
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }

    // 메일발송
    // sendSimpleMessage의 매개변수로 들어온 to는 곧 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    // 그리고 bean으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!
    public String sendSimpleMessage(String toEmail) throws MessagingException, UnsupportedEncodingException{
        log.info("sendSimpleMessage()");

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = creatMessage(toEmail); //메일 발송
        //실제 메일 전송
        emailsender.send(emailForm);

        return authNum; //인증 코드 발송
    }
}//class end
