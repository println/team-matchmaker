package proto.games.matchmaker.domain

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import proto.games.matchmaker.domain.matchmaker.Matchmaker
import proto.games.matchmaker.domain.player.Player
import proto.games.matchmaker.domain.player.PlayerService

@Component
class AppRunner(
    private val playerService: PlayerService,
    private val matchmaker: Matchmaker
) : CommandLineRunner {
    @Throws(Exception::class)
    override fun run(vararg args: String) {
        val players: Collection<Player> = playerService.listAllPlayers()
        val result = matchmaker.createTeams(players)
    }
}