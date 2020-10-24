#!/bin/bash

export RED='\033[0;31m'
export CYAN="\033[0;36m"
export NC='\033[0m' # No Color

function log {
  [ -z "$SILENT" ] && echo -e "$(date +"%Y-%m-%d %H:%M:%S") ${CYAN}$1${NC}"
}

function error {
  [ -z "$SILENT" ] && echo -e "$(date +"%Y-%m-%d %H:%M:%S") ${RED}$1${NC}" >&2
  exit 1
}

workspace=~/Leonard/GitHub
since="1970-01-01"

if [ -n "$1" ]; then
  log "Setting working directory to $1"
  workspace="$1"
fi

gitRepos=()
while IFS='' read -r line; do gitRepos+=("$line"); done < \
  <(find "$workspace" -name .git -type d -prune -print0 | xargs -0 -I {} dirname {} | tr -s / )

if [ ${#gitRepos[@]} -eq 0 ]; then
  log "Could not find any repository in $workspace" && exit 0
fi

authors=()
log "Found the following repositories:"
for gitRepo in "${gitRepos[@]}"; do
  echo "$gitRepo"
  cd "$gitRepo" || exit 1
  while IFS=$'\n' read -r line; do authors+=("$line"); done < <(git log --format='%aE' | sort -u)
done

if [ ${#authors[@]} -eq 0 ]; then
  log "Could not find any authors" && exit 0
fi

sortedAuthors=()
IFS=$'\n' read -d '' -r -a sortedAuthors < <(printf '%s\n' "${authors[@]}" | sort -u)

stats=()
((counter=0))

log "Found the following authors:"
for author in "${sortedAuthors[@]}"; do
  echo "$author"
  ((numberOfLinesAdded=0))
  ((numberOfLinesDeleted=0))
  for gitRepo in "${gitRepos[@]}"; do
    cd "$gitRepo" || exit 1

    changes=()
    while IFS='' read -r line; do changes+=("$line"); done < \
      <(git log --since=$since --author="$author" --pretty=tformat: --numstat)

    for change in "${changes[@]}"; do
      regexp='^(\-?[0-9\.]+)[[:space:]]*(\-?[0-9\.]+)[[:space:]]*.*$';
      if [[ "$change" =~ $regexp ]]; then
        ((numberOfLinesAdded+=BASH_REMATCH[1]))
        ((numberOfLinesDeleted+=BASH_REMATCH[2]))
      fi
    done
  done
  stats[$counter]=$(printf "%-50s added: %-10s removed: %-10s modified: %-10s" \
    "$author" $numberOfLinesAdded $numberOfLinesDeleted $((numberOfLinesAdded+numberOfLinesDeleted)))
  ((counter++))
done

sortedStats=()
IFS=$'\n' read -d '' -r -a sortedStats < <(printf '%s\n' "${stats[@]}" | sort --key=7,7 --numeric-sort)

log "Here are the stats:"
for stat in "${sortedStats[@]}"; do
  echo "$stat"
done
