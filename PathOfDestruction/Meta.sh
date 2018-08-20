# Some meta functions to work with files in this directory.
# Written by Tiger Sachse.

# Test the testing script.
if [ "$1" == "--test" ]
then
    cp ../Solutions/PathOfDestruction.java TestSuite/
    cd TestSuite/
    ./Test.sh
    rm PathOfDestruction.java
fi

# Test the grading script.
if [ "$1" == "--grade" ]
then
    cp ../Solutions/PathOfDestruction.java GradeSuite/PathOfDestruction-25.java
    cd GradeSuite/
    ./Grade.sh
    ./Grade.sh --use-old
    ./Grade.sh --clean
fi

# Zip the test suite and PDF for students.
if [ "$1" == "--zip-student" ]
then
    cp Documentation/PathOfDestruction.pdf .
    zip PathOfDestruction.zip PathOfDestruction.pdf TestSuite/* TestSuite/Inputs/*
    rm PathOfDestruction.pdf
fi

if [ "$1" == "--zip-grader" ]
then
    cp Documentation/PathOfDestruction.pdf .
    zip PathOfDestructionGradeSuite.zip PathOfDestruction.pdf GradeSuite/* GradeSuite/Inputs/*
    rm PathOfDestruction.pdf
fi
