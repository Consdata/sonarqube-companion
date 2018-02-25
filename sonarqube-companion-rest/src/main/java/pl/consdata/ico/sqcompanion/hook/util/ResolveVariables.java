package pl.consdata.ico.sqcompanion.hook.util;

import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;

public final class ResolveVariables {
    public static String resolveVariables(ActionResponse response, String source) {
        String output = source;
        for (String key : response.getValues().keySet()) {
            output = output.replaceAll("\\$\\{\\s*" + key + "\\s*}", response.getValues().get(key));
        }
        return output;
    }
}
