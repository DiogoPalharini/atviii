package com.autobots.automanager.excecao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("mensagem", ex.getMessage());
		response.put("status", "404");
		response.put("erro", "Recurso não encontrado");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		Map<String, Object> response = new HashMap<>();
		Map<String, String> erros = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String campo = ((FieldError) error).getField();
			String mensagem = error.getDefaultMessage();
			erros.put(campo, mensagem);
		});
		
		response.put("mensagem", "Erro de validação");
		response.put("status", "400");
		response.put("erro", "Dados inválidos");
		response.put("erros", erros);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
		Map<String, String> response = new HashMap<>();
		response.put("mensagem", ex.getMessage() != null ? ex.getMessage() : "Erro interno do servidor");
		response.put("status", "500");
		response.put("erro", "Erro interno do servidor");
		
		// Log da exceção (em produção, usar um logger adequado)
		ex.printStackTrace();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}

