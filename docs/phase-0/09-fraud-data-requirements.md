# UPI Fraud Data Requirements

Before implementing the Java-centric ML fraud engine, the following data inputs and features must be established. 

**IMPORTANT**: A dataset has not yet been selected. The exact features are TO BE FINALIZED based on the chosen training data.

## Anticipated Data Categories

### 1. Transaction Information
* Transaction Amount
* Currency (assuming INR for UPI)
* Transaction Type (P2P, P2M)

### 2. Temporal Information
* Time of day (hour)
* Day of week
* Is weekend/holiday?

### 3. Behavioral / Velocity Information
* Number of transactions in the last 24h
* Total amount transferred in the last 24h
* Time since last transaction

### 4. Context Information
* Sender VPA (Virtual Payment Address) hash/category
* Receiver VPA hash/category
* Location/IP data (if available in dataset)

### 5. Target Variable (For Training)
* `is_fraud` (Boolean: 0 or 1)

## Architecture Pipeline
Input JSON -> Java Data Preprocessing -> Feature Engineering -> ML Inference Engine -> Probability Output -> Risk Classification -> DB Persistence.

## External Dependency
* **Java ML Library**: Tribuo, DJL, or similar (TO BE FINALIZED during the Fraud phase based on model format—e.g., ONNX, PMML, or native Java model).
