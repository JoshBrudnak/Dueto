cd react/dueto
npm run build

cd ../../Server
now=$(date +"%Y-%m-%d")
echo log.txt >> old_log_${now}.txt
go run server.go
