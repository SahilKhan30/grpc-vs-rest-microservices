# gRPC vs REST: A Microservices Experiment

This project is a small playground designed to look at how gRPC and REST handle inter-service communication in a real-world scenario. Instead of just talking about performance differences, I built three services that actually talk to each other to see how they feel in development and how they perform in practice.

## The Services

The project is split into three main pieces, each handling a specific part of a simple polling system:

*   **User Service**: This is where all the user profiles live. It handles everything from registration to basic lookups.
*   **Vote Service**: A dedicated service for managing votes. It tracks which users have voted on which polls and keeps the tallies.
*   **Poll Service (The Orchestrator)**: This is the "brain" of the project. It doesn't just manage polls; it aggregates data from both the User and Vote services.

## How the Comparison Works

The cool part of this setup is how the **Poll Service** bridges everything. 

To test the protocols, I set up a standard REST API endpoint on the Poll Service. When you hit this endpoint, the Poll Service has to go out and fetch details from the other two services to give you a full response.

I implemented this internal communication in two different ways so we can compare them side-by-side:
1.  **The REST Way**: The Poll Service uses standard REST calls (via OpenFeign) to talk to the User and Vote services.
2.  **The gRPC Way**: The Poll Service uses gRPC stubs to fetch the exact same data.

By calling the external REST API, you can trigger either an internal REST-to-REST flow or a REST-to-gRPC flow, making it easy to see exactly where the bottlenecks are and how much faster (or more complex) gRPC really is.
