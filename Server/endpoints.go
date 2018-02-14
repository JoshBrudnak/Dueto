package main

import (
	"encoding/json"
	"golang.org/x/crypto/bcrypt"
	"net/http"
	"time"
)

const (
	AddComment = "insert into Comment(videoId, message, user, time) VALUES($1, $2, $3, $4);"
	AddArtist  = "insert into Artist(username, name, avatar, password, followers, description, likeCount) VALUES($1, $2, $3, $4, $5, $6, $7);"
	AddVideo   = "insert into Videos(artist, title, desc, time, views, likes) VALUES($1, $2, $3, $4, $5, $6);"
	AddGenre   = "insert into Genre(name, description) VALUES('classical', 'melodic usually orchestral instumental or vocal');"
	AddSession = "insert into Session(userId, sessionKey) VALUES($1, $2);"

	SelectBasicArtistData = "select username, name, avatar from artist where id = $1;"
	SelectIntArtistData   = "select username, name, followers, description, date, active, likeCount, id from Artist where username = $1;"
	SelectExtArtistData   = "select username, name, followers, description, date, active, likeCount, id from Artist where username = $1;"
	SelectArtistVideos    = "select filePath, title, description, artistId, thumbnail, uploadTime, views, likes, genre from Video where artistId = $1;"
	SelectVideoComments   = "select message, user, timeStamp from Comment where videoId = $1"
	SelectVideosByGenre   = "select filePath, title, description, views, likes, uploadTime, artistId from Video where genre = $1;"
	SelectVideosByArtist  = "select filePath, title, description, views, likes, uploadTime, genre from Video where artistId = $1;"
	SelectGenres          = "select name, description from Genre;"
	SelectUserAuth        = "select id, password from artist where username = $1;"
	SelectSession         = "select count(userId) from session where sessionkey = $1;"
)

type Genre struct {
	Name        string
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

func authenticate(sessionId string) bool {
    var sessionCount int

	rows, err := db.Query(SelectSession, sessionId)
	checkErr(err)

	rows.Next()
	err = rows.Scan(&sessionCount)
	logIfErr(err)

    if sessionCount > 0 {
        return true
    }

    return false
}

func profile(w http.ResponseWriter, r *http.Request) {
	var v Video
	var a IntArtist
	var artistId int

	w.Header().Set("Access-Control-Allow-Origin", "*")
	username := r.URL.Query().Get("username")
	sessionId := r.URL.Query().Get("sessionid")

    if !authenticate(sessionId) {
        http.Error(w, "Authentication failed", http.StatusForbidden)
        return
    }

	rows, err := db.Query(SelectIntArtistData, username)
	checkErr(err)

	rows.Next()
	err = rows.Scan(&a.UserName, &a.Name, &a.Followers, &a.Desc, &a.Date, &a.Active, &a.LikeCount, &artistId)
	logIfErr(err)
	defer rows.Close()

	videoRows, viderr := db.Query(SelectArtistVideos, artistId)
	logIfErr(viderr)
	defer videoRows.Close()

	for videoRows.Next() {
		err = videoRows.Scan(&v.File, &v.Title, &v.Desc, &artistId, &v.Thumbnail, &v.Time, &v.Views, &v.Likes, &v.Genre)
		logIfErr(err)

		a.VideoList = append(a.VideoList, v)
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
	sessionId := r.URL.Query().Get("sessionid")

    if !authenticate(sessionId) {
        http.Error(w, "Authentication failed", http.StatusForbidden)
        return
    }

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

		err = artistRow.Scan(&a.Name, &a.UserName, &a.Avatar)
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
	sessionId := r.URL.Query().Get("sessionid")

    if !authenticate(sessionId) {
        http.Error(w, "Authentication failed", http.StatusForbidden)
        return
    }

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

		err = artistRow.Scan(&a.Name, &a.UserName, &a.Avatar)
		logIfErr(err)

		a.Id = artistId
		v.Artist = a

		videos.VideoCards = append(videos.VideoCards, v)
	}

	err = json.NewEncoder(w).Encode(videos)
	logIfErr(err)
}

func login(w http.ResponseWriter, r *http.Request) {
	var id int
	var hashPassword string

	w.Header().Set("Access-Control-Allow-Origin", "*")
	username := r.URL.Query().Get("username")
	password := r.URL.Query().Get("password")

	//bHash, err := bcrypt.GenerateFromPassword([]byte(password), 1)

	rows, err := db.Query(SelectUserAuth, username)
	logIfErr(err)
	defer rows.Close()

	rows.Next()
	err = rows.Scan(&id, &hashPassword)
	logIfErr(err)

	err = bcrypt.CompareHashAndPassword([]byte(hashPassword), []byte(password))

	if err == nil {
		sessionId := createHash()
		_, err := db.Query(AddSession, id, sessionId)
		logIfErr(err)

		exp := time.Now().Add(365 * 24 * time.Hour)
		cookie := http.Cookie{Name: "SESSIONID", Value: sessionId, Expires: exp}
		http.SetCookie(w, &cookie)

	}
}
