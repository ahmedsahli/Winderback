package com.example.pidev.Service;

import com.example.pidev.Repository.DictionnaireRepository;
import com.example.pidev.Repository.ReclamationRepository;
import com.example.pidev.entity.DictionnaireBadWords;
import com.example.pidev.entity.Reclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReclamationService {
@Autowired
ReclamationRepository reclamationRepository;
@Autowired
DictionnaireRepository badwordsRepo;

@Autowired
PasswordEncoder passwordencoder;


public void ajouterReclamation(Reclamation r) {
	List<String> badwords1 = new ArrayList<String>();
	List<DictionnaireBadWords> badwords = badwordsRepo.findAll();
	for (DictionnaireBadWords bd : badwords) {
		badwords1.add(bd.getWord());
	}
	if (verif(r) == 1) {
	r.setEtat("non traitée");
	 reclamationRepository.save(r);
	}
	else if(verif(r) == 0) {
		String encodedPass = passwordencoder.encode(r.getContenuRec());

		r.setContenuRec(encodedPass);

		r.setEtat("non traitée");
		 reclamationRepository.save(r);

	}

	
}
public int verif(Reclamation c) {

	for (DictionnaireBadWords d : badwordsRepo.findAll()) {

		if (c.getContenuRec().toLowerCase().contains(d.getWord().toLowerCase()) || c.getContenuRec() == null
				|| c.getContenuRec().length() == 0) {
			return 0;
		} else {
			return 1;
		}

	}
	return 2;

}
public List<Reclamation> ListReclamations(){
	return reclamationRepository.findAll();
}


public void supprimerReclamation(Long idReclamation) {
	Reclamation r = reclamationRepository.findById(idReclamation).orElse(null);
	
	reclamationRepository.delete(r);
}
	
public void updateReclamation(Reclamation reclamation, Long idReclamation) {
	
	Reclamation rec= reclamationRepository.findById(idReclamation).orElse(null);	
	rec.setEtat("non traitée");
	rec.setIdRec(idReclamation);
	rec.setContenuRec(reclamation.getContenuRec());
	rec.setType(reclamation.getType());
	rec.setSendingDate(reclamation.getSendingDate());
	reclamationRepository.save(rec);
	
}	


public Reclamation getReclamationById(Long idReclamation) {
	return reclamationRepository.findById(idReclamation).orElse(null);	
}


public List<Reclamation> reclamationAujourdhui(){
	Date currentSqlDate = new Date(System.currentTimeMillis());
	List<Reclamation> listrec = reclamationRepository.findAll();
	List<Reclamation> listrec2 = new ArrayList<>();

	for (Reclamation r : listrec) {

		if(r.getSendingDate().getYear()==(currentSqlDate.getYear())&& r.getSendingDate().getMonth()==(currentSqlDate.getMonth())
				&&r.getSendingDate().getDay()==(currentSqlDate.getDay()) ){
			listrec2.add(r);
		}
	}
	return listrec2;
	
}
public int nbrReclamationAujourdhui(){
	Date currentSqlDate = new Date(System.currentTimeMillis());
	List<Reclamation> listrec = reclamationRepository.findAll();
	List<Reclamation> listrec2 = new ArrayList<>();
int nombre = 0;
	for (Reclamation r : listrec) {

		if(r.getSendingDate().getYear()==(currentSqlDate.getYear())&& r.getSendingDate().getMonth()==(currentSqlDate.getMonth())
				&&r.getSendingDate().getDay()==(currentSqlDate.getDay()) ){
			nombre++;
		}
	}
	return nombre;
	
}





public List<Reclamation> listerReclamationParDateDonnéé(Date d1) {

	List<Reclamation>  l1 = new ArrayList<>();
	List<Reclamation>  listreclamation = reclamationRepository.findAll();
	for (Reclamation r : listreclamation ) {
		if (r.getSendingDate().equals(d1.getDate())) {
			l1.add(r);
		}
	}
return listreclamation;
}

	public List<Reclamation> getReclamationsByUser(String userName) {

	System.out.println("username"+userName);
		return reclamationRepository.getReclamationByUsername(userName);
	}
	/*public long getResponseTime(Long reclamationId) {
		Reclamation r = reclamationRepository.findById(reclamationId).orElseThrow(() -> new IllegalArgumentException("Invalid visit request id"));
		LocalDateTime requestTime = r.getSendingDate();
		LocalDateTime now = LocalDateTime.now();
		//return ChronoUnit.MINUTES.between(requestTime, now);
		return ChronoUnit.DAYS.between(requestTime, now);


	}*/
}
