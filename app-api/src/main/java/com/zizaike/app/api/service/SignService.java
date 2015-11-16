/**  
 * Project Name:app-api  <br/>
 * File Name:SignService.java  <br/>
 * Package Name:com.zizaike.app.api.service  <br/>
 * Date:2015年11月16日上午11:04:26  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.app.api.service;  

import java.util.Map;

import com.zizaike.core.framework.exception.ZZKServiceException;

/**  
 * ClassName:SignService <br/>  
 * Function: 签名服务. <br/>  
 * Date:     2015年11月16日 上午11:04:26 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface SignService {
    /**
     * 
     * signVerification:签名校验. <br/>  
     *  
     * @author snow.zhang  
     * @param params  
     * @since JDK 1.7
     */
     void signVerification(Map params)  throws ZZKServiceException;
}
  
