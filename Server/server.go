package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	_ "github.com/lib/pq"
	"log"
	"net/http"
	"os"
)

var db *sql.DB

const (
	createArtistT  = "create table if not exists Artist(id serial primary key, username text, name text, followers text, description text, date timestamp, active boolean, likeCount int);"
	createVideoT   = "create table if not exists Video(id serial primary key, artist text, filePath text, title text, description text, time text, views int, likes int, genre text);"
	createCommentT = "create table if not exists Comment(id serial primary key, videoId text, message text, users text, time timestamp);"

	AddComment = "insert into Comment(videoId, message, user, time) VALUES($1, $2, $3, $4);"
	AddArtist  = "insert into Artist(username, name, followers, description, likeCount) VALUES($1, $2, $3, $4, $5);"
	AddVideo   = "insert into Videos(artist, title, desc, time, views, likes) VALUES($1, $2, $3, $4, $5, $6);"

	SelectArtistData    = "select username, name, followers, description, date, active, likeCount from Artist where username = $1;"
	SelectArtistVideos  = "select fileName, title, description, views, likes from Videos where artist = $1;"
	SelectVideoComments = "select message, user, timeStamp from Comment where videoId = $1"
	SelectVideosByGenre = "select artist, filePath, title, description, views, likeCount, date from Video where genre = $1"
	SelectVideosByArtist = "select filePath, title, description, views, likeCount, date, genre from Video where artist = $1"
)

type Video struct {
	Artist string
	File   string
	Title  string
	Desc   string
	Tags   string
	Genre  string
	Likes  string
	Views  string
	Time   string
}

type Artist struct {
	UserName  string
	Name      string
	Followers string
	Desc      string
	Date      string
	Active    string
	LikeCount string
}

type Comment struct {
	Message string
	User    string
	Time    string
}

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

func query(sql string) {
	_, err := db.Query(sql)
	checkErr(err)
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

func profile(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "*")
    username := r.URL.Query().Get("name")

	var a Artist
	rows, err := db.Query(SelectArtistData, username)
	checkErr(err)
	defer rows.Close()

	rows.Next()
	if err := rows.Scan(&a.UserName, &a.Name, &a.Followers, &a.Desc, &a.Date, &a.Active, &a.LikeCount); err != nil {
		logIfErr(err)
	}

	if err := json.NewEncoder(w).Encode(a); err != nil {
		logIfErr(err)
	}
}

func main() {
	r := mux.NewRouter()
	r.HandleFunc("/api/home", home)
	r.HandleFunc("/api/profile", profile)
	http.Handle("/", r)
	http.ListenAndServe(":8082", nil)
}
