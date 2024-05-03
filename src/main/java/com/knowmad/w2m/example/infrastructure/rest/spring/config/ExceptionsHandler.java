package com.knowmad.w2m.example.infrastructure.rest.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.knowmad.w2m.infrastructure.rest.spring.dto.ErrorDto;
import com.knowmad.w2m.infrastructure.rest.spring.dto.ErrorErrorsDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionsHandler {

  public static final String INTERNAL_ERROR = "Internal server";
  public static final String TYPE = "/v1/name/pathSuffix";
  public static final String DETAIL_INTERNAL_SERVER = "Se produjo un error interno, lo estamos revisando.";
  public static final String INSTANCE_0000 = "0000";

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorDto> handleInternal(final RuntimeException ex) {
    log.error(INTERNAL_ERROR);
    return buildErrorDto(TYPE, INTERNAL_ERROR, DETAIL_INTERNAL_SERVER,
                    getErrorErrorsDtos(ex.getMessage(), INTERNAL_ERROR), INSTANCE_0000,
            HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorDto> buildErrorDto(String type, String title, String detail,
                                                      List<ErrorErrorsDto> errorErrorsDtoList, String instance,
                                                      HttpStatus httpStatus) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.setType(type);
    errorDto.setTitle(title);
    errorDto.setDetail(detail);
    errorDto.setErrors(errorErrorsDtoList);
    errorDto.setInstance(instance);
    return new ResponseEntity<>(errorDto, httpStatus);
  }

  private static List<ErrorErrorsDto> getErrorErrorsDtos(String exceptionMessage, String message) {
    ErrorErrorsDto errorErrorsDto = new ErrorErrorsDto();
    errorErrorsDto.setMessage(message);
    errorErrorsDto.setBusinessMessage(exceptionMessage);
    List<ErrorErrorsDto> errorErrorsDtoList = new ArrayList<>();
    errorErrorsDtoList.add(errorErrorsDto);
    return errorErrorsDtoList;
  }

}

