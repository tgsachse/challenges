# Some meta functions to work with files in this directory.
# Written by Tiger Sachse.

# Test the testing script.
if [ "$1" == "--test" ]
then
    cp ../Solutions/PathOfDestruction.java .
    ./Test.sh
    rm PathOfDestruction.java
fi

# Test the grading script.
if [ "$1" == "--grade" ]
then
    cp ../Solutions/PathOfDestruction.java PathOfDestruction-25.java
    ./Grade.sh
    ./Grade.sh --use-old
    ./Grade.sh --clean
fi

if [ "$1" == "--zip" ]
then
    cp Documentation/PathOfDestruction.pdf .
    zip PathOfDestruction.zip PathOfDestruction.pdf Inputs/* PathOfDestructionTester.java Test.sh
    rm PathOfDestruction.pdf
fi
