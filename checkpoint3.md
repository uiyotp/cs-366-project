# Checkpoint 3

### ER Diagram Revisions
> + We are going to be adding a Publisher ID and a Developer ID attribute (PubID and DevID) to the publisher and developer.  This is to use as the primary key for both.
> + We will remove reviewedGame and instead attach all of reviewedGames' attributes to Game.  This is because it makes the most sense to us.
> + We are changing the Sales entity to a weak entity that is dependent on Game, with the relationship hasSales.  This just makes the most sense, since everything is dependent on GameID.
> + We are removing the global sales attribute, since all the other sales attributes would be dependent on this.  We will instead calculate global sales using all of the other sale numbers.

### Relational Schema
> + PUBLISHER(PubID, Name)
>   + PubID is the primary key.
> + GAME(GameID, rating, game, year, platform, name, PubID, critic score, critic count, user score, user count, DevID)
>   + GameID is the primary key. PubID and DevID are foreign keys. Published and Made relations are not needed due to being a many-one relationship.
> + DEVELOPER(name, DevID)
>   + DevID is the primary key.
> + SALES(na sales, eu sales, jp sales, other sales, GameID)
>   + Sales does not have a primary key due to being a weak entity. It relies on foreign key GameID, from the Game entity. In addition, it is not necessary to include hasSales in the relational schema due to sales being a weak entity.

### Non Trivial FDs and Normal Forms
##### Game
> (Is BCNF)
> + GameID -> Name, GameID -> Platform, GameID -> Year, GameID -> Genre, GameID -> AgeRating, 
> GameID -> UserCount, GameID -> UserScore, GameID -> CriticCount, GameID -> CriticScore

##### Sales
> (Is BCNF)
> + GameID -> EUSales, GameID -> NASales, GameID -> JPSales, GameID -> OtherSales

##### Publisher
> (Is BCNF)
> + PublisherID -> Name

##### Developer
> (Is BCNF)
> + DeveloperID -> Name

### Software Platforms/Languages
> We will be using Java for back end and JavaScript/HTML for the front end

### Labor Divisions
> + Solomon - Team Lead and Main Front-End Developer
> + Melissa - Main Back-End Developer
> + Nathaniel - Mostly Back-End Developer, Helps out where needed
