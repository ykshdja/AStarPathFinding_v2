# A* Pathfinding Algorithm (Java)

**Author:** Yash Khanduja  
**Project Type:** Pathfinding / AI Search Algorithms  
**Language:** Java  

---

## Overview

This project implements the **A\* (A-Star) pathfinding algorithm** in Java.  
It supports multiple heuristic functions (Manhattan and Euclidean) and finds the optimal path between two nodes on a grid-based map.

The implementation is modular, allowing heuristics to be swapped easily using Java functional interfaces.

---

## Features

- A* pathfinding algorithm
- Grid-based map input from text files
- Pluggable heuristic functions
- Manhattan and Euclidean distance heuristics
- Node-based graph representation
- Priority-based search using cost + heuristic

---

## File Structure

```text
├── AStarPathFinding.java      # Main A* algorithm implementation
├── Node.java                 # Node representation for the grid
├── ManhattanHeuristic.java   # Manhattan distance heuristic
├── EuclideanHeuristic.java   # Euclidean distance heuristic
├── map_data1.txt             # Sample map input
├── map_data2.txt             # Sample map input
├── map_data3.txt             # Sample map input
├── .gitignore
└── .idea / *.iml             # IDE configuration files
---

## Heuristics

- **Manhattan Heuristic**  
  Uses `|x1 - x2| + |y1 - y2|`, suitable for grid-based movement without diagonals.

- **Euclidean Heuristic**  
  Uses straight-line distance, suitable when diagonal movement is allowed.

Both heuristics implement:

```java
BiFunction<Node, Node, Integer>


