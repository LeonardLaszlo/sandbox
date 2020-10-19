# docker image build -t laslaul/cv-latex:cv .
# docker run -it laslaul/cv-latex
# or
# CONTAINER_ID=$( docker run --detach --tty laslaul/cv-latex )
# docker cp cv.tex "$CONTAINER_ID":/usr/docker/ && docker exec --interactive --tty "$CONTAINER_ID" make xelatex && docker cp "$CONTAINER_ID":/usr/docker/cv.pdf .
# docker stop "$CONTAINER_ID"
# docker rm "$CONTAINER_ID"

# Use the official image as a parent image.
FROM ubuntu:20.04

# Set the working directory.
WORKDIR /usr/docker

# Copy the files from your host to your current location.
COPY fonts/ ./fonts
COPY cv.tex .
COPY fontawesome.sty .
COPY friggeri-cv.cls .
COPY Makefile .
COPY photo.jpg .

# Run the command inside your image filesystem.
RUN apt-get update
RUN apt-get -y upgrade
RUN apt-get -y install bash-completion make tzdata nano
RUN echo "Europe/Zurich" > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata
RUN apt-get -y install texlive-xetex
# Needs to be executed twice for the first time.
RUN make xelatex
RUN make xelatex
