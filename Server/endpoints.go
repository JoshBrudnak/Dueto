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
	AddArtist  = "insert into Artist(username, name, age, password, followers, description, likeCount, location) VALUES($1, $2, $3, $4, $5, $6, $7, $8::json);"
	AddVideo   = "insert into Video(artistId, title, description, uploadTime, views, likes) VALUES($1, $2, $3, now()::timestamp, 0, 0);"
	AddGenre   = "insert into Genre(name, description) VALUES($1, $2);"
	AddSession = "insert into Session(userId, sessionKey, time) VALUES($1, $2, now()::timestamp);"
	AddMessage = "insert into Comment(sender, receiver, message, time) VALUES($1, $2, $3, now()::timestamp);"

	RemoveSession = "delete from Session where sessionkey = $1;"
    RemoveOldSessions = "delete from session where age(now(), time) > '1 hour';"

	SelectBasicArtistData = "select username, name, avatar from artist where id = $1;"
	SelectIntArtistData   = "select username, name, followers, description, date, active, likeCount from Artist where id = $1;"
	SelectExtArtistData   = "select username, name, description, date, active, followerCount, likeCount from Artist where id = $1;"
	SelectArtistVideos    = "select id, title, description, artistId, uploadTime, views, likes, genre from Video where artistId = $1;"
	SelectVideosByArtist  = "select id, title, description, views, likes, uploadTime, genre from Video where artistId = $1;"
	SelectVideoComments   = "select message, user, time from Comment where videoId = $1"
	SelectVideosByGenre   = "select id, title, description, views, likes, uploadTime, artistId from Video where genre = $1;"
	SelectGenres          = "select name, description from Genre;"
	SelectUserAuth        = "select id, password from artist where username = $1;"
	SelectSession         = "select count(userId) from session where sessionkey = $1;"
	SelectAuthId          = "select userId from session where sessionKey = $1;"
	SelectArtistByZip     = "select id, name, username from artist where location::json->>'zip_code'::text = (select location::json->>'zip_code' from artist where id = $1)::text;"
	SelectArtistByCity    = "select id, name, username from artist where location::json->>'city'::text = (select location::json->>'city' from artist where id = $1)::text;"
    SelectVideoLoc        = "select artistId, title from video where id = $1;"
    SelectChatThread = "select sender, receiver, message, time from Comment where (sender = $1 or sender= $2) and (receiver = $1 or receiver = $2);"

	UpdateArtist = "update artist set username = $1, name = $2, description = $3, password = $4 where id = $5;"
    IncreaseViews = "update video set views = (select views from video where artistId = $1 and title = $2) + 1 where artistId = $1 and title = $2;"
)

type Authentication struct {
	Username string
	Password string
}

type Message struct {
    Artist   string
    Receiver string
    Message  string
    Time     string
}

type PostMessage struct {
    Artist   string
    Message  string
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
	Username string
}

type Video struct {
	Artist    BasicArtist
	Id        string
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
	Username      string
	Name          string
	Age           string
	Active        string
	Desc          string
	Date          string
	FollowerCount string
	LikeCount     string
	VideoList     []Video
}

