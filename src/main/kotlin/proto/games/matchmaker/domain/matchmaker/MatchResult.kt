package proto.games.matchmaker.domain.matchmaker

import proto.games.matchmaker.domain.player.Player


class MatchResult(
    val teams: List<Team>,
    val unselectedPlayers: List<Player>
) {
    override fun toString(): String {
        val teamLevel = teams[0].tierRank.toString()
        val teamLevelVariation = teams
            .map { team -> team.weaknessLevel.toString() }
        return """{ nível-dos-times=${teamLevel}, 
                variação-de-nível=${teamLevelVariation}, 
                não-escolhidos=${unselectedPlayers.size}}"""
    }
}