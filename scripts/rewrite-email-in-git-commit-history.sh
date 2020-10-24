#!/bin/bash

git filter-branch --commit-filter '
        if [ "$GIT_AUTHOR_EMAIL" = "LeonardLaszlo@users.noreply.github.com" ];
        then
                GIT_AUTHOR_NAME="Leonard Laszlo";
                GIT_AUTHOR_EMAIL="laslaul@yahoo.com";
                git commit-tree "$@";
        else
                git commit-tree "$@";
        fi' HEAD
