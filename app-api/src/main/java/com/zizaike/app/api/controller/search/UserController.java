package com.zizaike.app.api.controller.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.app.api.BaseAjaxController;
import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.solr.UserSolrService;

@Controller
@RequestMapping("/search/user")
public class UserController extends BaseAjaxController {
    @Autowired
    private UserSolrService userSolrService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getAllAddress(@PathVariable String id) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(id);
        if (!isNum.matches()) {
            throw new IllegalParamterException("id type error");
        }
        result.setInfo(userSolrService.queryUserById(Integer.parseInt(id)));
        return result;
    }
}