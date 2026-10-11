# Stock Fact Layer

A factual-stock-page MVP. The backend exposes source-aware facts; the frontend renders explicit unavailable states until a provider is configured.

## Run

```sh
docker compose up -d
mvn spring-boot:run
cd frontend && npm install && npm run dev
```

Run validation with `mvn test` and `cd frontend && npm run build`.
