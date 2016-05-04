/**  
 * Project Name:zizaike-core  <br/>
 * File Name:SignAdivce.java  <br/>
 * Package Name:com.zizaike.core.framework.sign  <br/>
 * Date:2015年11月16日下午4:54:27  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.app.api.sign;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.zizaike.app.api.service.SignService;
import com.zizaike.core.common.PropertyConfigurer;

/**
 * ClassName:SignAdivce <br/>
 * Function: 签到切面. <br/>
 * Date: 2015年11月16日 下午4:54:27 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Component
@Aspect
public class SignAdivce {
    @Autowired
    SignService signService;
    @Autowired
    PropertyConfigurer propertyConfigurge;
    private static final String VERIFICATION_SIGN = "verificationSign";
    private static final String IGNORE_PARAM = "ignoreParam";

    @Pointcut("execution(public * com.zizaike.app.api.controller..*.*.*(..))")
    public void signValit() {
    }

    /**
     * 
     * before:before 验签. <br/>
     * 
     * @author snow.zhang
     * @param joinPoint
     * @throws Throwable
     * @since JDK 1.7
     */
    @Before("signValit()")
    public void before(JoinPoint joinPoint) throws Throwable {
        String verificationSign = propertyConfigurge.getProperty(VERIFICATION_SIGN);
        if (!verificationSign.equals("true")) {
            return;
        }
        String ignoreParam = propertyConfigurge.getProperty(IGNORE_PARAM);
        String[] ingoreProperties = null;
        if (ignoreParam != null) {
            ingoreProperties = ignoreParam.split(",");
        }
        Object[] args = joinPoint.getArgs();
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
        String[] ingore = method.getAnnotation(SignValid.class).ingore();
        List<String> ingoreList = Arrays.asList(ingoreProperties);
        List<String> ingoreListRequest =  Arrays.asList(ingore);
        //忽略默认值
        if(!(ingoreListRequest.size()==1 && ingoreListRequest.get(0).equals(""))){
            ingoreList.addAll(ingoreListRequest);
        }
        // 得到输入参数列表
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] params = u.getParameterNames(method);
        Map map = new HashMap();
        for (int i = 0; i < params.length; i++) {
            if (!ingoreList.contains(params[i])) {
                map.put(params[i], args[i]==null ? null:args[i].toString());
            }
        }
        signService.signVerification(map);
    }
}
