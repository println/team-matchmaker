package proto.games.matchmaker.domain.matchmaker

import org.springframework.stereotype.Service
import proto.games.matchmaker.configuration.properties.DefaultProperties
import proto.games.matchmaker.domain.matchmaker.position.PositionMatchmaker
import proto.games.matchmaker.domain.matchmaker.position.PositionTeam
import proto.games.matchmaker.domain.matchmaker.rank.RankMatchmaker
import proto.games.matchmaker.domain.matchmaker.rank.RankTeam
import proto.games.matchmaker.domain.player.Player

@Service
class Matchmaker(private val defaultProperties: DefaultProperties) {
    fun createTeams(players: Collection<Player>): MatchResult {
        val playerBulk = PlayerBulk(players)
        val teams = organizeTeams(playerBulk)
        return generateResult(teams, playerBulk)
    }

    private fun generateResult(positionTeams: List<PositionTeam>, playerBulk: PlayerBulk): MatchResult {
        val teams = positionTeams
            .map { t -> Team(t.players, playerBulk.medianRank) }
        return MatchResult(teams, playerBulk.players)
    }

    private fun organizeTeams(playerBulk: PlayerBulk): List<PositionTeam> {
        val rankTeams = clusterByRank(playerBulk)
        return integratePlayersByPositions(playerBulk, rankTeams)
    }

    private fun clusterByRank(playerBulk: PlayerBulk): List<RankTeam> {
        val rankMatchmaker = RankMatchmaker(defaultProperties)
        return rankMatchmaker.match(playerBulk.clone())
    }

    private fun integratePlayersByPositions(playerBulk: PlayerBulk, rankTeams: List<RankTeam>): List<PositionTeam> {
        val positionMatchmaker = PositionMatchmaker()
        return positionMatchmaker.match(playerBulk, rankTeams)
    }
}