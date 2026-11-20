# Remote farm — selenoid

Endpoint: `https://user1:1234@selenoid.qa.guru/wd/hub`.
No `pool` capability — the hub default is cold: a Docker container per session (required for video/HAR/VNC sidecars).

Session capabilities — verbatim from this project:

```java
        caps.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true,
                "enableLog", true,
                "enableHAR", true));  // capabilities сессии фермы
```

## Do
- Sidecar capabilities (`enableVideo`, `enableVNC`, `enableHAR`, `enableLog`) require a cold container — they cannot be attached to a warm session.
- Session artifacts are named by session id (`<session-id>.mp4/.log/.har`); correlate runs via the hub's `poolRouting` metadata, not by inventing session names.

## Don't
- `pool=warm` with sidecar capabilities or a non-Chrome browser is refused with `forcedPoolReason` — do not retry it as a workaround.