type IntArtist struct {
	Username           string
	Name               string
	Followers          string
	Desc               string
	Date               string
	Active             string
	LikeCount          string
	Id                 string
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

func getUserId(sessionId string) string {
	var id string
	rows, err := db.Query(SelectAuthId, sessionId)
	logIfErr(err)

	rows.Next()
	err = rows.Scan(&id)
	logIfErr(err)
	rows.Close()

	return id
}

func artist(w http.ResponseWriter, r *http.Request) {
	var v Video
	var a ExtArtist

	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, _ := r.Cookie("SESSIONID")
	artistId := r.URL.Query().Get("artist")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

	rows, err := db.Query(SelectIntArtistData, artistId)
	checkErr(err)

	rows.Next()
	err = rows.Scan(&a.Username, &a.Name, &a.Desc, &a.Date, &a.Active, &a.FollowerCount, &a.LikeCount)
	logIfErr(err)
	defer rows.Close()

	videoRows, viderr := db.Query(SelectArtistVideos, artistId)
	logIfErr(viderr)
	defer videoRows.Close()

	for videoRows.Next() {
		err = videoRows.Scan(&v.Id, &v.File, &v.Title, &v.Desc, &artistId, &v.Time, &v.Views, &v.Likes, &v.Genre)
		logIfErr(err)

		a.VideoList = append(a.VideoList, v)
	}

	if err := json.NewEncoder(w).Encode(a); err != nil {
		logIfErr(err)
	}
}

func (data *IntArtist) SetVideoList(videos []Video) {
    data.VideoList = videos
}

func profile(w http.ResponseWriter, r *http.Request) {
	var a IntArtist
    var videos []Video

	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, _ := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

    artistId = getUserId(cookie.Value)
	a.Id = artistId

	rows, err := db.Query(SelectIntArtistData, artistId)
	checkErr(err)

	rows.Next()
	err = rows.Scan(&a.Username, &a.Name, &a.Followers, &a.Desc, &a.Date, &a.Active, &a.LikeCount)
	logIfErr(err)
	defer rows.Close()

	videoRows, viderr := db.Query(SelectArtistVideos, artistId)
	logIfErr(viderr)

	for videoRows.Next() {
	    var v Video
		err = videoRows.Scan(&v.Id, &v.Title, &v.Desc, &artistId, &v.Time, &v.Views, &v.Likes, &v.Genre)
		logIfErr(err)

		videos = append(videos, v)
	}
	videoRows.Close()

    a.SetVideoList(videos)

	err = json.NewEncoder(w).Encode(a)
	logServerErr(w, err)
}

func homePage(w http.ResponseWriter, r *http.Request) {
	var v Video
	var videos VideoList

	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, err := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

	artistId := getUserId(cookie.Value)

	rows, err := db.Query(SelectArtistVideos, artistId)
	checkErr(err)
	defer rows.Close()

	for rows.Next() {
		err = rows.Scan(&v.Id, &v.Title, &v.Desc, &artistId, &v.Time, &v.Views, &v.Likes, &v.Genre)
		logIfErr(err)

		var a BasicArtist
		artistRow, aErr := db.Query(SelectBasicArtistData, artistId)
		logIfErr(aErr)
		defer artistRow.Close()

		artistRow.Next()
		err = artistRow.Scan(&a.Id, &a.Name, &a.Username)
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
		err := rows.Scan(&g.Name, &g.Description)
		logIfErr(err)

		genres.GenreList = append(genres.GenreList, g)
	}

	err = json.NewEncoder(w).Encode(genres)
	logServerErr(w, err)
}

func genre(w http.ResponseWriter, r *http.Request) {
	var v Video
	var videos VideoList
	var artistId string

	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, err := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}
	genre := r.URL.Query().Get("genre")

	rows, err := db.Query(SelectVideosByGenre, genre)
	logIfErr(err)
	defer rows.Close()

	for rows.Next() {
		err = rows.Scan(&v.Id, &v.Title, &v.Desc, &v.Views, &v.Likes, &v.Time, &artistId)
		logServerErr(w, err)

		var a BasicArtist
		v.Genre = genre
		artistRow, err := db.Query(SelectBasicArtistData, artistId)
		logServerErr(w, err)
		defer artistRow.Close()
		rows.Next()

		err = artistRow.Scan(&a.Id, &a.Name, &a.Username)
		logServerErr(w, err)

		a.Id = artistId
		v.Artist = a

		videos.VideoCards = append(videos.VideoCards, v)
	}

	err = json.NewEncoder(w).Encode(videos)
	logServerErr(w, err)
}

func fileLoc(w http.ResponseWriter, id string) string {
    var artistId string
    var videoName string

	rows, err := db.Query(SelectVideoLoc, id)
    logIfErr(err)

    if rows.Next() {
        rows.Scan(&artistId, &videoName)
    } else {
        http.Error(w, "Video not found", http.StatusNotFound)
        return ""
    }

    return artistId + "/" + videoName
}

func video(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "*")
	videoId := r.URL.Query().Get("id")

    loc := fileLoc(w, videoId)
	filePath := "./data/videos/" + loc + ".mp4"
    _, err := os.Stat(filePath)

    if os.IsNotExist(err) {
		http.Error(w, "Video not found", http.StatusNotFound)
        return
    }

	rows, err := db.Query(IncreaseViews, videoId)
    rows.Close()

	http.ServeFile(w, r, filePath)
}

func avatar(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "*")
	artistId := r.URL.Query().Get("artist")

	filePath := "./data/avatars/" + artistId + "/avatar.jpeg"
    _, err := os.Stat(filePath)

    if os.IsNotExist(err) {
		http.Error(w, "Image not found", http.StatusNotFound)
    }

	http.ServeFile(w, r, filePath)
}

func thumbnail(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "*")
	videoId := r.URL.Query().Get("id")

    loc := fileLoc(w, videoId)
	filePath := "./data/thumbnails/" + loc + ".jpeg"
    _, err := os.Stat(filePath)

    if os.IsNotExist(err) {
		http.Error(w, "Image not found", http.StatusNotFound)
    }

	http.ServeFile(w, r, filePath)
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
	logServerErr(w, err)

	if authRows.Next() {
		err = authRows.Scan(&id, &hashPassword)
		logIfErr(err)
	} else {
		http.Error(w, "Unable to create user", http.StatusForbidden)
	}
	authRows.Close()

	os.Mkdir("./data/videos/"+id, os.ModePerm)
	os.Mkdir("./data/thumbnails/"+id, os.ModePerm)
	os.Mkdir("./data/avatars/"+id, os.ModePerm)
}

