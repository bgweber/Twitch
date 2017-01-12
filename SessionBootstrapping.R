library(boot)
data <- read.csv("UserSessions.csv")

# Function for computing the difference of differences 
run_DiD <- function(data, indices){
  d <- data[indices,]
  new <- mean(d$postval[d$group=='Test'])/
         mean(d$priorval[d$group=='Test'])
  old <-mean(d$postval[d$expgroup=='Control'])/
         mean(d$priorval[d$expgroup=='Control'])
  return((new - old)/old * 100.0) 
}

# perform the bootstrapping and output the results 
boot_est <- boot(data, run_DiD, R=1000, 
                 parallel="multicore", ncpus = 8)  
quantile(boot_est$t, c(0.025, 0.975)) 
plot(density(boot_est$t), xlab = "% Increase vs. Control")
