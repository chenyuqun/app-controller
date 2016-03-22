package com.zizaike.app.api.controller.search;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.app.api.BaseAjaxController;
import com.zizaike.app.api.service.SignService;
import com.zizaike.app.api.sign.SignValid;
import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.redis.HotRecommendRedisService;
import com.zizaike.is.redis.RecommendAreaRedisService;

@Controller
@RequestMapping("/search/recommend")
public class RecommendController extends BaseAjaxController {
    @Autowired
    private HotRecommendRedisService hotRecommendRedisService;
    @Autowired
    private SignService signService;
    @Autowired
    private RecommendAreaRedisService recommendAreaRedisService;

    @RequestMapping(value = "hot", method = RequestMethod.GET)
    @ResponseBody
    @SignValid(ingore={"request"})
    public ResponseResult getHotRecommend(HttpServletRequest request ,@RequestParam("apiSign") String apiSign,@RequestParam("apiKey") String apiKey ,@RequestParam("multilang") Integer multilang) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        result.setInfo(hotRecommendRedisService.qury());
        return result;
    }
    
    
    
    /**
     * 
     * getLocAndHotRecommend:地址与热推. <br/>  
     * @author snow.zhang  
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @RequestMapping(value = "area_recommend", method = RequestMethod.GET)
    @ResponseBody
    @SignValid
    public ResponseResult getLocAndHotRecommend(@RequestParam("apiSign") String apiSign,@RequestParam("apiKey") String apiKey,@RequestParam("multilang") Integer multilang) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        result.setInfo(recommendAreaRedisService.query());
        return result;
    }

    /**
     *
     * getLocAndHotRecommendByDest:根据目的地获得地址与热推. <br/>
     * @author alex
     * @return
     * @throws ZZKServiceException
     * @since JDK 1.7
     */
    @RequestMapping(value = "area_recommend_bydest", method = RequestMethod.GET)
    @ResponseBody
    @SignValid
    public ResponseResult getLocAndHotRecommendByDest(@RequestParam("apiSign") String apiSign,@RequestParam("apiKey") String apiKey,@RequestParam("multilang") Integer multilang,@RequestParam("destId") Integer destId) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        result.setInfo(recommendAreaRedisService.queryByDest(destId));
        return result;
    }
}