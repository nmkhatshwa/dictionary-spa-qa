# Test Plan: Dictionary Single Page App

## 1. Purpose
Verify that the Dictionary SPA behaves correctly for a user: navigation, word lookup,
results display and error handling.

## 2. Application under test
A single page web app built with plain HTML, CSS and JavaScript.
- Menu with three views: **Dictionary**, **Synonyms**, **Antonyms**
- Each view shows a lookup form; results appear on the same page without a reload
- Navigation uses the URL hash (for example `#/synonyms`)
- Data comes from the third-party Free Dictionary API (`api.dictionaryapi.dev`)
- Served locally at `http://localhost:5050`

## 3. Scope
**In scope**
- Default page content and menu links
- Switching between views, including the Back button and opening a view by URL
- Lookup form: rendering, empty input, valid word, unknown word
- Dictionary results: word, phonetic, audio, parts of speech, definitions, examples
- Synonyms and Antonyms results: word and related-word list
- Error messages when a lookup fails

**Out of scope**
- The Free Dictionary API itself
- Performance, load and security testing
- Full accessibility audit

## 4. Test approach
- **Manual testing:** scripted test cases plus exploratory testing
- **Automated UI tests:** Selenium WebDriver with JUnit 5 (Java, Maven) for regression
- **Test data:** valid words (`code`, `greed`, `hard`), an unknown word (`zzzzqx`),
  empty input, whitespace only, and special characters (`<b>test</b>`)

## 5. Test environment
- Browsers: Chrome (automated and manual), Firefox (manual)
- Java 21, Maven, JUnit 5, Selenium 4
- App served from `http://localhost:5050`

## 6. Risks and mitigations
| Risk | Mitigation |
|---|---|
| The dictionary API is a third-party service that has been unavailable (Cloudflare Error 1033, HTTP 530) and rate-limited (HTTP 429) | Keep UI-only tests separate from tests that need live data; skip API-dependent tests when the API is down; record the outage in the bug reports |
| Rate limiting can block repeated test runs | Keep lookups few; run API-dependent tests sparingly |
| Results arrive asynchronously | Use explicit waits in automated tests, not fixed sleeps |

## 7. Entry and exit criteria
- **Entry:** the app runs at `http://localhost:5050` and the menu loads
- **Exit:** every test case has been executed and its result recorded; all failures are
  logged as bug reports; automated UI-only tests pass

## 8. Deliverables
- Test plan (this document)
- Test cases
- Bug reports
- Automated test suite
- README with setup and run instructions