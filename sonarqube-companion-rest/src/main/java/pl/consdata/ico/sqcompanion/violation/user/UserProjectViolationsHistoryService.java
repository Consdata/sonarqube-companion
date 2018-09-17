package pl.consdata.ico.sqcompanion.violation.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserProjectViolationsHistoryService {

    @Transactional
    public void sync() {
        // user repository get all users
        // for each user build missing dates range
        // - always include current day, due to current day statistics override
        // for each user and each missing day fetch issues (or get all issues for missing range to now)
        // for each user build day by day aggregate (filling missing days with previous known analysis)
        // store all metrics
    }

}
