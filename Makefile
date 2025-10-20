
# Couleurs pour les logs
YELLOW = \033[1;33m
GREEN = \033[1;32m
NC = \033[0m # No Color

# Commandes principales
up:
	@echo "$(YELLOW)🚀 Démarrage des services...$(NC)"
	docker-compose --env-file .env up -d back
	@echo "$(GREEN)✅ Tous les services sont démarrés !$(NC)"

down:
	@echo "$(YELLOW)🧹 Arrêt et suppression des conteneurs...$(NC)"
	docker-compose stop back
	docker-compose rm -sf back
	@echo "$(GREEN)✅ Conteneurs arrêtés et supprimés.$(NC)"

build:
	@echo "$(YELLOW)🔧 Construction des images Docker...$(NC)"
	docker-compose  --env-file .env  build back
	@echo "$(GREEN)✅ Construction terminée !$(NC)"

logs:
	@echo "$(YELLOW)📜 Affichage des logs de rag-app (Ctrl+C pour quitter)...$(NC)"
	docker logs rag-back -f

all: down build up logs