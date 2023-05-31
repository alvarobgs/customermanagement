package br.com.abg.core.configuration.exceptionhandler

import br.com.abg.core.common.Messages
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.TypeMismatchException
import org.springframework.core.convert.ConversionFailedException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.sql.SQLException
import java.util.*
import java.util.stream.Collectors

@ControllerAdvice
class DefaultExceptionHandler {

    private val logger = LoggerFactory.getLogger(DefaultExceptionHandler::class.java)

    @ExceptionHandler(ConversionFailedException::class)
    fun handleConversionFailedException(e: ConversionFailedException, request: WebRequest?): ResponseEntity<RestExceptionResponse> {
        val error = RestExceptionResponse.Error(
            message = Messages.findByKey("invalid.input.parameter"),
            description = e.message
        )

        return ResponseEntity.badRequest().body(RestExceptionResponse(error = error))
            .also { logger.warn(ExceptionUtils.getRootCauseMessage(e)) }
    }

    @ExceptionHandler(SQLException::class)
    fun handleSQLException(e: SQLException?, request: WebRequest?): ResponseEntity<RestExceptionResponse> {
        val error = RestExceptionResponse.Error(message = Messages.findByKey("internal.error"))

        return ResponseEntity.internalServerError().body(RestExceptionResponse(error = error))
            .also { logger.error(ExceptionUtils.getRootCauseMessage(e)) }
    }

    @ExceptionHandler(ClassNotFoundException::class)
    fun handleClassNotFoundException(e: ClassNotFoundException?, request: WebRequest?): ResponseEntity<RestExceptionResponse> {
        val error = RestExceptionResponse.Error(message = Messages.findByKey("internal.error"))

        return ResponseEntity.internalServerError().body(RestExceptionResponse(error = error))
            .also { logger.error(ExceptionUtils.getRootCauseMessage(e)) }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException, request: WebRequest?): ResponseEntity<RestExceptionResponse> {
        val error = RestExceptionResponse.Error(
            message = Messages.findByKey("invalid.input.parameter"),
            description = "${e.name}: ${e.value}"
        )

        return ResponseEntity.badRequest().body(RestExceptionResponse(error = error))
            .also { logger.warn(ExceptionUtils.getRootCauseMessage(e)) }
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameter(e: MissingServletRequestParameterException, request: WebRequest?): ResponseEntity<RestExceptionResponse> {
        val error = RestExceptionResponse.Error(
            message = Messages.findByKey("invalid.input.parameter"),
            description = "${e.parameterName} ${Messages.findByKey("is.required")}"
        )

        return ResponseEntity.badRequest().body(RestExceptionResponse(error = error))
            .also { logger.warn(ExceptionUtils.getRootCauseMessage(e)) }
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException, request: WebRequest?): ResponseEntity<RestExceptionResponse> {
        val error = RestExceptionResponse.Error(message = Messages.findByKey("invalid.input.parameter"))

        val cause = e.cause
        if (Objects.nonNull(cause)) {
            if (cause is UnrecognizedPropertyException) {
                error.fields.add(
                    RestExceptionResponse.Field(
                        name = cause.propertyName,
                        message = Messages.findByKey("unexpected.attribute")
                    )
                )
            } else if (cause is InvalidFormatException) {
                val fieldName = cause.path.stream()
                                          .map { reference: JsonMappingException.Reference -> reference.fieldName + "." }
                                          .collect(Collectors.joining())
                error.fields.add(
                    RestExceptionResponse.Field(
                        name = fieldName.substring(0, fieldName.length - 1),
                        message = Messages.findByKey("invalid.input.parameter"),
                        value = cause.value.toString()
                    )
                )
            }
        }

        return ResponseEntity.badRequest().body(RestExceptionResponse(error = error))
            .also { logger.warn(ExceptionUtils.getRootCauseMessage(e)) }
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException, request: WebRequest?): ResponseEntity<RestExceptionResponse> {
        val error = RestExceptionResponse.Error(message = Messages.findByKey("invalid.input.parameter"))

        for (fe in e.bindingResult.fieldErrors) {
            error.fields.add(RestExceptionResponse.Field(name = fe.field, message = fe.defaultMessage))
        }
        for (oe in e.bindingResult.globalErrors) {
            error.fields.add(RestExceptionResponse.Field(name = oe.objectName, message = oe.defaultMessage))
        }

        return ResponseEntity.badRequest().body(RestExceptionResponse(error = error))
            .also { logger.warn(ExceptionUtils.getRootCauseMessage(e)) }
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException, request: WebRequest?): ResponseEntity<RestExceptionResponse> {
        val error = RestExceptionResponse.Error(message = Messages.findByKey("invalid.input.parameter"))

        for (fe in e.bindingResult.fieldErrors) {
            val fieldName = fe.field
            val fieldValue = if (Objects.isNull(fe.rejectedValue)) null else fe.rejectedValue?.toString()
            var message = fe.defaultMessage
            if (fe.contains(TypeMismatchException::class.java)) {
                val tme = fe.unwrap(TypeMismatchException::class.java)
                val knownValues = StringBuilder()
                message = tme.requiredType?.let { it.enumConstants?.let { e ->
                        for (o in e) {
                            knownValues.append(o.toString())
                            knownValues.append(";")
                        }
                        "${Messages.findByKey("use.one.of.the.following")}: $knownValues"
                    } } ?: tme.message
//                    message = if (Objects.nonNull(tme.requiredType) && Objects.nonNull(tme.requiredType!!.enumConstants) && tme.requiredType!!.enumConstants.isNotEmpty()) {
//                        for (o in tme.requiredType?.enumConstants!!) {
//                            knownValues.append(o.toString())
//                            knownValues.append(";")
//                        }
//                        "${Messages.findByKey("use.one.of.the.following")}: $knownValues"
//                    } else {
//                        tme.message
//                    }
            }
            error.fields.add(RestExceptionResponse.Field(name = fieldName, value = fieldValue, message = message))
        }

        return ResponseEntity.badRequest().body(RestExceptionResponse(error = error))
            .also { logger.warn(ExceptionUtils.getRootCauseMessage(e)) }
    }

    @ExceptionHandler(ServletRequestBindingException::class)
    fun handleServletRequestBindingException(ex: ServletRequestBindingException, request: WebRequest?): ResponseEntity<RestExceptionResponse> {
        val error = if (ex is MissingRequestHeaderException) {
            RestExceptionResponse.Error(
                message = Messages.findByKey("invalid.input.parameter"),
                description = "Header ${ex.headerName} ${Messages.findByKey("is.required")}"
            )
        } else {
            RestExceptionResponse.Error(
                message = Messages.findByKey("invalid.input.parameter"),
                description = ex.message
            )
        }
        return ResponseEntity.badRequest().body(RestExceptionResponse(error = error))
            .also { logger.error(ExceptionUtils.getRootCauseMessage(ex)) }
    }
}