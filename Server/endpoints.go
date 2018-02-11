package main

import (
	"encoding/json"
	"net/http"
    "fmt"
)

const (
	AddComment = "insert into Comment(videoId, message, user, time) VALUES($1, $2, $3, $4);"
	AddArtist  = "insert into Artist(username, name, followers, description, likeCount) VALUES($1, $2, $3, $4, $5);"
	AddVideo   = "insert into Videos(artist, title, desc, time, views, likes) VALUES($1, $2, $3, $4, $5, $6);"

	SelectIntArtistData  = "select username, name, followers, description, date, active, likeCount from Artist where username = $1;"
	SelectExtArtistData  = "select username, name, followers, description, date, active, likeCount from Artist where username = $1;"
	SelectArtistVideos   = "select filePath, title, description, artistId, thumbnail, uploadTime, views, likes, genre from Video where artistId = $1;"
	SelectVideoComments  = "select message, user, timeStamp from Comment where videoId = $1"
	SelectVideosByGenre  = "select artist, filePath, title, description, views, likeCount, date from Video where genre = $1"
	SelectVideosByArtist = "select filePath, title, description, views, likeCount, date, genre from Video where artistId = $1"
)

type BasicArtist struct {
	Id       string
	Name     string
	UserName string
	Avatar   string
}

type Video struct {
	Id        string
	Avatar    string
	Artist    string
	ArtistId  string
	Thumbnail string
	File      string
	Title     string
	Desc      string
	Tags      string
	Genre     string
	Likes     string
	Views     string
	Time      string
}

type VideoList struct {
	VideoCards []Video
}

type ExtArtist struct {
	Name          string
	Username      string
	Age           string
	FollowerCount string
	Bio           string
	Date          string
	Active        string
	LikeCount     string
	VideoList     []Video
}

type IntArtist struct {
	UserName           string
	Name               string
	AccountCreationDay string
	FollowerCount      string
	Followers          string
	Desc               string
	Date               string
	Active             string
	LikeCount          string
	VideoList          []Video
}

type Comment struct {
	Id      string
	artist  BasicArtist
	Message string
	Time    string
}

func query(sql string) {
	_, err := db.Query(sql)
	checkErr(err)
}

func profile(w http.ResponseWriter, r *http.Request) {
	var a IntArtist

	w.Header().Set("Access-Control-Allow-Origin", "*")
	username := r.URL.Query().Get("name")

	rows, err := db.Query(SelectIntArtistData, username)
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
	var v Video
	var videos VideoList
	var filepath string

	w.Header().Set("Access-Control-Allow-Origin", "*")

	rows, err := db.Query(SelectArtistVideos, 1)
	checkErr(err)
	defer rows.Close()

	for rows.Next() {
		if err := rows.Scan(&filepath, &v.Title, &v.Desc, &v.ArtistId, &v.Thumbnail, &v.Time, &v.Views, &v.Likes, &v.Genre); err != nil {
			logIfErr(err)
		}
        fmt.Println(v)

		videos.VideoCards = append(videos.VideoCards, v)
	}

	if err := json.NewEncoder(w).Encode(videos); err != nil {
		logIfErr(err)
	}
}
