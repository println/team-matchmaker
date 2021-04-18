package proto.games.matchmaker.support

import proto.games.matchmaker.domain.player.Position
import proto.games.matchmaker.domain.player.Rank

object Converters {
    fun eloToRank(elo: String): Rank {
        return when (elo.trim { it <= ' ' }) {
            "Ferro" -> Rank.IRON
            "Bronze" -> Rank.BRONZE
            "Prata" -> Rank.SILVER
            "Ouro" -> Rank.GOLD
            "Platina" -> Rank.PLATINUM
            "Diamente" -> Rank.DIAMOND
            "Mestre" -> Rank.MASTER
            "Grão-Mestre" -> Rank.GRANDMASTER
            "Desafiante" -> Rank.CHALLENGER
            else -> Rank.UNRANKED
        }
    }

    fun rotaToPosition(rota: String): Position {
        return when (rota.trim { it <= ' ' }) {
            "Topo" -> Position.TOP
            "Selva" -> Position.JUNGLE
            "Meio" -> Position.MIDDLE
            "Atirador" -> Position.BOTTOM
            "Suporte" -> Position.SUPPORT
            else -> Position.SUPPORT
        }
    }
}