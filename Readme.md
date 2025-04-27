# Wallet Service

This is a **Wallet Service** for handling user wallets, transactions, deposits, and withdrawals. It includes features for managing wallet balances, performing transactions such as deposits, withdrawals, and transfers, as well as logging transaction activities. The system supports managing user accounts and securely handling payments.

## API Documentation

Detailed API specifications, including endpoints, request/response formats, and error codes, are available in the following Google Sheets document:

- **API Specification and Error Codes**: [View Google Sheets](https://docs.google.com/spreadsheets/d/1Krw9iX8vBfCq1_6MNlKubYfb5JenaoL-HPp7dqDcEF8/edit?usp=sharing)

This document includes:
- API endpoints for managing accounts, wallets, transactions, deposit requests, withdrawal requests, and transaction logs.
- Error codes with HTTP status, code, type, message, and descriptions.
- Status definitions for tracking API completion (SUCCESS, FAILED, PENDING, TIMEOUT).


## Features

- User Registration & Authentication (passwords are stored securely with hashing).
- Manage Wallets for each user.
- Perform different types of transactions (Deposit, Withdraw, Transfer).
- Track the status of transactions and request logs.
- Handle deposit and withdrawal requests.
- Real-time tracking of wallet balances and transaction status.

## Technologies Used

- **Backend**: Java Spring Boot
- **Database**: MySQL
- **Transactions**: Kafka (for asynchronous processing of transactions)
- **Authentication**: JWT for user authentication
- **Logging**: SLF4J with Logback

## Architecture

The wallet service is designed to follow a microservice architecture, and it primarily handles the following entities:

- **User**: A user who can register, authenticate, and have wallets.
- **Wallet**: A user's digital wallet, which stores funds.
- **Transaction**: A record of deposits, withdrawals, or transfers that occur between wallets.
- **Transaction Log**: Logs of actions performed during the transaction lifecycle (INIT, PROCESS, SUCCESS, FAIL).
- **Deposit Request**: A request to deposit funds into a wallet via external payment providers (e.g., VNPay, MoMo, ZaloPay).
- **Withdraw Request**: A request to withdraw funds from a wallet to an external bank account.

## Database Schema

The system uses the following database tables:

1. **Users Table**: Contains user credentials and metadata.
2. **Wallets Table**: Each user can have multiple wallets.
3. **Transactions Table**: Records all transactions performed (deposits, withdrawals, transfers).
4. **Transaction Logs Table**: Logs detailed actions performed in transactions.
5. **Deposit Requests Table**: Tracks requests to deposit funds into a wallet.
6. **Withdraw Requests Table**: Tracks requests to withdraw funds from a wallet.
