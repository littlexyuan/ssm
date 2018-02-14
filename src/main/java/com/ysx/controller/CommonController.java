package com.ysx.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ysx.entity.Letter;
import com.ysx.service.LetterRepository;


@Controller
public class CommonController {
	
	@Autowired
	private LetterRepository letterRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(){
		return new ModelAndView("redirect");
	}
	
	@RequestMapping(value = "/pc",method = RequestMethod.GET)
	public ModelAndView pcIndex(){
		return new ModelAndView("pc/index");
	}
	
	@RequestMapping(value = "/mobile",method = RequestMethod.GET)
	public ModelAndView mobileIndex(){
		long n=letterRepository.count();
		int i=(int)(Math.random()*n);
		List<Letter> letters=letterRepository.findAll();
		return new ModelAndView("mobile/index","letter",letters.get(i));
	}
	
	@RequestMapping(value = "/mobile/all",method = RequestMethod.GET)
	public ModelAndView mobileAll(){
		List<Letter> letter=letterRepository.findAll(new Sort("id"));
		return new ModelAndView("mobile/list","letters",letter);
	}
	
	@RequestMapping(value = "/mobile/info",method = RequestMethod.GET)
	public ModelAndView mobileInfo(){
		return new ModelAndView("mobile/info");
	}
	
	@RequestMapping(value = "/mobile/add",method = RequestMethod.GET)
	public ModelAndView mobileAdd(Integer id){
		Letter letter=new Letter();
		if(id!=null){
			letter=letterRepository.findOne(id);
			String content = letter.getContent().replaceAll("<p .*?>", "\r\n").replaceAll("\\<.*?>", "");
			letter.setContent(content);
		}
		return new ModelAndView("mobile/add","letter",letter);
	}
	
	@ResponseBody
	@RequestMapping(value = "/mobile/insert",method = RequestMethod.POST)
	public Integer mobileInsert(String content, Integer id){
		String[] contents=content.split("\n");
		String p="";
		for(int i=0;i<contents.length;i++){
			p+="<p class=\"ani\" swiper-animate-effect=\"bounceInRight\" swiper-animate-duration=\"1s\" swiper-animate-delay=\"0."+(3+i)+"s\">"+contents[i]+"</p>";
		}
		Letter letter=new Letter();
		if(id!=null){
			letter.setId(id);
		}
		letter.setContent(p);
		letter.setIsDelete(false);
		letter.setTime(new Date());
		Letter newLetter=letterRepository.save(letter);
		return newLetter.getId();
	}
	
	@RequestMapping(value = "/mobile/dal",method = RequestMethod.GET)
	public ModelAndView mobileSuccess(Integer id){
		Letter letters=letterRepository.findOne(id);
		return new ModelAndView("mobile/index","letter",letters);
	}
	
}

