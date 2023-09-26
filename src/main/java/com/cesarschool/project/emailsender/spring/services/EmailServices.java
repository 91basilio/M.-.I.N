package com.cesarschool.project.emailsender.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cesarschool.project.emailsender.spring.dto.request.CustomMailRequestDTO;
import com.cesarschool.project.emailsender.spring.dto.request.EmailRequestDTO;
import com.cesarschool.project.emailsender.spring.dto.response.GenericResponseDTO;
import com.cesarschool.project.emailsender.spring.entities.Email;
import com.cesarschool.project.emailsender.spring.entities.User;
import com.cesarschool.project.emailsender.spring.enums.StatusMail;
import com.cesarschool.project.emailsender.spring.exceptions.GeneralException;
import com.cesarschool.project.emailsender.spring.repositories.EmailRepository;
import com.cesarschool.project.emailsender.spring.repositories.MessageRepository;
import com.cesarschool.project.emailsender.spring.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailServices {

	private final MessageRepository messageRepository;

	private final UserRepository userRepository;

	private final JavaMailSender mailSender;

	private final EmailRepository emailRepository;

	public GenericResponseDTO sendMessageByOrganization(String idMessage, String organization) {

		Optional.ofNullable(messageRepository.findById(idMessage)).ifPresentOrElse(message -> {
			Optional.ofNullable(userRepository.findByOrganization(organization)).ifPresentOrElse(user -> {

				EmailRequestDTO e = new EmailRequestDTO();

				try {
					SimpleMailMessage email = new SimpleMailMessage();
					email.setFrom("emailsendernext@gmail.com");
					email.setTo(user.stream().map(User::getEmail).toArray(String[]::new));
					email.setSubject(message.get().getSubject());
					email.setText(message.get().getText());
					mailSender.send(email);

					e.setStatusMail(StatusMail.SENT);
				} catch (MailException ex) {
					e.setStatusMail(StatusMail.ERROR);
					throw new GeneralException("ERROR SENDING THE EMAIL", HttpStatus.BAD_GATEWAY);
				} finally {
					List<Email> mailSent = new ArrayList<>();

					for (User users : user) {

						Email entity = new Email();

						e.setUser(users);
						e.setSendTo(users.getEmail());
						e.setSubject(message.get().getSubject());
						e.setText(message.get().getText());
						BeanUtils.copyProperties(e, entity);
						mailSent.add(entity);
					}

					emailRepository.saveAll(mailSent);
					
					
					/*
					 * List<Email> mailSent = user.parallelStream()
    						.map(user -> {
       						Email entity = new Email();
        					entity.setUser(user);
        					entity.setSendTo(user.getEmail());
        					entity.setSubject(message.get().getSubject());
        					entity.setText(message.get().getText());
        					return entity;
    						})
    						.collect(Collectors.toList());

							emailRepository.saveAll(mailSent);
					 */

				}
			}, () -> {
				throw new GeneralException("USER NOT FOUND IN OUR DATABASE", HttpStatus.NOT_FOUND);
			});
		}, () -> {

			throw new GeneralException("MESSAGE NOT FOUND IN OUR DATABASE", HttpStatus.NOT_FOUND);
		});

		return GenericResponseDTO.builder().message("SENT").status(HttpStatus.OK).build();

	}


	public GenericResponseDTO sendMessageByfunc(String idMessage, String organization, String func) {

		Optional.ofNullable(messageRepository.findById(idMessage)).ifPresent(message -> {
			Optional.ofNullable(userRepository.findByOrganization(organization)).ifPresentOrElse(user -> {
			Optional.ofNullable(userRepository.findByfunc(func)).ifPresentOrElse(fun -> {
				
				EmailRequestDTO e = new EmailRequestDTO();
				
				List<User> u = userRepository.findByfunc(func);
				
				try {
					SimpleMailMessage email = new SimpleMailMessage();
					email.setFrom("emailsendernext@gmail.com");
					email.setTo(u.stream().map(User::getEmail).toArray(String[]::new));
					email.setSubject(message.get().getSubject());
					email.setText(message.get().getText());
					mailSender.send(email);

					e.setStatusMail(StatusMail.SENT);
				} catch (MailException ex) {
					e.setStatusMail(StatusMail.ERROR);
					throw new GeneralException("ERROR SENDING THE EMAIL", HttpStatus.BAD_GATEWAY);
				} finally {
					
					List<Email> mailSent = new ArrayList<>();
					
					for (User users : u) {
						
						Email entity = new Email();
						
						e.setUser(users);
						e.setSendTo(users.getEmail());
						e.setSubject(message.get().getSubject());
						e.setText(message.get().getText());
						BeanUtils.copyProperties(e, entity);
						mailSent.add(entity);
					}

					emailRepository.saveAll(mailSent);

				}
			}, () -> {
				throw new GeneralException("USER NOT FOUND IN OUR DATABASE", HttpStatus.NOT_FOUND);
			});
		}, () -> {
		});
		throw new GeneralException("MESSAGE NOT FOUND IN OUR DATABASE", HttpStatus.NOT_FOUND);
	});
		return GenericResponseDTO.builder().message("SENT").status(HttpStatus.OK).build();
	}


	public GenericResponseDTO sendCustomMessageByEmail(CustomMailRequestDTO request) {
		Email entity = new Email();
		Optional.ofNullable(userRepository.findByEmail(request.getSendTo())).ifPresentOrElse(user -> {
			try {
				SimpleMailMessage email = new SimpleMailMessage();
				email.setFrom("emailsendernext@gmail.com");
				email.setTo(request.getSendTo());
				email.setSubject(request.getSubject());
				email.setText(request.getText());
				mailSender.send(email);
				entity.setStatusMail(StatusMail.SENT);
			} catch (MailException e) {
				entity.setStatusMail(StatusMail.ERROR);
			} finally {
				entity.setUser(user);
				entity.setSendTo(request.getSendTo());
				entity.setSubject(request.getSubject());
				entity.setText(request.getText());
				emailRepository.save(entity);
			}
		}, () -> {
			throw new GeneralException("USER NOT FOUND IN OUR DATABASE", HttpStatus.NOT_FOUND);
		});

		return GenericResponseDTO.builder().message("SENT").status(HttpStatus.OK).build();
	}

}
