package main

import (
    "fmt"
    "os"
	"database/sql"
	"encoding/json"
	"log"
    "net/http"
	_ "github.com/lib/pq"
    "github.com/gorilla/mux"
)

var db *sql.DB

const (
	createArtistT = "create table if not exists Artists(id serial primary key, username text, name text, followers text, description text, date timestamp, active boolean, likeCount int);"
	createVideoT = "create table if not exists Videos(id serial primary key, artist text, filePath text, title text, description text, time text, views int, likes int);"
	createCommentT = "create table if not exists Comment(id serial primary key, videoId text, message text, user text, time timestamp);"

	AddComment = "insert into Comment(videoId, message, user, time) VALUES($1, $2, $3, $4);"
	AddArtist = "insert into Artist(username, name, followers, description, likeCount) VALUES($1, $2, $3, $4, $5);"
	AddVideo = "insert into Videos(artist, title, desc, time, views, likes) VALUES($1, $2, $3, $4, $5, $6);"

	SelectArtistData = "select username, name, followers from Artist where username = $1;"
	SelectArtistVideos = "select fileName, title, description, views, likes from Videos where artist = $1;"
	SelectVideoComments = "select message, user, timeStamp from Comment where videoId = $1"
)

type Video struct {
	Artist       string
	File         string
	Title        string
	Desc         string
	Tags         string
	Genre        string
	Likes        string
	Views        string
	Time         string
}

type Artist struct {
	UserName       string
	Name         string
	Followers        string
	Desc         string
	Date        string
	Active        string
	LikeCount        string
}

type Comment struct {
	Message       string
	User         string
	Time        string
}

type config struct {
	URL      string
	Username string
	Password string
	Dbname   string
}

func checkErr(err error) {
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

func home(http.ResponseWriter, *http.Request) {

}

func main() {
	r := mux.NewRouter()
	r.HandleFunc("/api/home", home)
	r.HandleFunc("/api/profile", home)
	http.Handle("/", r)
	http.ListenAndServe(":8082", nil)
}
