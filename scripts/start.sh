mkdir -p .././outputs

FILE=config.json
if [ ! -f "$FILE" ]; then
    echo "$FILE does not exist"
    exit 1
fi

# get params from config.json file
export CONFIG="$(cat config.json | jq -r .)"

# set variables
export LOCATION="$(echo $CONFIG | jq -r '.location')"
export USERNAME="$(echo $CONFIG | jq -r '.username')"
export USERNAME_CUT=${USERNAME:0:5}
export ADMIN_PASSWORD="$(echo $CONFIG | jq -r '.adminpassword')"
export DEPLOY_TARGET="$(echo $CONFIG | jq -r '.deploytarget')"
export INCLUDE_OPENAI="$(echo $CONFIG | jq -r '.include_openai')"
export SUFFIX=$RANDOM
export RG=reddog-$USERNAME_CUT-$DEPLOY_TARGET-$SUFFIX
# make it lowercase
export RG=$(echo $RG | tr '[:upper:]' '[:lower:]')
export LOGFILE_NAME=".././outputs/${RG}.log"

./walk-the-dog.sh $RG $LOCATION $SUFFIX $USERNAME_CUT $ADMIN_PASSWORD $DEPLOY_TARGET $INCLUDE_OPENAI 2>&1 | tee -a $LOGFILE_NAME