package com.zizaike.app.api.controller.search;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.app.api.BaseAjaxController;
import com.zizaike.app.api.service.SignService;
import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.redis.HotRecommendRedisService;

@Controller
@RequestMapping("/search/recommend")
public class HotRecommendController extends BaseAjaxController {
    @Autowired
    private HotRecommendRedisService hotRecommendRedisService;
    @Autowired
    private SignService signService;

    @RequestMapping(value = "hot", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getHotRecommend(@RequestParam("apiSign") String apiSign,@RequestParam("apiKey") String apiKey) throws ZZKServiceException {
        Map map = new HashMap();
        map.put("apiSign", apiSign);
        map.put("apiKey", apiKey);
        signService.signVerification(map);
        ResponseResult result = new ResponseResult();
        result.setInfo(hotRecommendRedisService.qury());
        return result;
    }
}