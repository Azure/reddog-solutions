mkdir -p outputs

# get params from config.json file
export CONFIG="$(cat config.json | jq -r .)"

export LOCATION="$(echo $CONFIG | jq -r '.location')"
export USERNAME="$(echo $CONFIG | jq -r '.username')"
export ADMIN_PASSWORD="$(echo $CONFIG | jq -r '.adminpassword')"
export DEPLOY_TARGET="$(echo $CONFIG | jq -r '.deploytarget')"

# set initial variables
export SUFFIX=$RANDOM
export RG=reddog-spring-$SUFFIX
export LOGFILE_NAME="./outputs/${RG}.log"

./walk-the-dog.sh $RG $LOCATION $SUFFIX $USERNAME $ADMIN_PASSWORD $DEPLOY_TARGET 2>&1 | tee -a $LOGFILE_NAME