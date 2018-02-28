package com.ysx.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ysx.entity.Letter;

@Service
public class LetterService {
	
	@Autowired
	private LetterRepository letterRepository;
	
	@Value("${fileUpload}")
	private String resource;
	
	public Letter findOne(Integer id){
		return letterRepository.findOne(id);
	}
	
	public List<Letter> findAll(){
		return letterRepository.findAll();
	}
	
	public List<Letter> findAll(Sort sort){
		return letterRepository.findAll(sort);
	}
	
	public Letter save(Letter letter){
		return letterRepository.save(letter);
	}
	
	public long count(){
		return letterRepository.count();
	}
	
	public Letter add(String content, Integer id, MultipartFile pic, String music) throws IllegalStateException, IOException{
		
		String[] contents = content.split("\n");
		String p = "";
		for (int i = 0; i < contents.length; i++) {
			p += "<p class=\"ani\" swiper-animate-effect=\"bounceInRight\" swiper-animate-duration=\"1s\" swiper-animate-delay=\"0."
					+ (3 + i) + "s\">" + contents[i] + "</p>";
		}
		Letter letter = new Letter();
		if (id != null) {
			letter.setId(id);
		}
		letter.setContent(p);
		letter.setIsDelete(false);
		letter.setTime(new Date());
		if(!"".equals(music)){
			letter.setMusic(music);
		}
		if(!"".equals(pic.getOriginalFilename())){
			// 如果上传目录为/static/images/upload/，则可以如下获取：
			File upload = new File(resource);
			if (!upload.exists()) {
				upload.mkdirs();
			}

			String filename = UUID.randomUUID().toString();
			String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
			String filePath = upload.getAbsolutePath() + "\\" + filename + "." + ext;
			// 转存文件
			filePath = filePath.replaceAll("\\\\+", "/");// 把反斜杠\替换成正斜杠/
			pic.transferTo(new File(filePath));
			letter.setPic(filename + "." + ext);
		}
		return save(letter);
	}
}
