package com.portfolio.www.auth.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.portfolio.www.auth.mybatis.MemberRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class JoinService {
	
	@Autowired
	private MemberRepository memberRepository;
	
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
			
			if(cnt == 1) {
				
			}
			
		}
		
		return cnt;
	}
}
