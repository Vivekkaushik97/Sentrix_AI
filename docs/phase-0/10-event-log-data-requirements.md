# Phase 0: Windows Event Log Data Requirements

This document outlines the data requirements and parsing strategy for the Windows Event Log Analysis module.

## Status: REQUIRES IMPLEMENTATION SPIKE

Parsing binary `.evtx` files natively in Java requires a specific library. The exact library (**TO BE DECIDED**) will dictate the exact format of the extracted fields. No Python parsers are permitted in the architecture.

## 1. Supported File Types
* `.evtx` (Windows XML Event Log format) - Primary focus.
* `.xml` (Exported event logs) - Secondary support if EVTX parsing is too complex.

## 2. Event Log Structure (Target Extraction)
For every relevant event parsed from the file, the system aims to extract:

* **Event ID** (Integer): e.g., 4624 (Logon), 4688 (Process Creation).
* **Provider Name** (String): e.g., Microsoft-Windows-Security-Auditing.
* **Timestamp** (DateTime): When the event occurred.
* **Level/Severity** (Integer/String): Information, Warning, Error, Critical.
* **Computer Name** (String): Hostname.
* **Account/User Information**:
    * TargetUserName
    * TargetDomainName
* **Process Information**:
    * NewProcessName
    * ParentProcessName
    * CommandLine
* **Network Indicators** (if Sysmon is used):
    * SourceIp, DestinationIp, DestinationPort

## 3. Analysis Heuristics & Rules
The Java backend will implement a rule engine (or predefined heuristic checks) to identify suspicious patterns based on the extracted fields.

Examples of patterns to detect (**TO BE FINALIZED**):
* **Repeated Failed Logons**: Multiple 4625 events followed by a 4624.
* **Suspicious Process Execution**: Event 4688 where CommandLine contains `powershell.exe -ExecutionPolicy Bypass` or encoded commands (`-enc`).
* **Audit Log Clearing**: Event 1102.
* **Scheduled Task Creation**: Event 4698.

## 4. Pipeline Concept

1. **Upload**: User uploads an EVTX file to the frontend.
2. **Storage**: Controller saves file temporarily and enqueues a RabbitMQ message.
3. **Parsing**: RabbitMQ worker reads the EVTX file using a Java parser.
4. **Extraction**: Converts binary/XML records into Java `LogEvent` DTOs.
5. **Rule Evaluation**: Passes `LogEvent`s through the threat detection engine.
6. **Aggregation**: Aggregates found threats into `ThreatIndicator` objects.
7. **Risk Scoring**: Calculates overall `RiskProfile`.
8. **AI Summary**: (Optional) Passes the list of high-severity `ThreatIndicators` to Spring AI for an executive summary of the attack chain.
9. **Persistence**: Saves `LogAnalysis` and associated data to PostgreSQL. Deletes temporary EVTX file.
