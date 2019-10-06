# Operations to test and package this assignment.
# Written by Tiger Sachse.

ASSIGNMENT="BabylonianSort"

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
        zip ${ASSIGNMENT}Assignment.zip $ASSIGNMENT.pdf TestSuite/* TestSuite/Inputs/* TestSuite/Outputs/*
    elif [ "$1" == "grader" ]
    then
        zip ${ASSIGNMENT}GradeSuite.zip $ASSIGNMENT.pdf GradeSuite/* GradeSuite/Inputs/* GradeSuite/Outputs/*
    fi
    rm $ASSIGNMENT.pdf
}

# Generate a new number list for input.
function generateInput {
    python3 GenerateNumberList.py $1 $2
}

# Sort a given input.
function sortInput {
    cp ../Solutions/$ASSIGNMENT.java .
    javac $ASSIGNMENT.java ${ASSIGNMENT}Driver.java
    java ${ASSIGNMENT}Driver $1
    rm $ASSIGNMENT.java
    rm *.class
}

# Create a new input and create it's sorted output.
function generateCase {
    generateInput $1 $2
    mv "output.txt" "Input.txt"
    sortInput "Input.txt"
    mv "output.txt" "Output.txt"
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
    "--gen-input")
        generateInput $2 $3
        ;;
    "--gen-case")
        generateCase $2 $3
        ;;
    "--sort")
        sortInput $2
        ;;
esac
