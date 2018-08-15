# Automatically load a prewritten solution to test the
# testing and grading scripts.
# Written by Tiger Sachse.

# Run these lines to use the Test.sh script.
if [ "$1" == "--test" ]
then
    cp ../Solutions/PathOfDestruction.java .
    ./Test.sh
    rm PathOfDestruction.java
fi

# Run these lines for the Grade.sh script.
if [ "$1" == "--grade" ]
then
    cp ../Solutions/PathOfDestruction.java PathOfDestruction-25.java
    ./Grade.sh
    ./Grade.sh --use-old
    ./Grade.sh --clean
fi
