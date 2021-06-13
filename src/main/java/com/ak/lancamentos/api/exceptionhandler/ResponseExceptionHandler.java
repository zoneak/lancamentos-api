package com.ak.lancamentos.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String msgUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String msgDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	//@ResponseStatus(HttpStatus.NOT_FOUND) - Poderia deixar assim com o método void vazio e já retornaria o 404
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String msgUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String msgDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String msgUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String msgDesenvolvedor = fieldError.toString();
			erros.add(new Erro(msgUsuario, msgDesenvolvedor));
		}
		
		return erros;
	}
	
	// Classe auxiliar utilizada somente nesta classe
	public static class Erro {
		private String msgUsuario;
		private String msgDesenvolvedor;

		public Erro(String msgUsuario, String msgDesenvolvedor) {
			this.msgUsuario = msgUsuario;
			this.msgDesenvolvedor = msgDesenvolvedor;
		}
		
		public String getMsgUsuario() {
			return msgUsuario;
		}
		
		public String getMsgDesenvolvedor() {
			return msgDesenvolvedor;
		}
	}
}
