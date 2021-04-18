package proto.games.matchmaker.domain.matchmaker.rank

import proto.games.matchmaker.domain.player.Rank

class RankTeam(baseRank: Rank) : ArrayList<Rank>() {
    val limit: Int = baseRank.weight * 5
    val currentWeight: Int
        get() = sumBy { it.weight }
    val remainingSpace: Int
        get() = limit - currentWeight

}