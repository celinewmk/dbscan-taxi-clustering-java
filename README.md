# Taxi Clustering using Java

## Instructions
1. Start terminal and `cd` into the directory 
2. Compile all the java files 
3. run `java TaxiClusters`. 

The values of MinPts and eps can also be changed.

## Description
The goal of this project is to implement the DBSCAN algorithm in order to cluster various trip records using the GPS coordinates of the starting points.

Link to an explanation of the DBSCAN algorithm: https://en.wikipedia.org/wiki/DBSCAN

The dataset I am using is a CSV file containing all the trip records for January 15, 2009 between 12pm and 1pm in New York City.

The implementation is object-oriented programming therefore it will contain different classes such as:
- TaxiClusters: the main application that processes the data, and the values of the parameters `MinPts` and `eps`. It also creates a CSV file for each cluster created that contains all the points in it. A function that gives you the 10 biggest clusters is also implemented (It can be commented in on line 172 of `TaxiClusters.java` to get them too).
- GPSCoord: a class with the attributes latitude and longitude.
- TripRecord: a class with attributes `label`, `pickup_DateTime`, `pickup_Location`, `dropoff_Location` and `trip_Distance`.
- Cluster: a class with attributes `clusterId`, `points`, `position` and `numPoints`.

## 10 biggest clusters with MinPts = 5 and eps = 0.0003

These are the 10 biggest obtained are shown in the map below, each with its own colour.

<img width="590" alt="Screenshot 2022-03-18 at 10 08 10" src="https://user-images.githubusercontent.com/71091659/159018125-edb13a09-58aa-4f6a-a98a-09b89597095c.png">

Link to map: https://www.google.com/maps/d/edit?mid=1kfZBIcvRJEtT0D0xaKl4MxH1cece_yz8&usp=sharing

