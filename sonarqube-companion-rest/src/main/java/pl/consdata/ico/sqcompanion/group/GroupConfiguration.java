package pl.consdata.ico.sqcompanion.group;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroupConfiguration {

    @Bean
    public GroupRepository groupRepository() {
        return new GroupRepository();
    }

}
