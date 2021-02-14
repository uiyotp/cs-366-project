# Checkpoint 1

## Video Game Sales Database

### Group members
> + Solomon Paprocki - paprockisj04@uww.edu
> + Melissa Olhausen - olhausenmm13@uww.edu
> + Nathaniel Morris - morrisnl24@uww.edu

### Motivation and project description
> This application will be web-based and will be about video games.  It is important to have a database for this so that people can learn more about what kind of video games have had the most success and critical acclaim.  This app will be able to show the end user lists of the top games for each genre based on sales or ratings, it will also be able to show the top games irregardless of genre.  I will have an easy way to interact with the information so that the end user can display and see what they are interested in from the video game database.

### Datasets
> [Video Games Sales Dataset](https://data.world/sumitrock/video-games-sales/workspace/file?filename=Video_Games.csv)  
> This is our dataset.  We chose this dataset because we like video games and we wanted to do a video game related dataset.  It has over 16,000 rows of video games, a separate row for each video game/platform pair.  The attributes include name, platform, year of release, genre, publisher, sales (from na, eu, jp, other, and global), critic count and score, user reviewer count and score, developer, and rating.

### Project Schedule

Dates | Deliverables
--- | ---
2/7-2/20 | Finish Checkpoint 2
2/20-3/14 | Finish Checkpoint 3
3/14-4/11 | Finish Checkpoint 4
4/11-5/7 | Finish Final Project

### Assessment plan
> + As a user, I want to be able to see the best video games for each genre.
> + As a user, I want to be able to see the best overall video games.
> + As a user, I want to be able to choose the best video game definition metric (By user reviews, critic reviews, or sales).
> + As a developer, I want to create a database that is secure and cannot be SQL injeceted.

### Personnel management
> + Solomon Paprocki - Team Lead and Web Master
> + Melissa Olhausen - Lead Java Developer
> + Nathaniel Morris - Developer

### Communication
> We will communicate via Discord, maybe we will add a virtual meeting or two if needed.

# Checkpoint 2

### Application Description
> This will be a video game database that stores information such as sales, ratings, genre, platform, and more.  It will include the following features:
> + Have a fully integrated search feature where the user can search for video games by top grossing, top reviewed, developer, publisher, genre, name, platform, and more.
> + Give the user the ability to add their own review for any game.  They will only be allowed to enter user reviews.  This will be added to the calculation and the overall game score will be adjusted accordingly.
> + The user will also be allowed to delete any games from the database that do not have any reviews.

### ER Diagram
>![Our ER Diagram](https://github.com/uiyotp/cs-366-project/raw/master/video-game-sales-ERdiagram.png "Our ER Diagram")
#### Entity Sets and Primary Keys
> Entity sets include Sales, Publisher, Developer, Game, and Reviewed Game.  Publisher attributes consist of only the publisher's name, which is the primary key.  Developer attributes consist of only the developer's name, which is the primary key.  The Game Entity Set has Game ID as the primary key, and also includes the following attributes: name, platform, year, genre, and rating.  Sales includes the primary key Game ID, which will help identify which game the sales belong to, and the following other attributes: North American Sales, European Sales, Japanese Sales, Other Sales, and Global Sales.  Reviewed Game, which is a child entity of Game, consists of all the same attributes as Game, except it also has critic score, critic count, user score, and user count.  Reviewed Game's primary key is Game ID.  We needed to have this as a subclass of Game because only about half of the games have reviews.  
#### Relationships
> There are a total of 4 relationships.  Published is one to many, meaning that one publisher can have many games. This relationship requires referential integrity, meaning that if a game was made by a publisher, then it must be a publisher that is in the database.  Published requires at least one game and exactly one publisher.  Every game must have a publisher.  Made is one to many, meaning that one developer can have many games. This relationship requires referential integrity, meaning that if a game was made by a developer, then it must be a developer that is in the database.  Made requires at least one game and exactly one developer.  It is not required for every game to have a separate developer, since the publisher can also be the one that made the game.  Has is one to one, meaning that one game can have only one group of sales information.  This relationship requires referential integrity, meaning that if a sales entity references a game by ID, then that game must exist in the database.  Every game must have sales information.  The last "relationship" is isa.  I put relationship in quotes because it is not like the other relationships, it is an inheritance triangle.  Isa means that a reviewed game is also a game.  It is a inheritance triangle since Reviewed Game inherits all attributes from the game entity.
#### Assumptions
> I assumed that each game has a single publisher or developer, that there aren't more than two companies who worked on the game.  I assumed that all developers and publishers have unique names.  I also assumed that all non-key attributes are not necessarily unique.

### Functionalities
> As previously outlined above in the Application Description Section, we will have the following functionalities:
> + Have a fully integrated search feature where the user can search for video games by top grossing, top reviewed, developer, publisher, genre, name, platform, and more.  This will involve joins (to display information from all databases in the same section), projection (returning columns that the user asks for), selection (returning which games are part of the search), and aggregation (computing which titles are the top grossing or reviewed).
> + Give the user the ability to add their own review for any game.  They will only be allowed to enter user reviews.  This will be added to the calculation and the overall game score will be adjusted accordingly.  This will involve insertion (if the user adds the first review to a game, we will have to insert a new row into the reviewed game database) and modification (the user review total and user score will have to be modified).
> + The user will also be allowed to delete any games from the database that do not have any reviews.  This will involve deletion (the row will be deleted from the database).

### Queries
> All of the commonly asked queries are mentioned in the above Functionalities section.  To review what was previously mentioned:
> + Search by many parameters
> + Choose which columns to return
> + Add reviews
> + Delete games

### User Interface
> We will have a web-based user interface created with HTML and JavaScript.

### Programming Languages
> We will be using JavaScript for the User Interface and we will be using Java for the main functionalities of the project.
