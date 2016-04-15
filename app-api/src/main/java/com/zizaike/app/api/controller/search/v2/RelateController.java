/**  
 * Project Name:commodity-view  <br/>
 * File Name:PlaceController.java  <br/>
 * Package Name:com.zizaike.commodity.view.controller.place  <br/>
 * Date:2015年11月5日下午4:35:49  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
 */
package com.zizaike.app.api.controller.search.v2;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.app.api.BaseAjaxController;
import com.zizaike.app.api.sign.SignValid;
import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.solr.PlaceSolrService;

/**
 * ClassName:PlaceController <br/>
 * Function: 地址关联查询. <br/>
 * Reason: 地址关联查询. <br/>
 * Date: 2015年11月5日 下午4:35:49 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */

@Controller("v2-RalateController")
@RequestMapping("/search/v2/relate")
public class RelateController extends BaseAjaxController {
    @Autowired
    private PlaceSolrService placeSolrService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    @SignValid(ingore = {})
    public ResponseResult getAllAddress(@RequestParam("words") String words ,@RequestParam("destId") String destId,@RequestParam("locid") String locid,@RequestParam("apiSign") String apiSign,@RequestParam("apiKey") String apiKey ,@RequestParam("multilang") Integer multilang) throws ZZKServiceException, UnsupportedEncodingException {
       
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(destId);
        if (!isNum.matches()) {
            throw new IllegalParamterException("destId type error");
        }
        Matcher isNum2 = pattern.matcher(locid);
        if (!isNum2.matches()) {
            throw new IllegalParamterException("locid type error");
        }
        ResponseResult result = new ResponseResult();
        result.setInfo(placeSolrService.queryPlaceByWordsAndLocCodition(words, Integer.parseInt(destId),
                Integer.parseInt(locid)));
        return result;
    }
}
