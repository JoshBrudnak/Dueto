package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	_ "github.com/lib/pq"
	"io/ioutil"
	"os"
	"strings"
)

var db *sql.DB

type config struct {
	URL      string
	Username string
	Password string
	Dbname   string
}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}

func logIfErr(err error) {
	if err != nil {
		fmt.Println(err)
	}
}

func init() {
	var c config
	file, err := os.Open("../database.json")
	checkErr(err)
	decoder := json.NewDecoder(file)
	err = decoder.Decode(&c)
	checkErr(err)
	dbURL := fmt.Sprintf("postgres://%s:%s@%s/%s?sslmode=disable", c.Username, c.Password, c.URL, c.Dbname)
	db, err = sql.Open("postgres", dbURL)
	checkErr(err)
	db.SetMaxOpenConns(80)
}

func main() {
	file, err := ioutil.ReadFile("./testData.sql")
	checkErr(err)

	queries := strings.Split(string(file), ";")

	for _, query := range queries {
		_, dbErr := db.Query(query)
		logIfErr(dbErr)

		if dbErr == nil {
			fmt.Println("Executed: " + query)
		}
	}
}
