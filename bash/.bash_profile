SHELL_SESSION_HISTORY=0

export DOCKER_API_VERSION=1.39

alias tar='gtar'

source ~/.git-completion.bash
source ~/.git-prompt.sh

export GIT_PS1_SHOWDIRTYSTATE=1
export PS1='\e[96m\w\e[93m$(__git_ps1 " %s")\e[0m\n\$ '
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.4.jdk/Contents/Home/
export PATH=$JAVA_HOME/bin:$PATH

alias ec="/Applications/calibre.app//Contents/ebook-viewer.app/Contents/MacOS/ebook-convert "
alias dc="~/projects/bash-scripts/docker-clean.sh"
alias rsc="docker run -d --name sonarqube -p 9000:9000 sonarqube"

# grep -Ril --include \*.java "optionValues()" .
alias findInJava="grep -Ril --include \*.java"
alias findInDir="grep -Ril"

alias e="nano ~/.bash_profile"
alias u="source ~/.bash_profile"
alias c="clear"
alias x="exit"
alias ll="ls -la"

alias gf="git fetch --all --tags --prune --prune-tags"
alias gl="git log --oneline --graph --all --decorate --pretty"
alias ga="git add"
alias gb="git branch"
alias gs="git status"
alias gp="gf && git pull"
alias gpu="git push"
alias gd="git diff"
alias gdc="git diff --cached"
alias gdn="git diff --numstat |  cut -d$'\t' -f 1 | paste -sd+ - | bc"
alias gdlc="git diff HEAD^ HEAD"
alias gc="git commit -s"
alias gco="git checkout"

alias mpit="mvn pre-integration-test -DskipTests=true -DskipITs=false"

function notifyUser() {
  if [[ $1 -eq 0 ]]
  then
    /usr/bin/osascript -e 'display notification "Fuck yeah!" with title "Success"'
  else
    /usr/bin/osascript -e 'display notification "Fuck no!" with title "Failure"'
  fi
}

function mci() {
  mvn clean install -DskipTests=true -DskipITs=true -P verify ${@}
  notifyUser ${?}
}

function mc() {
  mvn clean ${@}
  notifyUser ${?}
}

function mi() {
  mvn install -DskipTests=true -DskipITs=true -P verify ${@}
  notifyUser ${?}
}

function mut() {
  mvn clean install -DskipTests=false -DskipITs=true ${@}
  notifyUser ${?}
}
