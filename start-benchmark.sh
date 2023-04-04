mkdir -p ./results

DATE=$(date '+%H-%M-%d-%m-%Y')
echo "Results will be output into file results-${DATE}-[threadCount].txt"
sleep 1

echo "Number of threads $TC"
java -jar target/benchmarks.jar $JMH_BENCH_NAME -r $JMH_MIN_MEASUREMENT_DURATION -f $JMH_FORK -tu $JMH_TIME_UNIT -w $JMH_MIN_WARMUP_DURATION -wi $JMH_WARMUP_ITERATIONS -i $JMH_MEASUREMENT_ITERATIONS -t $JMH_THREAD_COUNT -to $JMH_TIMEOUT -o "./results/results-${DATE}-$JMH_THREAD_COUNT.txt"
