# Windows Event Log Data Requirements

## Input Support
* `.evtx` (Binary Windows Event Log format)
* `.xml` (Exported XML format)
* *Final supported formats TO BE DECIDED based on Java parser capabilities.*

## Required Event Fields
The parser must extract at minimum:
* Event ID (e.g., 4624, 4625)
* Timestamp (`SystemTime`)
* Provider Name
* Severity/Level (Information, Warning, Error, Critical)
* Computer Name

## Advanced Fields (Targeted extraction)
For security analysis, the engine should extract data from `EventData` / `UserData`:
* Account Name / User SID
* Process Name / Process ID
* Source IP Address / Port (for network events)

## Suspicious Patterns (Examples to target)
* Repeated Event ID 4625 (Failed Logon) -> Brute force indicator.
* Event ID 1102 (Audit log cleared) -> Defense evasion.
* Event ID 4720 (User account created) -> Persistence.

## Processing Strategy
* Do not choose an EVTX library blindly. Must evaluate Java libraries capable of reading `.evtx` directly, or require the user to upload XML exports.
* Large files will require streaming parsers (e.g., SAX for XML) and asynchronous processing via RabbitMQ to avoid HTTP timeouts and memory exhaustion.
