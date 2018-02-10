package main

import (
	"fmt"
    "encoding/json"
	"io/ioutil"
	"net/http"
)

const (
	AddComment = "insert into Comment(videoId, message, user, time) VALUES($1, $2, $3, $4);"
	AddArtist  = "insert into Artist(username, name, followers, description, likeCount) VALUES($1, $2, $3, $4, $5);"
	AddVideo   = "insert into Videos(artist, title, desc, time, views, likes) VALUES($1, $2, $3, $4, $5, $6);"

	SelectArtistData     = "select username, name, followers, description, date, active, likeCount from Artist where username = $1;"
	SelectArtistVideos   = "select fileName, title, description, views, likes from Videos where artist = $1;"
	SelectVideoComments  = "select message, user, timeStamp from Comment where videoId = $1"
	SelectVideosByGenre  = "select artist, filePath, title, description, views, likeCount, date from Video where genre = $1"
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

func query(sql string) {
	_, err := db.Query(sql)
	checkErr(err)
}

func api(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
	data, err := ioutil.ReadFile("../react/dueto/build/static/js/main.js")
	if err != nil {
		panic(err)
	}
	w.Header().Set("Content-Length", fmt.Sprint(len(data)))
	fmt.Fprint(w, string(data))
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

func homePage(w http.ResponseWriter, r *http.Request) {
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
