/**  
 * Project Name:app-api  <br/>
 * File Name:RoomController.java  <br/>
 * Package Name:com.zizaike.app.api.controller.search  <br/>
 * Date:2015年11月23日下午4:50:13  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.app.api.controller.search;  

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
import com.zizaike.entity.solr.SearchType;
import com.zizaike.entity.solr.SearchWordsVo;
import com.zizaike.is.solr.RoomSolrService;

/**  
 * ClassName:RoomController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2015年11月23日 下午4:50:13 <br/>  
 * @author   alex  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Controller
@RequestMapping("/search/room")
public class RoomController extends BaseAjaxController {
    @Autowired
    private RoomSolrService roomSolrService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    @SignValid(ingore = {})
    public ResponseResult getSearchResult(@RequestParam("keyWords") String keyWords ,@RequestParam("destId") String destId,@RequestParam("searchid") String searchid,@RequestParam("checkInDate") String checkInDate,
            @RequestParam("checkOutDate") String checkOutDate,@RequestParam("searchType") SearchType searchType,@RequestParam("page") String page,@RequestParam("price") String price,@RequestParam("service") String service,
            @RequestParam("roomModel") String roomModel,@RequestParam("order") String order,@RequestParam("apiSign") String apiSign,@RequestParam("apiKey") String apiKey) throws ZZKServiceException, UnsupportedEncodingException {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(destId);
        keyWords = new String(keyWords.getBytes("ISO-8859-1"),"UTF-8");
        if (!isNum.matches()) {
            throw new IllegalParamterException("destId type error");
        }
        Matcher isNum2 = pattern.matcher(searchid);
        if (!isNum2.matches()) {
            throw new IllegalParamterException("searchid type error");
        }
        Matcher isNum3 = pattern.matcher(page);
        if (!isNum3.matches()) {
            throw new IllegalParamterException("page type error");
        }
        Matcher isNum4 = pattern.matcher(roomModel);
        if (!isNum4.matches()) {
            throw new IllegalParamterException("roomModel type error");
        }
        Matcher isNum5 = pattern.matcher(order);
        if (!isNum5.matches()) {
            throw new IllegalParamterException("order type error");
        }
        SearchWordsVo searchWordsVo=new SearchWordsVo();
        searchWordsVo.setCheckInDate(checkInDate);
        searchWordsVo.setCheckOutDate(checkOutDate);
        if(destId!=null&&destId!=""){
            searchWordsVo.setDestId(Integer.parseInt(destId));
        }
        if(keyWords!=null&&keyWords!=""){
            searchWordsVo.setKeyWords(keyWords);
        }
        searchWordsVo.setPage(Integer.parseInt(page));
        searchWordsVo.setSearchid(Integer.parseInt(searchid));
        searchWordsVo.setSearchType(searchType);
        searchWordsVo.setRoomModel(Integer.parseInt(roomModel));
        searchWordsVo.setService(service);
        searchWordsVo.setPrice(price);
        searchWordsVo.setOrder(Integer.parseInt(order));
        ResponseResult result = new ResponseResult();
        result.setInfo(roomSolrService.searchSolr(searchWordsVo));
        return result;
    }
}
  
