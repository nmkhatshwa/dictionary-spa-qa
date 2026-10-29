# Bug Reports

| ID | Title | Severity | Status |
|----|-------|----------|--------|
| BUG-001 | Lookups fail when the dictionary API is down | High | Closed (external fault, API recovered) |

---

## BUG-001: Lookups fail when the dictionary API is down

- **Found in:** Synonyms and Antonyms views (also Dictionary for most words)
- **Environment:** Firefox, app at http://localhost:5050
- **Severity:** High (the main feature stops working)
- **Related test cases:** TC-10 to TC-13, TC-15

**Steps to reproduce**
1. Open the app and click Synonyms.
2. Look up `greed` (or `hard` in Antonyms).

**Expected result**
The word and a list of related words.

**Actual result (during the outage)**
The page showed: `No result found for "hard". (NetworkError when attempting to fetch resource.)`

**Evidence**
Opening https://api.dictionaryapi.dev/api/v2/entries/en/hard directly showed
"Error 1033, Cloudflare Tunnel error" (Ray ID a4e69019b9084ed2, 2026-10-22 06:49 UTC).
Direct requests also returned HTTP 530 and, later, HTTP 429.

**Analysis**
The fault was in the third-party API, not in the app. The app's request was correct,
and it showed an error message instead of crashing.

**Retest**
On 29 Oct 2026 all lookups (TC-10 to TC-13 and TC-15) passed, so the API had recovered.

**Remaining risk and suggestion**
The app depends on a service it does not control. The error message shows a technical
reason; it could say that the dictionary service is unavailable and to try again later.