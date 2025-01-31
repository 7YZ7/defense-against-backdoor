#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# Uses https://github.com/rkistner/contest-algorithms
 
# Cookie Clicker is a Javascript game by Orteil, where players click on a picture of a giant cookie. Clicking on the giant cookie gives them cookies. They can spend those cookies to buy buildings. Those buildings help them get even more cookies. Like this problem, the game is very cookie-focused. This problem has a similar idea, but it does not assume you have played Cookie Clicker. Please don't go play it now: it might be a long time before you come back.
 
# Problem
 
# In this problem, you start with 0 cookies. You gain cookies at a rate of 2 cookies per second, by clicking on a giant cookie. Any time you have at least C cookies, you can buy a cookie farm. Every time you buy a cookie farm, it costs you C cookies and gives you an extra F cookies per second.
 
# Once you have X cookies that you haven't spent on farms, you win! Figure out how long it will take you to win if you use the best possible strategy.
 
# Example
 
# Suppose C=500.0, F=4.0 and X=2000.0. Here's how the best possible strategy plays out:
 
# You start with 0 cookies, but producing 2 cookies per second.
# After 250 seconds, you will have C=500 cookies and can buy a farm that produces F=4 cookies per second.
# After buying the farm, you have 0 cookies, and your total cookie production is 6 cookies per second.
# The next farm will cost 500 cookies, which you can buy after about 83.3333333 seconds.
# After buying your second farm, you have 0 cookies, and your total cookie production is 10 cookies per second.
# Another farm will cost 500 cookies, which you can buy after 50 seconds.
# After buying your third farm, you have 0 cookies, and your total cookie production is 14 cookies per second.
# Another farm would cost 500 cookies, but it actually makes sense not to buy it: instead you can just wait until you have X=2000 cookies, which takes about 142.8571429 seconds.
# Total time: 250 + 83.3333333 + 50 + 142.8571429 = 526.1904762 seconds.
# Notice that you get cookies continuously: so 0.1 seconds after the game starts you'll have 0.2 cookies, and π seconds after the game starts you'll have 2π cookies.
 
# Input
 
# The first line of the input gives the number of test cases, T. T lines follow. Each line contains three space-separated real-valued numbers: C, F and X, whose meanings are described earlier in the problem statement.
 
# C, F and X will each consist of at least 1 digit followed by 1 decimal point followed by from 1 to 5 digits. There will be no leading zeroes.
 
# Output
 
# For each test case, output one line containing "Case #x: y", where x is the test case number (starting from 1) and y is the minimum number of seconds it takes before you can have X delicious cookies.
 
 
# Limits
 
# 1 ≤ T ≤ 100.
 
# Small dataset
 
# 1 ≤ C ≤ 500.
# 1 ≤ F ≤ 4.
# 1 ≤ X ≤ 2000.
# Large dataset
 
# 1 ≤ C ≤ 10000.
# 1 ≤ F ≤ 100.
# 1 ≤ X ≤ 100000.
 
import sys
 
 
def debug(*args):
    print(*args, file=sys.stderr)
 
fin = sys.stdin
T = int(fin.readline())
for case in range(1, T + 1):
    C, F, X = map(float, fin.readline().split())
 
 
    rate = 2
    time = 0
 
    best = 10**20
 
    while time < best:
        result = X / rate + time
        if result < best:
            best = result
        time += C / rate
        rate += F
 
 
 
    print("Case #%d: %.7f" % (case, best))
 
