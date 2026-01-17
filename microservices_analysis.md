# Microservices Analysis: REST vs gRPC Comparison

This document provides a detailed breakdown of the three microservices: **User Service**, **Vote Service**, and **Poll Service**. It explores their architecture and compares the implementation of REST and gRPC for inter-service communication.

---

## 1. Overall System Architecture

The system consists of three independent services that interact to provide a polling platform. The **Poll Service** acts as a central orchestrator, aggregating data from both the **User Service** and the **Vote Service**.

### Technology Stack
- **Framework**: Spring Boot 3.4.4
- **Language**: Java 21
- **Database**: PostgreSQL (JPA/Hibernate)
- **Communication**: 
  - **REST**: Spring Web + OpenFeign
  - **gRPC**: `grpc-spring-boot-starter` + Protobuf

---

## 2. Service-by-Service Breakdown

### A. User Service
**Purpose**: Manages user profiles (Registration, Retrieval, Updates).

- **REST Interface**: `UserController` (Path: `/api/rest/users`)
  - Standard CRUD operations using JSON over HTTP.
- **gRPC Interface**: `UserServiceG` (Service: `UserService`)
  - Implementation of `GetUserById` rpc.
  - Returns complex `UserResponse` including enums and timestamps.
- **Data Layer**: `UserRepository` (JPA) interacting with `User` entity.

### B. Vote Service
**Purpose**: Handles user votes on specific poll options.

- **REST Interface**: `VoteController` (Path: `/api/rest/votes`)
  - Supports creating votes and retrieving votes by Poll ID.
- **gRPC Interface**: `VoteServiceG` (Service: `VoteService`)
  - Implementation of `GetVotesByPollId`.
  - Returns a list of `VoteResponse` messages.
- **Data Layer**: `VoteRepository` (JPA) interacting with `Vote` entity.

### C. Poll Service (The Orchestrator)
**Purpose**: Manages poll creation and aggregates data from User and Vote services to provide a "Poll Info" view.

- **Dual Controllers**:
  - `PollRestController`: Standard REST endpoints for CRUD.
  - `PollGrpcController`: Provides an endpoint `/api/grpc/polls/info` that triggers gRPC-based aggregation.
- **Aggregation Logic**: `PollServiceImpl` contains two distinct methods to demonstrate the comparison:
  1. `getPollInfoRest()`: Uses Feign clients to fetch Users and Votes.
  2. `getPollInfoGrpc()`: Uses gRPC stubs (`UserServiceBlockingStub`, `VoteServiceBlockingStub`) for the same purpose.

---

## 3. REST vs gRPC: Implementation Comparison

Using the **Poll Service**'s aggregation logic as a case study, here are the key implementation differences:

### 1. Client Definition

#### REST (Feign Client)
Requires an interface with mapping annotations. Very similar to controller syntax.
```java
@FeignClient(name = "user-service", url = "http://localhost:8081/api/rest/users")
public interface UserRestClient {
    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") UUID id);
}
```

#### gRPC (Stub Configuration)
Requires channel configuration and stub injection.
```java
@Bean
public UserServiceGrpc.UserServiceBlockingStub userServiceStub(ManagedChannel channel) {
    return UserServiceGrpc.newBlockingStub(channel);
}
```

### 2. Service Invocation

#### REST
Direct method call, usually synchronous and blocking by default in Feign.
```java
UserResponse userResponse = userRestClient.getUserById(userId);
```

#### gRPC
Built using Protobuf builders and called via the generated stub.
```java
GetUserByIdRequest request = GetUserByIdRequest.newBuilder().setId(userId.toString()).build();
UserResponse userResponse = UserGrpcResponseMapper.toUserResponse(userStub.getUserById(request));
```

### 3. Data Transfer Objects (DTOs)

| Feature | REST (JSON) | gRPC (Protobuf) |
| :--- | :--- | :--- |
| **Format** | Text-based (JSON) | Binary (encoded Protobuf) |
| **Schema** | POJO classes in each service | Centralized `.proto` files |
| **Type Safety** | Runtime (Jackson mapping) | Compile-time (Generated code) |
| **Enums** | String/Integer mapping | Strongly typed Enum support |
| **Default Values** | Null for missing fields | Default values (e.g., 0, empty string) |

---

## 4. Observations on Performance and Scalability

1. **Payload Size**: gRPC payloads are significantly smaller due to binary encoding, which is advantageous for high-throughput orchestration in `Poll Service`.
2. **CPU Overhead**: Serialization/Deserialization of JSON is more CPU-intensive than Protobuf's binary handling.
3. **Complexity**:
   - **REST** is easier to test with `curl` or Postman.
   - **gRPC** requires more boilerplate (Protos, Mappers, Channels) but provides a more robust contract between services.

---

## 5. Directory Structure Mapping

```text
├── common-proto/          # Should contain shared .proto files (currently in poll-service/src/main/proto)
├── user-service/         # User Management
│   └── src/main/java/.../service/UserServiceG.java (gRPC Server)
├── vote-service/         # Vote Handling
│   └── src/main/java/.../service/VoteServiceG.java (gRPC Server)
└── poll-service/         # Orchestration & Aggregation
    └── src/main/java/.../clients/ (REST Clients)
    └── src/main/java/.../config/GrpcClientConfig.java (gRPC Setup)
```
