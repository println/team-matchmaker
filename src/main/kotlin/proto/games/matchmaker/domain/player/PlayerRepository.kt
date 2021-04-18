package proto.games.matchmaker.domain.player

internal interface PlayerRepository {
    val all: Collection<Player?>?
}