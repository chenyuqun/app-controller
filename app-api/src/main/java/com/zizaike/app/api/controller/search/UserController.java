package com.zizaike.app.api.controller.search;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.app.api.BaseAjaxController;
import com.zizaike.app.api.service.SignService;
import com.zizaike.app.api.sign.SignValid;
import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.solr.UserSolrService;

@Controller
@RequestMapping("/search/user")
public class UserController extends BaseAjaxController {
    @Autowired
    private UserSolrService userSolrService;
    @Autowired
    private SignService signService;
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @SignValid(ingore = {"id"})
    public ResponseResult getAllAddress(@PathVariable String id,@RequestParam("apiSign") String apiSign,@RequestParam("apiKey") String apiKey ,@RequestParam("multilang") Integer multilang) throws ZZKServiceException {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(id);
        if (!isNum.matches()) {
            throw new IllegalParamterException("id type error");
        }
        Map map = new HashMap();
        map.put("apiSign", apiSign);
        map.put("apiKey", apiKey);
        signService.signVerification(map);
        ResponseResult result = new ResponseResult();
        result.setInfo(userSolrService.queryUserById(Integer.parseInt(id)));
        return result;
    }
}