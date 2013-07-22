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
	
	@RequestMapping(value={"/{userId}/pre"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public List<NewsGrowthLogDTO> getPreGrowthLog(@PathVariable("userId") Long userId,@RequestParam(value="bottomNewsId", defaultValue="5", required=false) long bottomNewsId, @RequestParam(value="count", defaultValue="5", required=false) int count){
		return growLogService.getPreGrowthLog(userId,bottomNewsId,count);
	}
	
	@RequestMapping(value={"/{userId}/allNewGrowlog"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public List<NewsGrowthLogDTO> allNewGrowlog(@PathVariable("userId") Long userId,@RequestParam(value="topNewsId", defaultValue="5", required=false) long topNewsId){
		return growLogService.allNewGrowlog(userId,topNewsId);
	}
	
	@RequestMapping(value={"/{userId}/topNews"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public List<NewsGrowthLogDTO> getNewsGrowthLogByDownRefresh(@PathVariable("userId") Long userId,
								@RequestParam(value="topNewsId", defaultValue="5", required=false) long topNewsId, 
								@RequestParam(value="count", defaultValue="5", required=false) int count){
		return growLogService.getNewsGrowthLogByDownRefresh(userId,topNewsId,count);
	}
}
