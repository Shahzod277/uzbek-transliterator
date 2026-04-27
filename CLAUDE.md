# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bash
./gradlew build -x test    # Build without tests
./gradlew bootRun           # Run locally on port 8080
./gradlew test              # Run JUnit 5 tests
```

Docker build (used by Render.com):
```bash
docker build -t uzbek-transliterator .
docker run -p 8080:8080 uzbek-transliterator
```

## Architecture

Spring Boot 3.4.4 / Java 17 / Gradle 9.3.0 application — a Telegram bot for Uzbek script transliteration with a web admin panel and public web translator.

### Core Flow

**Telegram Bot** (`bot/TransliteratorBot`) handles user interactions via long polling:
`/start` → contact sharing → registration in `bot_users` → mode selection (saved in `user_state`) → text input → `TransliterationService.convert()` → result + saved to `conversion_history`

**Web Translator** (`controller/WebController`): public page at `/translate` with AJAX calls to `/api/translate` — uses the same `TransliterationService`.

**Admin Panel** (`controller/AdminController`): session-based auth at `/admin`, password from `admin.password` config. Dashboard stats, user list, conversion history with detail modal.

### Transliteration Engine

`service/TransliterationService` uses three `LinkedHashMap<String, String>` for character mappings. Order matters — 2-char combos (Gʻ, Oʻ, Ng, Ch, Sh) must be checked before single chars. Four apostrophe variants are supported (ʻ, ', ', ').

Three conversion modes defined in `entity/ConversionType`:
- `LITERARY_TO_NEW` — Adabiy (Latin) → Yangi transkripsiya
- `LITERARY_TO_TRADITIONAL` — Adabiy (Latin) → Kirill
- `TRADITIONAL_TO_NEW` — Kirill → Yangi transkripsiya

### Data Model

PostgreSQL on Render.com, auto-schema via Hibernate `ddl-auto: update`.

- **bot_users** (PK: `chat_id`) — registered Telegram users with phone number
- **user_state** (PK: `chat_id`) — current selected conversion mode per user
- **conversion_history** (PK: `id` auto) — all conversions with original/converted text, type, timestamp

`ConversionTypeConverter` handles legacy enum values from earlier schema versions. `LegacyDataCleaner` runs on startup to migrate old data and drop stale CHECK constraints.

### Key Packages

- `bot/` — Telegram bot handler (long polling, inline keyboards, state management)
- `config/` — BotConfig (properties), BotInitializer (registration), KeepAliveScheduler (10min ping to prevent Render sleep), LegacyDataCleaner
- `controller/` — AdminController (admin panel), WebController (public translator + API)
- `entity/` — JPA entities + ConversionType enum + legacy converter
- `repository/` — Spring Data JPA with optimized GROUP BY queries for dashboard stats
- `service/` — TransliterationService (stateless, character-mapping based)

## Deployment

Render.com (free tier) — auto-deploys on push to `master`. Config in `render.yaml` and `Dockerfile`.

Environment variables: `PORT`, `ADMIN_PASSWORD`, `BOT_TOKEN`, `BOT_USERNAME`, `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`.

## UI

- Admin templates: `resources/templates/admin/` (Thymeleaf + Bootstrap 5 + Chart.js + Inter font)
- Public translator: `resources/templates/translate.html` (standalone dark UI)
- Shared layout fragments in `admin/fragments.html` (sidebar, CSS variables, common styles)
- Static assets in `resources/static/img/`

## Language

UI text and bot messages are in **Uzbek**. Code comments and commit messages mix Uzbek and English.
