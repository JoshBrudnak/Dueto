import React, { Component } from 'react'
import {Avatar, Typography, Paper, IconButton} from 'material-ui'
import Header from '../component/Header.js'
import {AddCircle, Settings} from 'material-ui-icons'
import {Link} from 'react-router-dom'
import {getProfileData} from '../utils/fetchData.js'

class Profile extends Component {
  constructor() {
    super()
   
    this.state = {
      name: "",
      username: "",
      desc: "",
      id: undefined,
      likes: undefined,
      followers: undefined,
      avatarUrl: "/api/avatar?artist=0" 
    }
  }

  componentDidMount() {
    getProfileData()
      .then(data => {
        let avUrl = "/api/avatar?artist=" + data.Id

        this.setState({
          name: data.Name,
          username: data.UserName,
          desc: data.Desc,
          followers: data.FollowerCount,
          likes: data.LikeCount,
          avatarUrl: avUrl 
        })
      })
      .catch(error => {
        console.error(error)
      })
  }

  edit = () => {
    document.getElementById("edit").click()
  }

  addVideo = () => {
    document.getElementById("video").click()
  }

  render() {
    return (
      <div>
        <Header/>
        <div style={{display: "flex", flexDirection: "row", alignItems: "center"}}>
          <Paper style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
            <div style={{display: "flex", flexDirection: "row"}}> 
              <IconButton onClick={this.edit}>
                <Settings/>
              </IconButton>
              <Avatar src={this.state.avatarUrl} style={{width: 100, height: 100, marginBotton: 10}}/>
              <IconButton onClick={this.addVideo}>
                <AddCircle/>
              </IconButton>
            </div> 
            <Typography>{this.state.name}</Typography> 
            <Typography>{this.state.username}</Typography> 
          </Paper>
        </div>
        <Link id="edit" to="/editprofile" style={{visibility: "collapse"}}/>
        <Link id="video" to="/addvideo" style={{visibility: "collapse"}}/>
      </div>
    )
  }
}

export default Profile;
