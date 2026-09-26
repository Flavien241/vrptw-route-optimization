# Vehicle Routing Problem with Time Windows

Java implementation of a Vehicle Routing Problem with Time Windows (VRPTW) coursework project. It models delivery routes subject to capacity and customer time-window constraints, and includes tools for building, comparing and visualising solutions.

## What the project contains

- Domain model for vehicles, customers, routes and VRPTW instances
- Input/output and bundled instance data
- Solution construction and random-solution generation
- Solution comparison utilities
- Route visualisation with GraphStream
- Test classes and helper utilities

## Project layout

- `model/`: VRPTW entities and solution representation
- `solver/`: solution builders, generator and visualisation tools
- `io/`: instance loading and output support
- `data/`: problem instances
- `tests/`: executable test cases
- `Main.java`: project entry point

## Technical stack

- Java
- GraphStream for route visualisation
- Project-specific data files and utilities

## Running the project

Open the project in a Java IDE, resolve the libraries in `lib/`, then run `Main.java` or an appropriate test class. The available input instances are stored in `data/`.

## Scope

This repository is an academic optimisation project. It focuses on modelling constrained vehicle routing and examining candidate solutions through construction and visualisation tools.
