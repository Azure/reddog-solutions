mkdir -p outputs

# set initial variables
export SUFFIX=$RANDOM
export LOCATION="westus"
export RG=reddog-spring-$SUFFIX
export LOGFILE_NAME="./outputs/${RG}.log"

./walk-the-dog.sh $RG $LOCATION 2>&1 | tee -a $LOGFILE_NAME
