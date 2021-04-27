package com.csxy.project.token;

import com.csxy.core.util.DateUtils;
import com.csxy.core.util.StringUtils;
import com.csxy.core.util.WebContextUtil;
import com.csxy.project.constants.TokenConstant;

public class TokenManager {

    public static String generateToken(String id){
        if(StringUtils.isEmpty(id)){
            return TokenConstant.DEFAULT_TOKEN_NAME + "_" + DateUtils.getCurrentCompactTime() + "_" +  WebContextUtil.getRequest().getRemoteAddr();
        }else{
            return TokenConstant.DEFAULT_TOKEN_NAME + "_" + id + "_" +  WebContextUtil.getRequest().getRemoteAddr() + DateUtils.getCurrentCompactTime();
        }
    }
}
