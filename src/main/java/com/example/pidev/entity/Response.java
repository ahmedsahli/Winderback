package com.example.pidev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="idResponse")  
	private Long idResponse; 

	//@NotBlank(message="The description must be written")
	//@Size(max=4000 , message="The total number of characters cannot be exceeded")
	private String description;
	
	@ManyToOne
	@JsonIgnore
	Reclamation reclamation;
	
	
}
