package proto.games.matchmaker.domain.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Rank {
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

    private final int weight;
}
