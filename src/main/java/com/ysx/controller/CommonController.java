package com.ysx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		System.out.println(i);
		return new ModelAndView("mobile/index","letter",letters.get(i));
	}
	
	@RequestMapping(value = "/mobile/all",method = RequestMethod.GET)
	public ModelAndView mobileAll(){
		List<Letter> letter=letterRepository.findAll();
		return new ModelAndView("mobile/list","letters",letter);
	}
	
	@RequestMapping(value = "/mobile/info",method = RequestMethod.GET)
	public ModelAndView mobileInfo(){
		return new ModelAndView("mobile/info");
	}
}

