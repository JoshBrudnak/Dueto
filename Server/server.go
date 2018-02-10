package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	_ "github.com/lib/pq"
	"html/template"
	"log"
	"net/http"
	"os"
)

var db *sql.DB
var templates *template.Template

const (
	createArtistT  = "create table if not exists Artist(id serial primary key, username text, name text, age int, followerCount int, followers text, description text, location text, date timestamp, active boolean, likeCount int);"
	createVideoT   = "create table if not exists Video(id serial primary key, thumbnail text, artistId text, filePath text, title text, description text, uploadTime text, views int, likes int, genre text, tags text);"
	createCommentT = "create table if not exists Comment(id serial primary key, videoId text, artistId text, message text, time timestamp);"
    createSessionT = "create table if not exists Session(userId text, sessionKey text);"
)

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
		log.Println(err)
	}
}

func compileTemplates() {
	t, err := template.ParseFiles("../react/dueto/public/index.html")
	templates = template.Must(t, err)
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

	query(createArtistT)
	query(createVideoT)
	query(createCommentT)

	f, err := os.OpenFile("dueto.log", os.O_WRONLY|os.O_CREATE|os.O_APPEND, 0644)
	log.SetOutput(f)
	fmt.Println("Server started ...")
}

func home(w http.ResponseWriter, r *http.Request) {
	templates.ExecuteTemplate(w, "index.html", nil)
}

func main() {
	compileTemplates()

	http.Handle("/js/", http.StripPrefix("/js/", http.FileServer(http.Dir("../react/dueto/build/static/js"))))
	http.HandleFunc("/api/home", homePage)
	http.HandleFunc("/api/profile", profile)
	http.HandleFunc("/", home)
	http.ListenAndServe(":8080", nil)
}
