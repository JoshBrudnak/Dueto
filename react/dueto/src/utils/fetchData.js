export const getLocation =  async () => {
  const response = await fetch('http://ip-api.com/json', {credentials: "exclude"})
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

function getFileUrl(video, type) {
  const data = {
     id: video
  }
  const params = encodeUrl(data)

  return "/api/" + type + "?" + params
}

export const getProfileData = async (name) => {
  const response = await fetch("/api/profile", { credentials: "include" })
  const newData = await response.json()

  return newData
}

export const getArtistData = async (artistId) => {
  const data = {
     artist: artistId
  }
  const params = encodeUrl(data)

  const response = await fetch("/api/artist?" + params, { credentials: "include" })
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

export const getMessages = async (artistId) => {
  const data = {artist: artistId}

  const params = encodeUrl(data)
  const response = await fetch("/api/getmessages?" + params, { credentials: "include" })
  const newData = await response.json()
 
  return newData
}

export const getSharedVideos = async () => {
  const response = await fetch("/api/getsharedvideos", { credentials: "include" })
  const newData = await response.json()
 
  return newData
}

export const sendMessage = async (message, artist) => {
  const data = {
     Message: message,
     Artist: artist
  }
  const formBody = JSON.stringify(data)

  const response = await fetch("/api/postmessage", {
	body: formBody,
	credentials: 'include',
	method: 'POST',
	headers: {Accept: 'application/json'}
  })

  const newData = await response

  return newData
}

export const getVideoUrl = (videoId) => {
  return getFileUrl(videoId, "video")
}

export const getThumbnailUrl = (videoId) => {
  return getFileUrl(videoId, "thumbnail")
}

export const getAvatarUrl = (artistId) => {
  return "/api/avatar?artist=" + artistId 
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
  })

  const newData = await response

  return newData
}

export const shareVideo = async (id) => {
  const data = {
     video: id,
  }

  const formBody = JSON.stringify(data)

  const response = await fetch("/api/sharevideo", {
	body: formBody,
	credentials: 'include',
	method: 'POST',
  })

  const newData = await response

  return newData
}
