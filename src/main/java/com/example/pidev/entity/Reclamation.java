package com.example.pidev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reclamation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="idRec")  
	private Long idRec;


	//@NotBlank(message="The content must be written")
	//@Size(max=4000 , message="The total number of characters cannot be exceeded")
	private String contenuRec;
	
	//@Enumerated(EnumType.STRING)
	private String type;
	

	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private Date sendingDate;
	

	private String etat;
	
	@ManyToOne
	@JsonIgnore
    private User user;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reclamation")
	private List<Response> responses;
	
	
	
	
	
	

}
