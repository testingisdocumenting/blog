---
type: two-sides
---

# Query Games

To list all the games use `GET` [`:file: game-store-list-after-db/request.url.txt`](http://test)

```api-parameters {title: "Game Schema"}
id, String, optional value that will be auto generated if not provided
title, String, game title
type, String, "game type, can be a multi space separated value"
priceUsd, Number, price in USD
```

:include-empty-block: {rightSide: true}

:include-json: game-store-list-after-db/response.json { 
  rightSide: true,
  title: "Games list sample response", 
  pathsFile: "game-store-list-after-db/paths.json"
} 

# Query User Preferences

To query user preferences use `GET` [`:file: game-store-get-user-preferences/request.url.txt`](http://test)

```api-parameters {title: "User Preferences Schema"}
userId, String, user id
favoriteGenre, String, favorite games genre
```

:include-empty-block: {rightSide: true}

:include-json: game-store-get-user-preferences/response.json { 
  rightSide: true,
  title: "User preferences sample response", 
  pathsFile: "game-store-get-user-preferences/paths.json"
} 
