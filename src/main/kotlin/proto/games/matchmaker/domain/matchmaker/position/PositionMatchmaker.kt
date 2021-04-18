package proto.games.matchmaker.domain.matchmaker.position

import proto.games.matchmaker.domain.matchmaker.PlayerBulk
import proto.games.matchmaker.domain.matchmaker.rank.RankTeam

class PositionMatchmaker {
    fun match(playerBulk: PlayerBulk, rankTeams: List<RankTeam>): List<PositionTeam> {
        val positionTeams = prepare(rankTeams)
        distribute(positionTeams, playerBulk)
        populateMissingPositions(positionTeams, playerBulk)
        return positionTeams
    }

    private fun prepare(rankTeams: List<RankTeam>): List<PositionTeam> {
        return rankTeams
            .map { rankTeam -> PositionTeam(rankTeam) }
    }

    private fun distribute(teams: List<PositionTeam>, playerBulk: PlayerBulk) {
        for (team in teams) {
            for (player in playerBulk.players) {
                if (team.include(player)) {
                    playerBulk.remove(player)
                }
            }
        }
    }

    private fun populateMissingPositions(teams: List<PositionTeam>, playerBulk: PlayerBulk) {
        for (team in teams) {
            for (player in playerBulk.players) {
                if (team.populate(player)) {
                    playerBulk.remove(player)
                }
            }
        }
    }
}