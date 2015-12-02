/**  
 * Project Name:zizaike-core  <br/>
 * File Name:SignAdivce.java  <br/>
 * Package Name:com.zizaike.core.framework.sign  <br/>
 * Date:2015年11月16日下午4:54:27  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.app.api.translate;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.bean.ResponseResult;
import com.zizaike.entity.recommend.CountryType;
import com.zizaike.entity.recommend.vo.CountryArea;
import com.zizaike.is.common.HanLPService;

/**
 * ClassName:SignAdivce <br/>
 * Function: 翻译服务. <br/>
 * Date: 2015年11月16日 下午4:54:27 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Component
@Aspect
public class TranslateAdivce {
    public static final String TRANSLATE_SERVICE_EXCUTION = "execution(public * com.zizaike.app.api.controller..*.*.*(..))";
    @Autowired
    HanLPService hanLPService;

    @Pointcut(TRANSLATE_SERVICE_EXCUTION)
    public void ranslate() {
    }

    /**
     * 
     * before:before 国际化. <br/>
     * 
     * @author snow.zhang
     * @param joinPoint
     * @throws Throwable
     * @since JDK 1.7
     */
    @AfterReturning(value = TRANSLATE_SERVICE_EXCUTION, argNames = "obj", returning = "obj")
    public void afterReturning(JoinPoint joinPoint, Object obj) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
        // 得到输入参数列表
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] params = u.getParameterNames(method);
        for (int i = 0; i < params.length; i++) {
            if (params[i].equals("multilang")) {
                if(obj instanceof ResponseResult){
                    Object info =  ((ResponseResult) obj).getInfo();
                     ((ResponseResult) obj).setInfo(translate(info,(Integer) args[i]));
                 }
                break;
            }
        }
    }


    public Object translate(Object obj,Integer multilang) {
        String str = JSON.toJSONString(obj);
        String returnStr = null;
        if(multilang == CountryType.TWD.getValue()){
            returnStr = hanLPService.convertToTraditionalChinese((String) str);
        }else if(multilang == CountryType.CNY.getValue()){
            returnStr = hanLPService.convertToSimplifiedChinese((String) str);
        }
        Object object = null;
        try{
            object =   JSON.parseObject(returnStr);
        }catch(Exception e){
            object =   JSON.parseArray(returnStr);
        }

        return object;
    }
    /**
     * public void translateTest(Object obj) throws Exception { if(obj==null){ return ; } Class<? extends Object> clazz
     * = obj.getClass();// 类 System.err.println("clazz.getSuperclass()>>>>>>>>>:"+clazz.getSuperclass()); if
     * (clazz.getSuperclass().equals(AbstractList.class)) {//list的处理 List list = (List) obj; for (Object object : list)
     * { translate(object); } } for (Field field : clazz.getDeclaredFields()) { field.setAccessible(true);
     * if(field.getType().equals(List.class)){ translate(field.get(obj)); } Object val = field.get(obj);
     * System.err.println("field.getType().getName()"+field.getType().getName()); if
     * (field.getAnnotation(Translate.class) != null && field.getType() == String.class) { System.err.println("name:" +
     * field.getName() + "\t value = " + val + "\t annotation:" + field.getAnnotation(Translate.class)); field.set(obj,
     * hanLPService.convertToTraditionalChinese((String) val)); } else if (field.toString().contains("com.zizaike")) {
     * translate(val);//递归遍历所有的数据 } } System.err.println(obj); }
     **/
}
