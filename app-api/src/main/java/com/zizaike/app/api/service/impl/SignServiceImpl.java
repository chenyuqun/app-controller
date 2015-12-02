/**  
 * Project Name:app-api  <br/>
 * File Name:SignServiceImpl.java  <br/>
 * Package Name:com.zizaike.app.api.service.impl  <br/>
 * Date:2015年11月16日上午11:37:37  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.app.api.service.impl;  

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.app.api.service.SignService;
import com.zizaike.app.api.sign.SignValid;
import com.zizaike.core.common.PropertyConfigurer;
import com.zizaike.core.common.util.sign.SignUtil;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.sign.ApiSignErrorException;
import com.zizaike.core.framework.exception.sign.ApiSignKeyNotEqulesException;
import com.zizaike.core.framework.exception.sign.ApiSignParameterMissedException;

/**  
 * ClassName:SignServiceImpl <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2015年11月16日 上午11:37:37 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service
public class SignServiceImpl implements SignService {
    private static final Logger LOG = LoggerFactory.getLogger(SignServiceImpl.class);
    @Autowired
    PropertyConfigurer propertyConfigurge;
    @Override
    public void signVerification(Map params) throws ZZKServiceException{
            String apiKeySys=  propertyConfigurge.getProperty(SignUtil.API_KEY);
            String apiKeyParam = (String) params.get(SignUtil.API_KEY);
            String apiSecret = propertyConfigurge.getProperty(SignUtil.API_SECRET);
            String apiSign = (String) params.get(SignUtil.API_SIGN);
            if(StringUtils.isBlank(apiKeyParam)||StringUtils.isBlank(apiSign)){
                throw new ApiSignParameterMissedException();
            }
            if(!apiKeySys.equals(apiKeyParam)){
                throw new ApiSignKeyNotEqulesException();
            }
            if (!apiSign.equals(SignUtil.getSign(params,apiSecret))) {
                LOG.error(" api sign error, apiKey={0}, apiSign={1} ", apiKeyParam, apiSign);
                throw new ApiSignErrorException(); 
            }
    }

}
  
