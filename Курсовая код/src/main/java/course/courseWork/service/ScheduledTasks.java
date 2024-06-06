package course.courseWork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final DataCleanupService dataCleanupService;

    @Scheduled(fixedRate = 30000) // Запускать каждые 30 секунд
    public void performDataCleanup() {
        dataCleanupService.deleteRecordsWithNullUser();
    }
}
