package com.portfolio.www.auth.dao.mybatis;

import java.util.HashMap;

public interface JoinRepository {
	
	public int join(HashMap<String, String> params);

}
