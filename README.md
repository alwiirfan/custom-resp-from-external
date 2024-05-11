# Custom Response When Consume External URL

## First & Last

- you can build `backend` URL `endpoint` then response like `example-external-response.json`.
- so you can make `DTO` then response like `example-custom-response.json` and setup `env.sh` file.

## How to run this project

1. open your `terminal`, so you can do 🔽

```bash
    source env.sh
```

2. finally you can run your project 🔽

```bash
    mvn spring-boot:run
```

# RESPONSES

```java
    public ResponseEntity<?> getDatas() {
        try {
            if (ResponseEntity.ok(dataResponses).getStatusCode().is2xxSuccessful()) {
                log.info("Congratulations ", 💯);
                return ResponseEntity.ok(dataResponses);
            }

            return ResponseEntity.ok(Collections.emptyList())
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("You can try again and correct your error so fix be your self 😃, ok ", 👍, e.getMessage());
        }
    }
```
