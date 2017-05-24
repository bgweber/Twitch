val games = sqlContext.read
    .format("com.databricks.spark.csv")
    .option("header", "false")  
    .option("inferSchema", "true")  
    .load("/Users/bgweber/spark/Games.csv")
    
games.registerTempTable("games"
)
val result = sqlContext.sql("""
with users as (
    select _c0 as User_ID, sum(1) as NumGames 
    from games 
    group by 1 
)
, purchases as (
    select _c0 as User_ID, _c1 as Game_ID, NumGames 
    from games g
    join users u
      on g._c0 = u.User_ID
)
select u.User_ID, v.Game_ID, avg(Tanimoto) as GameWeight
from ( 
    select u.User_ID, v.User_ID as Other_User_ID,
        count(distinct u.Game_ID)/(u.NumGames + v.NumGames - count(distinct u.Game_ID)) as Tanimoto
    from purchases u
    Join purchases v
        on u.Game_ID = v.Game_ID 
    where u.User_ID = 101
    group by u.User_ID, v.User_ID, u.NumGames, v.NumGames
) u
Join purchases v
    on Other_User_ID = v.User_ID
group by u.User_ID, v.Game_ID
order by GameWeight desc
limit 5
""")

result.show(5)
