package course.courseWork.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class TaskExceptionHandlerByUsers {

    @ExceptionHandler(TaskNotFoundExceptionByDeadline.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        400L,
                        "У вас нет такой задачи"
                )
        );
    }

}
