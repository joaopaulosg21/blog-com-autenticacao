package aprendendo.api.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import aprendendo.api.blog.exceptions.user.EmailUsedException;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler({EmailUsedException.class})
    protected ResponseEntity<Object> EmailUsedHandler(RuntimeException exc,WebRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,String> erros = ((EmailUsedException)exc).getErros();
        return handleExceptionInternal(exc,erros,headers,HttpStatus.BAD_REQUEST,req);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exc,HttpHeaders headers,HttpStatus status,WebRequest req) {
        Map<String,String> erros = new HashMap<>();
        exc.getBindingResult().getAllErrors().forEach((error)->{
            String fieldname = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            erros.put(fieldname,message);
        });
        return handleExceptionInternal(exc,erros,headers,status,req);
    }
    
}
