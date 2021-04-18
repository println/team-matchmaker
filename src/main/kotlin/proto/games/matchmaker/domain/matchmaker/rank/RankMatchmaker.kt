package proto.games.matchmaker.domain.matchmaker.rank

import proto.games.matchmaker.configuration.properties.DefaultProperties
import proto.games.matchmaker.domain.matchmaker.PlayerBulk

class RankMatchmaker(
    private val defaultProperties: DefaultProperties
) {
    fun match(playerBulk: PlayerBulk): List<RankTeam> {
        var rankTeams = distribute(playerBulk.clone())
        rankTeams = equalize(rankTeams)
        return rankTeams
    }

    private fun distribute(playerBulkClone: PlayerBulk): List<RankTeam> {
        val totalTeams = playerBulkClone.count().toInt() / defaultProperties.team.size
        var teamHeads = playerBulkClone.takeTopHighLevelPlayers(totalTeams)
        val rankTeams = teamHeads
            .map { p ->
                val rankTeam = RankTeam(playerBulkClone.medianRank)
                rankTeam.add(p.rank)
                rankTeam
            }

        for (i in 0..3) {
            teamHeads = playerBulkClone.takeTopHighLevelPlayers(totalTeams).reversed()
            for (j in teamHeads.indices) {
                rankTeams[j].add(teamHeads[j].rank)
            }
        }
        return rankTeams
    }

    private fun equalize(shadows: List<RankTeam>): List<RankTeam> {
        val rankTeams: List<RankTeam> = ArrayList(shadows)
        do {
            val discrepantTeams = findTeamDiscrepancies(rankTeams)
            val weakerTeam = discrepantTeams.first
            val strongerTeam = discrepantTeams.second
            if (canEqualize(weakerTeam, strongerTeam)) {
                val threshold = calculateThreshold(weakerTeam.remainingSpace, strongerTeam.remainingSpace)
                swapRank(weakerTeam, strongerTeam, threshold)
            } else {
                break
            }
        } while (true)
        return rankTeams
    }

    private fun findTeamDiscrepancies(rankTeams: List<RankTeam>): Pair<RankTeam, RankTeam> {
        val weakerTeam = rankTeams.stream()
            .max(Comparator.comparingInt { obj: RankTeam -> obj.remainingSpace })
            .get()
        val strongerTeam = rankTeams.stream()
            .min(Comparator.comparingInt { obj: RankTeam -> obj.remainingSpace })
            .get()
        return Pair(weakerTeam, strongerTeam)
    }

    private fun canEqualize(weakerTeam: RankTeam, strongerTeam: RankTeam): Boolean {
        val weaker = weakerTeam.remainingSpace
        val stronger = strongerTeam.remainingSpace
        return when {
            (stronger < 0) -> true
            else -> calculateThreshold(weaker, stronger) < 0
        }
    }

    private fun calculateThreshold(weakerSpace: Int, strongerSpace: Int): Int {
        return if (strongerSpace < 0) {
            strongerSpace
        } else (weakerSpace - strongerSpace) / 2 * -1
    }

    private fun swapRank(weakerTeam: RankTeam, strongerTeam: RankTeam, threshold: Int) {
        outerloop@ for (i in 4 downTo 1) {
            val rankOfWeaker = weakerTeam[i]
            for (j in 4 downTo 1) {
                val rankOfStronger = strongerTeam[j]
                if (rankOfWeaker.weight - rankOfStronger.weight == threshold) {
                    strongerTeam.add(rankOfWeaker)
                    strongerTeam.remove(rankOfStronger)
                    weakerTeam.add(rankOfStronger)
                    weakerTeam.remove(rankOfWeaker)
                    if (weakerTeam.remainingSpace >= 0) {
                        break@outerloop
                    }
                }
            }
        }
        strongerTeam.sort()
        strongerTeam.reverse()
        weakerTeam.sort()
        weakerTeam.reverse()
    }
}