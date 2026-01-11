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

## Project Structure

```text
├── AStarPathFinding.java
├── Node.java
├── ManhattanHeuristic.java
├── EuclideanHeuristic.java
├── map_data1.txt
├── map_data2.txt
├── map_data3.txt
├── .gitignore
└── .idea / *.iml
```text


---

## Heuristics

- **Manhattan Heuristic**  
  Uses `|x1 - x2| + |y1 - y2|`, suitable for grid movement without diagonals.

- **Euclidean Heuristic**  
  Uses straight-line distance, suitable when diagonal movement is allowed.

Both heuristics implement:
```java
BiFunction<Node, Node, Integer>
