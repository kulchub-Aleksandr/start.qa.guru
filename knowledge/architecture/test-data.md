# Test data and environment

Fixed inputs and expected messages: [src/test/java/testdata/TestData.java](../../src/test/java/testdata/TestData.java).
No runtime data generator file is emitted for this selection.

## Lifecycle and isolation
Browser lifecycle: [src/test/java/tests/BaseTest.java](../../src/test/java/tests/BaseTest.java).
Closing the browser is not server-side cleanup. No account deletion, database reset or API cleanup fixture is generated.
Registration changes persistent application data; a repeated username may already exist. Do not delete users or weaken expected results to obtain a pass.
Do not assume parallel isolation or a clean database; establish the actual SUT state before diagnosing a data failure.

## Environment
Current target: `https://autotests.ai/stack/backend-java-spring/frontend-typescript-react`.
Configuration locations: [src/test/java/tests/BaseTest.java](../../src/test/java/tests/BaseTest.java).
The harness adds no BASE_URL env override, dotenv loader, Docker stand or remote-browser service.
Before changing a target, inspect the linked configuration and keep the application's path prefix.
This URL is configuration, not authorization to access the external SUT.
Keep secrets in approved environment/CI secret storage; never copy credentials or tokens into knowledge files.
