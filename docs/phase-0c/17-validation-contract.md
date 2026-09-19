# Validation Contract

All inputs are validated using Java Bean Validation (`@Valid`, `@NotNull`, `@Size`).

## Rules
* **Strings**: Cannot be empty/blank unless specified. Maximum length defined to prevent DB truncation (typically 255 for standard strings).
* **Enums**: Strictly enforced. Invalid enum strings throw `400 Bad Request`.
* **Pagination**: `page` >= 0, `size` > 0 and <= 100.
* **CVE IDs**: Regex validation `^CVE-\d{4}-\d{4,7}$`.

## Error Handling
Validation errors return `400 Bad Request` with `code = VALIDATION_ERROR` and a populated `details` array identifying specific fields (as defined in the Global API Contract).
