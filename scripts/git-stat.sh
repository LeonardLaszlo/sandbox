#!/bin/bash

workspace=/Users/laszloleonard/Documents/git
since=1970-01-01

gitRepos=(`find $workspace -name .git -type d -prune`)

authors=()
stats=()

for gitRepo in ${gitRepos[@]}; do
  echo $gitRepo
  cd $gitRepo/..
  authors+=(`git log --format='%aE' | sort -u`)
done

IFS=$'\n'
sortedAuthors=($(sort -u <<<"${authors[*]}"))
unset IFS

((counter=0))

for author in ${sortedAuthors[@]}; do
  echo $author
  ((numberOfLinesAdded=0))
  ((numberOfLinesDeleted=0))
  ((numberOfModifiedNumbers=0))

  for gitRepo in ${gitRepos[@]}; do
    cd $gitRepo/..

    IFS=$'\n' # to iterate over the lines
    changes=(`git log --since=$since --author="$author" --pretty=tformat: --numstat`)

    for change in ${changes[@]}; do
      unset IFS
      set $change
      regex="^[0-9]+$"

      if [[ $1 =~ $regex ]]; then
        ((numberOfLinesAdded+=$1))
      else
        ((numberOfLinesAdded+=1))
      fi

      if [[ $2 =~ $regex ]]; then
        ((numberOfLinesDeleted+=$2))
      else
        ((numberOfLinesDeleted+=1))
      fi
    done

    unset IFS
  done

  ((numberOfModifiedNumbers+=$numberOfLinesAdded))
  ((numberOfModifiedNumbers+=$numberOfLinesDeleted))
  stats[$counter]=`printf "%-50s added: %-10s removed: %-10s modified: %-10s" $author $numberOfLinesAdded $numberOfLinesDeleted $numberOfModifiedNumbers`
  ((counter++))
done

IFS=$'\n'
sortedStats=($(sort --key=7,7 --numeric-sort <<<"${stats[*]}"))

for stat in "${sortedStats[@]}"; do
  echo $stat
done

unset IFS
