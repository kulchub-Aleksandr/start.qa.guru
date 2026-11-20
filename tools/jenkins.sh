#!/bin/sh
set -eu

cd "$(CDPATH= cd "$(dirname "$0")/.." && pwd)"

usage() { printf "%s\n" "Usage: sh tools/jenkins.sh status [N] | console [N] | artifacts N | --allow-ci-write trigger [K=V ...]" >&2; exit 64; }
allow_write=false
if [ "${1-}" = "--allow-ci-write" ]; then allow_write=true; shift; fi
case "${1-}" in
  status|console) [ "$#" -le 2 ] || usage ;;
  artifacts) [ "$#" -eq 2 ] || usage ;;
  trigger) [ "$allow_write" = true ] || { printf "%s\n" "BLOCKED: approve CI write — run with --allow-ci-write." >&2; exit 77; } ;;
  *) usage ;;
esac
if [ "${1-}" != "trigger" ]; then
  n=${2:-lastBuild}
  case "$n" in
    lastBuild|lastSuccessfulBuild|lastFailedBuild|lastCompletedBuild|lastStableBuild) ;;
    *[!0-9]*) usage ;;
  esac
fi
missing=""
[ -n "${JENKINS_URL:-}" ] || missing="$missing JENKINS_URL"
[ -n "${JENKINS_USER:-}" ] || missing="$missing JENKINS_USER"
[ -n "${JENKINS_TOKEN:-}" ] || missing="$missing JENKINS_TOKEN"
[ -z "$missing" ] || { printf "%s\n" "BLOCKED: missing env$missing — see knowledge/integrations/ci-jenkins.md." >&2; exit 69; }
command -v curl >/dev/null 2>&1 || { printf "%s\n" "BLOCKED: missing curl." >&2; exit 69; }

# job path "a/b" maps to REST segments "job/a/job/b"; default is the live test-evolution job
job=$(printf "%s" "${JENKINS_JOB:-test-evolution/run}" | sed "s|/|/job/|g")
base="${JENKINS_URL%/}/job/$job"

# credentials travel through a stdin curl config — never argv, files or stdout
jcurl() { printf 'user = "%s:%s"\n' "$JENKINS_USER" "$JENKINS_TOKEN" | curl -sfS -K - "$@"; }

case "$1" in
  status)
    resp=$(jcurl "$base/$n/api/json?tree=number,result,building,url")
    printf "%s\n" "$resp" | tr "," "\n" | sed -n '
      s/.*"number":\([0-9][0-9]*\).*/JENKINS_BUILD=\1/p
      s/.*"result":"\([A-Za-z_]*\)".*/JENKINS_RESULT=\1/p
      s/.*"result":null.*/JENKINS_RESULT=null/p
      s/.*"building":\([a-z][a-z]*\).*/JENKINS_BUILDING=\1/p
      s/.*"url":"\([^"]*\)".*/JENKINS_BUILD_URL=\1/p'
    ;;
  console)
    jcurl "$base/$n/consoleText"
    ;;
  artifacts)
    resp=$(jcurl "$base/$n/api/json?tree=artifacts%5BfileName,relativePath%5D")
    printf "%s\n" "$resp" | tr "}" "\n" | sed -n 's/.*"relativePath":"\([^"]*\)".*/\1/p'
    ;;
  trigger)
    shift
    url="$base/build"
    if [ "$#" -gt 0 ]; then
      url="$base/buildWithParameters"
      count=$#
      for kv in "$@"; do
        case "$kv" in *=*) ;; *) usage ;; esac
        set -- "$@" --data-urlencode "$kv"
      done
      shift "$count"
    fi
    headers=$(jcurl -o /dev/null -D - -X POST "$url" "$@")
    location=$(printf "%s\n" "$headers" | sed -n "s/^[Ll]ocation:[[:space:]]*//p" | tr -d "\r")
    printf "JENKINS_QUEUE=%s\n" "${location:-unreported}"
    ;;
esac
