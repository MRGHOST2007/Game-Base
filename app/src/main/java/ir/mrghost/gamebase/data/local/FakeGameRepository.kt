package ir.mrghost.gamebase.data.local

import ir.mrghost.gamebase.R
import ir.mrghost.gamebase.data.local.Game
import ir.mrghost.gamebase.utils.GameGenre

interface GameRepository {
    suspend fun getAllGames(): List<Game>
    suspend fun getGameAwards(): List<Game>
    suspend fun getTopPagerGames(): List<Game>
    suspend fun getGamesByGenre(genre: GameGenre): List<Game>
}

class FakeGameRepository : GameRepository {
    override suspend fun getAllGames(): List<Game> = gameList

    override suspend fun getGameAwards(): List<Game> = listOf(
        gameList.last(),
        gameList[2],
        gameList[13],
        gameList[14]
    )

    override suspend fun getTopPagerGames(): List<Game> = listOf(
        gameList.random(),
        gameList.random(),
        gameList.random()
    )

    override suspend fun getGamesByGenre(genre: GameGenre): List<Game> =
        gameList.filter { it.genre == genre }

    val gameList: List<Game> = listOf(

        Game(
            id = 1,
            title = "Hollow Knight",
            releaseYear = 2019,
            developer = "Team Cherry",
            genre = GameGenre.Platformer,
            description = "Explore HollowNest as a nameless knight to discover its secrets.",
            rating = 5.0f,
            price = 14.99f,
            image = R.drawable.hk
        ),
        Game(
            id = 2,
            title = "The Legend of Zelda: Breath of the Wild",
            releaseYear = 2017,
            developer = "Nintendo",
            genre = GameGenre.Adventure,
            description = "Explore a vast Hyrule in this open-air adventure classic.",
            rating = 4.9f,
            price = 59.99f,
            image = R.drawable.zelda
        ),
        Game(
            id = 3,
            title = "The Witcher 3: Wild Hunt",
            releaseYear = 2015,
            developer = "CD Projekt Red",
            genre = GameGenre.RPG,
            description = "Become a monster hunter in a dark fantasy world.",
            rating = 4.9f,
            price = 39.99f,
            image = R.drawable.witcher
        ),
        Game(
            id = 4,
            title = "Civilization VI",
            releaseYear = 2016,
            developer = "Firaxis Games",
            genre = GameGenre.Strategy,
            description = "Build an empire that stands the test of time.",
            rating = 4.6f,
            price = 59.99f,
            image = R.drawable.civilization
        ),
        Game(
            id = 5,
            title = "Hades",
            releaseYear = 2020,
            developer = "Supergiant Games",
            genre = GameGenre.Action,
            description = "Roguelike dungeon crawling with Greek mythology charm.",
            rating = 4.8f,
            price = 24.99f,
            image = R.drawable.hades
        ),
        Game(
            id = 6,
            title = "Stardew Valley",
            releaseYear = 2016,
            developer = "ConcernedApe",
            genre = GameGenre.Simulation,
            description = "Farm, mine, fish, and build relationships.",
            rating = 4.9f,
            price = 14.99f,
            image = R.drawable.stardew
        ),
        Game(
            id = 7,
            title = "Resident Evil 2 Remake",
            releaseYear = 2019,
            developer = "Capcom",
            genre = GameGenre.Horror,
            description = "Tense survival horror in Raccoon City.",
            rating = 4.7f,
            price = 39.99f,
            image = R.drawable.re2
        ),
        Game(
            id = 8,
            title = "Portal 2",
            releaseYear = 2011,
            developer = "Valve",
            genre = GameGenre.Casual,
            description = "Brilliant first-person puzzle game with co-op.",
            rating = 4.9f,
            price = 9.99f,
            image = R.drawable.portal
        ),
        Game(
            id = 9,
            title = "Street Fighter 6",
            releaseYear = 2023,
            developer = "Capcom",
            genre = GameGenre.Action,
            description = "Fast-paced, modern fighting game.",
            rating = 4.7f,
            price = 59.99f,
            image = R.drawable.streetfighter
        ),
        Game(
            id = 10,
            title = "Among Us",
            releaseYear = 2018,
            developer = "Innersloth",
            genre = GameGenre.Casual,
            description = "Social deduction game of teamwork and betrayal.",
            rating = 4.4f,
            price = 4.99f,
            image = R.drawable.amongus
        ),
        Game(
            id = 11,
            title = "Doom Eternal",
            releaseYear = 2020,
            developer = "id Software",
            genre = GameGenre.Action,
            description = "Rip-and-tear through demons with metal music.",
            rating = 4.6f,
            price = 39.99f,
            image = R.drawable.doom
        ),
        Game(
            id = 12,
            title = "The Sims 4",
            releaseYear = 2014,
            developer = "Maxis",
            genre = GameGenre.Simulation,
            description = "Control virtual people and build homes.",
            rating = 4.3f,
            price = 19.99f,
            image = R.drawable.sims
        ),
        Game(
            id = 13,
            title = "Elden Ring",
            releaseYear = 2022,
            developer = "FromSoftware",
            genre = GameGenre.RPG,
            description = "Difficult open-world action RPG with deep lore.",
            rating = 5f,
            price = 59.99f,
            image = R.drawable.eldenring
        ),
        Game(
            id = 14,
            title = "FC26",
            releaseYear = 2025,
            developer = "EA Sports",
            genre = GameGenre.Simulation,
            description = "Realistic football (soccer) simulation.",
            rating = 4.4f,
            price = 69.99f,
            image = R.drawable.fc26
        ),
        Game(
            id = 15,
            title = "Celeste",
            releaseYear = 2018,
            developer = "Extremely OK Games",
            genre = GameGenre.Platformer,
            description = "Tight, touching precision platformer about anxiety.",
            rating = 4.8f,
            price = 19.99f,
            image = R.drawable.celeste
        ),
        Game(
            id = 16,
            title = "Dead by Daylight",
            releaseYear = 2016,
            developer = "Behaviour Interactive",
            genre = GameGenre.Horror,
            description = "4v1 asymmetric multiplayer horror game.",
            rating = 4.1f,
            price = 19.99f,
            image = R.drawable.deadbydaylight
        ),
        Game(
            id = 17,
            title = "Stray",
            releaseYear = 2022,
            developer = "BlueTwelve Studio",
            genre = GameGenre.Adventure,
            description = "Play as a stray cat in a cybercity.",
            rating = 4.7f,
            price = 29.99f,
            image = R.drawable.stray
        ),
        Game(
            id = 18,
            title = "Baldur's Gate 3",
            releaseYear = 2023,
            developer = "Larian Studios",
            genre = GameGenre.RPG,
            description = "Deep, choice-driven D&D-based RPG.",
            rating = 4.9f,
            price = 59.99f,
            image = R.drawable.baldurs
        ),
        Game(
            id = 19,
            title = "Mario Kart 8 Deluxe",
            releaseYear = 2017,
            developer = "Nintendo",
            genre = GameGenre.Simulation,
            description = "Chaotic, fun arcade racing for all ages.",
            rating = 4.8f,
            price = 59.99f,
            image = R.drawable.mario
        ),
    )

}