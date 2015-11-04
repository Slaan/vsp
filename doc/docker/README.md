# Docker

* Bearbeite die template dockerfile.yml
* Baue Image mit `docker build -f ./docker_for_dice.yml .`. Den Punkt nicht vergessen!
* `docker run -p 127.0.0.1:4567:8080 13b17fe3aac1`
* Mit `docker inspect $(docker ps -q) | grep IP` die IP herausfinden. 
