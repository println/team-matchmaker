package proto.games.matchmaker.domain.matchmaker

import proto.games.matchmaker.domain.player.Player
import proto.games.matchmaker.domain.player.Rank

class PlayerBulk(players: Collection<Player>) : Cloneable {
    private val _players: MutableList<Player> = players.sorted().toMutableList()
    val medianRank: Rank = defineMedianRank(_players)

    val players: MutableList<Player>
        get() = ArrayList(_players)

    fun takeTopHighLevelPlayers(length: Int): List<Player> {
        val topPlayers: List<Player> = ArrayList(_players.subList(0, length))
        remove(topPlayers)
        return topPlayers
    }

    fun remove(players: List<Player>) {
        for (player in players) {
            remove(player)
        }
    }

    fun remove(player: Player?) {
        _players.remove(player)
    }

    fun count(): Long {
        return _players.size.toLong()
    }

    public override fun clone(): PlayerBulk {
        return PlayerBulk(ArrayList(_players))
    }

    private fun defineMedianRank(players: List<Player>): Rank {
        return if (players.size % 2 == 0) {
            players[players.size / 2 + 1].rank
        } else {
            players[players.size / 2].rank
        }
    }
}