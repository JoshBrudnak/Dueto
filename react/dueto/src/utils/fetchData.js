//AIzaSyDKscT5t4lFXsQCS2GRoctWJw5aGjSHJ0k

export const getLocation = async () => {
  const response = await fetch('https://freegeoip.net/json/', {})
  const newData = await response.json()
 
  return newData 
}

export const eraseCookie = () => {   
    document.cookie = 'SESSIONID=; Max-Age=0;';  
}

export const getSessionId = () => {
    let cookies = document.cookie
    let cookieParts = cookies.split("=")

    if(cookieParts[0] === "SESSIONID") {
      return cookieParts[1]
    } 
    else {
      return ""
    }
}

function encodeUrl(data) {
  const formBody = Object.keys(data)
    .map(
      key =>
        encodeURIComponent(key) +
        '=' +
        encodeURIComponent(data[key])
    )
    .join('&')
  return formBody
}

function getArtistData(artistId, video, type) {
  const data = {
     artist: artistId,
     name: video
  }
  const params = encodeUrl(data)

  return "/api/" + type + "?" + params
}

export const getProfileData = async (name) => {
  const response = await fetch("/api/profile", { credentials: "include" })
  const newData = await response.json()

  return newData
}

export const getGenres = async () => {
  const response = await fetch("/api/discover", { credentials: "include" })
  const newData = await response.json()

  return newData
}

export const getGenreVideos = async (name) => {
  const data = {
     genre: name
  }
  const params = encodeUrl(data)
  const response = await fetch("/api/genre?" + params, { credentials: "include" })
  const newData = await response.json()

  return newData
}

export const getHomeData = async () => {
  const response = await fetch("/api/home", { credentials: "include" })
  const newData = await response.json()

  return newData
}

export const getDiscoverData = async () => {
  const response = await fetch("/api/discover", { credentials: "include" })
  const newData = await response.json()

  return newData
}

export const getArtistsByZip = async () => {
  const response = await fetch("/api/zipcode", { credentials: "include" })
  const newData = await response.json()

  return newData
}

export const getArtistsByCity = async () => {
  const response = await fetch("/api/city", { credentials: "include" })
  const newData = await response.json()

  return newData
}

export const getVideoUrl = (artistId, video) => {
  return getArtistData(artistId, video, "video")
}

export const getThumbnailUrl = (artistId, image) => {
  return getArtistData(artistId, image, "thumbnail")
}

export const getAvatarUrl = (artistId, image) => {
  return getArtistData(artistId, image, "avatar")
}

export const loginUser = async (username, password) => {
  const data = {
     Username: username, 
     Password: password 
  }

  let formBody = JSON.stringify(data)

  const response = await fetch("/api/login", {
    body: formBody,
    credentials: 'include',
    method: 'POST',
    headers: {Accept: 'application/json'}
  })

  const newData = await response

  return newData.status 
}

export const logoutUser = async () => {
  const response = await fetch("/api/logout", {
    credentials: 'include',
    method: 'POST',
    headers: {Accept: 'application/json'}
  })

  const newData = await response

  return newData 
}

export const addVideo = async (formBody) => {
  const response = await fetch("/api/addvideo", {
    body: formBody,
    credentials: 'include',
    method: 'POST',
    headers: {Accept: 'application/json'}
  })

  const newData = await response

  return newData
}

export const addAvatar = async (formBody) => {
  const response = await fetch("/api/changeavatar", {
    body: formBody,
    credentials: 'include',
    method: 'POST',
    headers: {Accept: 'application/json'}
  })

  const newData = await response

  return newData
}

export const updateUser = async (body) => {
  let formBody = JSON.stringify(body)

  const response = await fetch("/api/updateuser", {
	body: formBody,
	credentials: 'include',
	method: 'POST',
	headers: {Accept: 'application/json'}
  })

  const newData = await response

  return newData
}

export const addUser = async (body) => {
  let formBody = JSON.stringify(body)

  const response = await fetch("/api/createuser", {
	body: formBody,
	credentials: 'include',
	method: 'POST',
	headers: {Accept: 'application/json'}
  })

  const newData = await response

  return newData
}
