package blck.wayd.config;

import blck.wayd.exceptions.AppException;
import blck.wayd.exceptions.NoData;
import blck.wayd.model.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Error Controller.
 */
@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoData.class)
    public ResponseEntity<ErrorResponse> handleNoData(NoData noData) {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleException(AppException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }
}
