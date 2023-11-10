package com.example.pidev.Controller;

import com.example.pidev.Service.ResponseService;
import com.example.pidev.entity.Response;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/response")

public class ResponseController {
	@Autowired
	ResponseService responseService;

	
	@GetMapping("/listResponses")
	@ResponseBody
	public List<Response> listResponses(){
		return responseService.getAllResponses();
	}
	
	
	@PostMapping("/add-response/{idReclamation}")
	@ResponseBody
	public void addResponse(@RequestBody Response r,@PathVariable("idReclamation") Long idReclamation)
	{
		  responseService.addResponse(r,idReclamation);
		  System.out.println(r.getReclamation().getUser().getPhoneNumber());
		  sendsms("+216"+r.getReclamation().getUser().getPhoneNumber());
		  

	}
	public void sendsms(String str) {
		System.out.println(str+"number");
		Twilio.init("ACbc4373fd2cc5375dec9807665d085c2b", "1901138ff2aefe44943750f0919ed39e");
		try {
			com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message
					.creator(new PhoneNumber(str), // To number
							new PhoneNumber("+16813219835"), // From number
							"Cher Client,Votre réclamation a été traité")
					.create();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	@DeleteMapping("/deleteResponse/{idResponse}")
	@ResponseBody
	public void deleteReclamation(@PathVariable("idResponse") Long idResponse){
		responseService.supprimerResponse(idResponse);
	}

	
	@PutMapping("/modifierResponse/{idResponse}")
	@ResponseBody
	public void modifierResponse(@RequestBody Response r,@PathVariable("idResponse") Long idResponse){
		responseService.updateResponse(r,idResponse);
	}
	
	@PutMapping("/getResponsesByReclamation/{idReclamation}")
	@ResponseBody
	public List<Response> getResponsesByReclamation(@PathVariable("idReclamation") Long idReclamation){
		 return responseService.getResponsesByReclamation(idReclamation);
	}
	

}
