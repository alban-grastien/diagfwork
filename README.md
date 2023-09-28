# diagfwork
A general framework for diagnosis developed at NICTA by Alban Grastien with the support of many people, mainly Sylvie Thiébaux and Patrik Haslum.

# Content

This repository contains a Java framework for computing diagnosis for
discrete event systems, alongside some benchmarks.

There are three implementations: one based on automata and junction
tree, one based on BDDs, and one based on SAT.
They should be usable out-of-the-box.
Examples are given in `jt_command`, `bdd_command`, and `sat_command`.

## Junction Trees

This implementation converts the graph of automata into a junction
tree, which allows to use Kan-John & Grastien's algorithm to compute
the diagnosis.  This implementation does not specifically look at
faults, but instead computes the model trajectories that are
consistent with the observations.

## BDD

This implementation updates the belief state according to the
observations, representing it with Binary Decision Diagrams.  The BDD
implementation utilises BuDDy which is provided as the library
`libbuddy.so` in the `lib/` folder.

Notice that this implementation assumes that the three files (model,
observations, and faults) share the same prefix and end with
`mmld-ground`, `obs` and `faults` respectively.  Alternatively, the
observations file can end with `alarms` instead, in which case an
error message is emitted that can be safely ignored.

## SAT

This implementation uses SAT to search for minimal diagnosis.  It uses
`multisat_core`, a version of minisat that can be found in the `sat/`
folder and that returns conflicts.  On top of the trio
model/observations/faults, this implementation includes two extra
dimensions:

1. What type of hypothesis space we are working with.  Proposed
   hypothesis spaces are the Set Hypothesis Space, the Multi Set
   Hypothesis Space, and the Sequential Hypothesis Space.  They can be
   called by using the following option:
   `space=diag.reiter.hypos.SetHypothesisSpace`,
   `space=diag.reiter.hypos.MultiSetHypothesisSpace`, and
   `space=diag.reiter.hypos.SequentialHypothesisSpace`.  These options
   refer to Java classes, so you could create your own class and call
   it.

2. What search strategy is used.  Proposed strategies are PFS (aka
   TopDown), PLS (aka BlindBottomUp) and PLS+r (aka BottomUp).  They
   can be called by using the following options:
   `solver=diag.reiter.solvers.TopDown`,
   `solver=diag.reiter.solvers.BlindBottomUp`, and
   `solver=diag.reiter.solvers.BottomUp` respectively.  Again, these
   are simple Java classes, and one can create new strategies.

# Compile and sources

The repository provides the following packages:

* `antlr-3.2.jar`: used to parse input files.  Antlr
  [https://www.antlr.org/] is a parser generator that transforms the
  `*.g` files from the repository to java files.  Version 3.2 is an
  old version.  Unfortunately, there seems to be incompatibilities
  between this version and recent versions of Java, which makes it
  tricky to use.  Fortunately, the lexers and parsers have already
  been generated.  One does not need to regenerate the java files (but
  `antlr` still needs to be included in the classpath).

* `swt` and `draw2d` are used for drawing purposes.  They are not
  necessary for diagnosis, but they are included because we need them
  to compile the code.

* `javabdd` and `libbuddy` are used to manipulate BDDs in Java.

* `diag.jar` is the jar file that includes all the classes.

* `multisat_core` is a modified version of `minisat` [minisat.se] that
  takes a list of assumptions as input and returns a "conflict"
  (sublist of assumptions that make the CNF unsat).  The modified
  solver is included in `sat/multisat2.2/`.

* The source of the framework is in `src/`.  It can be compiled using
  `Makefile`, but this is not necessary as `lib/diag.jar` contains the
  compiled version of the code.

# Some references:

* Kan John, Priscilla, and Alban Grastien. "Local Consistency and Junction Tree for Diagnosis of Discrete-Event Systems." ECAI. 2008.

* Schumann, Anika, Yannick Pencolé, and Sylvie Thiébaux. "A spectrum of symbolic on-line diagnosis approaches." AAAI. 2007.

* Grastien, Alban, Patrik Haslum, and Sylvie Thiébaux. "Exhaustive diagnosis of discrete event systems through exploration of the hypothesis space." DX. 2011.

* Grastien, Alban, Patrik Haslum, and Sylvie Thiébaux. "Conflict-based diagnosis of discrete event systems: theory and practice." KR. 2012.

