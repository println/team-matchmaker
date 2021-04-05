package proto.games.matchmaker.domain.matchmaker.position;

import proto.games.matchmaker.domain.matchmaker.PlayerBulk;
import proto.games.matchmaker.domain.matchmaker.rank.RankTeam;
import proto.games.matchmaker.domain.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PositionMatchmaker {

    public List<PositionTeam> match(PlayerBulk playerBulk, List<RankTeam> rankTeams) {
        List<PositionTeam> positionTeams = prepare(rankTeams);
        distribute(positionTeams, playerBulk);
        populateMissingPositions(positionTeams, playerBulk);
        return positionTeams;
    }

    private List<PositionTeam> prepare(List<RankTeam> rankTeams) {
        return rankTeams.stream()
                .map(PositionTeam::new)
                .collect(Collectors.toList());
    }

    private void distribute(List<PositionTeam> teams, PlayerBulk playerBulk) {
        for (PositionTeam team : teams) {
            for (Player player : playerBulk.getPlayers()) {
                if (team.include(player)) {
                    playerBulk.remove(player);
                }
            }
        }
    }

    private void populateMissingPositions(List<PositionTeam> teams, PlayerBulk playerBulk) {
        for (PositionTeam team : teams) {
            for (Player player : playerBulk.getPlayers()) {
                if (team.populate(player)) {
                    playerBulk.remove(player);
                }
            }
        }
    }
}
