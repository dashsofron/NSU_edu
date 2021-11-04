package com.ooad.g18208.FoodStorage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, reason = "No item with this id found")
public class LackOfIdException extends RuntimeException  {

}
