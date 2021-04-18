package proto.games.matchmaker.domain.player

class Player(
    val name: String,
    val email: String,
    val nickName: String,
    val rank: Rank,
    val primaryPosition: Position,
    val secondaryPosition: Position
) : Comparable<Player> {
    override fun compareTo(other: Player): Int {
        return other.rank.weight.compareTo(rank.weight)
    }
}