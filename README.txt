Authors: Arik Tatievski, 208997056
	Roi Meshulam, 315635649

What is this project?
This project is a Graph analayzing project which allows us to insert graphs, create graphs, changing graphs and study them.

The way we made our project:
We have made a few classes these next lines will explain them.
FormalEdge/FormalNode/FormalGraph - are 3 classes created to help us save and load graphs the way we are expected via json file.
Vertex/Edge/Arrow - are 3 low-key classes that know how to do the most basic functions.(Creating nodes/edges/getting their location values etc..)
Graph - this class is represting a graph = (V=group of vertexes,E=group of edges) and all the functions we can do on a graph(adding and removing edges/nodes, getting Iterators of the graph edges/nodes and more)
GraphAlgo - this class allows us to make alogrithems on graphs such as tsp(getting cities),calculating shortest path(through dijkstra algorithm), checking if a graph is connected(BFS on same vertes on a graph&the graph's opposite) and more.
GUI - this class is the gui representor of our project(we will expand on it in the 'How to use the GUI' section)

How did we test our project?
In every step and every function we made we did all the tests on the extreme conditions of the graph to make sure that our graph can handle all possible situations.

Graph stability:
1000 Nodes - You can make any algorithm u would like and it wont take more than 30 seconds to run it + GUI representaion
10000 Nodes - You can make any algorithm u would like and it wont take more than an hour to run it(talking about TSP algorithm) + GUI representaion
100000 Nodes - We can only get a GUI representaion of it.

How to use our project:
Please run the jar file with the following command "java -jar Ex2.jar Filename.json and our GUI will apear.

How to use the GUI!!
Well the uses are pretty simple and every user can enjoy it, though we have some strict rules in order to run it.
When opening the GUI you will see a simple empty window with a menu, the menu options are:
Load - allows us to load a graph to the GUI - NOTICE, the graph must be represented in the way our program allows. You can see an example in the "data" folder.
Save - allows us to save the given presented graph in a json form.
Change - allows us to make simple changes on the graph(adding/deleting nodes/edges) - NOTICE, you must enter the values the same way we give in the pop-up example.
Algorithm - allows us to ask the graph to make all kinds of algorithms and the return value is the answer.

Hope you find good usuage of this project!

