package course.courseWork.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class TaskExceptionHandlerByDeadline {

    @ExceptionHandler(TaskNotFoundExceptionByDeadline.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundExceptionByDeadline e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        400L,
                        "У вас нет задачи на этот день: " + e.getDeadline()
                )
        );
    }
}
