# Generate long lists of random sexagesimal numbers.
# Written by Tiger Sachse.

import random

NUMBER_COUNT = 50
NUMBER_WIDTH = 10
VALID_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX"

with open("output.txt", "w") as f:

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
