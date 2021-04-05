package proto.games.matchmaker.domain.matchmaker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import proto.games.matchmaker.domain.player.Player;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class MatchResult {
    private final List<Team> teams;
    private final List<Player> unselectedPlayers;

    @Override
    public String toString() {
        String teamLevel = teams.get(0).getTierRank().toString();
        List<String> teamLevelVariation = teams.stream()
                .map(Team::getWeaknessLevel)
                .map(String::valueOf)
                .collect(Collectors.toList());

        return "{" +
                "nível-dos-times=" + teamLevel +
                ", variação-de-nível=" + teamLevelVariation +
                ", não-escolhidos=" + unselectedPlayers.size() +
                '}';
    }
}
