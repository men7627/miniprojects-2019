package techcourse.fakebook.controller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import techcourse.fakebook.exception.InvalidSignupException;
import techcourse.fakebook.exception.NotFoundUserException;
import techcourse.fakebook.exception.NotMatchPasswordException;

@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(InvalidSignupException.class)
    public ResponseEntity<String> handleNotMatchAuthorException(InvalidSignupException e, Model model) {
        log.debug("begin");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<String> handleNotFoundUserException(NotFoundUserException e, Model model) {
        log.debug("begin");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(NotMatchPasswordException.class)
    public ResponseEntity<String> handleNotMatchPasswordException(NotMatchPasswordException e, Model model) {
        log.debug("begin");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
