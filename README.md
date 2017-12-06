# Advent of Code

My [Advent Of Code](http://adventofcode.com/) implementations. In 2015 I started in Java and used it using this as a 
learning experience to learn Scala. In 2017 I didn't really participate due to time constraints. This year, in 2017, I'm
going to implement the solutions in Kotlin.

## Code

### 2017

* [Kotlin](/src/main/kotlin/com/nibado/projects/advent/y2017/)

| Day | Lines | Link |
|----:|------:|------|
| 01  | 16    | [Kotlin](/src/main/kotlin/com/nibado/projects/advent/y2017/Day01.kt)    |
| 02  | 24    | [Kotlin](/src/main/kotlin/com/nibado/projects/advent/y2017/Day02.kt)    |
| 03  | 103   | [Kotlin](/src/main/kotlin/com/nibado/projects/advent/y2017/Day03.kt)    |
| 04  | 17    | [Kotlin](/src/main/kotlin/com/nibado/projects/advent/y2017/Day04.kt)    |
| 05  | 32    | [Kotlin](/src/main/kotlin/com/nibado/projects/advent/y2017/Day05.kt)    |
| 06  | 43    | [Kotlin](/src/main/kotlin/com/nibado/projects/advent/y2017/Day06.kt)    |

#### Day 01

Nice easy start! First time I'm doing something 'serious' with Kotlin and so far I really like it. The 
benefits of functional programming show in the solution; both parts use the same method with slightly
altered behavior. I also really like the short-hand method definition Kotlin allows you to use!

#### Day 02

Similar to Day 1 but even simpler. Again the solution is the 'same' for both parts but again with 
slightly altered logic being fed in via a lambda.

#### Day 03

Day 3 was a huge jump in complexity from the previous day. It also didn't help that I did not read the 
instructions correctly. I tried to go for a functional approach in part 1 which really did not help me in 
part 2 where I really needed to basically just generate the spiral. This is evident in the solution where
I have totally separate implementations for part 1 and 2.

#### Day 04

Not much to say here. Day 4 was probably the easiest day so far. Especially compared to Day 3!

#### Day 05

Fortunately Day 5 was again a bit more challenging. Unfortunately I did not come up with a solution that 
avoids using a mutable collection but it still has a single 'solution' that is parametrized for two parts. 
It's also by far the most computationally complex one sofar; part 2 takes roughly 200 milliseconds.

#### Day 06

Again I was not able to avoid using mutable collections. This day is the first day where part 2 is basically 
a continuation of the state from part 1. While I submitted the results the moment I got them I went through
a few refactoring steps that made the code more concise. The main one being having the entire day solved
in one go and then storing the result.

I also am using a lazily evaluated solution for the first time for the simple reason of having my benchmark 
still work. Without the 'solve' would run the moment the Day06 object gets created. 

### 2015 
* [Java](/src/main/java/com/nibado/projects/advent/y2015/)  
* [Scala](/src/main/scala/com/nibado/projects/adventscala/y2015/)
