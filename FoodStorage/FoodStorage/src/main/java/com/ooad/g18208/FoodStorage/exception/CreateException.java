package com.ooad.g18208.FoodStorage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "No item created")
public class CreateException extends RuntimeException {
}
