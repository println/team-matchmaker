package proto.games.matchmaker.support;

import proto.games.matchmaker.domain.player.Position;
import proto.games.matchmaker.domain.player.Rank;

public final class Converters {
    private Converters() {
    }

    public static Rank eloToRank(String elo) {
        switch (elo.trim()) {
            case "Ferro":
                return Rank.IRON;
            case "Bronze":
                return Rank.BRONZE;
            case "Prata":
                return Rank.SILVER;
            case "Ouro":
                return Rank.GOLD;
            case "Platina":
                return Rank.PLATINUM;
            case "Diamente":
                return Rank.DIAMOND;
            case "Mestre":
                return Rank.MASTER;
            case "Grão-Mestre":
                return Rank.GRANDMASTER;
            case "Desafiante":
                return Rank.CHALLENGER;
            default:
                return Rank.UNRANKED;
        }
    }

    public static Position rotaToPosition(String rota) {
        switch (rota.trim()) {
            case "Topo":
                return Position.TOP;
            case "Selva":
                return Position.JUNGLE;
            case "Meio":
                return Position.MIDDLE;
            case "Atirador":
                return Position.BOTTOM;
            case "Suporte":
            default:
                return Position.SUPPORT;

        }
    }
}
