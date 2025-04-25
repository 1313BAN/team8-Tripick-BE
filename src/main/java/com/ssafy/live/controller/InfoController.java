package com.ssafy.live.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/attraction")
public class InfoController {

	@GetMapping("search")
	public String infoView() {
		return "travel-information";
	}
	
}
