docker run \
    --rm \
    --platform linux/amd64/v8 \
    --network host \
  	-e PACT_BROKER_BASE_URL=http://localhost:8000 \
  	-e PACT_BROKER_USERNAME=test \
  	-e PACT_BROKER_PASSWORD=test \
  	pactfoundation/pact-cli:latest \
  	broker can-i-deploy \
  	--pacticipant $1 \
  	--latest