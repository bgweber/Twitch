library(CausalImpact) 
data <- read.csv(data, file = "DailySessions.csv")

# Create a DataFrame and plot the input data 
ts <- cbind(data$test, data$control)
matplot(ts, type = "l")

# Use two week prior and post periods and plot results  
pre.period <- c(1, 14)
post.period <- c(15, 30) 
impact <- CausalImpact(ts, pre.period, post.period)

# Plot the results and explain the outcome 
plot(impact, c("original", "pointwise")) 
summary(impact, "report")
