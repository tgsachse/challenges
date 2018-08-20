#! /usr/bin/python3

import random

WIDTH = 10
HEIGHT = 20

checkers = []
for x in range(2, WIDTH, 2):
    for y in range(2, HEIGHT, 2):
        checkers.append((x, y))

random.shuffle(checkers)

with open("output.txt", "w") as f:
    f.write("{0} {1} {2}\n".format(WIDTH - 1, HEIGHT - 1, len(checkers)))
    for checker in checkers:
        f.write("{0} {1}\n".format(checker[0], checker[1]))
