export const getProfileData = async (url, name) => {
  const response = await fetch(url, { credentials: "include" })
  const newData = await response.json()

  return newData
}
