package application.controllers;

import application.Application;
import application.services.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/kafka")
public class WebRestController {
	
	@Autowired
    KafkaProducer producer;
	
	@GetMapping(value="/producer")
	public String producer(@RequestParam("data")String data) {
		producer.send(data);
		return "Done";
	}
	
	@GetMapping(value="/consumer")
	public String getAllRecievedMessage() {
		return "a = " + Application.a;
	}
}
