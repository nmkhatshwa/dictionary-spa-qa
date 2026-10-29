# Test Cases: Dictionary Single Page App

App address: http://localhost:5050
Test words: `code`, `greed`, `hard`, unknown word `zzzzqx`

"Needs API" means the test depends on the live Free Dictionary API.
Fill in the **Status** column (Pass / Fail / Blocked) when you run the tests.

| ID | Area | Test | Steps | Expected result | Needs API | Status |
|----|------|------|-------|-----------------|-----------|--------|
| TC-01 | Navigation | Default page | Open the app | Three links (Dictionary, Synonyms, Antonyms) and the text "Click on a menu item." | No | Not run |
| TC-02 | Navigation | Dictionary view | Click Dictionary | Heading "Dictionary", a text box and a "Look up" button | No | Not run |
| TC-03 | Navigation | Synonyms view | Click Synonyms | Heading "Synonyms", a text box and a "Look up" button | No | Not run |
| TC-04 | Navigation | Antonyms view | Click Antonyms | Heading "Antonyms", a text box and a "Look up" button | No | Not run |
| TC-05 | Navigation | Switch views | Click Dictionary, then Synonyms, then Antonyms | The heading changes each time and the page does not reload | No | Not run |
| TC-06 | Navigation | Open a view by address | Open `http://localhost:5050/#/synonyms` | The Synonyms view shows straight away | No | Not run |
| TC-07 | Navigation | Back button | Click Dictionary, then Synonyms, then press Back | The Dictionary view is shown again | No | Not run |
| TC-08 | Form | Empty input | In Dictionary, leave the box empty and click Look up | The message "Please enter a word." | No | Not run |
| TC-09 | Form | Spaces only | Type three spaces and click Look up | The message "Please enter a word." | No | Not run |
| TC-10 | Dictionary | Valid word | In Dictionary, look up `code` | The word "code", its phonetic, an audio player, noun and verb sections, definitions and examples | Yes | Not run |
| TC-11 | Synonyms | Valid word | In Synonyms, look up `greed` | The word "greed" and a list of related words | Yes | Not run |
| TC-12 | Antonyms | Valid word | In Antonyms, look up `hard` | The word "hard" and a list of related words | Yes | Not run |
| TC-13 | Errors | Unknown word | In Dictionary, look up `zzzzqx` | A message starting `No result found for "zzzzqx"` | Yes | Not run |
| TC-14 | Security | Special characters | In Dictionary, look up `<b>test</b>` | Any message shows the text as plain text (not bold); no HTML is injected | Either | Not run |
| TC-15 | Form | Enter key | Type `code` and press Enter instead of clicking | Same result as clicking Look up | Yes | Not run |