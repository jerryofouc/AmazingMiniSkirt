package com.netease.amazing.server.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.amazing.sdk.dto.NewsGrowthLogDTO;
import com.netease.amazing.server.service.GrowthLogService;

@Controller
@RequestMapping(value = "/api/growthlog")
public class GrowLogRestController {
	@Autowired 
	private GrowthLogService growLogService;
	@RequestMapping(value={"/{userId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public List<NewsGrowthLogDTO> getAllGrowthLog(@PathVariable("userId") Long userId, @RequestParam(value="count", defaultValue="5", required=false) int count){
		return growLogService.getGrowthLog(userId,count);
	}
}