func editprofile(w http.ResponseWriter, r *http.Request) {
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

	rows, err := db.Query(UpdateArtist, data.Username, data.Name, hash, data.Bio)
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

	rows, err := db.Query(RemoveOldSessions)
    logIfErr(err)
    rows.Close()

	rows, err = db.Query(SelectUserAuth, auth.Username)
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
		cookie := http.Cookie{Name: "SESSIONID", Value: sessionId, Path: "/", Expires: exp, HttpOnly: true}
		http.SetCookie(w, &cookie)
	} else {
		http.Error(w, "Incorrect username or password", 401)
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
		exp := time.Unix(0, 0)
		cookie := http.Cookie{Name: "SESSIONID", Value: "", Expires: exp, HttpOnly: true}
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
	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, _ := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

	artist := getUserId(cookie.Value)

	file, _, err := r.FormFile("file")
	logServerErr(w, err)

	name := r.FormValue("name")
	desc := r.FormValue("desc")
	data, err := ioutil.ReadAll(file)
	logServerErr(w, err)

	filePath := fmt.Sprintf("./data/videos/%s/%s.mp4", artist, name)

	f, err := os.Create(filePath)
	logServerErr(w, err)

	_, err = f.Write(data)
	logServerErr(w, err)
	f.Close()

	getThumbnail(artist, name)

	rows, err := db.Query(AddVideo, artist, name, desc, filePath)
	logServerErr(w, err)
	rows.Close()
}

func addAvatar(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, _ := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

	artist := getUserId(cookie.Value)

	file, _, err := r.FormFile("file")
	logServerErr(w, err)

	data, err := ioutil.ReadAll(file)
	logServerErr(w, err)

	filePath := fmt.Sprintf("./data/avatars/%s/avatar.jpeg", artist)

	f, err := os.Create(filePath)
	logServerErr(w, err)

	_, err = f.Write(data)
	logServerErr(w, err)
	f.Close()
}

func searchByZipCode(w http.ResponseWriter, r *http.Request) {
	var a BasicArtist
	var artistList []BasicArtist

	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, _ := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

	artistId := getUserId(cookie.Value)
	rows, err := db.Query(SelectArtistByZip, artistId)
	checkErr(err)
	defer rows.Close()

	for rows.Next() {
		err = rows.Scan(&a.Id, &a.Name, &a.Username)
		logIfErr(err)

		artistList = append(artistList, a)
	}

	err = json.NewEncoder(w).Encode(artistList)
	logServerErr(w, err)
}

func searchByCity(w http.ResponseWriter, r *http.Request) {
	var a BasicArtist
	var artistList []BasicArtist

	w.Header().Set("Access-Control-Allow-Origin", "*")
	cookie, _ := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

	artistId := getUserId(cookie.Value)
	rows, err := db.Query(SelectArtistByCity, artistId)
	checkErr(err)
	defer rows.Close()

	for rows.Next() {
		err = rows.Scan(&a.Id, &a.Name, &a.Username)
		logIfErr(err)

		artistList = append(artistList, a)
	}

	err = json.NewEncoder(w).Encode(artistList)
	logServerErr(w, err)
}

func getMessages(w http.ResponseWriter, r *http.Request) {
    var messages []Message

	cookie, _ := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}
    artistId := getUserId(cookie.Value)
    receiver := r.URL.Query().Get("artist")

    rows, err := db.Query(SelectChatThread, artistId, receiver)
    logIfErr(err)
    defer rows.Close()

    for rows.Next() {
       var m Message
       err = rows.Scan(&m.Artist, &m.Receiver, &m.Message, &m.Time)
       logIfErr(err)
       messages = append(messages, m)
    }

	err = json.NewEncoder(w).Encode(messages)
	logServerErr(w, err)
}

func postMessages(w http.ResponseWriter, r *http.Request) {
    var message PostMessage
	cookie, _ := r.Cookie("SESSIONID")

	if !authenticate(cookie) {
		http.Error(w, "Authentication failed", http.StatusForbidden)
		return
	}

    artistId := getUserId(cookie.Value)
	decoder := json.NewDecoder(r.Body)
	decoder.Decode(&message)

    rows, err := db.Query(AddMessage, artistId, message.Artist, message.Message)
    logIfErr(err)
    rows.Close()
}
