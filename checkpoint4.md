# Checkpoint 4

### SQL queries
> + Level 1 - standard SQL queries using only one table.
> + Level 2 - SQL queries on multiple tables that join with each other or subqueries involve more than one table. Use group by, having, order by, built-in functions if applicable.
> + Level 3 - 
Queries in level 2 and one of the followings:
Complicated calculation needed,
Subquery in FROM clause,
Self-join,
Nested subquery in WHERE clause,
Anything that goes beyond simple group by/having/order/built-in functions
#### MAKE SURE THAT AT LEAST HALF OF THE QUERIES YOU WRITE ARE LEVEL 2 OR 3
#### We each write 1/3 of the queries.  I will take care of the interface, since I am in charge of front end.
#### Take a look at the schema at the bottom to help write queries.
##### SQL queries (6 total) - 2 each
> + 
> + 
> + 
> + 
> + 
> + 
##### Store Procedures (3 total) - 1 each
> + 
> + 
> + 
##### Stored Procedures (9 total) - 3 each
> + 
> + 
> + 
> + 
> + 
> + 
> + 
> + 
> + 

### Interface
> + I will take of of the interface preview.

### Relational Schema
> + PUBLISHER(PubID, Name)
>   + PubID is the primary key.
> + GAME(GameID, ageRating, game, year, platform, name, PubID, critic score, critic count, user score, user count, DevID)
>   + GameID is the primary key. PubID and DevID are foreign keys. Published and Made relations are not needed due to being a many-one relationship.
> + DEVELOPER(name, DevID)
>   + DevID is the primary key.
> + SALES(na sales, eu sales, jp sales, other sales, GameID)
>   + Sales does not have a primary key due to being a weak entity. It relies on foreign key GameID, from the Game entity. In addition, it is not necessary to include hasSales in the relational schema due to sales being a weak entity.
