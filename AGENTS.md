# Repository Guidelines

## Project Structure & Module Organization

This repository is currently a blank Git project. Keep the root limited to project configuration and top-level documentation. As the stock-analysis application is introduced, use a predictable layout:

- `src/` for application code, organized by feature or domain.
- `tests/` for automated tests that mirror the source layout.
- `data/` for small, versioned sample datasets only; do not commit large market-data exports.
- `scripts/` for repeatable maintenance, import, or analysis tasks.
- `docs/` for design notes and operational documentation.

Add a README when the first executable component is introduced, including setup and local-run instructions.

## Build, Test, and Development Commands

No build system or runtime has been selected yet. Add the chosen tool's manifest at the root and document canonical commands in `README.md`, such as `pytest` or `npm test`.

Avoid one-off manual steps. Put reproducible workflows in package scripts, a `Makefile`, or versioned files under `scripts/`.

## Coding Style & Naming Conventions

Use the formatter and linter native to the selected language, and commit their configuration with the project. Indent source files consistently (four spaces for Python; two spaces for JavaScript/TypeScript unless the project formatter specifies otherwise). Use descriptive names:

- `snake_case` for Python modules, functions, and variables.
- `PascalCase` for classes and TypeScript/JavaScript components.
- `kebab-case` for command-line scripts and non-code filenames.

Keep financial calculations explicit about currency, time zone, interval, and adjusted versus unadjusted prices.

## Testing Guidelines

Place tests in `tests/` and name them after the component they exercise, such as `tests/test_indicators.py` or `src/portfolio.test.ts`. Cover calculation boundaries, missing market data, and date/time-zone handling. Run the full test suite before opening a pull request, and add a regression test for each fixed defect.

## Commit & Pull Request Guidelines

There is no commit history yet, so use concise imperative commit subjects, such as `Add moving-average indicator` or `Fix duplicate ticker import`. Keep each commit focused.

Pull requests should explain the user-visible change, link the relevant issue, list validation, and include screenshots or sample output for visual or report changes. Call out new data sources, credentials, configuration keys, and migration steps.

## Security & Configuration

Never commit API tokens, broker credentials, or private datasets. Store local secrets in ignored environment files and provide a tracked `.env.example` containing placeholder variable names. Document each required configuration value and its expected format.

## Stock Fact Layer

当前阶段只实现股票事实层。不要实现股票根因分析。

## Data Truthfulness

- 禁止硬编码真实股票价格、财务指标或财报日期。
- 禁止伪造 fallback 数值。
- 所有外部事实必须具有 `source`、`asOfTime` 和 `status`。
- 缺失数据必须返回 `UNAVAILABLE`。
- 预估数据必须明确标记 `ESTIMATED`。

## Stack

| Area | Technology |
| --- | --- |
| Backend | Java 21, Spring Boot 3, Maven |
| Frontend | Next.js, TypeScript |
| Database | PostgreSQL, Redis |
| Chart | TradingView Lightweight Charts |
