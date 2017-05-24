install.packages("recommenderlab")
library(recommenderlab)

matrix <- as(read.csv("Games.csv"),"realRatingMatrix")
model <-Recommender(matrix, method = "UBCF")
games <- predict(model, matrix["101",], n=5)
as(games, "list")
