package com.cesarschool.project.emailsender.spring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cesarschool.project.emailsender.spring.dto.request.CustomMailRequestDTO;
import com.cesarschool.project.emailsender.spring.dto.response.GenericResponseDTO;
import com.cesarschool.project.emailsender.spring.services.EmailServices;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/email")
public class EmailController {
	private final EmailServices service;

	@PostMapping(value="/{id}/org={organization}")
	public ResponseEntity<GenericResponseDTO> sendMessageByOrganization(@PathVariable(value="id") String idMessage,
			@PathVariable(value="organization") String organization) {
		return new ResponseEntity<GenericResponseDTO>(service.sendMessageByOrganization(idMessage, organization),
				HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<GenericResponseDTO> sendCustomMessageByEmail(@RequestBody @Valid CustomMailRequestDTO request) {
		return new ResponseEntity<GenericResponseDTO>(service.sendCustomMessageByEmail(request), HttpStatus.OK);
		
	}




	
	@PostMapping(value="/{id}/func={function}")
	public ResponseEntity<GenericResponseDTO> sendMessageByfunc(@PathVariable(value="id") String idMessage,
			@PathVariable(value="function") String func,  String organization) {
		return new ResponseEntity<GenericResponseDTO>(service.sendMessageByfunc(idMessage, organization , func),
				HttpStatus.OK);
	}

}
