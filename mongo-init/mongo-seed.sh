#!/bin/bash
mongo -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD --authenticationDatabase admin <<EOF
use $MONGO_INITDB_ROOT_USERNAME
db.createUser({
  user: "$MONGO_INITDB_ROOT_USERNAME",
  pwd: "$MONGO_INITDB_ROOT_USERNAME",
  roles: [{ role: "readWrite", db: "$MONGO_DB" }]
})
EOF

mongoimport --db $MONGO_DB --collection house_rental --file /docker-entrypoint-initdb.d/app.house_rental.json --jsonArray --authenticationDatabase admin -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD
mongoimport --db $MONGO_DB --collection users --file /docker-entrypoint-initdb.d/app.users.json --jsonArray --authenticationDatabase admin -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD
