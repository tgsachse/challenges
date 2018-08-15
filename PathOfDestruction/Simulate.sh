# Automatically load a prewritten solution to test the
# testing and grading scripts.

if [ "$1" == "--test" ]
then
    cp ../Solutions/PathOfDestruction.java .
    ./Test.sh
    rm PathOfDestruction.java
fi

if [ "$1" == "--grade" ]
then
    cp ../Solutions/PathOfDestruction.java PathOfDestruction-25.java
    ./Grade.sh
    ./Grade.sh --use-old
    ./Grade.sh --clean
fi
