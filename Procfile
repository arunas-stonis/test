web: java  -jar target/*.war --spring.profiles.active=dev,heroku --server.port=$PORT --spring.data.mongodb.database=$(echo "$MONGODB_URI" | sed "s/^.*:[0-9]*\///g")
