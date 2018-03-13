package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"golang.org/x/crypto/bcrypt"
	"io/ioutil"
	"net/http"
	"os"
	"os/exec"
	"time"
)

const (
	AddComment = "insert into Comment(videoId, message, user, time) VALUES($1, $2, $3, $4);"
	AddArtist  = "insert into Artist(username, name, age, password, followers, description, likeCount, location) VALUES($1, $2, $3, $4, $5, $6, $7, $8);"
	AddVideo   = "insert into Video(artistId, title, description, uploadTime, views, likes, filePath) VALUES($1, $2, $3, now()::timestamp, 0, 0, $4);"
	AddGenre   = "insert into Genre(name, description) VALUES($1, $2);"
	AddSession = "insert into Session(userId, sessionKey, time) VALUES($1, $2, now()::timestamp);"

	RemoveSession = "delete from Session where sessionkey = $1;"

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
	SetExpirationTime     = "expire from session after $1 where sessionKey= $1;"
	SelectAuthId          = "select userId from session where sessionKey = $1;"
)

type Authentication struct {
	Username string
	Password string
}

type NewVideo struct {
	Video string
	Name  string
	Desc  string
}

type NewUser struct {
	Name       string
	Username   string
	Password   string
	Repassword string
	Bio        string
	Age        string
	Loc        string
}

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

func authenticate(cookie *http.Cookie) bool {
	var sessionCount int

	if cookie.String() != "" {
		sessionId := cookie.Value

		rows, err := db.Query(SelectSession, sessionId)
		checkErr(err)

		rows.Next()
		err = rows.Scan(&sessionCount)
		logIfErr(err)

		if sessionCount > 0 {
			return true
		}
	}

	return false
}

func profile(w http.ResponseWriter, r *http.Request) {
	var v Video
	var a IntArtist
	var artistId int

	w.Header().Set("Access-Control-Allow-Origin", "*")
	username := r.URL.Query().Get("username")
	cookie, err := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
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
	cookie, err := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
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

		artistRow.Next()
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
	cookie, err := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
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

func serveFile(w http.ResponseWriter, r *http.Request, fileType string) {
	w.Header().Set("Access-Control-Allow-Origin", "*")
	artistId := r.URL.Query().Get("artist")
	videoName := r.URL.Query().Get("name")

	filePath := "./data/" + fileType + "/" + artistId + "/" + videoName + ".mp4"
	http.ServeFile(w, r, filePath)
}

func video(w http.ResponseWriter, r *http.Request) {
	serveFile(w, r, "videos")
}

func avatar(w http.ResponseWriter, r *http.Request) {
	serveFile(w, r, "avatars")
}

func thumbnail(w http.ResponseWriter, r *http.Request) {
	serveFile(w, r, "thumbnails")
}

func genreImage(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "*")
	genreName := r.URL.Query().Get("name")

	filePath := "./data/genres/" + genreName + ".png"
	http.ServeFile(w, r, filePath)
}

func createUser(w http.ResponseWriter, r *http.Request) {
	var id string
	var hashPassword string
	var data NewUser

	w.Header().Set("Access-Control-Allow-Origin", "*")
	decoder := json.NewDecoder(r.Body)
	decoder.Decode(&data)

	if data.Password != data.Repassword {
		http.Error(w, "Passwords do not match", http.StatusNotAcceptable)
		return
	}

	bHash, err := bcrypt.GenerateFromPassword([]byte(data.Password), 1)
	hash := string(bHash)

	rows, err := db.Query(AddArtist, data.Username, data.Name, data.Age, hash, 0, data.Bio, 0, data.Loc)
	rows.Close()

	authRows, err := db.Query(SelectUserAuth, data.Username)
	logIfErr(err)

	if authRows.Next() {
		err = authRows.Scan(&id, &hashPassword)
		logIfErr(err)
	} else {
		http.Error(w, "Unable to create user", http.StatusForbidden)
	}
	authRows.Close()

	os.Mkdir("./data/videos/"+id, os.ModePerm)
	os.Mkdir("./data/avatars/"+id, os.ModePerm)
}

