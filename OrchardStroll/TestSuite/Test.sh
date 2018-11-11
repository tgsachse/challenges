# Test launcher for OrchardStroll.
# You will need to make sure your directory looks like
# the directory described in the PDF, section 6.
# Written by Tiger Sachse.

# To use this script, execute the following command:
#     bash Test.sh

MALFUNCTION=false
ASSIGNMENT="OrchardStroll"

# Omit an error message and change the malfunction flag to true.
function error {
    printf "\nERROR: $1"
    printf "       $2"
    MALFUNCTION=true
}

# If anything has gone wrong, break out of the script.
function malfunction_check {
    if [ "$MALFUNCTION" = true ]
    then
        printf "\nAborting script.\n"
        rm -f *.class
        exit
    fi
}

# Start of the script.
printf "=========================================================\n"
printf "Welcome to the $ASSIGNMENT test script!\n"
printf "=========================================================\n\n"
printf "Checking directory structure.\n"

# Ensure that the necessary source code is in the right place.
for FILE in $ASSIGNMENT "${ASSIGNMENT}Tester" "Tree";
do
    if [ ! -f $FILE.java ]
    then
        error "$FILE.java not found.\n" "Refer to PDF section 6.\n"
    fi
done

# If there have been any issues up to this point, this function will exit the script.
malfunction_check

# Clean up the work area.
printf "Cleaning up the work area.\n"
rm -f *.class

# Attempt to compile the program.
printf "Attempting to compile.\n\n"
javac *.java
if [ $? != 0 ]
then
    error "Program failed to compile.\n" "Try compiling on your own.\n" 
fi

# One last check before running the tests.
malfunction_check

# Call the test program.
java ${ASSIGNMENT}Tester

# Post-execution clean up.
rm -f *.class
