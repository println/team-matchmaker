package proto.games.matchmaker.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "default")
class DefaultProperties {
    var team: TeamProperties = TeamProperties()

    class TeamProperties {
         var size: Int = 0
    }
}