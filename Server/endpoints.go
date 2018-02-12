package main

import (
	"encoding/json"
	"net/http"
)

const (
	AddComment = "insert into Comment(videoId, message, user, time) VALUES($1, $2, $3, $4);"
	AddArtist  = "insert into Artist(username, name, avatar, password, followers, description, likeCount) VALUES($1, $2, $3, $4, $5, $6, $7);"
	AddVideo   = "insert into Videos(artist, title, desc, time, views, likes) VALUES($1, $2, $3, $4, $5, $6);"
    AddGenre   = "insert into Genre(name, description) VALUES('classical', 'melodic usually orchestral instumental or vocal');"
    AddSession = "insert into Session(userId, sessionKey) VALUES($1, $2);"

    SelectBasicArtistData = "select username, name, avatar from artist where artistId = $1;"
	SelectIntArtistData  = "select username, name, followers, description, date, active, likeCount from Artist where username = $1;"
	SelectExtArtistData  = "select username, name, followers, description, date, active, likeCount from Artist where username = $1;"
	SelectArtistVideos   = "select filePath, title, description, artistId, thumbnail, uploadTime, views, likes, genre from Video where artistId = $1;"
	SelectVideoComments  = "select message, user, timeStamp from Comment where videoId = $1"
	SelectVideosByGenre  = "select filePath, title, description, views, likes, uploadTime, artistId from Video where genre = $1;"
	SelectVideosByArtist = "select filePath, title, description, views, likes, uploadTime, genre from Video where artistId = $1;"
    SelectGenres         = "select name, description from Genre;"
)

type Genre struct {
    Name string
    Description string
}

type Genres struct {
    GenreList []Genre
}

type BasicArtist struct {
	Id       string
	Name     string
	UserName string
	Avatar   string
}

type Video struct {
    Artist    BasicArtist
	Id        string
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
	logIfErr(err)
}

func profile(w http.ResponseWriter, r *http.Request) {
	var a IntArtist

	w.Header().Set("Access-Control-Allow-Origin", "*")
	username := r.URL.Query().Get("username")

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
    var artistId string

	w.Header().Set("Access-Control-Allow-Origin", "*")

	rows, err := db.Query(SelectArtistVideos, 1)
	checkErr(err)
	defer rows.Close()

	for rows.Next() {
		err = rows.Scan(&filepath, &v.Title, &v.Desc, &artistId, &v.Thumbnail, &v.Time, &v.Views, &v.Likes, &v.Genre)
		logIfErr(err)

        var a BasicArtist
	    artistRow, aErr := db.Query(SelectBasicArtistData, artistId)
        logIfErr(aErr)
	    defer artistRow.Close()
        rows.Next()

		err = rows.Scan(&a.Name, &a.UserName, &a.Avatar)
		logIfErr(err)

        a.Id = artistId
        v.Artist = a

		videos.VideoCards = append(videos.VideoCards, v)
	}

	if err := json.NewEncoder(w).Encode(videos); err != nil {
		logIfErr(err)
	}
}

func discover(w http.ResponseWriter, r *http.Request) {
	var g Genre
	var genres Genres

	w.Header().Set("Access-Control-Allow-Origin", "*")

	rows, err := db.Query(SelectGenres)
	checkErr(err)
	defer rows.Close()

	for rows.Next() {
		if err := rows.Scan(&g.Name, &g.Description); err != nil {
			logIfErr(err)
		}

		genres.GenreList = append(genres.GenreList, g)
	}

	if err := json.NewEncoder(w).Encode(genres); err != nil {
		logIfErr(err)
	}
}

func genre(w http.ResponseWriter, r *http.Request) {
	var v Video
	var videos VideoList
	var filepath string
    var artistId string

	w.Header().Set("Access-Control-Allow-Origin", "*")
	genre := r.URL.Query().Get("genre")

	rows, err := db.Query(SelectVideosByGenre, genre)
    logIfErr(err)
	defer rows.Close()

	for rows.Next() {
		err = rows.Scan(&filepath, &v.Title, &v.Desc, &v.Views, &v.Likes, &v.Time, &artistId)
		logIfErr(err)

        var a BasicArtist
        v.Genre = genre
	    artistRow, err := db.Query(SelectBasicArtistData, artistId)
        logIfErr(err)
	    defer artistRow.Close()
        rows.Next()

		err = rows.Scan(&a.Name, &a.UserName, &a.Avatar)
		logIfErr(err)

        a.Id = artistId
        v.Artist = a

		videos.VideoCards = append(videos.VideoCards, v)
	}

	err = json.NewEncoder(w).Encode(videos)
	logIfErr(err)
}
