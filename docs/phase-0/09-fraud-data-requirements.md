# Phase 0: UPI Fraud Data Requirements

This document outlines the data requirements for the UPI Fraud Detection module.

## Status: REQUIRES DATASET

A machine learning model cannot be built or evaluated without defining the exact dataset it will be trained on. The fields listed below are a comprehensive projection of what *should* be in a robust UPI fraud dataset. The final schema will be restricted to what is available in the chosen open-source or academic dataset.

## Target Variable
* `is_fraud` (Boolean or Integer 0/1): The label for training.

## Proposed Input Categories

### 1. Transaction Information
* `transaction_id` (String): Unique identifier.
* `amount` (Decimal): Transaction value.
* `currency` (String): e.g., INR.
* `timestamp` (DateTime): Exact time of the transaction.

### 2. Sender Information (Anonymized)
* `sender_vpa_hash` (String): Hashed Virtual Payment Address.
* `sender_bank` (String): Originating bank.
* `sender_account_age_days` (Integer): How long the account has been active.

### 3. Receiver Information (Anonymized)
* `receiver_vpa_hash` (String): Hashed Virtual Payment Address.
* `receiver_bank` (String): Destination bank.
* `is_merchant` (Boolean): Whether the receiver is a registered merchant.
* `merchant_category_code` (MCC) (String): Type of business, if applicable.

### 4. Behavioral & Velocity Metrics (Calculated Features)
* `transactions_last_24h` (Integer): Velocity metric.
* `amount_last_24h` (Decimal): Velocity metric.
* `is_new_payee` (Boolean): Has the sender paid this VPA before?
* `time_since_last_transaction` (Seconds): Time gap between this and previous transaction.

### 5. Contextual Information
* `device_type` (String): Mobile, Web, etc.
* `location_region` (String): Generalized location to prevent PII leakage.
* `time_of_day` (Integer): Hour of the day (0-23).
* `is_weekend` (Boolean).

## Pipeline Concept

1. **Input**: User submits transaction data matching the required schema via the API.
2. **Feature Engineering**: Backend Java service calculates derived metrics (e.g., `is_new_payee`, `time_of_day`).
3. **Model Inference**: Java ML library (Tribuo/DJL - **TO BE DECIDED**) runs inference on the feature array.
4. **Probability**: Model outputs a probability between 0.0 and 1.0.
5. **Risk Classification**: Probability is normalized into the standard `RiskProfile`.
6. **Explanation**: Input data and risk score are passed to Spring AI. The LLM generates a human-readable explanation (e.g., "This transaction was flagged as HIGH risk because the amount is unusually large for a new payee during non-business hours").
7. **Persistence**: Saved to `fraud_analyses` and `fraud_transactions`.
