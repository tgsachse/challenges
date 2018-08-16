# Meta functions to test contents of this assignment directory.
# Written by Tiger Sachse.

ASSIGNMENT="BabylonianSort"

# Test a sample assignment.
if [ "$1" == "--test" ]
then
    cp ../Solutions/$ASSIGNMENT.java .
    ./Test.sh
    rm $ASSIGNMENT.java
fi

# Grade a sample assignment.
if [ "$1" == "--grade" ]
then
    cp ../Solutions/$ASSIGNMENT.java $ASSIGNMENT-25.java
    ./Grade.sh
    ./Grade.sh --use-old
    ./Grade.sh --clean
fi

# Zip contents for students.
if [ "$1" == "--zip" ]
then
    cp Documentation/$ASSIGNMENT.pdf .
    zip $ASSIGNMENT.zip $ASSIGNMENT.pdf Inputs/* $ASSIGNMENTTester.java Test.sh
    rm $ASSIGNMENT.pdf
fi
