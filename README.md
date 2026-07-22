# ParkUM 🚗
## Smart Campus Parking Management System

A Java-based Smart Campus Parking Management System designed to simulate real-time vehicle allocation, navigation, and searching within a university environment.

This project demonstrates practical applications of **Object-Oriented Programming (OOP), Data Structures, and Algorithms** by combining multiple core computer science concepts to solve complex real-world parking logistics.

---

## ✨ Key Features

### 🚘 Vehicle Management
- Register and remove parked vehicles efficiently.
- Maintain persistent vehicle records using a custom **Linked List**.

### 🚦 Vehicle Processing
- Process incoming vehicles sequentially using FIFO processing via a **Queue**.

### 🅿️ Smart Parking Allocation
- Assign the nearest available parking slot automatically using a **Priority Queue (Min Heap)**.

### 🗺️ Campus Navigation
- Model campus roads and parking nodes using a **Graph**.
- Compute the shortest path to an assigned slot using **Dijkstra's Algorithm**.

### 🔍 Fast Search & Display
- Instantly locate vehicles in **O(1)** time using a **HashMap**.
- Display sorted vehicle records alphabetically using a **Binary Search Tree (BST)**.

### ↩️ Action Recovery System
- Undo recent parking management operations using a **Stack**.

---

## 🏗️ System Architecture & Workflow

1. **Arrival & Queueing**  
   Incoming vehicles enter a FIFO processing queue (**VehicleQueue**).

2. **Slot Assignment**  
   The system queries a **Min Heap** to locate and assign the nearest vacant parking slot.

3. **Navigation Routing**  
   A **Graph** models the campus roads, and **Dijkstra's Algorithm** calculates the shortest route from the entrance to the assigned slot.

4. **Record Storage & Search**  
   Vehicles are indexed in a **Linked List**, indexed in a **HashMap** for instant $O(1)$ search, and structured in a **BST** for sorted display.

5. **State Recovery**  
   Every action is pushed to an **Undo Stack**, allowing system actions to be reverted safely.

---

# 📚 Data Structures Used

| Data Structure | Purpose |
|---|---|
| Linked List | Store vehicle records |
| Queue | Manage vehicle arrival order |
| Stack | Undo previous operations |
| HashMap | Fast vehicle searching |
| Binary Search Tree | Sorted vehicle records |
| Priority Queue | Nearest parking slot allocation |
| Graph | Campus navigation system |

---

## ⚙️ Core Algorithms

### Dijkstra's Algorithm
Calculates the shortest navigational route from campus entry points to assigned parking slots.
* **Time Complexity:** $O((V + E) \log V)$

### Priority Queue (Min Heap Selection)
Retrieves the optimal parking slot in logarithmic time.
* **Time Complexity:** $O(\log n)$

---

## 🛠️ Tech Stack

* **Language:** Java
* **Concepts:** Object-Oriented Programming, Data Structures & Algorithms
* **Version Control:** Git & GitHub

