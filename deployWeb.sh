cd react/dueto
npm run build

cd ../../Server
now=$(date +"%Y-%m-%d")
echo log.txt >> old_log_${now}.txt
rm -f log.txt

go get github.com/gorilla/mux
go get github.com/lib/pq
go run server.go endpoints.go
