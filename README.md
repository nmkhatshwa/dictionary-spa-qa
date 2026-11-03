# Dictionary SPA: QA Project

A quality assurance project for a small dictionary single page app (plain HTML, CSS and
JavaScript). It covers test planning, manual test cases, bug reporting and automated UI tests
written in Java with JUnit 5 and Selenium.

## What is being tested

A single page web app with three views: **Dictionary**, **Synonyms** and **Antonyms**. A user
types a word and the app fetches the result from the third-party
[Free Dictionary API](https://dictionaryapi.dev) and shows it on the same page, without a
page reload. Navigation uses the URL hash (for example `#/synonyms`).

The app's source code is not part of this repository. The tests run against the app served at
`http://localhost:5050`.

## What is in this repository

| Path | Contents |
|------|----------|
| `docs/test-plan.md` | Scope, approach, environment, risks and exit criteria |
| `docs/test-cases.md` | 15 manual test cases (TC-01 to TC-15) with results |
| `docs/bug-reports.md` | Defects and findings |
| `src/test/java/qa/dictionary/` | Automated UI tests (Selenium WebDriver + JUnit 5) |
| `pom.xml` | Maven build |

## Results

**Manual testing:** all 15 test cases passed.

**Automated testing:** all 15 automated tests passed.

| Suite | Tests | Covers | Needs the live API |
|-------|-------|--------|--------------------|
| `NavigationTest` | 7 | TC-01 to TC-07 | No |
| `FormTest` | 3 | TC-08, TC-09, TC-14 | No |
| `LookupTest` | 5 | TC-10 to TC-13, TC-15 | Yes |

**Findings:** one external issue was recorded (see `docs/bug-reports.md`). The dictionary
API went down for a period (Cloudflare Error 1033, HTTP 530, then rate limiting with HTTP 429),
which broke lookups. The app handled it by showing an error message instead of crashing, and
all lookups passed again once the API recovered.

## Design decision: handling a flaky external dependency

The app depends on a service that the tests do not control. To keep the suite trustworthy:

- Tests that **do not need the API** run by default and are stable.
- Tests that **need the API** are tagged `live-api` and run only on request.
- If the API returns no data, a live test is **skipped**, not failed, because an outage of
  someone else's service is not a defect in the app under test.
- Tests wait for page elements with explicit waits instead of fixed sleeps.

## How to run the tests

**Requirements:** Java 21, Maven, Google Chrome, and the app running at `http://localhost:5050`.

1. Serve the app's `public` folder on port 5050. Use the full path to the folder:
   ```
   python3 -m http.server --directory /full/path/to/app/src/main/resources/public 5050
   ```
2. In a second terminal, run the stable UI tests:
   ```
   mvn test
   ```
3. Run the live lookup tests. They call the real dictionary API, so run them sparingly:
   ```
   mvn test -Dgroups=live-api -DexcludedGroups=none
   ```

To test an app running on another address, add `-DbaseUrl=http://localhost:PORT`.

## Test case coverage

| Area | Test cases | Automated |
|------|-----------|-----------|
| Navigation and views | TC-01 to TC-07 | Yes |
| Form validation | TC-08, TC-09 | Yes |
| Dictionary, Synonyms and Antonyms lookups | TC-10 to TC-13, TC-15 | Yes (`live-api`) |
| Special characters / HTML injection | TC-14 | Yes |

## Tools

Java 21, Maven, JUnit 5, Selenium WebDriver 4, headless Chrome.