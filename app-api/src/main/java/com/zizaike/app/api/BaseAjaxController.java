package com.zizaike.app.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.IErrorCode;
import com.zizaike.core.framework.exception.ZZKServiceException;

/**
 * 
 * ClassName: BaseAjaxController <br/>  
 * Function: 捕获业务异常. <br/>  
 * Reason: 捕获业务异常. <br/>  
 * date: 2015年10月28日 下午12:00:00 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public abstract class BaseAjaxController {

    protected  Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    @ResponseBody
    public  void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        String callback = request.getParameter("callback");

        ResponseResult resultBean = new ResponseResult();

        if (ex instanceof ZZKServiceException) {
            ZZKServiceException ue = (ZZKServiceException) ex;
            IErrorCode iErrorCode = ((ZZKServiceException) ex).getErrorCode();
            resultBean.setCode(iErrorCode.getErrorCode());
            resultBean.setMessage(iErrorCode.getErrorMsg());
            if(ue.getDescription() != null){
                resultBean.setMessage(ue.getDescription());
            }else{
                resultBean.setMessage(iErrorCode.getErrorMsg());
            }
            log.error("ZZKServiceException errorCode={},errorMsg={}", iErrorCode.getErrorCode(),iErrorCode.getErrorMsg());
        } else {
            log.error("Exception:", ex);
            log.error("system error ", ex.getCause());
            resultBean.setCode("500");
            resultBean.setMessage(ex.getMessage());
        }

        try {
            response.getWriter().write(jsonpBuilder(callback, resultBean));
        } catch (IOException e) {
            log.error("IOException ", e);
        }
    }

    public String jsonpBuilder(String callback, ResponseResult resultBean) {
        if (StringUtils.isEmpty(callback)) {
            return JSON.toJSONString(resultBean);
        }
        StringBuilder sb = new StringBuilder();
        return sb.append(callback).append("(").append(JSON.toJSONString(resultBean)).append(")").toString();
    }

}
