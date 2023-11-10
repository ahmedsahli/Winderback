package com.example.pidev.Controller;

import com.example.pidev.Service.ReclamationService;
import com.example.pidev.Service.UserServiceImpl;
import com.example.pidev.entity.Reclamation;
import com.example.pidev.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reclamation")
public class ReclamationController {
@Autowired
ReclamationService reclamationService;

@Autowired
	UserServiceImpl userService;


@PostMapping("/add-reclamation/{userName}")
@ResponseBody
public void addReclamation(@RequestBody Reclamation r, @PathVariable("userName") String userName)

{

	User user = userService.GetUserByUsername(userName);

	  r.setUser(user);
	  reclamationService.ajouterReclamation(r);

}


@GetMapping("/listReclamations")
@ResponseBody
public List<Reclamation> listReclamations(){
	return reclamationService.ListReclamations();
}

@DeleteMapping("/deleteReclamation/{idReclamation}")
@ResponseBody
public void deleteReclamation(@PathVariable("idReclamation") Long idReclamation){
	 reclamationService.supprimerReclamation(idReclamation);
}
@PutMapping("/modifierReclamation/{idReclamation}")
@ResponseBody
public void modifierReclamation(@RequestBody Reclamation r,@PathVariable("idReclamation") Long idReclamation){
	 reclamationService.updateReclamation(r,idReclamation);
}

@GetMapping("/getReclamation/{idReclamation}")
@ResponseBody
public Reclamation getReclamationByiD(@PathVariable("idReclamation") Long idReclamation){
	return reclamationService.getReclamationById(idReclamation);
}

@GetMapping("/getReclamationByDate/{datedate}")
@ResponseBody
public List<Reclamation> listerReclamationbydate(@PathVariable("datedate") Date datedate){
	return reclamationService.listerReclamationParDateDonnéé(datedate);
}
@GetMapping("/ReclamationAujourdhui")
@ResponseBody
public List<Reclamation> ReclamationAujourdhui(){
	return reclamationService.reclamationAujourdhui();
}


@GetMapping("/nombresReclamationAujourdhui")
@ResponseBody
public int nombresReclamationAujourdhui(){
	return reclamationService.nbrReclamationAujourdhui();
}

	@GetMapping("/getreclamationparuser/{userName}")
	public List<Reclamation> getReclamationsByUser(@PathVariable String userName) {

System.out.println("user name : "+userName);
		return reclamationService.getReclamationsByUser(userName);
	}
/*	@GetMapping("/{id}/responseTime")
	public long getResponseTime(@PathVariable Long id) {
		return reclamationService.getResponseTime(id);
	}*/


}
