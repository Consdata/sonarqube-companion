package pl.consdata.ico.sqcompanion.cache;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class Caches {

    public static final String GROUP_OVERVIEW_CACHE = "cache_group_overview";
    public static final String GROUP_DETAILS_CACHE = "cache_group_details";
    public static final String PROJECT_SUMMARY_CACHE = "cache_project_summary";
    public static final String GROUP_VIOLATIONS_HISTORY_CACHE = "cache_group_violations_history";
    public static final String ALL_PROJECTS_VIOLATIONS_HISTORY_DIFF_CACHE = "cache_all_projects_violations_history_diff";
    public static final String ALL_PROJECTS_DETAILS_CACHE = "cache_all_projects_details";
    public static final String PROJECT_VIOLATIONS_HISTORY_CACHE = "cache_project_violations_history";
    public static final String PROJECT_VIOLATIONS_HISTORY_DIFF_CACHE = "cache_project_violations_history_diff";
    public static final String GROUP_USER_VIOLATIONS_HISTORY_DIFF_CACHE = "cache_group_user_violations_history_diff";
    public static final String GROUP_USERS_VIOLATIONS_HISTORY_DIFF_CACHE = "cache_group_users_violations_history_diff";
    public static final String GROUP_PROJECT_VIOLATIONS_HISTORY_CACHE = "cache_group_project_violations_history";
    public static final String GROUP_PROJECT_VIOLATIONS_HISTORY_DIFF_CACHE = "cache_group_project_violations_history_diff";
    public static final String GROUP_PROJECT_SUMMARY_CACHE = "cache_group_project_summary";

    public static final List<String> LIST = Arrays.asList(
            GROUP_OVERVIEW_CACHE,
            GROUP_DETAILS_CACHE,
            GROUP_VIOLATIONS_HISTORY_CACHE,
            ALL_PROJECTS_VIOLATIONS_HISTORY_DIFF_CACHE,
            ALL_PROJECTS_DETAILS_CACHE,
            PROJECT_SUMMARY_CACHE,
            PROJECT_VIOLATIONS_HISTORY_CACHE,
            PROJECT_VIOLATIONS_HISTORY_DIFF_CACHE,
            GROUP_USER_VIOLATIONS_HISTORY_DIFF_CACHE,
            GROUP_USERS_VIOLATIONS_HISTORY_DIFF_CACHE,
            GROUP_PROJECT_VIOLATIONS_HISTORY_CACHE,
            GROUP_PROJECT_VIOLATIONS_HISTORY_DIFF_CACHE,
            GROUP_PROJECT_SUMMARY_CACHE
    );

}
