package pl.consdata.ico.sqcompanion.hook.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionResponse {

    private Map<String, String> values;
    private String actionResult;

}
