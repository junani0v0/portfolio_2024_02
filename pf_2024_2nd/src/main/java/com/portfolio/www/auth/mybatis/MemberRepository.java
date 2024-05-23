package com.portfolio.www.auth.mybatis;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

public interface MemberRepository {
	//회원가입
	public int addJoin(HashMap<String, String> params);
	//회원 ID 조회
	public String getMemberId(String memberId);
	
}
