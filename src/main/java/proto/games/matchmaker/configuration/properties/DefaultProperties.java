package proto.games.matchmaker.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "default")
public class DefaultProperties {
    private TeamProperties team;

    @Data
    public static class TeamProperties {
        private int size;
    }
}
