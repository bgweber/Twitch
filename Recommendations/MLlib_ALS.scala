import org.apache.spark.mllib.recommendation._

val games = sqlContext.read
    .format("com.databricks.spark.csv")
    .option("header", "false")  
    .option("inferSchema", "true")  
    .load("/Users/bgweber/spark/Games.csv")

val ratings = games.rdd.map(row =>
  Rating(row.getInt(0), row.getInt(1), 1)
)

val rank = 10
val model = ALS.trainImplicit(ratings, rank, 5, 0.01, 1)
val recommendations = model.recommendProducts(101, 5)
recommendations.foreach(println)
