# Limit Order Book Implementation

## Data Structure of Core Engine
- To keep orders in the data structure, the red black tree concept is used where price is used as key and LinkedList of orders is used as values. LinedList is used to keep natural time based ordering. 
- From the cost perspective, it takes O(logN) time complexity. To find the minimum and maximum element in the LinkedList is O(logN). 
- To add order in the LinkedList it will take O(1) and delete will take O(N).

## Order transactions
- To save the order transactions, HashMap is used as data structure. From time complexity perspective, insertion and retrieval takes O(1). 

## Start application
```bash
make dc-up
```

## Stop application
```bash
make dc-down
```

## Get order sample request

```bash
curl --location --request GET 'http://localhost:8080/orders/0'
```

## Place order sample request
```bash
curl --location 'http://localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data '{
  "asset": "BTC",
  "price": 43251.00,
  "amount": 0.65,
  "direction": "BUY"
}'
```