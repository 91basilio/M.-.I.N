package com.cesarschool.project.emailsender.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cesarschool.project.emailsender.spring.dto.request.UserRequestDTO;
import com.cesarschool.project.emailsender.spring.dto.response.GenericResponseDTO;
import com.cesarschool.project.emailsender.spring.entities.User;
import com.cesarschool.project.emailsender.spring.exceptions.GeneralException;
import com.cesarschool.project.emailsender.spring.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServices {

	private final UserRepository repository;

	public GenericResponseDTO createUser(UserRequestDTO request) {

		Optional.ofNullable(repository.findByEmail(request.getEmail())).ifPresentOrElse(user -> {
			throw new GeneralException("Usuário já cadastrado", HttpStatus.CONFLICT);
		}, () -> {
			User entity = new User();
			BeanUtils.copyProperties(request, entity);
			repository.save(entity);
		});
		return GenericResponseDTO.builder().message("Usuário cadastrado com sucesso").status(HttpStatus.CREATED)
				.build();
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new GeneralException("Usuário não encontrado", HttpStatus.NOT_FOUND));
	}

	public GenericResponseDTO deleteUser(String id) {
		Optional.ofNullable(repository.findById(id).orElse(null)).ifPresentOrElse(client -> {
			repository.deleteById(id);
		}, () -> {
			throw new GeneralException("Usuário não encontrado em nosso banco de dados", HttpStatus.NOT_FOUND);
		});

		return GenericResponseDTO.builder().message("Usuário excluído com sucesso").status(HttpStatus.OK).build();
	}

	public GenericResponseDTO updateUser(String id, UserRequestDTO request) {

		Optional.ofNullable(repository.findById(id).orElse(null)).ifPresentOrElse(user -> {
			User entity = repository.findById(id).orElse(null);
			BeanUtils.copyProperties(request, entity);
			repository.save(entity);
		}, () -> {
			throw new GeneralException("Usuário não encontrado em nosso banco de dados", HttpStatus.NOT_FOUND);
		});
		return GenericResponseDTO.builder().message(" Usuário atualizado com sucesso").status(HttpStatus.OK).build();
	}
}
