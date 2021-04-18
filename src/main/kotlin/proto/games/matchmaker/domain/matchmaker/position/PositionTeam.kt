package proto.games.matchmaker.domain.matchmaker.position

import proto.games.matchmaker.domain.matchmaker.rank.RankTeam
import proto.games.matchmaker.domain.player.Player
import proto.games.matchmaker.domain.player.Position
import proto.games.matchmaker.domain.player.Rank


class PositionTeam(private val rankTeam: RankTeam) {
    val players: MutableList<Player> = ArrayList()
    val filledPositions: MutableSet<Position> = HashSet()

    val head: Player?
        get() = players.getOrNull(0)
    val headRank: Rank?
        get() = rankTeam.getOrNull(0)

    fun include(player: Player): Boolean {
        return when {
            hasNoPosition(player) -> false
            else -> populate(player)
        }
    }

    fun populate(player: Player): Boolean {
        return when {
            isIncompatible(player) -> false
            else -> insert(player)
        }
    }

    private fun isIncompatible(player: Player): Boolean {
        return when {
            players.contains(player) -> true
            rankTeam.contains(player.rank).not() -> true
            else -> wrongRank(player)
        }
    }

    private fun insert(player: Player): Boolean {
        filledPositions.add(player.primaryPosition)
        filledPositions.add(player.secondaryPosition)
        val inserted = players.add(player)
        players.sort()
        return inserted
    }

    private fun wrongRank(player: Player): Boolean {
        val totalRank = rankTeam
            .filter { r: Rank -> r === player.rank }
            .count()

        val totalPlayersByRank = players
            .filter { p -> p.rank === player.rank }
            .count()

        return totalRank <= totalPlayersByRank
    }

    private fun hasNoPosition(player: Player): Boolean {
        val primary: Position = player.primaryPosition
        val secondary: Position = player.secondaryPosition

        return when{
            filledPositions.contains(primary).not() -> false
            else -> filledPositions.contains(secondary).not()
        }
    }
}