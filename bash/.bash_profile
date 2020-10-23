SHELL_SESSION_HISTORY=0

export DOCKER_API_VERSION=1.39

alias tar='gtar'

# alias alarm="ssh alarm@192.168.1.201"
alias alarm="ssh alarm@192.168.1.202"
alias pi="ssh pi@192.168.1.200"
alias odroid="ssh odroid@192.168.1.205"
# alias us="ssh leonard@192.168.1.202" #"ssh -p 2222 leonard@127.0.0.1"
alias us="ssh -p 2222 leonard@127.0.0.1"

alias mong="mongod --config /usr/local/etc/mongod.conf &"
alias redis="redis-server /usr/local/etc/redis.conf &"

alias w="~/Leonard/GitHub/weather-in-hungary/weather.sh"
alias sem="~/Leonard/GitHub/sandbox/scripts/is-anyone-in-sem.sh"

# Git
. ~/Leonard/GitHub/sandbox/bash/.git-completion.bash
. ~/Leonard/GitHub/sandbox/bash/.git-prompt.sh
export GPG_TTY=$(tty)
# export PS1='\w$(__git_ps1 " (%s)")\$ '
# export PS1='\n\[\033[38;5;209m\]\u\[\033[m\] at \[\033[38;5;228;1m\]\h\[\033[m\] in \[\033[38;5;42;1m\]\w\[\033[31m\]$(__git_ps1 " (%s)")\[\033[m\]\n\$ '
export GIT_PS1_SHOWDIRTYSTATE=1
export PS1='\e[96m\w\e[93m$(__git_ps1 " %s")\e[0m\n\$ '
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.5.jdk/Contents/Home/
export PATH=$JAVA_HOME/bin:$PATH

alias ec="/Applications/calibre.app//Contents/ebook-viewer.app/Contents/MacOS/ebook-convert "
alias dc="~/projects/bash-scripts/docker-clean.sh"
alias rsc="docker run -d --name sonarqube -p 9000:9000 sonarqube"

# grep -Ril --include \*.java "optionValues()" .
alias findInJava="grep -Ril --include \*.java"
alias findInDir="grep -Ril"
alias f="grep -rnw . -e"

alias e="nano ~/Leonard/GitHub/sandbox/bash/.bash_profile"
alias u="source ~/.bash_profile"
alias c="clear"
alias x="exit"
alias ll="ls -la"

alias gf="git fetch --all --tags --prune --prune-tags"
alias gl="git log --oneline --all --graph --decorate --color --abbrev-commit"
# alias gl="git log --oneline --all --graph --decorate --pretty"
alias ga="git add"
alias gb="git branch"
alias gs="git status"
alias gp="gf && git pull"
alias gpu="git push"
alias gpo="git push --all odroid"
alias gpug="git push --all github"
alias gd="git diff"
alias gdc="git diff --cached"
alias gdn="git diff --numstat |  cut -d$'\t' -f 1 | paste -sd+ - | bc"
alias gdlc="git diff HEAD^ HEAD"
alias gc="git commit -S"
alias gco="git checkout"

# Gradle
alias gb="./gradlew clean build"
alias gw="./gradlew "
alias br="./gradlew bootRun"

# Maven
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
