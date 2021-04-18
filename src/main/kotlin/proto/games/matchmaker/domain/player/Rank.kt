package proto.games.matchmaker.domain.player

enum class Rank(val weight: Int) {
    UNRANKED(1),
    IRON(2),
    BRONZE(3),
    SILVER(4),
    GOLD(5),
    PLATINUM(7),
    DIAMOND(9),
    MASTER(11),
    GRANDMASTER(13),
    CHALLENGER(15);
}