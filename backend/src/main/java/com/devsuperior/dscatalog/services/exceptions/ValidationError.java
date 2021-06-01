package com.devsuperior.dscatalog.services.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.devsuperior.dscatalog.resources.exceptions.StandardError;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	List<FieldMessage> errors = new ArrayList<>();

	public List<FieldMessage> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String msg) {
		errors.add(new FieldMessage(fieldName,msg));
	}
}
