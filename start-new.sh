mkdir -p outputs

# set initial variables
export SUFFIX=$RANDOM
export LOCATION='eastus'
export USERNAME='briar'
export ADMIN_PASSWORD='w@lkingth3d0g'
export RG=reddog-spring-$SUFFIX
export LOGFILE_NAME="./outputs/${RG}.log"

./walk-the-dog.sh $RG $LOCATION $SUFFIX $USERNAME $ADMIN_PASSWORD 2>&1 | tee -a $LOGFILE_NAME