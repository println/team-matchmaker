package proto.games.matchmaker.domain.matchmaker.rank;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import proto.games.matchmaker.configuration.properties.DefaultProperties;
import proto.games.matchmaker.domain.matchmaker.PlayerBulk;
import proto.games.matchmaker.domain.player.Player;
import proto.games.matchmaker.domain.player.Rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RankMatchmaker {
    private final DefaultProperties defaultProperties;

    public List<RankTeam> match(PlayerBulk playerBulk) {
        List<RankTeam> rankTeams = distribute(playerBulk.clone());
        rankTeams = equalize(rankTeams);
        return rankTeams;
    }

    private List<RankTeam> distribute(PlayerBulk playerBulkClone) {
        final int totalTeams = (int) playerBulkClone.count() / defaultProperties.getTeam().getSize();
        List<Player> teamHeads = playerBulkClone.takeTopHighLevelPlayers(totalTeams);

        List<RankTeam> rankTeams = teamHeads.stream()
                .map(p -> {
                    RankTeam rankTeam = new RankTeam(playerBulkClone.getMedianRank());
                    rankTeam.add(p.getRank());
                    return rankTeam;
                })
                .collect(Collectors.toList());

        for (int i = 0; i < 4; i++) {
            teamHeads = playerBulkClone.takeTopHighLevelPlayers(totalTeams);
            Collections.reverse(teamHeads);
            for (int j = 0; j < teamHeads.size(); j++) {
                rankTeams.get(j).add(teamHeads.get(j).getRank());
            }
        }

        return rankTeams;
    }

    private List<RankTeam> equalize(List<RankTeam> shadows) {
        List<RankTeam> rankTeams = new ArrayList<>(shadows);

        do {
            Pair<RankTeam, RankTeam> discrepantTeams = findTeamDiscrepancies(rankTeams);
            var weakerTeam = discrepantTeams.getLeft();
            var strongerTeam = discrepantTeams.getRight();

            if (canEqualize(weakerTeam, strongerTeam)) {
                int threshold = calculateThreshold(weakerTeam.getRemainingSpace(), strongerTeam.getRemainingSpace());
                swapRank(weakerTeam, strongerTeam, threshold);
            } else {
                break;
            }
        } while (true);

        return rankTeams;
    }

    private Pair<RankTeam, RankTeam> findTeamDiscrepancies(List<RankTeam> rankTeams) {
        RankTeam weakerTeam = rankTeams.stream()
                .max(Comparator.comparingInt(RankTeam::getRemainingSpace))
                .get();

        RankTeam strongerTeam = rankTeams.stream()
                .min(Comparator.comparingInt(RankTeam::getRemainingSpace))
                .get();

        return Pair.of(weakerTeam, strongerTeam);
    }

    private boolean canEqualize(RankTeam weakerTeam, RankTeam strongerTeam) {
        int weaker = weakerTeam.getRemainingSpace();
        int stronger = strongerTeam.getRemainingSpace();

        if (stronger < 0) {
            return true;
        }

        if (calculateThreshold(weaker, stronger) < 0) {
            return true;
        }

        return false;
    }

    private int calculateThreshold(int weakerSpace, int strongerSpace) {
        if (strongerSpace < 0) {
            return strongerSpace;
        }
        return ((weakerSpace - strongerSpace) / 2) * -1;
    }

    private void swapRank(RankTeam weakerTeam, RankTeam strongerTeam, int threshold) {
        outerloop:
        for (int i = 4; i > 0; i--) {
            Rank rankOfWeaker = weakerTeam.get(i);
            for (int j = 4; j > 0; j--) {
                Rank rankOfStronger = strongerTeam.get(j);
                if (rankOfWeaker.getWeight() - rankOfStronger.getWeight() == threshold) {
                    strongerTeam.add(rankOfWeaker);
                    strongerTeam.remove(rankOfStronger);
                    weakerTeam.add(rankOfStronger);
                    weakerTeam.remove(rankOfWeaker);

                    if (weakerTeam.getRemainingSpace() >= 0) {
                        break outerloop;
                    }
                }
            }
        }

        Collections.sort(strongerTeam);
        Collections.reverse(strongerTeam);
        Collections.sort(weakerTeam);
        Collections.reverse(weakerTeam);
    }
}
