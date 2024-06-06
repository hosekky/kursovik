package course.courseWork.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class TaskNotFoundExceptionByDeadline extends Exception {

    private final Date deadline;
}
