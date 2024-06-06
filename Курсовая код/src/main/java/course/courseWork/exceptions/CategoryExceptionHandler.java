package course.courseWork.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CategoryExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        400L,
                        "У вас нет такой категории"
                )
        );
    }

}
