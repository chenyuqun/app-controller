/**  
 * Project Name:solr-service  <br/>
 * File Name:PlaceTest.java  <br/>
 * Package Name:com.zizaike.solr.user  <br/>
 * Date:2015年11月4日上午10:56:40  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.app.api.service;  

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.zizaike.app.api.basettest.BaseTest;
import com.zizaike.core.framework.exception.ZZKServiceException;

/**  
 * ClassName: PlaceTest <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2015年11月4日 上午10:56:40 <br/>  
 *  
 * @author snow.zhang 
 * @version   
 * @since JDK 1.7  
 */
public class SignServiceImplTest extends BaseTest{

    @Autowired
    SignService signService;
    @Test
    public void signVerification() throws ZZKServiceException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("service", "order.updateOrderStatus");
        map.put("field", "id");
        map.put("user_token", "32423424");
        map.put("apiKey", "6cd0f0bd288704f76711ff404f82c06c");
        map.put("apiSign", "14489ef86f8eba28cf04ed8060e9e7a6");
       signService.signVerification(map);
    }
    
}
  
