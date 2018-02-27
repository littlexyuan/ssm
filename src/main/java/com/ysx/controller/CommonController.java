package com.ysx.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ysx.entity.Letter;
import com.ysx.service.LetterService;

@Controller
public class CommonController {

	@Autowired
	private LetterService letterService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("redirect");
	}

	@RequestMapping(value = "/pc", method = RequestMethod.GET)
	public ModelAndView pcIndex() {
		return new ModelAndView("pc/index");
	}

	@RequestMapping(value = "/mobile", method = RequestMethod.GET)
	public ModelAndView mobileIndex() {
		long n = letterService.count();
		int i = (int) (Math.random() * n);
		List<Letter> letters = letterService.findAll();
		return new ModelAndView("mobile/index", "letter", letters.get(i));
	}

	@RequestMapping(value = "/mobile/all", method = RequestMethod.GET)
	public ModelAndView mobileAll() {
		List<Letter> letter = letterService.findAll(new Sort("id"));
		return new ModelAndView("mobile/list", "letters", letter);
	}

	@RequestMapping(value = "/mobile/info", method = RequestMethod.GET)
	public ModelAndView mobileInfo() {
		return new ModelAndView("mobile/info");
	}

	@RequestMapping(value = "/mobile/add", method = RequestMethod.GET)
	public ModelAndView mobileAdd(Integer id) {
		Letter letter = new Letter();
		if (id != null) {
			letter = letterService.findOne(id);
			String content = letter.getContent().replaceAll("<p .*?>", "\r\n").replaceAll("\\<.*?>", "");
			letter.setContent(content);
		}
		return new ModelAndView("mobile/add", "letter", letter);
	}

	@ResponseBody
	@RequestMapping(value = "/mobile/insert", method = RequestMethod.POST)
	public Integer mobileInsert(HttpServletRequest request, String content, Integer id,
			@RequestParam("pic") MultipartFile pic, String music) throws IllegalStateException, IOException {
		Letter newLetter=letterService.add(content, id, pic, music);
		return newLetter.getId();
	}

	@RequestMapping(value = "/mobile/dal", method = RequestMethod.GET)
	public ModelAndView mobileSuccess(Integer id) {
		Letter letters = letterService.findOne(id);
		return new ModelAndView("mobile/index", "letter", letters);
	}

}
