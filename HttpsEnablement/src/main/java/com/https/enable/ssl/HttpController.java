package com.https.enable.ssl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {

	@GetMapping("/https/enabled/hello")
	public String hello() {
		return "Hello world!!!";
	}
}
