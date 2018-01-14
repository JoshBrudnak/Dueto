package main

import (
    "fmt"
    "os"
    "json"
    "bufio"
	"database/sql"
	"encoding/json"
	"io/ioutil"
	"log"
	"os"
	_ "github.com/lib/pq"
)

var db *sql.DB

func checkErr(err *os.Error) {
  if(err != nil) {
     panic(err)
  }
}

func init() {
	var c config
	file, err := os.Open("database.json")
	checkErr(err)
	decoder := json.NewDecoder(file)
	err = decoder.Decode(&c)
	checkErr(err)
	dbURL := fmt.Sprintf("postgres://%s:%s@%s/%s?sslmode=disable", c.Username, c.Password, c.URL, c.Dbname)
	db, err = sql.Open("postgres", dbURL)
	checkErr(err)
	db.SetMaxOpenConns(80)

	f, err := os.OpenFile("dueto.log", os.O_WRONLY|os.O_CREATE|os.O_APPEND, 0644)
	log.SetOutput(f)
}

func main() {

}
