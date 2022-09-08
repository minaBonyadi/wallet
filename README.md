# Wallet
# Mina Bonyadi

## Description
This project is a simple wallet debit/credit APIs.

## Files
```
clone https://github.com/minaBonyadi/wallet.git
```

## Tools

- [ ] Java 17
- [ ] Maven
- [ ] Spring Boot
- [ ] mySql
- [ ] Git
- [ ] Jupiter
- [ ] Mockito
- [ ] Docker Compose

## Test and Deploy

- run application main method in (WalletApplication) and run docker-compose.yml to
  up mysql database

***

## Features

- Wallet APIs within a container based environment (Docker)
- providing documentation of my Wallet API endpoints
- User services are covering by integration tests
- Some parts need unit test too

## Wallet Model

Player:
- id: long
- name: string
- balance: bigDecimal
- transactions: List of transaction

PlayerTransaction:
- id: long
- type: string
- amount : bigDecimal
- player : player

## Usage

you can use it by postman service call they are services which implemented :
- [ ] GET -> /wallet/player/{playerId}   -> input (playerId), output (playerDto)
- [ ] POST -> /wallet/{playerId}/transaction   -> input (playerId, transactionDto), output(restResponse)
- [ ] GET -> /wallet/{playerId}/transaction -> input (playerId), , output(List<TransactionDto>)

## Roadmap
- add spring security
- add swagger UI

## Authors and acknowledgment

This is Mina. I am a java back-end developer who have more than six years experience in this career,
and I have a bachelor degree in software engineering.
