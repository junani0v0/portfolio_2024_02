package com.portfolio.www.auth.mybatis;

import com.portfolio.www.auth.dto.MemberAuthDto;

public interface MemberAuthRepository {
	//회원인증 정보추가
	public int addAuthInfo(MemberAuthDto memberAuthDto);
	
	
}
