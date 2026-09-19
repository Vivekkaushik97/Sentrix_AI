# History API

## Analysis
* The Frontend requires a `/history` route to show a paginated table of past actions.
* The `/api/v1/analyses` endpoint completely fulfills this requirement by providing paginated, filterable access to `analysis_records`.
* **Decision**: NO separate `/api/v1/history` endpoint will be created. The frontend will consume `/api/v1/analyses`.
