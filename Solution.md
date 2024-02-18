# Limit Order Book Implementation

## Data Structure of Core Engine
- To keep orders in the data structure, the red black tree concept is used where price is used as key and LinkedList of orders is used as values. LinedList is used to keep natural time based ordering. 
- From the cost perspective, it takes O(logN) time complexity. To find the minimum and maximum element in the LinkedList is O(logN). 
- To add order in the LinkedList it will take O(1) and delete will take O(N).

## Order transactions
- To save the order transactions, HashMap is used as data structure. From time complexity perspective, insertion and retrieval takes O(1). 



