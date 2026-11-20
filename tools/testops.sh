#!/bin/sh
set -eu

cd "$(CDPATH= cd "$(dirname "$0")/.." && pwd)"

usage() { printf "%s\n" "Usage: sh tools/testops.sh launches | launch N | --allow-sut | --upload | --finalize" >&2; exit 64; }
[ "$#" -ge 1 ] || usage
case "$1" in
  launches) [ "$#" -eq 1 ] || usage ;;
  launch) [ "$#" -eq 2 ] || usage ;;
  --allow-sut|--upload|--finalize) [ "$#" -eq 1 ] || usage ;;
  *) usage ;;
esac
case "$1:${2-}" in launch:*[!0-9]*) usage ;; esac
missing=""
[ -n "${ALLURE_ENDPOINT:-}" ] || missing="$missing ALLURE_ENDPOINT"
[ -n "${ALLURE_TOKEN:-}" ] || missing="$missing ALLURE_TOKEN"
[ -n "${ALLURE_PROJECT_ID:-}" ] || missing="$missing ALLURE_PROJECT_ID"
[ -z "$missing" ] || { printf "%s\n" "BLOCKED: missing env$missing — see knowledge/integrations/allure-testops.md." >&2; exit 69; }

need() { command -v "$1" >/dev/null 2>&1 || { printf "%s\n" "BLOCKED: missing $1." >&2; exit 69; }; }
# the token travels through a stdin curl config — never argv, files or stdout
tcurl() { printf 'header = "Api-Token: %s"\n' "$ALLURE_TOKEN" | curl -sfS -K - "$@"; }

case "$1" in
  launches)
    need curl
    tcurl "${ALLURE_ENDPOINT%/}/api/rs/launch?projectId=$ALLURE_PROJECT_ID&size=10"
    ;;
  launch)
    need curl
    tcurl "${ALLURE_ENDPOINT%/}/api/rs/launch/$2"
    ;;
  --allow-sut)
    need allurectl
    exec allurectl watch --results 'build/allure-results' -- gradle --offline test
    ;;
  --upload)
    need allurectl
    [ -d 'build/allure-results' ] || { printf "%s\n" "BLOCKED: build/allure-results missing — nothing to upload." >&2; exit 69; }
    exec allurectl upload 'build/allure-results'
    ;;
  --finalize)
    need allurectl
    export $(allurectl job-run env)
    [ -n "${ALLURE_LAUNCH_ID:-}" ] || { printf "%s\n" "BLOCKED: launch id unknown — allurectl job-run env did not resolve a launch." >&2; exit 69; }
    url="${ALLURE_ENDPOINT%/}/launch/$ALLURE_LAUNCH_ID"
    mkdir -p 'build'
    printf "TESTOPS_LAUNCH_ID=%s\nTESTOPS_LAUNCH_URL=%s\n" "$ALLURE_LAUNCH_ID" "$url" | tee 'build/testops-launch.env'
    ;;
esac
