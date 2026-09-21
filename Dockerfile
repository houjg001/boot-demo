FROM ubuntu:latest
LABEL authors="P50"

ENTRYPOINT ["top", "-b"]