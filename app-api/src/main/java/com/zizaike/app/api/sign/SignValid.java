/**  
 * Project Name:zizaike-core  <br/>
 * File Name:SignValid.java  <br/>
 * Package Name:com.zizaike.core.framework.sign  <br/>
 * Date:2015年11月16日下午4:25:38  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.app.api.sign;  

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
 * ClassName:SignValid <br/>  
 * Function: 签名校验 <br/>  
 * Date:     2015年11月16日 下午4:25:38 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignValid {
    /**
     * 
     * ingore:不加入校验的参数. <br/>  
     *  
     * @author snow.zhang
     * @return  
     * @since JDK 1.7
     */
    String[] ingore();
}
  
