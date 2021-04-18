package proto.games.matchmaker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MatchmakerApplication

fun main(args: Array<String>) {
	runApplication<MatchmakerApplication>(*args)
}