func login(w http.ResponseWriter, r *http.Request) {
	var id int
	var hashPassword string
	var auth Authentication

	w.Header().Set("Access-Control-Allow-Origin", "*")
	decoder := json.NewDecoder(r.Body)
	decoder.Decode(&auth)
	defer r.Body.Close()

	if auth.Username == "" || auth.Password == "" {
		return
	}

	rows, err := db.Query(SelectUserAuth, auth.Username)
	logIfErr(err)

	rows.Next()
	err = rows.Scan(&id, &hashPassword)
	logIfErr(err)
	rows.Close()

	err = bcrypt.CompareHashAndPassword([]byte(hashPassword), []byte(auth.Password))

	if err == nil {
		sessionId := createHash()
		rows, err = db.Query(AddSession, id, sessionId)
		logIfErr(err)
        rows.Close()

		exp := time.Now().Add(time.Hour)
		cookie := http.Cookie{Name: "SESSIONID", Value: sessionId, Path: "/", Expires: exp}
		http.SetCookie(w, &cookie)
	}
}

func logout(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, err := r.Cookie("SESSIONID")
	sessionId := cookie.Value

	rows, err := db.Query(RemoveSession, sessionId)
	logIfErr(err)
	rows.Close()

	if err == nil {
		exp := time.Now().AddDate(0, 0, 1)
		cookie := http.Cookie{Name: "SESSIONID", Value: "", Expires: exp}
		http.SetCookie(w, &cookie)
	}
}

func getThumbnail(artist string, name string) {
	var buffer bytes.Buffer

	width := 640
	height := 360
	videoPath := fmt.Sprintf("./data/videos/%s/%s.mp4", artist, name)
	thumbnailPath := fmt.Sprintf("./data/thumbnails/%s/%s.jpeg", artist, name)
	f, err := os.Create(thumbnailPath)
	logIfErr(err)
	f.Close()

	cmd := exec.Command("ffmpeg", "-i", videoPath, "-vframes", "1", "-s", fmt.Sprintf("%dx%d", width, height), "-f", "singlejpeg", "-")
	cmd.Stdout = &buffer
	err = cmd.Run()
	logIfErr(err)

	thumbnail, err := os.OpenFile(thumbnailPath, os.O_APPEND|os.O_WRONLY, os.ModeAppend)
	logIfErr(err)

	_, err = thumbnail.Write(buffer.Bytes())
	logIfErr(err)
	thumbnail.Close()
}

func addVideo(w http.ResponseWriter, r *http.Request) {
	var artist string

	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, err := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

	sessionId := cookie.Value

	file, _, err := r.FormFile("file")
	logIfErr(err)

	name := r.FormValue("name")
	desc := r.FormValue("desc")
	data, err := ioutil.ReadAll(file)
	logIfErr(err)

	rows, err := db.Query(SelectAuthId, sessionId)
	logIfErr(err)

	rows.Next()
	err = rows.Scan(&artist)
	logIfErr(err)
	rows.Close()

	filePath := fmt.Sprintf("./data/videos/%s/%s.mp4", artist, name)

	f, err := os.Create(filePath)
	logIfErr(err)

	_, err = f.Write(data)
	logIfErr(err)
	f.Close()

	getThumbnail(artist, name)

	rows, err = db.Query(AddVideo, artist, name, desc, filePath)
	logIfErr(err)
	rows.Close()
}

func addAvatar(w http.ResponseWriter, r *http.Request) {
	var artist string

	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, err := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

	sessionId := cookie.Value

	file, _, err := r.FormFile("file")
	logIfErr(err)

	name := r.FormValue("name")
	data, err := ioutil.ReadAll(file)
	logIfErr(err)

	rows, err := db.Query(SelectAuthId, sessionId)
	logIfErr(err)

	rows.Next()
	err = rows.Scan(&artist)
	logIfErr(err)
	rows.Close()

	filePath := fmt.Sprintf("./data/avatars/%s/%s", artist, name)

	f, err := os.Create(filePath)
	logIfErr(err)

	_, err = f.Write(data)
	logIfErr(err)
	f.Close()
}
