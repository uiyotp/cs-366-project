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

#### AT LEAST HALF THE QUERIES MUST BE LEVEL 2 OR 3
#### We each write 1/3 of the queries.  I will take care of the interface, since I am in charge of front end.
#### Take a look at the schema at the bottom to help write queries.

-----------------------------------------
#### SQL queries (6 total) - 2 each (I recommend you guys do the 4 that I described below.  You could also write something else you think of that we will need.)

##### 1. Select the top 10 games (based on *critic score*, with at least 10 critic reviews)
'SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating 
FROM game AS g  
INNER JOIN publisher AS p  
ON g.pub_ID = p.pub_ID  
INNER JOIN developer AS d  
ON g.dev_ID = d.dev_ID  
WHERE g.critic_count > 10  
ORDER BY g.critic_score desc  
LIMIT 10;'  

##### 2. Select the top 10 games (based on *user score*, with at least 10 user reviews)
'SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating 
FROM game AS g  
INNER JOIN publisher AS p  
ON g.pub_ID = p.pub_ID  
INNER JOIN developer AS d  
ON g.dev_ID = d.dev_ID  
WHERE g.user_count > 10  
ORDER BY g.user_score desc  
LIMIT 10;'  

##### 3. Select the top 10 games based (based on *total sales*, will have to add all four types of sales together for each game)
SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating
FROM game g, publisher p, developer d, sales s, 
WHERE g.game_ID in(
SELECT game_ID 
FROM game g 
WHERE game_ID in(
SELECT g.game_ID, (s.na_sales + s.eu_sales + s.jp_sales + s.other_sales ) AS total_sales
FROM game g, sales s
WHERE s.game_ID=g.game_ID 
ORDER BY total_sales desc
LIMIT 10));

##### 4. Insert a new game
INSERT INTO game(age_rating, year, platform, name, genre, pub_ID, critic_score, critic_count, user_score, user_count, dev_ID)  
VALUES (E10+, 2017, Switch, Breath of the Wild, Adventure, 0014, 97, 109, 8.7, 16687, 0014) ;

##### 5. Insert a user review (will have to take user_score times user_count, then add new score to that total, then add one to user_count and calculate the new score by dividing the total by the new user_count, then setting the user_score to the total)
UPDATE game
SET game_ID = Breath of the Wild
WHERE user_score = (user_count * user_score +  (Java input new user score))/++user_count; 

##### 6. Insert a critic review (will have to take critic_score times critic_count, then add new score to that total, then add one to critic_count and calculate the new score by dividing the total by the new critic_count, then setting the critic_score to that number)
UPDATE game
SET game_ID = Breath of the Wild
WHERE critic_score = (critic_count * critic_score +  (Java input new critic score))/++critic_count; 

------------------------------------
#### Stored Procedures (4 total) - 1 each (I recommend you guys do the 2 that I described below.  You could also write something else you think of that we will need.)

##### 1. Select the top 10 games by user selected genre (based on *sales*, will have to add all four types of sales together for each game)
delimiter $$
drop procedure if exists getTopSalesGenre;
create procedure getTopSalesGenre(IN genreChoice VARCHAR(50))
begin
SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating 
FROM game g, publisher p, developer d, sales s, 
WHERE g.game_ID in(
SELECT game_ID
FROM game g 
WHERE game_ID in(
SELECT g.game_ID, (s.na_sales + s.eu_sales + s.jp_sales + s.other_sales ) AS total_sales 
FROM game g, sales s
WHERE s.game_ID=g.game_ID 
ORDER BY total_sales desc 
LIMIT 10));
end $$ 
delimiter;

##### 2. Select the top 10 games by user selected genre (based on *user score*, with at least 10 critic reviews)
delimiter $$  
drop procedure if exists getTopUserGenre;  
create procedure getTopUserGenre(IN genreChoice VARCHAR(50))  
begin  
SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating  
FROM game AS g  
INNER JOIN publisher AS p  
ON g.pub_ID = p.pub_ID  
INNER JOIN developer AS d  
ON g.dev_ID = d.dev_ID  
WHERE g.user_count > 10 AND genre = genreChoice  
ORDER BY g.user_score desc  
LIMIT 10;  
end $$  
delimiter;  

##### 3. Select the top 10 games by user selected genre (based on *critic score*, with at least 10 critic reviews)
delimiter $$  
drop procedure if exists getTopCriticGenre;  
create procedure getTopCriticGenre(IN genreChoice VARCHAR(50))  
begin  
SELECT g.name, p.name, d.name, g.year, g.genre, g.platform, g.critic_score, g.user_score, g.age_rating 
FROM game AS g  
INNER JOIN publisher AS p  
ON g.pub_ID = p.pub_ID  
INNER JOIN developer AS d  
ON g.dev_ID = d.dev_ID  
WHERE g.critic_count > 10 AND genre = genreChoice 
ORDER BY g.critic_score desc  
LIMIT 10;
end $$  
delimiter; 

##### 4. Trigger upon inserting a new game (check if publisher exists, check if developer exists, if not add a new ones)
CREATE TRIGGER [this is optional.]new_game_trigger
ON game
FOR Insert

IF !EXISTS (check from developer names)
  INSERT INTO developer(name, dev_ID)
  VALUE (ID Studios, 0055)
IF !EXISTS (check from publisher names)
  INSERT INTO developer(pub_ID, name)
  VALUE (0034, EA)

### Relational Schema
> + publisher(pub_ID, name)
>   + pub_ID is the primary key.
> + game(game_ID, age_rating, year, platform, name, genre, pub_ID, critic_score, critic_count, user_score, user_count, dev_ID)
>   + game_ID is the primary key. pub_ID and dev_ID are foreign keys. Published and Made relations are not needed due to being a many-one relationship.
> + developer(name, dev_ID)
>   + dev_ID is the primary key.
> + SALES(na_sales, eu_sales, jp_sales, other_sales, game_ID)
>   + Sales does not have a primary key due to being a weak entity. It relies on foreign key game_ID, from the Game entity. In addition, it is not necessary to include hasSales in the relational schema due to sales being a weak entity.

### Interface
Dont mind the "All Games List" tab title.  That tab is the Search Games tab.  I figured it would not be a good idea to show 16,000 games to the end user.  (But I didn't want to redo the pictures after I took them :))
>![Top Games](https://github.com/uiyotp/cs-366-project/raw/master/interfacePictures/topGames.PNG "Top Games")
>![Edit Games 1](https://github.com/uiyotp/cs-366-project/raw/master/interfacePictures/editGames-1.PNG "Edit Games 1")
>![Edit Games 2](https://github.com/uiyotp/cs-366-project/raw/master/interfacePictures/editGames-2.PNG "Edit Games 2")
>![Edit Games 3](https://github.com/uiyotp/cs-366-project/raw/master/interfacePictures/editGames-3.PNG "Edit Games 3")
>![Review Games](https://github.com/uiyotp/cs-366-project/raw/master/interfacePictures/reviewGames.PNG "Review Games")
>![Search Games](https://github.com/uiyotp/cs-366-project/raw/master/interfacePictures/searchGames.PNG "Search Games")
>![Credits](https://github.com/uiyotp/cs-366-project/raw/master/interfacePictures/credits.PNG "Credits")
