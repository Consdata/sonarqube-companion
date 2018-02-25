package pl.consdata.ico.sqcompanion.hook.callback;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallbackResponse {
    private String text;
}