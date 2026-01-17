# gRPC vs REST – Microservices Playground

This repository is a small experimental project to compare gRPC and REST for service-to-service communication in a realistic microservices setup.

---

## Project Overview

The system consists of three Spring Boot services:

### 1. User Service
Responsible for storing and serving user profile data.

### 2. Vote Service
Manages votes for polls – tracks which users voted and on which poll.

### 3. Poll Service
This is the central service and the main subject of the experiment.

It:
*   Exposes a REST API to external clients
*   Fetches user data from the User Service
*   Fetches vote data from the Vote Service
*   Aggregates everything into a single response

---

## Why this setup?

The Poll Service supports two different internal communication modes while keeping the external API the same.

### REST → REST flow
The Poll Service calls the User Service and Vote Service using REST (OpenFeign).

### REST → gRPC flow
The Poll Service calls the same services using gRPC stubs generated from protobuf definitions.

From the outside, clients always call the Poll Service using REST. Internally, the protocol can be switched to compare:
*   Latency
*   Throughput
*   CPU utilization
*   Network payload size

This allows a clean, side-by-side comparison under identical workloads.

---

## Generating gRPC classes (important)

This project uses protobuf for gRPC, so Java classes must be generated before running the services.

From the project root:

```bash
mvn clean install
```

This will:
*   Compile all `.proto` files
*   Generate gRPC Java sources under `target/generated-sources`
*   Install shared proto modules into your local Maven repository

If this step is skipped, the Poll Service will fail to compile because the generated gRPC classes will be missing.

---

## API Endpoints

### REST-based internal calls
```http
GET /api/rest/polls/info
```

### gRPC-based internal calls
```http
GET /api/grpc/polls/info
```

Both endpoints return the same response structure. Only the internal protocol changes.

---

## Benchmarking

Example commands used for load testing:

```bash
wrk -t4 -c20 -d30s http://localhost:8091/api/rest/polls/info
wrk -t4 -c20 -d30s http://localhost:8091/api/grpc/polls/info
```

CPU usage and process behavior can be observed using:
*   `htop`
*   Activity Monitor (CPU + Network tabs)

Network usage and payload sizes can be estimated using:
*   Activity Monitor (Network tab totals)
*   `wrk` transfer statistics

---

## Notes

This project is meant to be:
*   A learning tool
*   A performance comparison playground
*   A reference for REST vs gRPC integration in Spring Boot

It is not intended to be production-ready or feature-complete.