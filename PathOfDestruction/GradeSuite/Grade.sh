# Automatically grade all assignment submissions.
# Written by Tiger Sachse.

FAILED_DIR="Failed"
RESULTS_DIR="Results"
SUBMIT_DIR="Submissions"
SUBMIT_ZIP="submissions.zip"
ASSIGNMENT_NAME="PathOfDestruction"

SUBMIT_STEM_PATTERN="^$SUBMIT_DIR/"
CATCHALL_PATTERN="[a-z]+_[0-9]+_[0-9]+_.*\.java"
SUBMIT_PATTERN="[a-z]+_[0-9]+_[0-9]+_$ASSIGNMENT_NAME(\-[0-9]+)?\.java$"

function grade_submission {
    SUBMIT_FILE=$1
    SUBMIT_NAME=$(echo $SUBMIT_FILE | egrep -o $CATCHALL_PATTERN)
    REPORT_NAME="$RESULTS_DIR/$SUBMIT_NAME"

    printf "Grading: %s\n" $SUBMIT_NAME

    # Prepend a header to the report file.
    printf "Report for $ASSIGNMENT_NAME\n" > $REPORT_NAME
    printf "==================================\n\n" >> $REPORT_NAME

    # Check the submission path name against a valid naming pattern. If the
    # submission is not named correctly, the submission fails.
    echo $SUBMIT_FILE | egrep $SUBMIT_STEM_PATTERN$SUBMIT_PATTERN &> /dev/null
    if [ $? != 0 ]
    then
        printf "Submission not named correctly.\n" >> $REPORT_NAME
        printf "\nFinal score: 0.0/100.0\n" >> $REPORT_NAME
        cp $SUBMIT_FILE $FAILED_DIR/$SUBMIT_NAME
        continue
    fi

    # Clean up the working space.
    rm -f *.class $ASSIGNMENT_NAME.java

    # Copy the submission into the working directory for testing.
    cp $SUBMIT_FILE $ASSIGNMENT_NAME.java

    # Compile the submission. If compilation fails, the submission fails. Otherwise,
    # run the Grader program.
    javac $ASSIGNMENT_NAME.java ${ASSIGNMENT_NAME}Grader.java &> /dev/null
    if [ $? != 0 ]
    then
        printf "Submission failed to compile.\n" >> $REPORT_NAME
        printf "Final score: 0.0/100.0\n" >> $REPORT_NAME
        cp $SUBMIT_FILE $FAILED_DIR/$SUBMIT_NAME
    else
        java -Xss512M "${ASSIGNMENT_NAME}Grader" $REPORT_NAME
    fi

    # Clean up after yourself!
    rm -f *.class $ASSIGNMENT_NAME.java
}

function clean {
    rm -rf $RESULTS_DIR $FAILED_DIR $SUBMIT_DIR
    rm -f *.class $ASSIGNMENT_NAME.java
}

function prep {
    clean

    # Make necessary directories and unzip the submissions.
    mkdir $RESULTS_DIR $FAILED_DIR &> /dev/null
    unzip -qq $SUBMIT_ZIP -d $SUBMIT_DIR
}

case $1
in
    # Prepare the submissions zip for grading.
    "--prep")
        prep
        ;;

    # Clean up a messy working directory.
    "--clean")
        clean
        ;;

    # Grade a single submission.
    "--single")
        mkdir $RESULTS_DIR &> /dev/null
        grade_submission $2
        ;;
    
    # Grade all the submissions.
    *)
        prep

        # Go through and grade every submission.
        for SUBMIT_FILE in $SUBMIT_DIR/*
        do
            grade_submission $SUBMIT_FILE
        done

        # Remove the unzipped submissions.
        rm -r $SUBMIT_DIR
        ;;
esac
