package org.example.expert.domain.manager.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(Long todoId, Long userId, Long managerId, String status) {
        Log log = Log.builder()
                .action("ADD_MANAGER")
                .userId(userId)
                .todoId(todoId)
                .managerUserId(managerId)
                .status(status)
                .build();

        logRepository.save(log);
    }
}
