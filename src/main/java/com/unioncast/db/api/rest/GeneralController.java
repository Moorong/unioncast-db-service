package com.unioncast.db.api.rest;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.unioncast.common.restClient.RestError;
import com.unioncast.common.restClient.RestResponse;
import com.unioncast.db.rdbms.core.exception.DaoException;

public abstract class GeneralController {

	private static final Logger LOG = LogManager.getLogger(GeneralController.class);

	@ExceptionHandler(DaoException.class)
	public RestResponse exceptionHandler(DaoException daoException) {
		RestResponse restResponse = new RestResponse();
		daoException.printStackTrace();
		restResponse.setStatus(RestResponse.FAIL);
		RestError restErrors = new RestError();
		restErrors.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		restErrors.setMessage(daoException.getMessage());
		restResponse.setRestErrors(new RestError[] { restErrors });
		LOG.info("DaoExceptionRestResponse:{}", restResponse);
		return restResponse;
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public RestResponse exceptionHandler(EmptyResultDataAccessException emptyResultDataAccessException) {
		RestResponse restResponse = new RestResponse();
		emptyResultDataAccessException.printStackTrace();
		restResponse.setStatus(RestResponse.FAIL);
		RestError restErrors = new RestError();
		restErrors.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		restErrors.setMessage(emptyResultDataAccessException.getMessage());
		restResponse.setRestErrors(new RestError[] { restErrors });
		LOG.info("EmptyResultDataAccessExceptionRestResponse:{}", restResponse);
		return restResponse;
	}

	@ExceptionHandler(ParseException.class)
	public RestResponse exceptionHandler(ParseException daoException) {
		RestResponse restResponse = new RestResponse();
		daoException.printStackTrace();
		restResponse.setStatus(RestResponse.FAIL);
		RestError restErrors = new RestError();
		restErrors.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		restErrors.setMessage(daoException.getMessage());
		restResponse.setRestErrors(new RestError[] { restErrors });
		LOG.info("ParseExceptionRestResponse:{}", restResponse);
		return restResponse;
	}

	@ExceptionHandler(Exception.class)
	public RestResponse exceptionHandler(Exception exception) {
		RestResponse restResponse = new RestResponse();
		exception.printStackTrace();
		restResponse.setStatus(RestResponse.FAIL);
		RestError restErrors = new RestError();
		restErrors.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		restErrors.setMessage(exception.getMessage());
		restResponse.setRestErrors(new RestError[] { restErrors });
		LOG.info("ParseExceptionRestResponse:{}", restResponse);
		return restResponse;
	}
}
