package proto.games.matchmaker.domain.matchmaker

import proto.games.matchmaker.domain.player.Player
import proto.games.matchmaker.domain.player.Rank

class Team(val players: List<Player>, val tierRank: Rank) {
    val currentWeight: Int
        get() =  players.map { player -> player.rank.weight}.sum()
    val weaknessLevel: Int
        get() = currentWeight - tierRank.weight * 5
}