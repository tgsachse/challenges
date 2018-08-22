# Automatically grade an assignment submission, written by Tiger Sachse.

# This script will produce a grade report with the scores for each
# test case, as well as a final score, out of 100. It will also
# display these results to the screen.

REPORT="report.txt"
ASSIGNMENT="BabylonianSort"

# If the user wants to clean up left over garbage, do so and exit.
if [ "$1" == "--clean" ]
then
    rm "$ASSIGNMENT-old.java"
    rm *.class 1>/dev/null 2>&1
    rm $REPORT 1>/dev/null 2>&1
    exit
fi

# Delete old reports.
rm $REPORT 1>/dev/null 2>&1

# If the grader wants to use a previously-graded submission, then
# rename that old submission to the correct name.
if [ "$1" == "--use-old" ]
then
    mv "$ASSIGNMENT-old.java" "$ASSIGNMENT.java"
fi

# Print the header for the report.
printf "Report for $ASSIGNMENT\n" | tee -a $REPORT
printf "=========================================================\n\n" | tee -a $REPORT

# Check that the submission is named correctly. This bit of the script
# also accepts files with "-[number]" at the end of the stem. Webcourses
# adds these numbers to files that have been resubmitted as a way to
# track the current version of the submission.
SUBMISSION_NAME=$(ls | egrep "$ASSIGNMENT(\-[0-9]+)?.java")
if [ $? != 0 ]
then
    printf "Submission not named correctly.\n" | tee -a $REPORT
    printf "\nFinal score: 0.0/100.0\n" | tee -a $REPORT
    exit
fi

# If there are multiple files in the directory that begin with the correct
# assignment stem, than the script cannot know which is the intended file
# and it must exit.
SUBMISSION_COUNT=$(ls | egrep "$ASSIGNMENT(\-[0-9]+)?.java" -c)
if [ $SUBMISSION_COUNT != "1" ]
then
    printf "ERROR: Too many files with names starting with \"$ASSIGNMENT\".\n"
    printf "       I don't know how to proceed! Please remove excess files.\n"
    rm $REPORT
    exit
fi

# Rename the submission so that it has the correct name. Any
# submissions at this point in the script will only be off by the
# aforementioned "-[number]".
if [ $SUBMISSION_NAME != "$ASSIGNMENT.java" ]
then
    mv $SUBMISSION_NAME "$ASSIGNMENT.java"
fi

# Attempt to compile the submission.
javac $ASSIGNMENT.java ${ASSIGNMENT}Grader.java 1>/dev/null 2>&1
if [ $? != 0 ]
then
    printf "Submission failed to compile.\n" | tee -a $REPORT
    printf "Final score: 0.0/100.0\n" | tee -a $REPORT
else
    java -Xss128M "${ASSIGNMENT}Grader" | tee -a $REPORT
fi

# Clean up any mess.
rm *.class 1>/dev/null 2>&1

# Get the tested submission out of the way, but preserve it for
# easy access if a mistake was made.
mv "$ASSIGNMENT.java" "$ASSIGNMENT-old.java"
