package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	_ "github.com/lib/pq"
	"html/template"
	"log"
	"math/rand"
	"net/http"
	"os"
	"time"
)

var db *sql.DB
var templates *template.Template

const (
	createArtistT  = "create table if not exists Artist(id serial primary key, username text, name text, password text, age int, followerCount int, followers text, description text, location text, date timestamp, active boolean, likeCount int);"
	createVideoT   = "create table if not exists Video(id serial primary key, artistId text, title text, description text, uploadTime text, views int, likes int, genre text, tags text);"
	createCommentT = "create table if not exists Comment(id serial primary key, videoId text, artistId text, message text, time timestamp);"
	createGenreT   = "create table if not exists Genre(id serial primary key, name text, description text);"
	createSessionT = "create table if not exists Session(userId text, sessionKey text, time timestamp);"
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

func logServerErr(w http.ResponseWriter, err error) {
	if err != nil {
		log.Println(err)
		http.Error(w, "Server error", http.StatusInternalServerError)
	}
}

func compileTemplates() {
	t, err := template.ParseFiles("../react/dueto/public/index.html")
	templates = template.Must(t, err)
}

func createHash() string {
	letters := []rune("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
	b := make([]rune, 20)

	for i := range b {
		b[i] = letters[rand.Intn(len(letters))]
	}

	return string(b)
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
	query(createSessionT)

	f, err := os.OpenFile("dueto.log", os.O_WRONLY|os.O_CREATE|os.O_APPEND, 0644)
	log.SetOutput(f)
	fmt.Println("Server started ...")

	rand.Seed(time.Now().UnixNano())

	os.MkdirAll("./data/thumbnails/", os.ModePerm)
	os.MkdirAll("./data/videos/", os.ModePerm)
	os.MkdirAll("./data/avatars/", os.ModePerm)
}

func home(w http.ResponseWriter, r *http.Request) {
	templates.ExecuteTemplate(w, "index.html", nil)
}

func main() {
	compileTemplates()

	http.Handle("/js/", http.StripPrefix("/js/", http.FileServer(http.Dir("../react/dueto/build/static/js"))))
	http.HandleFunc("/api/home", homePage)
	http.HandleFunc("/api/profile", profile)
	http.HandleFunc("/api/discover", discover)
	http.HandleFunc("/api/genre", genre)
	http.HandleFunc("/api/login", login)
	http.HandleFunc("/api/logout", logout)
	http.HandleFunc("/api/createuser", createUser)
	http.HandleFunc("/api/video", video)
	http.HandleFunc("/api/avatar", avatar)
	http.HandleFunc("/api/thumbnail", thumbnail)
	http.HandleFunc("/api/addvideo", addVideo)
	http.HandleFunc("/api/changeavatar", addAvatar)
	http.HandleFunc("/api/genreimage", genreImage)
	http.HandleFunc("/api/edituser", editprofile)
	http.HandleFunc("/api/artist", artist)
	http.HandleFunc("/", home)
	http.ListenAndServe(":8080", nil)
}
