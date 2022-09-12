#!/bin/bash

CURRENT_DIR=$(cd $(dirname $0); pwd)

DOCKER_IMAGE=flyway/flyway:9.3.0-alpine
DOCKER_NETWORK="examples-ddd-clean-architecture_default"

[[ $2 ]] || { echo "Arguments missing" >&2; exit 1; }

ENV=$1
COMMAND=$2

docker run --rm --network=$DOCKER_NETWORK \
  -v "${CURRENT_DIR}/../external/migration/conf:/flyway/conf" \
  -v "${CURRENT_DIR}/../external/migration/sql:/flyway/sql" \
  $DOCKER_IMAGE -configFiles="conf/${ENV}.conf" $COMMAND


