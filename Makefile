###############
#    Docker
###############

dc-up:
	docker-compose -f docker-compose.yml up -d
dc-down:
	docker-compose -f docker-compose.yml down
dc-restart: dc-down dc-up