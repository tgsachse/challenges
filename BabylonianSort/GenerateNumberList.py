#! /usr/bin/python3
# Generate long lists of random sexagesimal numbers.
# Written by Tiger Sachse.

import random
from sys import argv as ARGS

OUTPUT = "output.txt"
NUMBER_WIDTH = int(ARGS[1])
NUMBER_COUNT = int(ARGS[2])
VALID_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX"

with open(OUTPUT, "w") as f:
    f.write(str(NUMBER_COUNT) + "\n")

    # Create and write a number to the file, NUMBER_COUNT times.
    for iteration in range(NUMBER_COUNT):
        number = ""

        # Choose random characters from the VALID_CHARACTERS list for each
        # place in the number.
        for place in range(NUMBER_WIDTH):
            character = random.choice(VALID_CHARACTERS)

            # If at the most significant numeral, ensure that a 0 is not
            # possible.
            while place == 0 and character == "0":
                character = random.choice(VALID_CHARACTERS)

            # Append the new character to the end of the number.
            number += character

        f.write(number + "\n")
