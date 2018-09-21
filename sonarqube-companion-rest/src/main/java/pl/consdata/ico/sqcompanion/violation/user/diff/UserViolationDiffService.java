package pl.consdata.ico.sqcompanion.violation.user.diff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;

@Slf4j
@Service
public class UserViolationDiffService {

    public ViolationsHistory userViolationsHistory(String user) {
        return ViolationsHistory.builder().build();
    }

}
