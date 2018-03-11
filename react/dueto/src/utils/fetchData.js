function getSessionId() {
    let cookies = document.cookie
    let cookieParts = cookies.split("=")

    if(cookieParts[0] == "SESSIONID") {
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

export const getProfileData = async (name) => {
  const data = {
     username: name
  }
  const params = encodeUrl(data)
  const response = await fetch("/api/profile?" + params, { credentials: "include" })
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

export const getVideoUrl = (artistId, video) => {
  const data = {
     artist: artistId,
     name: video
  }
  const params = encodeUrl(data)

  return "/api/video?" + params
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

  const newData = await response.json()

  return newData
}

export const addVideo = async (formBody) => {
  const response = await fetch("/api/addvideo", {
    body: formBody,
    credentials: 'include',
    method: 'POST',
    headers: {Accept: 'application/json'}
  })

  const newData = await response.json()

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

  const newData = await response.json()

  return newData
}
