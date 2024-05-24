package com.portfolio.www.auth.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.portfolio.www.auth.dto.EmailDto;
import com.portfolio.www.auth.dto.MemberAuthDto;
import com.portfolio.www.auth.mybatis.MemberAuthRepository;
import com.portfolio.www.auth.mybatis.MemberRepository;
import com.portfolio.www.auth.util.EmailUtil;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class JoinService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberAuthRepository memberAuthRepository;
	
	@Autowired
	private EmailUtil emailUtil;
	
	//회원가입
	public int join(HashMap<String, String> params) {
		//비밀번호 암호화
		String passwd = params.get("passwd");
		String encPasswd = BCrypt.withDefaults().hashToString(12, passwd.toCharArray());
		BCrypt.Result result = BCrypt.verifyer().verify(passwd.toCharArray(), encPasswd);
		params.put("passwd", encPasswd);
		
		int cnt;
		String inputId = params.get("memberId");
		
		try {
			//회원ID 중복 체크
			memberRepository.getMemberId(inputId);
			cnt = 0;
			
		} catch (EmptyResultDataAccessException e) {
			//회원추가
			cnt = memberRepository.addJoin(params);
			int memberSeq = memberRepository.getMemberSeq(params.get("memberId"));
			
			if(cnt == 1) {
				//인증메일 uri 생성
				MemberAuthDto memberAuthDto = new MemberAuthDto();
				memberAuthDto.setMemberSeq(memberSeq);
				String uri = UUID.randomUUID().toString().replaceAll("-", "");
				memberAuthDto.setAuthUri(uri);
				
				//인증만료 시간 생성
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, 30);
				memberAuthDto.setExpireDtm(cal.getTimeInMillis());
				
				//인증 정보 추가
				memberAuthRepository.addAuthInfo(memberAuthDto);
				
				//인증메일 발송
				EmailDto emailDto = new EmailDto();
			}
			
		}
		
		return cnt;
	}
}
