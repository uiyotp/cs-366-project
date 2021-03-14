# Checkpoint 3

### ER Diagram Revisions
> We are going to be adding a Publisher ID and a Developer ID attribute (PubID and DevID) to the publisher and developer.
> We will rename the "isa" relationship between Game and Reviewed Game to "hasReviews"
> We are changing the Sales entity to a weak entity that is dependent on Game
> Besides these changes, we will use the same ER Diagram as Checkpoint 2

### Relational Schema
> PUBLISHER(PubID, Name)
> PubID is the primary key.
> GAME(GameID, rating, game, year, platform, name, PubID, DevID)
> GameID is the primary key. PubID and DevID are foreign keys. Published and Made are not needed due to being a many-one relationship.
> DEVELOPER(name, DevID)
> DevID is the primary key.
> SALES(no sales, eu sales, jp sales, other sales, global sales, GameID)
> Sales does not have a primary key due to being a weak entity. It relies on foreign key GameID, from the Game entity. In addition, has is not necessary to include in the relational schema due to sales being a weak entity.
> REVIEWED GAME (GameID, critic score, critic count, user score, user count)
> GameID is the primary key.
> hasReviews(GameID, GameID)
> GameID from Game and GameID from Reviewed Game are both primary keys.

### Non Trivial FDs and Normal Forms
Game
GameID -> Name, GameID -> Platforms, GameID -> Year, GameID -> Genre, GameID -> AgeRating, 
GameID -> UserCount, GameID -> UserScrore, GameID -> CriticCount, GameID -> CriticScore

Sales
GameID -> EUSales, GameID -> NASales, GameID -> JPSales, GameID -> OtherSales

Publisher
PublisherID -> Name

Developer
DeveloperID -> Name

### Software Platforms/Languages
> We will be using Java for back end and JavaScript/HTML for the front end

### Labor Divisions
> + Solomon - Team Lead and Main Front-End Developer
> + Melissa - Main Back-End Developer
> + Nathaniel - Mostly Back-End Developer, Helps out where needed
