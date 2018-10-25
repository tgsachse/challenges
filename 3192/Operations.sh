# Operations to test and package this assignment.
# Written by Tiger Sachse.

ASSIGNMENT="PathOfDestruction"

# Test the testing script.
function testTestScript {
    cp ../Solutions/$ASSIGNMENT.java TestSuite/
    cd TestSuite/
    ./Test.sh
    rm $ASSIGNMENT.java
}

# Test the grading script.
function testGradeScript {
    cp ../Solutions/$ASSIGNMENT.java GradeSuite/ts_1_1_${ASSIGNMENT}-1.java
    cd GradeSuite/
    ./Grade.sh --single ts_1_1_${ASSIGNMENT}-1.java
    echo ""
    cat Results/ts_1_1_${ASSIGNMENT}-1.java
    ./Grade.sh --clean
    rm ts_1_1_${ASSIGNMENT}-1.java
}

# Zip the test suite and PDF for students or graders.
function zipAssignment {
    cd Documentation
    rm $ASSIGNMENT.pdf
    libreoffice --headless --convert-to pdf $ASSIGNMENT.odt
    cp $ASSIGNMENT.pdf ..
    cd ..

    if [ "$1" == "student" ]
    then
        zip ${ASSIGNMENT}Assignment.zip $ASSIGNMENT.pdf TestSuite/* TestSuite/Inputs/*
    elif [ "$1" == "grader" ]
    then
        zip ${ASSIGNMENT}GradeSuite.zip $ASSIGNMENT.pdf GradeSuite/* GradeSuite/Inputs/*
    fi
    rm $ASSIGNMENT.pdf
}

# Use the provided Python script to generate a new input.
function generateInput {
    python3 GenerateTest.py
}

# Read the arguments and act accordingly.
case $1 in
    "--test")
        testTestScript
        ;;
    "--grade")
        testGradeScript
        ;;
    "--zip")
        zipAssignment $2
        ;;
    "--gen")
        generateInput
        ;;
esac
